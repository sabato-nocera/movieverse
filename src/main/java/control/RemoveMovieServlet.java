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
@WebServlet("/RemoveMovie")
public class RemoveMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(AddToWatchServelt.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("utente") == null || ((UtenteBean) request.getSession().getAttribute("utente")).getAdmin() == false) {
            logger.log(Level.WARNING, "Utente non loggato");
            String url = response.encodeURL("Login");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }
        HttpSession session = request.getSession();

        //Prendo l'id del film da rimuovere
        FilmBean sessionfilm = (FilmBean) session.getAttribute("Film");

        if(session==null ){
            String url = response.encodeURL("Catalogo");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        UtenteBean user= (UtenteBean) session.getAttribute("utente");

        logger.log(Level.WARNING, "L'utente loggato è "+user.getUsername());

        //rimuovo il film dalle liste


        //Prendo il film da DB
        MongoCollection mongoDatabase = MongoDBConnection.getDatabase().getCollection("movies");
        org.bson.Document filter = new Document("_id", sessionfilm.getId());
        FindIterable<Document> findIterable = mongoDatabase.find(filter);
        MongoCursor<Document> cursor = findIterable.iterator();
        if (cursor.hasNext()) {
            logger.log(Level.WARNING, "itero su "+cursor.toString());
            mongoDatabase.deleteOne(filter);
        }

        String url = response.encodeURL("Catalogo");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
