package fr.utc.assos.uvweb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Newsfeed {
    @SerializedName("comments")
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }
}
