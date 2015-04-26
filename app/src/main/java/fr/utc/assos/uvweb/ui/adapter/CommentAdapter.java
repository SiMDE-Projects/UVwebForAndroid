package fr.utc.assos.uvweb.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.model.UvDetailComment;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private static final String COMMENT_DATE_FORMAT = "dd/MM/yy";

    private List<UvDetailComment> comments = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public CommentAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_comment, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, final int position) {
        Context context = holder.itemView.getContext();
        UvDetailComment comment = comments.get(position);
        holder.authorView.setText(comment.getAuthor());
        holder.commentView.setText(comment.getComment());
        holder.rateView.setText(context.getString(R.string.global_rate, comment.getGlobalRate()));
        holder.dateView.setText(comment.getDate());
        holder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(comments.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setComments(List<UvDetailComment> comments) {
        this.comments.addAll(comments);
        sortComments();
        notifyDataSetChanged();
    }

    private void sortComments() {
        final DateFormat format = new SimpleDateFormat(COMMENT_DATE_FORMAT, Locale.FRENCH);
        Comparator<UvDetailComment> comparator = new Comparator<UvDetailComment>() {
            @Override
            public int compare(UvDetailComment lhs, UvDetailComment rhs) {
                try {
                    Date lDate = format.parse(lhs.getDate());
                    Date rDate = format.parse(rhs.getDate());
                    return rDate.compareTo(lDate);
                } catch (ParseException exception) {
                    return 1;
                }
            }
        };

        Collections.sort(this.comments, comparator);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView authorView;
        private final TextView commentView;
        private final TextView rateView;
        private final TextView dateView;
        private final Button detailButton;

        public ViewHolder(View itemView) {
            super(itemView);
            authorView = (TextView) itemView.findViewById(R.id.author);
            commentView = (TextView) itemView.findViewById(R.id.comment);
            rateView = (TextView) itemView.findViewById(R.id.globalrate);
            dateView = (TextView) itemView.findViewById(R.id.date);
            detailButton = (Button) itemView.findViewById(R.id.view);
        }
    }

    public interface ItemClickListener {
        void onClick(UvDetailComment comment);
    }
}
