package control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import model.UtenteBean;
import org.bson.Document;
import utils.MongoDBConnection;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilizzata per effettuare la registrazione di un utente.
 */
@WebServlet("/Registrazione")
public class RegistrazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(RegistrazioneServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("utente") != null) {
            String url = response.encodeURL("Catalogo");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        // Recupero i parametri
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String date = request.getParameter("dateOfBirth");
        String gender = request.getParameter("gender");

        // Controllo che i parametri fondamentali per la registrazione di un utente siano presenti
        if (email != null && username != null && password != null && passwordConfirm != null && !email.equals("") && !username.equals("") && !password.equals("") && !passwordConfirm.equals("")) {
            logger.log(Level.WARNING, "Sono stati passati i parametri fondamentali per la registrazione di un nuovo account");

            // Controllo che le password inserite siano uguali
            if (password.equals(passwordConfirm)) {

                // Creo un nuovo utente con gli attributi fondamentali
                UtenteBean utenteBean = new UtenteBean();
                utenteBean.setAdmin(false);
                utenteBean.setEmail(email);
                utenteBean.setPassword(Utils.generatePwd(password));
                utenteBean.setUsername(username);

                // Controllo l'eventuale presenta di ulteriori attributi per l'utente
                if (firstName != null && !firstName.equals("")) {
                    utenteBean.setFirstName(firstName);
                }
                if (lastName != null && !lastName.equals("")) {
                    utenteBean.setLastName(lastName);
                }
                if (date != null && !date.equals("")) {
                    String[] list = date.split("-");
                    String year = list[0];
                    String month = list[1];
                    String day = list[2];
                    Date dateOfBirth = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month), Integer.parseInt(day));
                    utenteBean.setDateOfBirth(dateOfBirth);
                }
                if (gender != null && !gender.equals("")) {
                    utenteBean.setGender(gender);
                }
                logger.log(Level.WARNING, "Utente creato: " + utenteBean.toString());

                // Controllo che non esista gi?? un utente con lo stesso username registrato
                Document filter = new Document("username", utenteBean.getUsername());
                Document document = new Document();
                Gson gson = new Gson();
                logger.log(Level.WARNING, "Filter: " + filter.toJson());
                FindIterable<Document> findIterable = MongoDBConnection.getDatabase().getCollection("users").find(filter);
                MongoCursor<Document> cursor = findIterable.iterator();
                if (!cursor.hasNext()) {
                    // Converto l'oggetto UtenteBean in una stringa JSON: nella stringa JSON ci saranno solo i campi
                    // per cui gli attributi in UtenteBean non sono null
                    String jsonString = gson.toJson(utenteBean);
                    logger.log(Level.WARNING, "Json: " + jsonString);

                    // Istanzio un oggetto mapper per convertire una stringa JSON in un oggetto Map
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        // Conversione della stringa JSON in un oggetto Map
                        Map<String, ?> map = mapper.readValue(jsonString, Map.class);
                        // Inserisco tutti gli elementi di Map nel documento
                        document.putAll(map);
                        // Le date vanno inserite senza gson
                        document.replace("dateOfBirth", utenteBean.getDateOfBirth());
                        // Effettuo l'inserimento effettivo nella collezione
                        logger.log(Level.WARNING, "Document: " + document.toJson());
                        MongoDBConnection.getDatabase().getCollection("users").insertOne(document);

                        // Effettuo il login per recuperare l'utente appena salvato in MongoDB, in quanto MongoDB lo ha
                        // inserito con un _id che potrebbe servirmi
                        filter = new Document("username", utenteBean.getUsername());
                        filter.append("password", utenteBean.getPassword());
                        logger.log(Level.WARNING, "Filter: " + filter.toJson());
                        findIterable = MongoDBConnection.getDatabase().getCollection("users").find(filter);
                        cursor = findIterable.iterator();
                        if (cursor.hasNext()) {
                            document = cursor.next();
                            Date dateRetrieved = document.getDate("dateOfBirth");
                            document.remove("dateOfBirth");
                            utenteBean = gson.fromJson(document.toJson(), UtenteBean.class);
                            utenteBean.setId(document.getObjectId("_id"));
                            utenteBean.setDateOfBirth(dateRetrieved);
                            request.getSession().setAttribute("utente", utenteBean);

                            String url = response.encodeURL("Catalogo");
                            request.getRequestDispatcher(url).forward(request, response);
                            return;
                        }
                    } catch (IOException e) {
                        logger.log(Level.WARNING, "Problema con l'inserimento di un nuovo utente nel database");
                        e.printStackTrace();
                    }
                } else {
                    logger.log(Level.WARNING, "Impossibile registrare utente " + utenteBean.getUsername() + " perch?? gi?? presente nel database");
                    request.setAttribute("errorMessage", "Username already exists");
                }
            } else {
                logger.log(Level.WARNING, "Le password inserite sono diverse: " + password + " , " + passwordConfirm);
                request.setAttribute("errorMessage", "Password missmatch");
            }
        } else {
            logger.log(Level.WARNING, "Non sono stati passati i parametri fondamentali per la registrazione di un nuovo account");
        }

        String url = response.encodeURL("WEB-INF/Registrazione.jsp");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}