package model;

import com.google.gson.Gson;
import org.bson.types.ObjectId;

import javax.json.JsonObject;

public class RecensioneBean {
    private ObjectId userid;
    private String comment;
    private Double rating;

    public ObjectId getUserid() {
        return userid;
    }

    public void setUserid(ObjectId userid) {
        this.userid = userid;
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
                "userid=" + userid +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }

}
