package control;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import model.FilmBean;
import model.UtenteBean;
import org.bson.Document;
import org.bson.types.ObjectId;
import utils.MongoDBConnection;

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
 * Permette di visualizzare le informazioni relative ad un film.
 * Inoltre, gestisce la possibilità da parte degli utenti di aggiungere il film alla lista di film guardati,
 * alla lista dei film che si vuole guardare, di aggiungere recensioni, di modificare recensioni e di eliminare il film
 * visualizzato.
 */
@WebServlet("/Film")
public class FilmServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(CatalogoServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UtenteBean utenteLoggato = (UtenteBean) request.getSession().getAttribute("utente");
        if (utenteLoggato == null) {
            logger.log(Level.WARNING, "Utente non loggato");
            String url = response.encodeURL("Login");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        String titoloFilm = request.getParameter("TitoloFilm");
        logger.log(Level.WARNING, "Film da visualizzare : " + titoloFilm);

        //Dato un filter vado ad ottenere il film e ad esso creo un FilmBean da passare alla JSP
        //Considerando che non so in che collezione ho quel film, vado a cercare in tutte e 4
        Gson gson = new Gson();
        FilmBean filmBean = new FilmBean();
        MongoCollection mongoDatabase = MongoDBConnection.getDatabase().getCollection("movies");
        org.bson.Document filter = new Document("title", titoloFilm);
        FindIterable<Document> findIterable = mongoDatabase.find(filter);
        MongoCursor<Document> cursor = findIterable.iterator();
        if (cursor.hasNext()) {
            Document document = cursor.next();
            Date date = (Date) document.get("releaseDate");
            document.remove("releaseDate");
            filmBean = gson.fromJson(document.toJson(), FilmBean.class);
            filmBean.setReleaseDate(date);
            filmBean.setId(document.getObjectId("_id"));
            filmBean.setReviews(document.getList("reviews", Document.class));

        }
        if (request.getSession().getAttribute("Film") != null) {
            logger.log(Level.WARNING, "in sessione si trova : " + filmBean.toString());
            request.getSession().removeAttribute("Film");
        }
        request.getSession().setAttribute("Film", filmBean);
        logger.log(Level.WARNING, "ho aggiunto in sessione : " + filmBean.toString());
        request.setAttribute("Film", filmBean);
        String url = response.encodeURL("WEB-INF/Film.jsp");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
