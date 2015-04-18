package fr.utc.assos.uvweb.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.model.UvListItem;


public class UvListAdapter extends RecyclerView.Adapter<UvListAdapter.ViewHolder> {

    private List<UvListItem> uvs = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_uv_list, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        UvListItem uv = uvs.get(position);
        viewHolder.nameView.setText(uv.name);
        viewHolder.titleView.setText(uv.title);
    }

    @Override
    public int getItemCount() {
        return uvs.size();
    }

    public void setUvs(List<UvListItem> uvs) {
        this.uvs = uvs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView titleView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.name);
            titleView = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
