package fr.utc.assos.uvweb.model;

import com.google.gson.annotations.SerializedName;

public class UvDetailComment {
    @SerializedName("identity")
    private String author;
    private int globalRate;
    private String date;
    private String semester;
    private boolean passed;
    private String comment;


    public String getAuthor() {
        return author;
    }

    public int getGlobalRate() {
        return globalRate;
    }

    public String getDate() {
        return date;
    }

    public String getSemester() {
        return semester;
    }

    public boolean isPassed() {
        return passed;
    }

    public String getComment() {
        return comment;
    }
}
