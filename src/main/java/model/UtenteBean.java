package model;

import org.bson.types.ObjectId;

import java.sql.Date;
import java.time.LocalDateTime;
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
    private LocalDateTime dateOfBirth;
    private List<ObjectId> viewedMovies;
    private List<ObjectId> moviesToSee;

    public UtenteBean() {
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

    public LocalDateTime  getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime  dateOfBirth) {
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
