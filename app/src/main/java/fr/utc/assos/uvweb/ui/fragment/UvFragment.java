package fr.utc.assos.uvweb.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.api.UvwebProvider;
import fr.utc.assos.uvweb.model.Comment;
import fr.utc.assos.uvweb.model.Poll;
import fr.utc.assos.uvweb.model.UvDetail;
import fr.utc.assos.uvweb.model.UvListItem;
import fr.utc.assos.uvweb.ui.activity.CommentActivity;
import fr.utc.assos.uvweb.ui.adapter.CommentAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UvFragment extends Fragment implements Callback<UvDetail>, CommentAdapter.ItemClickListener {

    private static final String TAG = UvFragment.class.getSimpleName();

    private static final String ARG_UV = "arg_uv";

    private static final int LOADING_STATE_IN_PROGRESS = 0;
    private static final int LOADING_STATE_COMPLETE = 1;
    private static final String STATE_COMMENTS = "state_comments";
    private static final String STATE_POLLS = "state_polls";
    private static final String STATE_AVERAGE_RATE = "state_averagerate";

    private UvListItem uv;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private CommentAdapter adapter;
    private List<Comment> comments;
    private List<Poll> polls;
    private float averageRate;

    public static UvFragment newInstance(UvListItem uv) {
        UvFragment fragment = new UvFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_UV, uv);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uv = getArguments().getParcelable(ARG_UV);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_uv, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        TextView titleView = (TextView) rootView.findViewById(R.id.title);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommentAdapter(this);
        recyclerView.setAdapter(adapter);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle(uv.getName());

        titleView.setText(uv.getTitle());

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null &&
                (comments = savedInstanceState.getParcelableArrayList(STATE_COMMENTS)) != null) {
            polls = savedInstanceState.getParcelableArrayList(STATE_POLLS);
            averageRate = savedInstanceState.getFloat(STATE_AVERAGE_RATE);
            updateViews();
        } else {
            setLoadingState(LOADING_STATE_IN_PROGRESS);
            UvwebProvider.getUvDetail(uv.getName(), this);
        }
    }

    private void updateViews() {
        adapter.setComments(comments, averageRate, polls);
        setLoadingState(LOADING_STATE_COMPLETE);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (comments != null) {
            outState.putParcelableArrayList(STATE_COMMENTS, new ArrayList<>(comments));
            outState.putParcelableArrayList(STATE_POLLS, new ArrayList<>(polls));
            outState.putFloat(STATE_AVERAGE_RATE, averageRate);
        }
    }

    @Override
    public void success(UvDetail uvDetail, Response response) {
        comments = uvDetail.getDetail().getComments();
        averageRate = uvDetail.getDetail().getAverageRate();
        polls = uvDetail.getDetail().getPolls();
        if (getActivity() != null) {
            updateViews();
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(TAG, error.getMessage(), error);
        Snackbar.make(getView(), getString(R.string.loading_error), Snackbar.LENGTH_SHORT).show();
        if (getActivity() != null) {
            setLoadingState(LOADING_STATE_COMPLETE);
        }
    }

    @Override
    public void onClick(Comment comment) {
        Intent intent = new Intent(getActivity(), CommentActivity.class);
        intent.putExtra(CommentActivity.ARG_COMMENT, comment);
        intent.putExtra(CommentActivity.ARG_UVNAME, uv.getName());
        startActivity(intent);
    }
}
