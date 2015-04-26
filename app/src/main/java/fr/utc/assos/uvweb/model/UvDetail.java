package fr.utc.assos.uvweb.model;

import com.google.gson.annotations.SerializedName;

public class UvDetail {
    @SerializedName("details")
    private UVDetailContainer detail;

    public UVDetailContainer getDetail() {
        return detail;
    }
}
