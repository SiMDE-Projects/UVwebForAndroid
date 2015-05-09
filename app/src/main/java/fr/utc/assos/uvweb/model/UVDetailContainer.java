package fr.utc.assos.uvweb.model;

import java.util.List;

public class UVDetailContainer {
    private UvDetailInfo uv;
    private List<Comment> comments;
    private List<Poll> polls;
    private float averageRate;

    public UvDetailInfo getUv() {
        return uv;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Poll> getPolls() {
        return polls;
    }

    public float getAverageRate() {
        return averageRate;
    }
}
