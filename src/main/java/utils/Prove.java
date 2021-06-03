package utils;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import control.LoginServlet;
import jdk.vm.ci.meta.Local;
import model.FilmBean;
import model.UtenteBean;

import javax.swing.text.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Questa classe serve soltanto per fare delle prove
 */
public class Prove {
    public static void main(String[] args) {

        MongoCollection mongoCollection = MongoDBConnection.getDatabase().getCollection("movies_in_theaters");
        FindIterable<Document> collection = mongoCollection.find();

        Iterator iterator = collection.iterator();
        while(iterator.hasNext()){
            org.bson.Document document = (org.bson.Document) iterator.next();
            System.out.println(document.get("releaseDate").getClass().getCanonicalName());
            Gson gson = new Gson();
            Date date = (Date) document.get("releaseDate");
            System.out.println("Date retrieved: " + date);
            document.remove("releaseDate");
            System.out.println(document.toJson());
            FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
            film.setReleaseDate(date);
            System.out.println(film);
            System.out.println("\n");
            System.out.println(LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("Z")).format(DateTimeFormatter.ofPattern("d MMM uuuu")));

            String s = "1998-04-25";
            String[] list = s.split("-");
            String year = list[0];
            String month = list[1];
            String day = list[2];

            Date dateOfBirth = new Date(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            System.out.println(dateOfBirth);
            java.sql.Date dd = new java.sql.Date(dateOfBirth.getTime());
            LocalDateTime localDateTime = LocalDateTime.now();
            System.out.println(localDateTime);

            iterator = MongoDBConnection.getDatabase().getCollection("users").find().iterator();
            UtenteBean utenteBean = null;
            while(iterator.hasNext()){
                document = (org.bson.Document) iterator.next();
                utenteBean = gson.fromJson(document.toJson(), UtenteBean.class);
                System.out.println(utenteBean);
            }

            System.out.println(utenteBean.getDateOfBirth());
        }
    }
}
