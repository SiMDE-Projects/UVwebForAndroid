package fr.utc.assos.uvweb.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.model.NewsfeedItem;

public class NewsfeedAdapter extends RecyclerView.Adapter<NewsfeedAdapter.ViewHolder> {

    private List<NewsfeedItem> items = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newsfeed, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsfeedItem item = items.get(position);
        Context context = holder.itemView.getContext();
        holder.authorView.setText(context.getString(R.string.newsfeed_author, item.getAuthor(), item.getGlobalRate()));
        holder.dateView.setText(item.getDate());
        holder.uvNameView.setText(item.getUvName());
        holder.commentView.setText(item.getComment());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<NewsfeedItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView authorView;
        private final TextView uvNameView;
        private final TextView commentView;
        private final TextView dateView;

        public ViewHolder(View itemView) {
            super(itemView);
            authorView = (TextView) itemView.findViewById(R.id.author);
            uvNameView = (TextView) itemView.findViewById(R.id.uvname);
            commentView = (TextView) itemView.findViewById(R.id.comment);
            dateView = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
