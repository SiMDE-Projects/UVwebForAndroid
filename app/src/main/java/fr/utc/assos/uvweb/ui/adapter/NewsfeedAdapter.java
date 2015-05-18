package fr.utc.assos.uvweb.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.model.Comment;

public class NewsfeedAdapter extends RecyclerView.Adapter<NewsfeedAdapter.ViewHolder> {

    private List<Comment> comments = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public NewsfeedAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newsfeed, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Comment comment = comments.get(position);
        Context context = holder.itemView.getContext();
        holder.authorView.setText(context.getString(R.string.newsfeed_author, comment.getAuthor(), comment.getGlobalRate()));
        holder.dateView.setText(comment.getDate());
        holder.uvNameView.setText(comment.getUvName());
        holder.commentView.setText(comment.getComment());
        holder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(comment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView authorView;
        private final TextView uvNameView;
        private final TextView commentView;
        private final TextView dateView;
        private final Button detailButton;

        public ViewHolder(View itemView) {
            super(itemView);
            authorView = (TextView) itemView.findViewById(R.id.author);
            uvNameView = (TextView) itemView.findViewById(R.id.uvname);
            commentView = (TextView) itemView.findViewById(R.id.comment);
            dateView = (TextView) itemView.findViewById(R.id.date);
            detailButton = (Button) itemView.findViewById(R.id.view);
        }
    }

    public interface ItemClickListener {
        void onClick(Comment comment);
    }
}
