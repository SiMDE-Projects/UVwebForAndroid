package fr.utc.assos.uvweb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Newsfeed {
    @SerializedName("comments")
    private List<NewsfeedItem> items;

    public List<NewsfeedItem> getItems() {
        return items;
    }
}
