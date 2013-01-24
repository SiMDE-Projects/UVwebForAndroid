package fr.utc.assos.uvweb;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.holders.UVHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UVCommentAdapter extends BaseAdapter {
    private List<UVwebContent.UVComment> mComments = Collections.emptyList();
    private Context mContext;
    private String[] mSections; // TODO: Character[] ?

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


        TextView userIDView;
        TextView rateView;
        TextView commentView;
        TextView dateView;

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.uvcomment, null);

            userIDView = (TextView)convertView.findViewById(R.id.userid);
            rateView = (TextView)convertView.findViewById(R.id.rate);
            commentView = (TextView)convertView.findViewById(R.id.comment);
            dateView = (TextView)convertView.findViewById(R.id.date);

            convertView.setTag(new ViewHolder(userIDView, rateView, commentView, dateView));
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            userIDView = viewHolder.userIDView;
            rateView = viewHolder.rateView;
            commentView = viewHolder.commentView;
            dateView = viewHolder.dateView;
        }

        UVwebContent.UVComment comment = getItem(position);

        userIDView.setText(comment.getAuthor());
        rateView.setText(comment.getFormattedRate());
        commentView.setText(comment.getComment());
        dateView.setText(comment.getFormattedDate());

        return convertView;
    }
}
