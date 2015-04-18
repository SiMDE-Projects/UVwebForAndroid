package fr.utc.assos.uvweb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class UvListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_uvlist, container, false);
        RecyclerView recycler = (RecyclerView) root.findViewById(R.id.recycler);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        UvListAdapter adapter = new UvListAdapter();

        List<UvListItem> uvs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            UvListItem uv = new UvListItem();
            uv.name = "UV" + i;
            uv.title = "This is a title for UV" + i;
            uvs.add(uv);
        }
        adapter.setUvs(uvs);

        recycler.setAdapter(adapter);

        return root;
    }
}
