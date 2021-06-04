package control;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
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
 * Permette di aggiungere un film nella lista dei film da quadrare di un utente
 */
@WebServlet("/AddToWatch")
public class AddToWatchServelt extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(CatalogoServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titolo = request.getParameter("TitoloFilm");
        UtenteBean user= (UtenteBean) request.getSession().getAttribute("utente");
        String username = user.getUsername();
        logger.log(Level.WARNING, " l'utente loggato è "+username);
        //TODO: Vedere perchè da problemi!
        MongoCollection mongoDatabase = MongoDBConnection.getDatabase().getCollection("users");
        org.bson.Document filter = new Document("username", user);
        FindIterable<Document> findIterable = mongoDatabase.find(filter);
        MongoCursor<Document> cursor = findIterable.iterator();
        if (cursor.hasNext()) {
            Document document = cursor.next();
            Document userDocument = document;
            Gson gson = new Gson();
            UtenteBean utenteBean = gson.fromJson(document.toJson(), UtenteBean.class);

            mongoDatabase = MongoDBConnection.getDatabase().getCollection("top_rated_movies");
            filter = new Document("title", titolo);
            findIterable = mongoDatabase.find(filter);
            cursor = findIterable.iterator();
            if (cursor.hasNext()) {
                document = cursor.next();
                Date date = (Date) document.get("releaseDate");
                ObjectId id = (ObjectId) document.get("_id");
                document.remove("releaseDate");
                document.remove("_id");
                FilmBean filmBean = gson.fromJson(document.toJson(), FilmBean.class);
                filmBean.setReleaseDate(date);
                filmBean.setId(id);
                utenteBean.addMovieToSee(filmBean);

                mongoDatabase = MongoDBConnection.getDatabase().getCollection("users");
                filter = new Document("username", username);

                BasicDBObject documentUpdater = new BasicDBObject();

                documentUpdater.put("moviesToSee", utenteBean.getMoviesToSee());

                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", documentUpdater);

                    System.out.println("DOC UPDATER : " + documentUpdater.toString());
                    System.out.println("OBJ UPDATER : " + updateObject.toString());

                    MongoDBConnection.getDatabase().getCollection("users").updateOne(filter, updateObject);

                }
            }



        String url = response.encodeURL("WEB-INF/Film.jsp");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
