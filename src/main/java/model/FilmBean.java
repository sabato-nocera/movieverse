package model;

import org.bson.types.ObjectId;

import java.sql.Date;
import java.util.List;

public class FilmBean {
    private ObjectId _id;
    private String title;
    private int year;
    private List<String> genres;
    private String duration;
    private Date releaseDate;
    private Double averageRating;
    private String originalTitle;
    private String storyline;
    private List<String> actors;
    private Double imdbRating;
    private String posterUrl;
    private List<RecensioneBean> reviews;

    public FilmBean() {
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<RecensioneBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<RecensioneBean> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "FilmBean{" +
                "id=" + _id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genres=" + genres +
                ", duration='" + duration + '\'' +
                ", releaseDate=" + releaseDate +
                ", averageRating=" + averageRating +
                ", originalTitle='" + originalTitle + '\'' +
                ", storyline='" + storyline + '\'' +
                ", actors=" + actors +
                ", imdbRating=" + imdbRating +
                ", posterUrl='" + posterUrl + '\'' +
                ", reviews=" + reviews +
                '}';
    }
}
