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
import java.util.Map;

/**
 * Permette di aggiungere un film nella lista dei film da quadrare di un utente
 */
@WebServlet("/AddRecensione")
public class AddRecensioneServelt extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(AddRecensioneServelt.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("utente") == null) {
            logger.log(Level.WARNING, "Utente non loggato");
            String url = response.encodeURL("Login");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }
        //prendo i parametri
        String vt = request.getParameter("vote");
        String review = request.getParameter("userReview");
        FilmBean film = (FilmBean) request.getSession().getAttribute("Film");
        UtenteBean user = (UtenteBean) request.getSession().getAttribute("utente");
        double vote = (Double.parseDouble(vt));

        //creo una recensione come una mappa
        Document nuovaRecensione = new Document();
        nuovaRecensione.put("comment", review);
        nuovaRecensione.put("vote", vote);
        nuovaRecensione.put("userId", user.getId());

        logger.log(Level.WARNING, "L'utente loggato è " + user.getUsername());

        MongoCollection mongoDatabase = MongoDBConnection.getDatabase().getCollection("movies");
        Document filter = new Document("_id", film.getId());
        FindIterable<Document> findIterable = mongoDatabase.find(filter);
        MongoCursor<Document> cursor = findIterable.iterator();
        if (cursor.hasNext()) {
            Gson gson = new Gson();
            Document filmDocument = cursor.next();
            Date date = (Date) filmDocument.get("releaseDate");
            ObjectId id = filmDocument.getObjectId("_id");
            filmDocument.remove("releaseDate");
            filmDocument.remove("_id");
            FilmBean filmBean = gson.fromJson(filmDocument.toJson(), FilmBean.class);
            filmBean.setReleaseDate(date);
            filmBean.setId(filmDocument.getObjectId("_id"));
            filmBean.setReviews(filmDocument.getList("reviews", Document.class));
            logger.log(Level.WARNING, "Le recensioni del Film Sono : " + filmBean.getReviews());
            logger.log(Level.WARNING, "Voglio aggiungere : " + nuovaRecensione);

            if(filmBean.getReviews()!=null){
                for(Document m : filmBean.getReviews()) {
                    logger.log(Level.WARNING, "UserId della recensione : " + m.get("userId"));
                    logger.log(Level.WARNING, "Id utente loggato : " + user.getId());
                    logger.log(Level.WARNING, "Id utente loggato == UserId della recensione ?  " + m.get("userId").equals(user.getId()));
                    if(m.getObjectId("userId").equals(user.getId())){
                        // Stai provando ad inserire una recensione per un film per cui hai già inserito una recensione
                        String url = response.encodeURL("Film?TitoloFilm=" + film.getTitle());
                        request.getRequestDispatcher(url).forward(request, response);
                        return ;
                    }
                }
            }

            // Se non c'è l'array di recensione, bisogna istanziarlo
            if(filmBean.getReviews()==null){
                List<Document> list = new ArrayList<>();
                list.add(nuovaRecensione);
                filmBean.setReviews(list);
            } else {
                filmBean.addRecensione(nuovaRecensione);
            }

            BasicDBObject documentUpdater = new BasicDBObject();

            documentUpdater.put("reviews", filmBean.getReviews());
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", documentUpdater);
            logger.log(Level.WARNING, "DOC UPDATER : " + documentUpdater.toString());
            logger.log(Level.WARNING, "OBJ UPDATER : " + updateObject.toString());

            MongoDBConnection.getDatabase().getCollection("movies").updateOne(filter, updateObject);
        }

        String url = response.encodeURL("Film?TitoloFilm=" + film.getTitle());
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
