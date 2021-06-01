package model;

import org.bson.types.ObjectId;

public class RecensioneBean {
    /**
     * Id dell'utente che ha effettuato la recensione
     */
    private ObjectId _id;
    private String comment;
    private Double rating;

    public ObjectId getUserid() {
        return _id;
    }

    public void setUserid(ObjectId userid) {
        this._id = userid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "RecensioneBean{" +
                "userid=" + _id +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }

}
