package control;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import model.FilmBean;
import model.UtenteBean;
import utils.MongoDBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
    private final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("utente")==null){
            logger.log(Level.WARNING, "Utente non loggato");
            String url = response.encodeURL("Login");
            request.getRequestDispatcher(url).forward(request, response);
            return ;
        }
    //Verifico se l'utente ha scelto uno specifico catalogo da visualizzare andandone a prendere il valore
        String el = request.getParameter("elenco");
        int elenco;
        //Controllo se il valore è nullo, e in quel caso gli assegno 1;
        if( el==null) {
            el = "1";
        }
        //Trasformo in intero il valore preso;
        elenco = (Integer.parseInt(el));
        logger.log(Level.WARNING, "Elenco: " + elenco + ", versione stringa: " + el);

        //effettuo uno switch, così da ottenere la collezione desiderata;
        MongoCollection mongoDatabase = null;
        boolean all = false;
        switch (elenco){
            case 1: mongoDatabase = MongoDBConnection.getDatabase().getCollection("movies_in_theaters");
                break;
            case 2: mongoDatabase = MongoDBConnection.getDatabase().getCollection("movies_coming_soon");
                break;
            case 3: mongoDatabase = MongoDBConnection.getDatabase().getCollection("top_rated_movies");
                break;
            case 4: all=true;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + elenco);
        }

        ArrayList<FilmBean> movie = new ArrayList<FilmBean>();

        if(!all){
            //trasformo la collezione in un oggetto iterabile
            FindIterable<Document> collection = mongoDatabase.find();

            //Ho ottenuto la collezione, la trasformo in un ArrayList<FilmBean> in modo da passarlo al catalogo

            Iterator iterator = collection.iterator();
            while(iterator.hasNext()){
                org.bson.Document document = (org.bson.Document) iterator.next();
                // Istanzio un oggetto Gson per l'effettuazione di manipolazioni facili ed efficaci con JSON
                Gson gson = new Gson();
                // TODO: 03/06/21 Aggiustare le date convertendole da intero a data (formato attuale: "releaseDate": {"$date": 1518566400000}) 
                logger.log(Level.WARNING, "JSON : "+document.toJson()+" FINE \n");
                // Creo l'oggetto FilmBean utilizzando automaticamente il metodo fromJson(), passando come
                // parametri il Document (convertito in Json) e la classe target e l'aggiungo all'ArrayList
                FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
                //logger.log(Level.WARNING, "JSON1 : "+film.toString()+" FINE \n");
                movie.add(film);
            }
        } else {
            //ripeto i passaggi sopra andando ad addizzionare nell'ArrayList<FilmBean> tutti i film di tutte le collezioni
            //Collezione Other_movies
            mongoDatabase = MongoDBConnection.getDatabase().getCollection("other_movies");
            FindIterable<Document> collection = mongoDatabase.find();
            Iterator iterator = collection.iterator();
            while(iterator.hasNext()) {
                org.bson.Document document = (org.bson.Document) iterator.next();
                Gson gson = new Gson();
                FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
                movie.add(film);
            }
            //Collezione movies_in_theaters
            mongoDatabase = MongoDBConnection.getDatabase().getCollection("movies_in_theaters");
            collection = mongoDatabase.find();
            iterator = collection.iterator();
            while(iterator.hasNext()) {
                org.bson.Document document = (org.bson.Document) iterator.next();
                Gson gson = new Gson();
                FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
                movie.add(film);
            }
            //Collezione Coming_Soon
            mongoDatabase = MongoDBConnection.getDatabase().getCollection("movies_coming_soon");
            collection = mongoDatabase.find();
            iterator = collection.iterator();
            while(iterator.hasNext()) {
                org.bson.Document document = (org.bson.Document) iterator.next();
                Gson gson = new Gson();
                FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
                movie.add(film);
            }
            //Collezione Best Movies
            mongoDatabase = MongoDBConnection.getDatabase().getCollection("top_rated_movies");
            collection = mongoDatabase.find();
            iterator = collection.iterator();
            while(iterator.hasNext()) {
                org.bson.Document document = (org.bson.Document) iterator.next();
                Gson gson = new Gson();
                FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
                movie.add(film);
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
