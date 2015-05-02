package fr.utc.assos.uvweb.model;

import java.util.List;

public class UVDetailContainer {
    private UvDetailInfo uv;
    private List<Comment> comments;

    public UvDetailInfo getUv() {
        return uv;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
