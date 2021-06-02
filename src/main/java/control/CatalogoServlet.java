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
    //Ottengo la collezione movies_in_theaters e la trasformo in un oggetto iterabile
        MongoCollection mongoDatabase = MongoDBConnection.getDatabase().getCollection("movies_in_theaters");
        FindIterable<Document> collection = mongoDatabase.find();

        //Ho ottenuto la collezione, la trasformo in un ArrayList<FilmBean> in modo da passarlo al catalogo
        ArrayList<FilmBean> movieInTheaters = new ArrayList<FilmBean>();
        Iterator iterator = collection.iterator();
        while(iterator.hasNext()){
            org.bson.Document document = (org.bson.Document) iterator.next();
            // Istanzio un oggetto Gson per l'effettuazione di manipolazioni facili ed efficaci con JSON
            Gson gson = new Gson();

            // Creo l'oggetto FilmBean utilizzando automaticamente il metodo fromJson(), passando come
            // parametri il Document (convertito in Json) e la classe target e l'aggiungo all'ArrayList
            FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
            logger.log(Level.WARNING, "JSON1 : "+film.toString()+" FINE \n");
            movieInTheaters.add(film);
        }


        request.setAttribute("movieInTheaters", movieInTheaters);
        String url = response.encodeURL("WEB-INF/Catalogo.jsp");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
