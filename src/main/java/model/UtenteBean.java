package model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UtenteBean {
    private ObjectId _id;
    private Boolean isAdmin;
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
    private List<ObjectId> viewedMovies = new ArrayList<>();
    private List<ObjectId> moviesToSee = new ArrayList<>();

    public UtenteBean() {
    }

    public void addViewedMovie(ObjectId objectId){
        if(this.viewedMovies==null){
            this.viewedMovies=new ArrayList<>();
        }
        viewedMovies.add(objectId);
    }

    public void addMovieToSee(ObjectId objectId){
        if(this.moviesToSee==null){
            this.moviesToSee=new ArrayList<>();
        }
        moviesToSee.add(objectId);
    }

    public void removeViewedMovie(FilmBean filmBean){
        viewedMovies.remove(filmBean.getId());
    }

    public void removeMovieToSee(FilmBean filmBean){
        moviesToSee.remove(filmBean.getId());
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<ObjectId> getViewedMovies() {
        return viewedMovies;
    }

    public void setViewedMovies(List<ObjectId> viewedMovies) {
        this.viewedMovies = viewedMovies;
    }

    public List<ObjectId> getMoviesToSee() {
        return moviesToSee;
    }

    public void setMoviesToSee(List<ObjectId> moviesToSee) {
        this.moviesToSee = moviesToSee;
    }

    @Override
    public String toString() {
        return "UtenteBean{" +
                "id=" + _id +
                ", isAdmin=" + isAdmin +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", viewedMovies=" + viewedMovies +
                ", moviesToSee=" + moviesToSee +
                '}';
    }
}
