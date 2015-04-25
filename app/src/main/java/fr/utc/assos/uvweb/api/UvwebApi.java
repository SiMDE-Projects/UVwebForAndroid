package fr.utc.assos.uvweb.api;

import java.util.List;

import fr.utc.assos.uvweb.model.Newsfeed;
import fr.utc.assos.uvweb.model.UvDetail;
import fr.utc.assos.uvweb.model.UvListItem;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface UvwebApi {
    @GET("/uv/app/all/name/false")
    void getUvs(Callback<List<UvListItem>> callback);

    @GET("/app/recentactivity")
    void getNewsfeed(Callback<Newsfeed> callback);

    @GET("/uv/app/details/{uvname}")
    void getUvDetail(@Path("uvname") String uvName, Callback<UvDetail> callback);
}
