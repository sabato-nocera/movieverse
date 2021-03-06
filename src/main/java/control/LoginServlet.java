package control;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import model.UtenteBean;
import org.bson.Document;
import org.bson.types.ObjectId;
import utils.MongoDBConnection;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilizzata dall'utente per effettuare il login.
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Utilizzo del logger per la stampa di messaggi informativi
     * Ulteriori informazioni: https://www.journaldev.com/977/logger-in-java-logging-example
     */
    private final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("utente") != null) {
            logger.log(Level.WARNING, "Utente già loggato");
            String url = response.encodeURL("Catalogo");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        // Recupero i parametri dalla JSP
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.log(Level.WARNING, "Username: " + username + ", password:" + password);

        // Controllo che i parametri non siano null oppure equivalenti alla stringa vuota
        if (username != null && password != null && !username.equals("") && !password.equals("")) {
            logger.log(Level.WARNING, "Username e password non vuoti");

            // Ottengo il riferimento alla collezione che mi interessa
            MongoCollection mongoDatabase = MongoDBConnection.getDatabase().getCollection("users");

            // Creo un filtro perché devo ricercare un utente per un dato username ed una data password
            Document filter = new Document("username", username);
            filter.append("password", Utils.generatePwd(password));
            logger.log(Level.WARNING, "Filter: " + filter.toJson());

            // Interrogo il database attraverso la funzione find()
            // Ulteriori informazioni: https://www.codota.com/code/java/classes/com.mongodb.client.FindIterable
            FindIterable<Document> findIterable = mongoDatabase.find(filter);

            // Ottengo l'oggetto su cui iterare
            MongoCursor<Document> cursor = findIterable.iterator();

            // Verifico di aver ottenuto effettivamente qualcosa dalla query effettuata
            if (cursor.hasNext()) {
                logger.log(Level.WARNING, "Risultati ottenuti dalla query");

                // Ottengo il documento restituito dalla query
                Document document = cursor.next();
                // Istanzio un oggetto Gson per l'effettuazione di manipolazioni facili ed efficaci con JSON
                Gson gson = new Gson();

                // Creo l'oggetto UtenteBean utilizzando automaticamente il metodo fromJson(), passando come
                // parametri il Document (convertito in Json) e la classe target
                Date dateRetrieved = document.getDate("dateOfBirth");
                document.remove("dateOfBirth");
                UtenteBean utenteBean = gson.fromJson(document.toJson(), UtenteBean.class);
                utenteBean.setId(document.getObjectId("_id"));
                utenteBean.setDateOfBirth(dateRetrieved);
                utenteBean.setViewedMovies(document.getList("viewedMovies", ObjectId.class));
                utenteBean.setMoviesToSee(document.getList("moviesToSee", ObjectId.class));
                logger.log(Level.WARNING, "Risultato della query: " + utenteBean.toString());

                // Inserisco l'utente loggato in sessione
                request.getSession().setAttribute("utente", utenteBean);
                request.getSession().setAttribute("elenco", "1");

                String url = response.encodeURL("Catalogo");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            } else {
                logger.log(Level.WARNING, "Risultati NON ottenuti dalla query");
                request.setAttribute("errorMessage", "Try again");
            }
        } else {
            logger.log(Level.WARNING, "Username e password vuoti");
        }

        String url = response.encodeURL("WEB-INF/Login.jsp");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}