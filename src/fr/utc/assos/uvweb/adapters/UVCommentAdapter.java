package fr.utc.assos.uvweb.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.ThreadPreconditions;
import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.holders.UVwebHolder;

import java.util.Collections;
import java.util.List;

public class UVCommentAdapter extends BaseAdapter {
    private List<UVwebContent.UVComment> mComments = Collections.emptyList();
    private Context mContext;

    public UVCommentAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public UVwebContent.UVComment getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateComments(List<UVwebContent.UVComment> comments) {
        ThreadPreconditions.checkOnMainThread();

        mComments = comments;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView userIDView;
        TextView rateView;
        TextView commentView;
        TextView dateView;

        public ViewHolder(TextView userIDView, TextView rateView, TextView commentView, TextView dateView) {
            this.userIDView = userIDView;
            this.rateView = rateView;
            this.commentView = commentView;
            this.dateView = dateView;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.uvcomment, null);
        }

        TextView userIdView = UVwebHolder.get(convertView, R.id.userid);
        TextView rateView = UVwebHolder.get(convertView, R.id.rate);
        TextView commentView = UVwebHolder.get(convertView, R.id.comment);
        TextView dateView = (TextView) convertView.findViewById(R.id.date);

        UVwebContent.UVComment comment = getItem(position);

        userIdView.setText(comment.getAuthor());
        rateView.setText(comment.getFormattedRate());
        commentView.setText(comment.getComment());
        dateView.setText(comment.getFormattedDate());

        return convertView;
    }
}
