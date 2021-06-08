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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permette di aggiungere un film nella lista dei film guardati da un utente
 */
@WebServlet("/AddWatched")
public class AddWatchedServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(AddWatchedServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("utente") == null) {
            logger.log(Level.WARNING, "Utente non loggato");
            String url = response.encodeURL("Login");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        String titolo = request.getParameter("TitoloFilm");

        if(titolo==null || titolo.equals("")){
            String url = response.encodeURL("Catalogo");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        UtenteBean user= (UtenteBean) request.getSession().getAttribute("utente");

        logger.log(Level.WARNING, "L'utente loggato è "+user.getUsername());

        MongoCollection mongoDatabase = MongoDBConnection.getDatabase().getCollection("users");
        Document filter = new Document("username", user.getUsername());
        FindIterable<Document> findIterable = mongoDatabase.find(filter);
        MongoCursor<Document> cursor = findIterable.iterator();
        if (cursor.hasNext()) {
            Document userDocument = cursor.next();

            Gson gson = new Gson();
            UtenteBean utenteBean;
            if(userDocument.get("dateOfBirth")!=null){
                Date date = (Date) userDocument.get("dateOfBirth");
                userDocument.remove("dateOfBirth");
                utenteBean = gson.fromJson(userDocument.toJson(), UtenteBean.class);
                utenteBean.setDateOfBirth(date);
            } else {
                utenteBean = gson.fromJson(userDocument.toJson(), UtenteBean.class);
            }
            utenteBean.setId(userDocument.getObjectId("_id"));
            if(userDocument.get("viewedMovies")!=null){
                utenteBean.setViewedMovies((List<ObjectId>) userDocument.get("viewedMovies"));
            }

            mongoDatabase = MongoDBConnection.getDatabase().getCollection("movies");
            filter = new Document("title", titolo);
            findIterable = mongoDatabase.find(filter);
            cursor = findIterable.iterator();
            if (cursor.hasNext()) {
                Document filmDocument = cursor.next();
                Date date = (Date) filmDocument.get("releaseDate");
                ObjectId id = filmDocument.getObjectId("_id");

                if(utenteBean.getViewedMovies()!=null){
                    if(utenteBean.getViewedMovies().contains(id)){
                        logger.log(Level.WARNING, "Non puoi aggiungere nuovamente uno stesso film alla lista dei film visti : " + filmDocument.get("title"));
                        String url = response.encodeURL("Catalogo");
                        request.getRequestDispatcher(url).forward(request, response);
                        return;
                    }
                }

                filmDocument.remove("releaseDate");
                filmDocument.remove("_id");
                FilmBean filmBean = gson.fromJson(filmDocument.toJson(), FilmBean.class);
                filmBean.setReleaseDate(date);
                filmBean.setId(filmDocument.getObjectId("_id"));
                utenteBean.addViewedMovie(id);

                mongoDatabase = MongoDBConnection.getDatabase().getCollection("users");
                filter = new Document("username", user.getUsername());

                BasicDBObject documentUpdater = new BasicDBObject();

                documentUpdater.put("viewedMovies", utenteBean.getViewedMovies());

                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", documentUpdater);

                logger.log(Level.WARNING, "DOC UPDATER : " + documentUpdater.toString());
                logger.log(Level.WARNING, "OBJ UPDATER : " + updateObject.toString());

                MongoDBConnection.getDatabase().getCollection("users").updateOne(filter, updateObject);
                user.addViewedMovie(id);
            }
        }

        String url = response.encodeURL("Film?TitoloFilm="+titolo);
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
