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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permette di aggiungere un film nella lista dei film da quadrare di un utente
 */
@WebServlet("/ChangeMovie")
public class ChangeMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(AddToWatchServelt.class.getName());

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
            if(userDocument.get("moviesToSee")!=null){
                utenteBean.setMoviesToSee((List<ObjectId>) userDocument.get("moviesToSee"));
            }

            mongoDatabase = MongoDBConnection.getDatabase().getCollection("movies");
            filter = new Document("title", titolo);
            findIterable = mongoDatabase.find(filter);
            cursor = findIterable.iterator();
            if (cursor.hasNext()) {
                Document filmDocument = cursor.next();
                Date date = (Date) filmDocument.get("releaseDate");
                ObjectId id = filmDocument.getObjectId("_id");

                if(utenteBean.getMoviesToSee()!=null){
                    if(utenteBean.getMoviesToSee().contains(id)){
                        logger.log(Level.WARNING, "Non puoi aggiungere nuovamente uno stesso film alla lista dei film da guardare : " + filmDocument.get("title"));
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
                utenteBean.addMovieToSee(id);

                mongoDatabase = MongoDBConnection.getDatabase().getCollection("users");
                filter = new Document("username", user.getUsername());

                BasicDBObject documentUpdater = new BasicDBObject();

                documentUpdater.put("moviesToSee", utenteBean.getMoviesToSee());

                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", documentUpdater);

                logger.log(Level.WARNING, "DOC UPDATER : " + documentUpdater.toString());
                logger.log(Level.WARNING, "OBJ UPDATER : " + updateObject.toString());

                MongoDBConnection.getDatabase().getCollection("users").updateOne(filter, updateObject);
            }
        }

        String url = response.encodeURL("Film?TitoloFilm="+titolo);
        request.getRequestDispatcher(url).forward(request, response);
        // TODO: Agigungere i controlli che un film che è stato aggiunto alla watchlist non vi venga aggiunto nuovamente né compaia il pulsante che può aggiungerlo
        // TODO: Non puoi aggiungere alla watched list dei film che ancora devono uscire
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
