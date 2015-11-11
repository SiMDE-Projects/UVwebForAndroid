package fr.utc.assos.uvweb.model;

import com.google.gson.annotations.SerializedName;

public class UvDetail {
    @SerializedName("details")
    private UvDetailContainer detail;

    public UvDetailContainer getDetail() {
        return detail;
    }
}
