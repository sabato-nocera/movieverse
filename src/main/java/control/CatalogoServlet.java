package control;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import model.FilmBean;
import model.UtenteBean;
import org.bson.Document;
import org.bson.conversions.Bson;
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
 * Permette di visualizzare cataloghi di film a seconda della tipologia, nello specifico permette di visualizzare
 * la lista di tutti i film "in sala", la lista dei film migliori, la lista dei film che prossimamente usciranno,
 * la lista di tutti i film presenti nel database, la lista dei film che si ha intenzione di guardare, la lista dei film
 * guardati e, infine, la lista dei film ricercati con la barra di ricerca.
 * Inoltre, è responsabile dell'ordinametno e del filtraggio dei film in base ad un particolare criterio.
 */
@WebServlet("/Catalogo")
public class CatalogoServlet extends HttpServlet {
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

        //Verifico se l'utente ha scelto uno specifico catalogo da visualizzare andandone a prendere il valore
        String el = request.getParameter("elenco");

        logger.log(Level.WARNING, "Elenco: " + el);

        int elenco;
        //Controllo se il valore è nullo, e in quel caso gli assegno 1;
        if (el == null) {
            el = "1";
        }
        //Trasformo in intero il valore preso;
        elenco = (Integer.parseInt(el));
        logger.log(Level.WARNING, "Elenco: " + elenco + ", versione stringa: " + el);

        ArrayList<FilmBean> movie = new ArrayList<>();

        Bson filter;

