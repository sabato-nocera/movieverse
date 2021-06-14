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
@WebServlet("/ChangeProfile")
public class ChangeProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(AddToWatchServelt.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("utente") == null) {
            logger.log(Level.WARNING, "Utente non loggato");
            String url = response.encodeURL("Login");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

            HttpSession session = request.getSession();

        //Prendo tutti i parametri dalla JSP
        FilmBean sessionfilm = (FilmBean) session.getAttribute("Film");
        String titolo = request.getParameter("title");
        String poster = request.getParameter("poster");
        String duration = request.getParameter("duration");
        String originalTitle = request.getParameter("originalTitle");
        String story = request.getParameter("Story");
        String catalogo = request.getParameter("catalog");
        String data = request.getParameter("dateRelased");
        String imbdRating = request.getParameter("imdbRating");
        String genres = request.getParameter("gen");
        String actors = request.getParameter("act");
        String[] g = genres.split(",");
        List<String> generi = new ArrayList<>();
        String[] a = actors.split(",");
        List<String> attori = new ArrayList<>();
        for (int i = 0; i < g.length; i++) {
            generi.add(g[i].trim());
        }
        for (int i = 0; i < a.length; i++) {
            attori.add(a[i].trim());
        }

        logger.log(Level.WARNING, "L'ID preso dalla sessione è : "+sessionfilm.toString());

        if(titolo==null || titolo.equals("")){
            String url = response.encodeURL("Catalogo");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        UtenteBean user= (UtenteBean) request.getSession().getAttribute("utente");

        logger.log(Level.WARNING, "L'utente loggato è "+user.getUsername());

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

            //Vado a modificare tutti i campi di FilmBean andando a mettere quelli presi dalla JSP
            //ho fatto in modo che i campi non risultassero MAI vuoti, ma che ogni campo contenesse già le informazioni presenti nel DB
            filmBean.setTitle(titolo);
            filmBean.setPosterurl(poster);
            filmBean.setActors(attori);
            filmBean.setGenres(generi);
            if (originalTitle != null && !originalTitle.equals("")) {
                filmBean.setOriginalTitle(originalTitle);
            }
            if (story != null && !story.equals("")) {
                filmBean.setStoryline(story);
            }
            if (duration != null && !duration.equals("")) {
                filmBean.setDuration("PT" + duration + "M");
            }
            if (catalogo != null && !catalogo.equals("") && !catalogo.equals("Choose...")) {
                filmBean.setCatalog(catalogo);
            }
            if (data != null && !data.equals("")) {
                String[] list = data.split("-");
                String year = list[0];
                String month = list[1];
                String day = list[2];
                Date dateOfRelased = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month), Integer.parseInt(day));
                filmBean.setReleaseDate(dateOfRelased);
            }
            if (imbdRating != null && !imbdRating.equals("")) {
                filmBean.setImdbRating(Double.parseDouble(imbdRating));
            } else {
                filmBean.setImdbRating(0.0);
            }

            //Creo un documento di modifica
                BasicDBObject documentUpdater = new BasicDBObject();
            //Effettuo le modifiche sul documento settando i parametri di FilmBean
            documentUpdater.put("title", filmBean.getTitle());
            documentUpdater.put("genres", filmBean.getGenres());
            documentUpdater.put("duration", filmBean.getDuration());
            documentUpdater.put("releaseDate", filmBean.getReleaseDate());
            documentUpdater.put("averageRating", filmBean.getAverageRating());
            documentUpdater.put("storyline", filmBean.getStoryline());
            documentUpdater.put("actors", filmBean.getActors());
            documentUpdater.put("imdbRating", filmBean.getImdbRating());
            documentUpdater.put("posterurl", filmBean.getPosterurl());
            documentUpdater.put("catalog", filmBean.getCatalog());
            documentUpdater.put("originalTitle",filmBean.getOriginalTitle());

            //Creo un oggetto di modifica e gli associo il documento di modifica creato prima
                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", documentUpdater);

                logger.log(Level.WARNING, "DOC UPDATER : " + documentUpdater.toString());
                logger.log(Level.WARNING, "OBJ UPDATER : " + updateObject.toString());
            //Inserisco l'oggetto di modifica nel DB andando a modificarlo al documento ricercato dal filtro
            MongoDBConnection.getDatabase().getCollection("movies").updateOne(filter, updateObject);

        }

        String url = response.encodeURL("Catalogo");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
