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
import fr.utc.assos.uvweb.model.UvListItem;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UvListFragment extends Fragment implements Callback<List<UvListItem>> {
    private static final String TAG = UvListFragment.class.getSimpleName();
    private static final String STATE_UVS = "uvs";
    private UvListAdapter adapter;
    private List<UvListItem> uvs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_uvlist, container, false);
        RecyclerView recycler = (RecyclerView) root.findViewById(R.id.recycler);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UvListAdapter();
        recycler.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            UvwebProvider.getUvs(this);
        } else {
            uvs = savedInstanceState.getParcelableArrayList(STATE_UVS);
            updateViews();
        }
    }

    @Override
    public void success(List<UvListItem> uvs, Response response) {
        this.uvs = uvs;
        updateViews();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (uvs != null) {
            outState.putParcelableArrayList(STATE_UVS, new ArrayList<Parcelable>(uvs));
        }
    }

    private void updateViews() {
        adapter.setUvs(uvs);
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(TAG, "Failed loading UV list", error);
        Toast.makeText(getActivity(), getString(R.string.loading_error), Toast.LENGTH_SHORT).show();
    }
}
