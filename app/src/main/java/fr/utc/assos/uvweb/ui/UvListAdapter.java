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
    private ItemClickListener itemClickListener;

    public UvListAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_uv_list, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final UvListItem uv = uvs.get(position);
        viewHolder.nameView.setText(uv.getName());
        viewHolder.titleView.setText(uv.getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(uv);
            }
        });
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

    public interface ItemClickListener {
        void onClick(UvListItem uv);
    }
}
