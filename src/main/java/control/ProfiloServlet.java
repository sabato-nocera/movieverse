package control;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import model.UtenteBean;
import org.bson.Document;
import utils.MongoDBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permette di visualizzare il profilo dell'utente.
 * Inoltre gestisce la possibilità, da parte dell'utente, di modificare le informazioni del proprio profilo.
 * Nel caso l'utente fosse un amministratore, recupera anche importanti statistiche relative alla piattaforma web.
 */
@WebServlet("/Profilo")
public class ProfiloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(ProfiloServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UtenteBean utenteLoggato = (UtenteBean) request.getSession().getAttribute("utente");
        if (utenteLoggato == null) {
            logger.log(Level.WARNING, "Utente non loggato");
            String url = response.encodeURL("Login");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        if(utenteLoggato.getAdmin()==true){
            MongoCollection users = MongoDBConnection.getDatabase().getCollection("users");

            // Costruzione della query per il genere degli utenti

            // Male
            BasicDBObject eqMale = new BasicDBObject("$eq", Arrays.asList("$gender", "male"));
            BasicDBObject condMale = new BasicDBObject("$cond", Arrays.asList(eqMale, 1, 0));

            // Female
            BasicDBObject eqFemale = new BasicDBObject("$eq", Arrays.asList("$gender", "female"));
            BasicDBObject condFemale = new BasicDBObject("$cond", Arrays.asList(eqFemale, 1, 0));

            // Other
            BasicDBObject eqOther = new BasicDBObject("$eq", Arrays.asList("$gender", "other"));
            BasicDBObject condOther = new BasicDBObject("$cond", Arrays.asList(eqOther, 1, 0));

            BasicDBObject singleProjects = new BasicDBObject();
            singleProjects.append("male", condMale);
            singleProjects.append("female", condFemale);
            singleProjects.append("other", condOther);

            BasicDBObject projectUsers = new BasicDBObject("$project", singleProjects);

            BasicDBObject groups = new BasicDBObject();
            groups.append("_id", null).append("male", new BasicDBObject("$sum", "$male")).
                    append("female", new BasicDBObject("$sum", "$female")).
                    append("other", new BasicDBObject("$sum", "$other")).
                    append("total", new BasicDBObject("$sum", 1));
            BasicDBObject groupUsers = new BasicDBObject("$group", groups);

            System.out.println(Arrays.asList(projectUsers, groupUsers));

            AggregateIterable<Document> iterable = users.aggregate(Arrays.asList(projectUsers, groupUsers));

            MongoCursor cursor = iterable.iterator();
            if(cursor.hasNext()){
                Document document = (Document) cursor.next();
                logger.log(Level.WARNING, "QUERY USER GENDER: "+document);
                int nMale = document.getInteger("male");
                int nFemale = document.getInteger("female");
                int nOthers = document.getInteger("other");
                int nTotal = document.getInteger("total");
                logger.log(Level.WARNING, "nMale: "+nMale);
                logger.log(Level.WARNING, "nFemale: "+nFemale);
                logger.log(Level.WARNING, "nOthers: "+nOthers);
                logger.log(Level.WARNING, "nWithoutGender: "+ (nTotal-nFemale-nMale-nOthers));

                request.setAttribute("numeroMaschi", nMale);
                request.setAttribute("numeroDonne", nFemale);
                request.setAttribute("numeroAltri", nOthers);
                request.setAttribute("numeroGenereNonEspresso", (nTotal-nFemale-nMale-nOthers));
            }
        }

        if(utenteLoggato.getAdmin()==true){
            MongoCollection movies = MongoDBConnection.getDatabase().getCollection("movies");

            // movies_coming_soon
            BasicDBObject eqMoviesComingSoon = new BasicDBObject("$eq", Arrays.asList("$catalog", "movies_coming_soon"));
            BasicDBObject condMoviesComingSoon = new BasicDBObject("$cond", Arrays.asList(eqMoviesComingSoon, 1, 0));

            // movies_in_theaters
            BasicDBObject eqMoviesInTheaters = new BasicDBObject("$eq", Arrays.asList("$catalog", "movies_in_theaters"));
            BasicDBObject condMoviesInTheaters = new BasicDBObject("$cond", Arrays.asList(eqMoviesInTheaters, 1, 0));

            // top_rated_movies
            BasicDBObject eqTopRatedMovies = new BasicDBObject("$eq", Arrays.asList("$catalog", "top_rated_movies"));
            BasicDBObject condTopRatedMovies = new BasicDBObject("$cond", Arrays.asList(eqTopRatedMovies, 1, 0));

            BasicDBObject singleProjects = new BasicDBObject();
            singleProjects.append("movies_coming_soon", condMoviesComingSoon);
            singleProjects.append("movies_in_theaters", condMoviesInTheaters);
            singleProjects.append("top_rated_movies", condTopRatedMovies);

            BasicDBObject projectMovies = new BasicDBObject("$project", singleProjects);

            BasicDBObject groups = new BasicDBObject();
            groups.append("_id", null).append("movies_coming_soon", new BasicDBObject("$sum", "$movies_coming_soon")).
                    append("movies_in_theaters", new BasicDBObject("$sum", "$movies_in_theaters")).
                    append("top_rated_movies", new BasicDBObject("$sum", "$top_rated_movies")).
                    append("total", new BasicDBObject("$sum", 1));
            BasicDBObject groupMovies = new BasicDBObject("$group", groups);

            System.out.println(Arrays.asList(projectMovies, groupMovies));

            AggregateIterable<Document> iterable = movies.aggregate(Arrays.asList(projectMovies, groupMovies));

            MongoCursor cursor = iterable.iterator();
            if(cursor.hasNext()){
                Document document = (Document) cursor.next();
                logger.log(Level.WARNING, "QUERY MOVIES CATALOG: "+document);
                int nMoviesComingSoon = document.getInteger("movies_coming_soon");
                int nMoviesInTheaters = document.getInteger("movies_in_theaters");
                int nTopRatedMovies = document.getInteger("top_rated_movies");
                int nTotal = document.getInteger("total");
                logger.log(Level.WARNING, "nMoviesComingSoon: "+nMoviesComingSoon);
                logger.log(Level.WARNING, "nMoviesInTheaters: "+nMoviesInTheaters);
                logger.log(Level.WARNING, "nTopRatedMovies: "+nTopRatedMovies);
                logger.log(Level.WARNING, "nWithoutCatalog: "+ (nTotal-nMoviesComingSoon-nMoviesInTheaters-nTopRatedMovies));

                request.setAttribute("numeroMoviesComingSoon", nMoviesComingSoon);
                request.setAttribute("numeroMoviesInTheaters", nMoviesInTheaters);
                request.setAttribute("numeroTopRatedMovies", nTopRatedMovies);
                request.setAttribute("numeroFilmNonInCatalogo", (nTotal-nMoviesComingSoon-nMoviesInTheaters-nTopRatedMovies));
            }
        }

        String url = response.encodeURL("WEB-INF/Profilo.jsp");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
