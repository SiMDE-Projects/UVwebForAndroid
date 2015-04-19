package fr.utc.assos.uvweb.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.api.UvwebProvider;
import fr.utc.assos.uvweb.model.Newsfeed;
import fr.utc.assos.uvweb.model.NewsfeedItem;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewsfeedFragment extends Fragment implements Callback<Newsfeed>, NewsfeedAdapter.ItemClickListener {
    private static final String TAG = NewsfeedFragment.class.getSimpleName();
    private static final String STATE_NEWSFEED_ITEMS = "state_newsfeed_items";

    private NewsfeedAdapter adapter;
    private List<NewsfeedItem> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        RecyclerView recycler = (RecyclerView) root.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsfeedAdapter(this);
        recycler.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            UvwebProvider.getNewsfeed(this);
        } else {
            items = savedInstanceState.getParcelableArrayList(STATE_NEWSFEED_ITEMS);
            updateViews();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (items != null) {
            outState.putParcelableArrayList(STATE_NEWSFEED_ITEMS, new ArrayList<Parcelable>(items));
        }
    }

    @Override
    public void success(Newsfeed newsfeed, Response response) {
        items = newsfeed.getItems();
        updateViews();
    }

    private void updateViews() {
        adapter.setItems(items);
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(TAG, "Failed loading newsfeed", error);
        Toast.makeText(getActivity(), getString(R.string.loading_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(NewsfeedItem item) {
        //TODO open comment detail
    }
}
