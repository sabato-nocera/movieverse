package control;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permette di aggiungere un film nella lista dei film da quadrare di un utente
 */
@WebServlet("/AddMovie")
public class AddMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(AddMovieServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("utente") == null || ((UtenteBean)request.getSession().getAttribute("utente")).getAdmin()==false ) {
            logger.log(Level.WARNING, "Utente non loggato");
            String url = response.encodeURL("Login");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        FilmBean film = new FilmBean();

        // Recupero i parametri
        String titolo = request.getParameter("title");
        film.setTitle(titolo);

        String poster = request.getParameter("poster");
        film.setPosterurl(poster);

        String duration = request.getParameter("duration");
        String originalTitle = request.getParameter("originalTitle");
        String story = request.getParameter("story");
        String catalogo = request.getParameter("catalog");
        String date = request.getParameter("releaseDate");
        String imbdRating = request.getParameter("imdbRating");
        String product = request.getParameter("product");

        String genres = request.getParameter("gen");
        String actors = request.getParameter("act");
        String dir = request.getParameter("director");
        String lan = request.getParameter("language");
        String cou = request.getParameter("country");

        film.setCatalog(catalogo);

        if (originalTitle != null && !originalTitle.equals("")) {
            film.setOriginalTitle(originalTitle);
        }
        if (story != null && !story.equals("")) {
            film.setStoryline(story);
        }
        if (duration != null && !duration.equals("")) {
            film.setDuration("PT" + duration + "M");
        }
        if (catalogo != null && !catalogo.equals("") && !catalogo.equals("Choose...")) {
            film.setCatalog(catalogo);
        }
        if (date != null && !date.equals("")) {
            String[] list = date.split("-");
            String year = list[0];
            String month = list[1];
            String day = list[2];
            Date dateOfRelased = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month), Integer.parseInt(day));
            film.setReleaseDate(dateOfRelased);
        }
        if (imbdRating != null && !imbdRating.equals("")) {
            film.setImdbRating(Double.parseDouble(imbdRating));
        } else {
            film.setImdbRating(0.0);
        }

        film.setAverageRating(0.0);

        //trasformo le stringhe in un array di stringhe da inserire nel DB
        String[] g = genres.split(",");
        List<String> generi = new ArrayList<>();
        String[] a = actors.split(",");
        List<String> attori = new ArrayList<>();
        String[] d = dir.split(",");
        List<String> director = new ArrayList<>();
        String[] l = lan.split(",");
        List<String> language = new ArrayList<>();
        String[] c = cou.split(",");
        List<String> country = new ArrayList<>();

        for (int i = 0; i < g.length; i++) {
            generi.add(g[i].trim());
        }
        for (int i = 0; i < a.length; i++) {
            attori.add(a[i].trim());
        }
        for (int i = 0; i < d.length; i++) {
            director.add(d[i].trim());
        }
        for (int i = 0; i < l.length; i++) {
            language.add(l[i].trim());
        }
        for (int i = 0; i < c.length; i++) {
            country.add(c[i].trim());
        }

        film.setGenres(generi);
        film.setActors(attori);
        film.setDirector(director);
        film.setLanguage(language);
        film.setCountry(country);

        // Controllo che non esista già il film
        Document filter = new Document("title", film.getTitle());
        Document document = new Document();
        Gson gson = new Gson();
        FindIterable<Document> findIterable = MongoDBConnection.getDatabase().getCollection("movies").find(filter);
        MongoCursor<Document> cursor = findIterable.iterator();
        if (!cursor.hasNext()) {
            // Converto l'oggetto UtenteBean in una stringa JSON: nella stringa JSON ci saranno solo i campi
            // per cui gli attributi in film non sono null
            String jsonString = gson.toJson(film);

            // Istanzio un oggetto mapper per convertire una stringa JSON in un oggetto Map
            ObjectMapper mapper = new ObjectMapper();
            try {
                // Conversione della stringa JSON in un oggetto Map
                Map<String, ?> map = mapper.readValue(jsonString, Map.class);
                // Inserisco tutti gli elementi di Map nel documento
                document.putAll(map);
                // Le date vanno inserite senza gson
                document.replace("releaseDate", film.getReleaseDate());
                // Effettuo l'inserimento effettivo nella collezione
                MongoDBConnection.getDatabase().getCollection("movies").insertOne(document);

                // Effettuo il login per recuperare l'utente appena salvato in MongoDB, in quanto MongoDB lo ha
                // inserito con un _id che potrebbe servirmi
                filter = new Document("title", film.getTitle());
                findIterable = MongoDBConnection.getDatabase().getCollection("movies").find(filter);
                cursor = findIterable.iterator();
                if (cursor.hasNext()) {
                    document = cursor.next();
                    Date dateRetrieved = document.getDate("releaseDate");
                    document.remove("releaseDate");
                    film = gson.fromJson(document.toJson(), FilmBean.class);
                    film.setId(document.getObjectId("_id"));
                    film.setReleaseDate(dateRetrieved);

                    //TODO: Deve reindirizzare alla pagina che visualizza i dettagli del film appena inserito
                    String url = response.encodeURL("Catalogo");
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Problema con l'inserimento di un nuovo utente nel database");
                e.printStackTrace();
            }
        }
        String url = response.encodeURL("Catalogo");
        request.getRequestDispatcher(url).forward(request, response);
        return;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
