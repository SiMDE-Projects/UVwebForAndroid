package fr.utc.assos.uvweb.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.api.UvwebProvider;
import fr.utc.assos.uvweb.model.UvDetail;
import fr.utc.assos.uvweb.model.UvListItem;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UvFragment extends Fragment implements Callback<UvDetail> {

    private static final String TAG = UvFragment.class.getSimpleName();

    private static final String ARG_UV = "arg_uv";

    private UvListItem uv;

    private TextView nameView;
    private TextView titleView;

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
        View rootView =  inflater.inflate(R.layout.fragment_uv, container, false);

        RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        nameView = (TextView) rootView.findViewById(R.id.name);
        titleView = (TextView) rootView.findViewById(R.id.title);

        nameView.setText(uv.getName());
        titleView.setText(uv.getTitle());

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        UvwebProvider.getUvDetail(uv.getName(), this);
    }

    @Override
    public void success(UvDetail uvDetail, Response response) {
        Log.d(TAG, "success");
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(TAG, error.getMessage(), error);
        Toast.makeText(getActivity(), getString(R.string.loading_error), Toast.LENGTH_SHORT).show();
    }
}
