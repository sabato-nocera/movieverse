package control;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import model.FilmBean;
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
        String titoloFilm = request.getParameter("TitoloFilm");

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
            document.remove("_id");
            filmBean = gson.fromJson(document.toJson(), FilmBean.class);
            filmBean.setReleaseDate(date);
            filmBean.setId(document.getObjectId("_id"));

        }

        request.setAttribute("Film", filmBean);
        String url = response.encodeURL("WEB-INF/Film.jsp");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
