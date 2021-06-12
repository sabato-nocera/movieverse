package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import control.LoginServlet;
import jdk.vm.ci.meta.Local;
import model.FilmBean;
import model.UtenteBean;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;

/**
 * Questa classe serve soltanto per fare delle prove
 */
public class Prove {
    public static void main(String[] args) {

//        MongoCollection mongoCollection = MongoDBConnection.getDatabase().getCollection("movies_in_theaters");
//        FindIterable<Document> collection = mongoCollection.find();
//
//        Iterator iterator = collection.iterator();
//        while(iterator.hasNext()){
//            org.bson.Document document = (org.bson.Document) iterator.next();
//            System.out.println(document.get("releaseDate").getClass().getCanonicalName());
//            Gson gson = new Gson();
//            Date date = (Date) document.get("releaseDate");
//            System.out.println("Date retrieved: " + date);
//            document.remove("releaseDate");
//            System.out.println(document.toJson());
//            FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
//            film.setReleaseDate(date);
//            System.out.println(film);
//            System.out.println("\n");
//            System.out.println(LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("Z")).format(DateTimeFormatter.ofPattern("d MMM uuuu")));
//
//            String s = "1998-04-25";
//            String[] list = s.split("-");
//            String year = list[0];
//            String month = list[1];
//            String day = list[2];
//
//            Date dateOfBirth = new Date(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
//            System.out.println(dateOfBirth);
//            java.sql.Date dd = new java.sql.Date(dateOfBirth.getTime());
//            LocalDateTime localDateTime = LocalDateTime.now();
//            System.out.println(localDateTime);
//
//            iterator = MongoDBConnection.getDatabase().getCollection("users").find().iterator();
//            UtenteBean utenteBean = null;
//            while(iterator.hasNext()){
//                document = (org.bson.Document) iterator.next();
//                utenteBean = gson.fromJson(document.toJson(), UtenteBean.class);
//                System.out.println(utenteBean);
//            }
//
//            System.out.println(utenteBean.getDateOfBirth());
//        }

//        MongoCollection mongoDatabase = MongoDBConnection.getDatabase().getCollection("users");
//        org.bson.Document filter = new Document("username", "sabato");
//        FindIterable<Document> findIterable = mongoDatabase.find(filter);
//        MongoCursor<Document> cursor = findIterable.iterator();
//        if (cursor.hasNext()) {
//            Document document = cursor.next();
//            Document userDocument = document;
//            System.out.println(userDocument.toJson());
//            Gson gson = new Gson();
//            UtenteBean utenteBean = gson.fromJson(document.toJson(), UtenteBean.class);
//
//            mongoDatabase = MongoDBConnection.getDatabase().getCollection("top_rated_movies");
//            filter = new Document("title", "Pulp Fiction");
//            findIterable = mongoDatabase.find(filter);
//            cursor = findIterable.iterator();
//
//            if (cursor.hasNext()) {
//                document = cursor.next();
//                Date date = (Date) document.get("releaseDate");
//                ObjectId id = (ObjectId) document.get("_id");
//                document.remove("releaseDate");
//                document.remove("_id");
//                FilmBean filmBean = gson.fromJson(document.toJson(), FilmBean.class);
//                filmBean.setReleaseDate(date);
//                filmBean.setId(id);
//
//                utenteBean.addMovieToSee(filmBean);
//                utenteBean.addViewedMovie(filmBean);
//
//                mongoDatabase = MongoDBConnection.getDatabase().getCollection("top_rated_movies");
//                filter = new Document("title", "Fight Club");
//                findIterable = mongoDatabase.find(filter);
//                cursor = findIterable.iterator();
//
//                if (cursor.hasNext()) {
//                    document = cursor.next();
//                    date = (Date) document.get("releaseDate");
//                    id = (ObjectId) document.get("_id");
//                    document.remove("releaseDate");
//                    document.remove("_id");
//                    filmBean = gson.fromJson(document.toJson(), FilmBean.class);
//                    filmBean.setReleaseDate(date);
//                    filmBean.setId(id);
//
//                    utenteBean.addMovieToSee(filmBean);
//                    utenteBean.addViewedMovie(filmBean);
//
//                    mongoDatabase = MongoDBConnection.getDatabase().getCollection("users");
//                    filter = new Document("username", "sabato");
//
//                    BasicDBObject documentUpdater = new BasicDBObject();
//
//                    documentUpdater.put("viewedMovies", utenteBean.getViewedMovies());
//                    documentUpdater.put("moviesToSee", utenteBean.getMoviesToSee());
//
//                    BasicDBObject updateObject = new BasicDBObject();
//                    updateObject.put("$set", documentUpdater);
//
//                    System.out.println("DOC UPDATER : " + documentUpdater.toString());
//                    System.out.println("OBJ UPDATER : " + updateObject.toString());
//
//                    MongoDBConnection.getDatabase().getCollection("users").updateOne(filter, updateObject);
//
//                }
//            }
//        }

//
//        // Ricerca per nome di un film
//        BasicDBObject regexQuery = new BasicDBObject();
//        regexQuery.put("title", new BasicDBObject("$regex", ".*ring.*").append("$options", "i"));
//
//        System.out.println(regexQuery.toString());
//
//        MongoCollection mongoCollection = MongoDBConnection.getDatabase().getCollection("movies");
//        FindIterable<Document> findIterable = mongoCollection.find(regexQuery);
//        MongoCursor<Document> cursor = findIterable.iterator();
//        while(cursor.hasNext()){
//            Document document = cursor.next();
//            Gson gson = new Gson();
//            Date date = (Date) document.get("releaseDate");
//            System.out.println("Date retrieved: " + date);
//            document.remove("releaseDate");
//            System.out.println(document.toJson());
//            FilmBean film = gson.fromJson(document.toJson(), FilmBean.class);
//            film.setReleaseDate(date);
//            ObjectId objectId = document.getObjectId("_id");
//            System.out.println(objectId);
//            System.out.println(document.get("_id"));
//        }

//        MongoCollection movies = MongoDBConnection.getDatabase().getCollection("movies");
//        MongoCollection users = MongoDBConnection.getDatabase().getCollection("users");
//
//        // Male
//        BasicDBObject eqMale = new BasicDBObject("$eq", Arrays.asList("$gender", "male"));
//        BasicDBObject condMale = new BasicDBObject("$cond", Arrays.asList(eqMale, 1, 0));
//
//        // Female
//        BasicDBObject eqFemale = new BasicDBObject("$eq", Arrays.asList("$gender", "female"));
//        BasicDBObject condFemale = new BasicDBObject("$cond", Arrays.asList(eqFemale, 1, 0));
//
//        // Other
//        BasicDBObject eqOther = new BasicDBObject("$eq", Arrays.asList("$gender", "other"));
//        BasicDBObject condOther = new BasicDBObject("$cond", Arrays.asList(eqOther, 1, 0));
//
//        BasicDBObject singleProjects = new BasicDBObject();
//        singleProjects.append("male", condMale);
//        singleProjects.append("female", condFemale);
//        singleProjects.append("other", condOther);
//
//        BasicDBObject project = new BasicDBObject("$project", singleProjects);
//
//        BasicDBObject groups = new BasicDBObject();
//        groups.append("_id", null).append("male", new BasicDBObject("$sum", "$male")).
//                append("female", new BasicDBObject("$sum", "$female")).
//                append("other", new BasicDBObject("$sum", "$other")).
//                append("total", new BasicDBObject("$sum", 1));
//        BasicDBObject group = new BasicDBObject("$group", groups);
//
//        System.out.println(Arrays.asList(project, group));
//
//        AggregateIterable<Document> iterable = users.aggregate(Arrays.asList(project, group));
//
//        MongoCursor cursor = iterable.iterator();
//        if(cursor.hasNext()){
//            Document document = (Document) cursor.next();
//            System.out.println(document);
//            int nMale = document.getInteger("male");
//            int nFemale = document.getInteger("female");
//            int nOthers = document.getInteger("other");
//            int nTotal = document.getInteger("total");
//            System.out.println(nMale);
//            System.out.println(nFemale);
//            System.out.println(nOthers);
//            System.out.println(nTotal-nFemale-nMale-nOthers);
//        }

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
            System.out.println(document);
//            int nMale = document.getInteger("male");
//            int nFemale = document.getInteger("female");
//            int nOthers = document.getInteger("other");
//            int nTotal = document.getInteger("total");
//            System.out.println(nMale);
//            System.out.println(nFemale);
//            System.out.println(nOthers);
//            System.out.println(nTotal-nFemale-nMale-nOthers);
        }

    }
}
