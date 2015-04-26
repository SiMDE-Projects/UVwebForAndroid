package fr.utc.assos.uvweb.model;

import java.util.List;

public class UVDetailContainer {
    private UvDetailInfo uv;
    private List<UvDetailComment> comments;

    public UvDetailInfo getUv() {
        return uv;
    }

    public List<UvDetailComment> getComments() {
        return comments;
    }
}