        // Creo un filter che mi aiuta nel caso dovessi filtrare i risultati in accordo ad un particolare catalogo
        switch (elenco) {
            case 1:
                filter = new Document("catalog", "movies_in_theaters");
                request.setAttribute("elenco", "" + 1);
                break;
            case 2:
                filter = new Document("catalog", "movies_coming_soon");
                request.setAttribute("elenco", "" + 2);
                break;
            case 3:
                filter = new Document("catalog", "top_rated_movies");
                request.setAttribute("elenco", "" + 3);
                break;
            case 4:
                filter = new Document();
                request.setAttribute("elenco", "" + 4);
                break;
            case 5:
                filter = new Document();
                request.setAttribute("elenco", "" + 5);
                break;
            case 6:
                filter = new Document();
                request.setAttribute("elenco", "" + 6);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + elenco);
        }

        // Filtrare/ordinare i risultati di un catalogo
        String genreSearched = request.getParameter("genreSearched");
        String actorSearched = request.getParameter("actorSearched");
        String order = request.getParameter("order");

        BasicDBObject sorter = new BasicDBObject();

        if (genreSearched != null && !genreSearched.equals("")) {
            // Filtra in base al genere
            Bson resultFilter = Filters.in("genres", genreSearched);
            // Faccio un AND dei criteri che servono per effettuare la ricerca
            filter = Filters.and(filter, resultFilter);
        } else if (actorSearched != null && !actorSearched.equals("")) {
            // Filtra in base all'attore
            Bson resultFilter = Filters.in("actors", actorSearched);
            // Faccio un AND dei criteri che servono per effettuare la ricerca
            filter = Filters.and(filter, resultFilter);
        } else if (order != null) {
            // Effettua l'ordinamento in base all'order passato
            if (order.equals("title")) {
                // Ordinamento crescente (lessicografico)
                sorter = new BasicDBObject(order, 1);
                // Ordinamento decrescente
            } else if (order.equals("releaseDate") || order.equals("averageRating") || order.equals("imdbRating")) {
                logger.log(Level.WARNING, "order : "+order);
                sorter = new BasicDBObject(order, -1);
            }
        }

        if (elenco == 1 || elenco == 2 || elenco == 3) {
            //trasformo la collezione in un oggetto iterabile
            FindIterable<Document> collection = MongoDBConnection.getDatabase().getCollection("movies").find(filter).sort(sorter);

            //Ho ottenuto la collezione, la trasformo in un ArrayList<FilmBean> in modo da passarlo al catalogo

            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                org.bson.Document document = (org.bson.Document) iterator.next();

                // Istanzio un oggetto Gson per l'effettuazione di manipolazioni facili ed efficaci con JSON
                Gson gson = new Gson();

                // Memorizzo a parte la data di rilascio del film e la tolgo dal documento,
                // in quanto dà problemi con gson
                Date date = (Date) document.get("releaseDate");
                document.remove("releaseDate");

                // logger.log(Level.WARNING, "JSON : "+document.toJson()+" FINE \n");

                // Creo l'oggetto FilmBean utilizzando automaticamente il metodo fromJson(), passando come
                // parametri il Document (convertito in Json) e la classe target e l'aggiungo all'ArrayList
                FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
                // Setto la data giusta
                film.setReleaseDate(date);
                film.setId(document.getObjectId("_id"));

                //logger.log(Level.WARNING, "JSON1 : "+film.toString()+" FINE \n");

                movie.add(film);
            }
        } else if (elenco == 4) {
            // Per effettuare la ricerca filtrata, mi baso su una regex che sarà vuota nel caso in cui
            // arrivo qui senza aver deciso di effettuare una ricerca filtrata per nome,
            // altrimenti la regex avrà del contenuto che andrà a filtrare i risultati
            BasicDBObject regexQuery = new BasicDBObject();
            String titleSearched = request.getParameter("titleSearched");
            if (titleSearched != null && !titleSearched.equals("")) {
                logger.log(Level.WARNING, "Ricerca filtrata");
                regexQuery.put("title", new BasicDBObject("$regex", ".*" + titleSearched + ".*").append("$options", "i"));
                // Faccio un AND dei criteri che servono per effettuare la ricerca
                filter = Filters.and(filter, regexQuery);
            }

            FindIterable<Document> collection = MongoDBConnection.getDatabase().getCollection("movies").find(filter).sort(sorter);
            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                org.bson.Document document = (org.bson.Document) iterator.next();
                Gson gson = new Gson();
                Date date = (Date) document.get("releaseDate");
                document.remove("releaseDate");
                FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
                film.setReleaseDate(date);
                film.setId(document.getObjectId("_id"));
                movie.add(film);
                logger.log(Level.WARNING, "Il film " + film.getTitle() + " si trova nel catalogo :" + film.getCatalog());
            }
        } else if (elenco == 5) {
            FindIterable<Document> collection = MongoDBConnection.getDatabase().getCollection("users").find(new Document("username", utenteLoggato.getUsername()));
            Iterator iterator = collection.iterator();

            if (iterator.hasNext()) {
                Document document = (Document) iterator.next();
                Gson gson = new Gson();
                Date dateRetrieved = document.getDate("dateOfBirth");
                document.remove("dateOfBirth");
                UtenteBean utenteBean = gson.fromJson(document.toJson(), UtenteBean.class);
                utenteBean.setId(document.getObjectId("_id"));
                utenteBean.setDateOfBirth(dateRetrieved);

                List<ObjectId> watchedMoviesIds = document.getList("viewedMovies", ObjectId.class);
                if(watchedMoviesIds!=null){
                    logger.log(Level.WARNING, "Watched movies Ids: " + watchedMoviesIds.toString());
                    BasicDBObject basicDBObject = new BasicDBObject();
                    // Questa query mi serve a recupera da "movies" tutti e soli i film i cui id sono quelli dei film guardati
                    basicDBObject.put("_id", new BasicDBObject("$in", watchedMoviesIds));

                    filter = Filters.and(filter, basicDBObject);

                    collection = MongoDBConnection.getDatabase().getCollection("movies").find(filter).sort(sorter);
                    iterator = collection.iterator();
                    while (iterator.hasNext()) {
                        document = (org.bson.Document) iterator.next();
                        Date date = (Date) document.get("releaseDate");
                        document.remove("releaseDate");
                        FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
                        film.setReleaseDate(date);
                        film.setId(document.getObjectId("_id"));
                        logger.log(Level.WARNING, "Film: " + film.getTitle());
                        movie.add(film);
                    }
                } else {
                    logger.log(Level.WARNING, "Watched movies Ids è null");
                }
            }
        } else if (elenco == 6) {
            FindIterable<Document> collection = MongoDBConnection.getDatabase().getCollection("users").find(new Document("username", utenteLoggato.getUsername()));
            Iterator iterator = collection.iterator();

            if (iterator.hasNext()) {
                Document document = (Document) iterator.next();
                Gson gson = new Gson();
                Date dateRetrieved = document.getDate("dateOfBirth");
                document.remove("dateOfBirth");
                UtenteBean utenteBean = gson.fromJson(document.toJson(), UtenteBean.class);
                utenteBean.setId(document.getObjectId("_id"));
                utenteBean.setDateOfBirth(dateRetrieved);

                List<ObjectId> moviesToSeeIds = document.getList("moviesToSee", ObjectId.class);
                if(moviesToSeeIds!=null){
                    logger.log(Level.WARNING, "Watched movies Ids: " + moviesToSeeIds.toString());
                    BasicDBObject basicDBObject = new BasicDBObject();
                    // Questa query mi serve a recupera da "movies" tutti e soli i film i cui id sono quelli dei film guardati
                    basicDBObject.put("_id", new BasicDBObject("$in", moviesToSeeIds));

                    filter = Filters.and(filter, basicDBObject);

                    collection = MongoDBConnection.getDatabase().getCollection("movies").find(filter).sort(sorter);
                    iterator = collection.iterator();
                    while (iterator.hasNext()) {
                        document = (org.bson.Document) iterator.next();
                        Date date = (Date) document.get("releaseDate");
                        document.remove("releaseDate");
                        FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
                        film.setReleaseDate(date);
                        film.setId(document.getObjectId("_id"));
                        logger.log(Level.WARNING, "Film: " + film.getTitle());
                        movie.add(film);
                    }
                } else {
                    logger.log(Level.WARNING, "Watched movies Ids è null");
                }
            }
        }

        request.setAttribute("movie", movie);
        String url = response.encodeURL("WEB-INF/Catalogo.jsp");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
