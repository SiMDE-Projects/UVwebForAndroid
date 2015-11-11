package fr.utc.assos.uvweb.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.api.UvwebProvider;
import fr.utc.assos.uvweb.model.UvListItem;
import fr.utc.assos.uvweb.ui.activity.UvActivity;
import fr.utc.assos.uvweb.ui.adapter.UvListAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UvListFragment extends Fragment implements Callback<List<UvListItem>>, UvListAdapter.ItemClickListener, SearchView.OnQueryTextListener {
    private static final String TAG = UvListFragment.class.getSimpleName();
    private static final String STATE_UVS = "uvs";
    private static final int LOADING_STATE_IN_PROGRESS = 0;
    private static final int LOADING_STATE_COMPLETE = 1;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private UvListAdapter adapter;
    private List<UvListItem> uvs;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_uv_list, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search_uv);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_uvlist, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler);
        progressBar = (ProgressBar) root.findViewById(R.id.progressbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UvListAdapter(this);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            uvs = savedInstanceState.getParcelableArrayList(STATE_UVS);
            if (uvs != null) {
                updateViews();
                return;
            }
        }
        setLoadingState(LOADING_STATE_IN_PROGRESS);
        UvwebProvider.getUvs(this);
    }

    private void setLoadingState(int loadingState) {
        if (loadingState == LOADING_STATE_IN_PROGRESS) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void success(List<UvListItem> uvs, Response response) {
        this.uvs = uvs;
        if (getActivity() != null) {
            updateViews();
        }
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
        setLoadingState(LOADING_STATE_COMPLETE);
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(TAG, "Failed loading UV list", error);
        Snackbar.make(getView(), getString(R.string.loading_error), Snackbar.LENGTH_SHORT).show();
        if (getActivity() != null) {
            setLoadingState(LOADING_STATE_COMPLETE);
        }
    }

    @Override
    public void onClick(UvListItem uv) {
        Intent intent = new Intent(getActivity(), UvActivity.class);
        intent.putExtra(UvActivity.ARG_UV, uv);
        startActivity(intent);
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        adapter.clearFilter();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        // no-op
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filter(s);
        return false;
    }
}
