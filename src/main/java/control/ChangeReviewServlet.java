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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permette di aggiungere un film nella lista dei film da quadrare di un utente
 */
@WebServlet("/ChangeReview")
public class ChangeReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(AddToWatchServelt.class.getName());

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
        FilmBean sessionfilm = (FilmBean) request.getSession().getAttribute("Film");
        UtenteBean user = (UtenteBean) request.getSession().getAttribute("utente");
        double vote = (Double.parseDouble(vt));
        logger.log(Level.WARNING, "I parametri presi sono: "+vote+" "+review);

        //creo una recensione come una mappa
        Document nuovaRecensione = new Document();
        nuovaRecensione.put("comment", review);
        nuovaRecensione.put("vote", vote);
        nuovaRecensione.put("userUsername", user.getUsername());
        logger.log(Level.WARNING, "La nuova recensione è : "+nuovaRecensione);

        FilmBean filmBean = new FilmBean();

        //Prendo il film da DB
        MongoCollection mongoDatabase = MongoDBConnection.getDatabase().getCollection("movies");
        Document filter = new Document("_id", sessionfilm.getId());
        FindIterable<Document> findIterable = mongoDatabase.find(filter);
        MongoCursor<Document> cursor = findIterable.iterator();
        if (cursor.hasNext()) {
            logger.log(Level.WARNING, "itero su "+cursor.toString());
            Document filmDocument = cursor.next();
            Gson gson = new Gson();
            ObjectId id = filmDocument.getObjectId("_id");
            filmDocument.remove("_id");
            //Trasformo il film in un FilmBean andando a settare correttamente data e id
            if(filmDocument.get("releaseDate")!=null){
                Date date = (Date) filmDocument.get("releaseDate");
                filmDocument.remove("releaseDate");
                filmBean = gson.fromJson(filmDocument.toJson(), FilmBean.class);
                filmBean.setReleaseDate(date);
            } else {
                filmBean = gson.fromJson(filmDocument.toJson(), FilmBean.class);
            }
            filmBean.setId(filmDocument.getObjectId("_id"));

            List<Document> recensioni = filmBean.getReviews();
            logger.log(Level.WARNING, "Le recensioni all'interno del DB sono "+recensioni.toString());

            double val=0;

            for (int i=0; i<recensioni.size(); i++){
                if(recensioni.get(i).getString("userUsername").equals(user.getUsername())){
                    logger.log(Level.WARNING, "Sostituisco la recensione "+recensioni.get(i).toString());
                    recensioni.remove(i);
                    recensioni.add(nuovaRecensione);
                    logger.log(Level.WARNING, "Ho aggiunto la recensione"+nuovaRecensione);
                }
                val=val+recensioni.get(i).getDouble("vote");
            }

            val=val/recensioni.size();

            //Creo un documento di modifica
                BasicDBObject documentUpdater = new BasicDBObject();
            //Effettuo le modifiche sul documento settando i parametri di FilmBean
            documentUpdater.put("reviews", recensioni);
            documentUpdater.put("averageRating", val);
            //Creo un oggetto di modifica e gli associo il documento di modifica creato prima
                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", documentUpdater);

                logger.log(Level.WARNING, "DOC UPDATER : " + documentUpdater.toString());
                logger.log(Level.WARNING, "OBJ UPDATER : " + updateObject.toString());
            //Inserisco l'oggetto di modifica nel DB andando a modificarlo al documento ricercato dal filtro
            MongoDBConnection.getDatabase().getCollection("movies").updateOne(filter, updateObject);

        }

        String url = response.encodeURL("Film?TitoloFilm=" + sessionfilm.getTitle());
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
