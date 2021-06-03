package model;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

public class FilmBean {
    private ObjectId _id;
    private String title;
    private int year;
    private List<String> genres;
    private String duration;
    private LocalDateTime releaseDate;
    private Double averageRating;
    private String originalTitle;
    private String storyline;
    private List<String> actors;
    private Double imdbRating;
    private String posterurl;
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

    public LocalDateTime  getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime  releaseDate) {
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
                ", posterUrl='" + posterurl + '\'' +
                ", reviews=" + reviews +
                '}';
    }
}
