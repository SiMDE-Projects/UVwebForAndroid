package fr.utc.assos.uvweb.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.util.GravatarUtils;
import fr.utc.assos.uvweb.util.ThreadPreconditionsUtils;

/**
 * An adapter used all together with the {@link fr.utc.assos.uvweb.ui.UVDetailFragment}'s ListView.
 * It relies on a standard ViewHolder pattern implemented in the {@link UVwebHolder}
 * class and thus allows UVs recycling.
 * It is used to show the users' comments of a given UV
 */
public class UVCommentAdapter extends UVAdapter implements StickyListHeadersAdapter {
	private static final OnInflateStickyHeader sDummyCallbacks = new OnInflateStickyHeader() {
		@Override
		public void onHeaderInflated(View headerView) {
		}
	};
	private final Context mContext;
	private final int mAvatarPixelSize;
	private List<UVwebContent.UVComment> mComments = Collections.emptyList();
	private OnInflateStickyHeader mCallbacks = sDummyCallbacks;

	public UVCommentAdapter(Context context) {
		super(context);
		mContext = context;
		mAvatarPixelSize = context.getResources().getDimensionPixelSize(R.dimen.avatar_image_view_size);
	}

	public void setOnInflateStickyHeader(OnInflateStickyHeader callbacks) {
		mCallbacks = callbacks;
	}

	public void resetCallbacks() {
		mCallbacks = sDummyCallbacks;
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
		final ImageView userAvatarImageView = UVwebHolder.get(convertView, R.id.user_avatar);
		final TextView rateView = UVwebHolder.get(convertView, R.id.rate);
		final TextView commentView = UVwebHolder.get(convertView, R.id.comment);
		final TextView semesterView = UVwebHolder.get(convertView, R.id.semester);
		final TextView dateView = UVwebHolder.get(convertView, R.id.date);

		final UVwebContent.UVComment comment = getItem(position);

		final UVwebContent.User author = comment.getAuthor();
		userIdView.setText(author.getName());
		Picasso.with(mContext).load(GravatarUtils.computeUrlRequest(author.getGravatarHash(), mAvatarPixelSize))
				.placeholder(R.drawable.ic_contact_picture)
				.error(R.drawable.ic_contact_picture)
				.into(userAvatarImageView);
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

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// TODO: it's the same as the empty header view, cache it
			convertView = mLayoutInflater.inflate(R.layout.uv_detail_header, null);
			mCallbacks.onHeaderInflated(convertView);
		}

		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		return 0;
	}

	public interface OnInflateStickyHeader {
		public void onHeaderInflated(View headerView);
	}
}
