package fr.utc.assos.uvweb.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.util.ThreadPreconditionsUtils;

import java.util.Collections;
import java.util.List;

/**
 * An adapter used all together with the {@link fr.utc.assos.uvweb.ui.UVDetailFragment}'s ListView.
 * It relies on a standard ViewHolder pattern implemented in the {@link UVwebHolder}
 * class and thus allows UVs recycling.
 * It is used to show the users' comments of a given UV
 */
public class UVCommentAdapter extends UVAdapter {
	private List<UVwebContent.UVComment> mComments = Collections.emptyList();

	public UVCommentAdapter(Context context) {
		super(context);
	}

	@Override
	public int getCount() {
		return mComments.size();
	}

	@Override
	public UVwebContent.UVComment getItem(int position) {
		return mComments.get(position);
	}

	public void updateComments(List<UVwebContent.UVComment> comments) {
		ThreadPreconditionsUtils.checkOnMainThread();

		mComments = comments;
		notifyDataSetChanged();
	}

	public List<UVwebContent.UVComment> getComments() {
		return mComments;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.uv_comment, null);
		}

		final TextView userIdView = UVwebHolder.get(convertView, R.id.userid);
		final TextView rateView = UVwebHolder.get(convertView, R.id.rate);
		final TextView commentView = UVwebHolder.get(convertView, R.id.comment);
		final TextView semesterView = UVwebHolder.get(convertView, R.id.semester);
		final TextView dateView = (TextView) convertView.findViewById(R.id.date);

		final UVwebContent.UVComment comment = getItem(position);

		userIdView.setText(comment.getAuthor());
		rateView.setText(comment.getFormattedRate());
		commentView.setText(comment.getComment());
		semesterView.setText(comment.getSemester());
		dateView.setText(comment.getFormattedDate());

		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
