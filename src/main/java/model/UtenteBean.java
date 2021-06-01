package model;

import org.bson.types.ObjectId;

import java.util.List;

public class UtenteBean {
    private ObjectId id;
    private Boolean isAdmin;
    private String email;
    private String username;
    private String password;
    private List<ObjectId> viewedMovies;
    private List<ObjectId> moviesToSee;

    public UtenteBean() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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
                "id=" + id +
                ", isAdmin=" + isAdmin +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", viewedMovies=" + viewedMovies +
                ", moviesToSee=" + moviesToSee +
                '}';
    }
}
