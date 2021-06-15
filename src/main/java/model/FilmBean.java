package model;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FilmBean {
    private ObjectId _id;
    private String title;
    private List<String> genres;
    private String duration;
    private Date releaseDate;
    private Double averageRating;
    private String originalTitle;
    private String storyline;
    private List<String> actors = new ArrayList<>();
    private Double imdbRating;
    private String posterurl;
    private String catalog;
    private String productionCompany;
    private List<String> country;
    private List<String> director;
    private List<String> language;
    private List<Document> reviews = new ArrayList<>();

    public FilmBean() {
    }

    public void addActor(String string) {
        actors.add(string);
    }

    public void addRecensione(Document map) {
        reviews.add(map);
    }

    public void removeActors(String string) {
        actors.remove(string);
    }

    public void removeRecensione(Document map) {
        reviews.remove(map);
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getPosterurl() {
        return posterurl;
    }

    public void setPosterurl(String posterurl) {
        this.posterurl = posterurl;
    }

    public List<Document> getReviews() {
        return reviews;
    }

    public void setReviews(List<Document> reviews) {
        this.reviews = reviews;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }

    public List<String> getDirector() {
        return director;
    }

    public void setDirector(List<String> director) {
        this.director = director;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "FilmBean{" +
                "_id=" + _id +
                ", title='" + title + '\'' +
                ", genres=" + genres +
                ", duration='" + duration + '\'' +
                ", releaseDate=" + releaseDate +
                ", averageRating=" + averageRating +
                ", originalTitle='" + originalTitle + '\'' +
                ", storyline='" + storyline + '\'' +
                ", actors=" + actors +
                ", imdbRating=" + imdbRating +
                ", posterurl='" + posterurl + '\'' +
                ", reviews=" + reviews +
                ", catalog='" + catalog + '\'' +
                ", country=" + country +
                ", director=" + director +
                ", language=" + language +
                ", productionCompany='" + productionCompany + '\'' +
                '}';
    }
}
