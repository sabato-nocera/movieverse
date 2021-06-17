package control;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import model.UtenteBean;
import org.bson.Document;
import utils.MongoDBConnection;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
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

        UtenteBean user= (UtenteBean) request.getSession().getAttribute("utente");
        logger.log(Level.WARNING, "L'utente loggato è "+user.getUsername());
        UtenteBean temp = new UtenteBean();

        if(user.getAdmin()){
            //prelevo i parametri
            String emaila= request.getParameter("email");
            String usernamea = request.getParameter("username");
            String passa = request.getParameter("password");
            String passCa = request.getParameter("passwordConfirm");
            logger.log(Level.WARNING, "PASSWORD OTTENUTA "+passa);
            temp.setEmail(emaila);
            temp.setAdmin(true);
            temp.setUsername(usernamea);
            if(!passa.equals("") && !passCa.equals("") && passCa.equals(passa)){
                temp.setPassword(Utils.generatePwd(passa));
            }
            //Prendo il film da DB
            MongoCollection mongoDatabase = MongoDBConnection.getDatabase().getCollection("users");
            Document filter = new Document("_id", user.getId());
            FindIterable<Document> findIterable = mongoDatabase.find(filter);
            MongoCursor<Document> cursor = findIterable.iterator();
            if (cursor.hasNext()) {
                logger.log(Level.WARNING, "itero su "+cursor.toString());


                //Creo un documento di modifica
                BasicDBObject documentUpdater = new BasicDBObject();
                //Effettuo le modifiche sul documento settando i parametri di FilmBean
                documentUpdater.put("email", temp.getEmail() );
                documentUpdater.put("username", temp.getUsername() );
                if(!passa.equals("")){
                    documentUpdater.put("password", temp.getPassword());
                }

                //Creo un oggetto di modifica e gli associo il documento di modifica creato prima
                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", documentUpdater);

                logger.log(Level.WARNING, "DOC UPDATER : " + documentUpdater.toString());
                logger.log(Level.WARNING, "OBJ UPDATER : " + updateObject.toString());
                //Inserisco l'oggetto di modifica nel DB andando a modificarlo al documento ricercato dal filtro
                MongoDBConnection.getDatabase().getCollection("users").updateOne(filter, updateObject);

            }
        }else {
            //prelevo i parametri
            String emailu= request.getParameter("email");
            String usernameu = request.getParameter("username");
            String passu = request.getParameter("password");
            String passCu = request.getParameter("passwordConfirm");
            logger.log(Level.WARNING, "PASSWORD OTTENUTA :"+passu);
            String nameU = request.getParameter("firstName");
            String cognomeU = request.getParameter("lastName");
            String date = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            logger.log(Level.WARNING, "Il genere preso in input è "+ gender);
            logger.log(Level.WARNING, "Il genere presente è "+ user.getGender());
            temp.setEmail(emailu);
            temp.setFirstName(nameU);
            temp.setLastName(cognomeU);
            temp.setUsername(usernameu);
            temp.setGender(gender);
            logger.log(Level.WARNING, "Il genere sostituito è "+ temp.getGender());
            temp.setAdmin(false);
            if (date != null && !date.equals("")) {
                String[] list = date.split("-");
                String year = list[0];
                String month = list[1];
                String day = list[2];
                Date dateOfBirth = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month), Integer.parseInt(day));
                temp.setDateOfBirth(dateOfBirth);
            }
            if(!passu.equals("") && !passCu.equals("") && passCu.equals(passu)){
                temp.setPassword(Utils.generatePwd(passu));
            }

            //Prendo il film da DB
            MongoCollection mongoDatabase = MongoDBConnection.getDatabase().getCollection("users");
            Document filter = new Document("_id", user.getId());
            FindIterable<Document> findIterable = mongoDatabase.find(filter);
            MongoCursor<Document> cursor = findIterable.iterator();
            if (cursor.hasNext()) {
                logger.log(Level.WARNING, "itero su "+cursor.toString());


                //Creo un documento di modifica
                BasicDBObject documentUpdater = new BasicDBObject();
                //Effettuo le modifiche sul documento settando i parametri di FilmBean
                documentUpdater.put("email", temp.getEmail() );
                if(!passu.equals("")){
                    documentUpdater.put("password", temp.getPassword());
                }
                documentUpdater.put("username", temp.getUsername() );
                documentUpdater.put("firstName", temp.getFirstName() );
                documentUpdater.put("lastName", temp.getLastName() );
                documentUpdater.put("gender", temp.getGender() );
                documentUpdater.put("dateOfBirth", temp.getDateOfBirth() );

                //Creo un oggetto di modifica e gli associo il documento di modifica creato prima
                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set", documentUpdater);

                logger.log(Level.WARNING, "DOC UPDATER : " + documentUpdater.toString());
                logger.log(Level.WARNING, "OBJ UPDATER : " + updateObject.toString());
                //Inserisco l'oggetto di modifica nel DB andando a modificarlo al documento ricercato dal filtro
                MongoDBConnection.getDatabase().getCollection("users").updateOne(filter, updateObject);
                request.getSession().removeAttribute("utente");
                request.getSession().setAttribute("utente", temp);
            }
        }





        String url = response.encodeURL("Catalogo");
        request.getRequestDispatcher(url).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
