package fr.utc.assos.uvweb.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.api.UvwebProvider;
import fr.utc.assos.uvweb.model.Newsfeed;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewsfeedFragment extends Fragment implements Callback<Newsfeed> {
    private static final String TAG = NewsfeedFragment.class.getSimpleName();
    private NewsfeedAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        RecyclerView recycler = (RecyclerView) root.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsfeedAdapter();
        recycler.setAdapter(adapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        UvwebProvider.getNewsfeed(this);
    }

    @Override
    public void success(Newsfeed newsfeed, Response response) {
        adapter.setItems(newsfeed.getItems());
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(TAG, "Failed loading newsfeed", error);
        Toast.makeText(getActivity(), getString(R.string.loading_error), Toast.LENGTH_SHORT).show();
    }
}
