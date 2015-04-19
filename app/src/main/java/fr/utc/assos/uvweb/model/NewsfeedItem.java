package fr.utc.assos.uvweb.model;

import com.google.gson.annotations.SerializedName;

public class NewsfeedItem {
    @SerializedName("identity")
    private String author;
    private String date;
    @SerializedName("name")
    private String uvName;
    private String globalRate;
    private String comment;

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getUvName() {
        return uvName;
    }

    public String getGlobalRate() {
        return globalRate;
    }

    public String getComment() {
        return comment;
    }
}
