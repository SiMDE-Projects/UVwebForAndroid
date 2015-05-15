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
import fr.utc.assos.uvweb.model.Comment;
import fr.utc.assos.uvweb.model.Poll;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private static final String COMMENT_DATE_FORMAT = "dd/MM/yy";
    private static final int VIEWTYPE_HEADER = 0;
    private static final int VIEWTYPE_COMMENT = 1;

    private List<Comment> comments = new ArrayList<>();
    private List<Poll> polls = new ArrayList<>();
    private float averageRate;

    private ItemClickListener itemClickListener;

    public CommentAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView;
        switch (viewType) {
            case VIEWTYPE_HEADER:
                rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_header, parent, false);
                return new HeaderViewHolder(rootView);
            case VIEWTYPE_COMMENT:
            default:
                rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_comment, parent, false);
                return new CommentViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case VIEWTYPE_HEADER:
                bindHeaderViewHolder((HeaderViewHolder) holder);
                break;
            case VIEWTYPE_COMMENT:
            default:
                bindCommentViewHolder((CommentViewHolder) holder, position - 1);
                break;
        }
    }

    private void bindCommentViewHolder(CommentViewHolder holder, final int commentPosition) {
        Context context = holder.itemView.getContext();
        Comment comment = comments.get(commentPosition);
        holder.authorView.setText(comment.getAuthor());
        holder.commentView.setText(comment.getComment());
        holder.rateView.setText(context.getString(R.string.global_rate, comment.getGlobalRate()));
        holder.dateView.setText(comment.getDate());
        holder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(comments.get(commentPosition));
            }
        });
    }

    private void bindHeaderViewHolder(HeaderViewHolder holder) {
        Context context = holder.itemView.getContext();
        holder.rateView.setText(context.getString(R.string.average_rate, averageRate));

        if (polls.size() < 0) {
            holder.successRateTitleView.setVisibility(View.GONE);
        }

        for (int i = 0; i < polls.size(); i++) {
            if (i >= holder.pollListView.getChildCount()) {
                break;
            }
            Poll poll = polls.get(i);
            TextView pollView = (TextView) holder.pollListView.getChildAt(i);
            pollView.setText(context.getString(R.string.poll_success_rate, poll.getSuccessRate()));
        }
    }

    @Override
    public int getItemCount() {
        return comments.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEWTYPE_HEADER : VIEWTYPE_COMMENT;
    }

    public void setComments(List<Comment> comments, float averageRate, List<Poll> polls) {
        this.comments.clear();
        this.comments.addAll(comments);
        sortComments();
        this.averageRate = averageRate;
        this.polls.clear();
        this.polls.addAll(polls);
        notifyDataSetChanged();
    }

    private void sortComments() {
        final DateFormat format = new SimpleDateFormat(COMMENT_DATE_FORMAT, Locale.FRENCH);
        Comparator<Comment> comparator = new Comparator<Comment>() {
            @Override
            public int compare(Comment lhs, Comment rhs) {
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

    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class CommentViewHolder extends ViewHolder {
        private final TextView authorView;
        private final TextView commentView;
        private final TextView rateView;
        private final TextView dateView;
        private final Button detailButton;

        public CommentViewHolder(View itemView) {
            super(itemView);
            authorView = (TextView) itemView.findViewById(R.id.author);
            commentView = (TextView) itemView.findViewById(R.id.comment);
            rateView = (TextView) itemView.findViewById(R.id.globalrate);
            dateView = (TextView) itemView.findViewById(R.id.date);
            detailButton = (Button) itemView.findViewById(R.id.view);
        }
    }

    private class HeaderViewHolder extends ViewHolder {
        private final TextView rateView;
        private final ViewGroup pollListView;
        private final TextView successRateTitleView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            rateView = (TextView) itemView.findViewById(R.id.average_rate);
            pollListView = (ViewGroup) itemView.findViewById(R.id.poll_list);
            successRateTitleView = (TextView) itemView.findViewById(R.id.success_rate_label);
        }
    }

    public interface ItemClickListener {
        void onClick(Comment comment);
    }
}
