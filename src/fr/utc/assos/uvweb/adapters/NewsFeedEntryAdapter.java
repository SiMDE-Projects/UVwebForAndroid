package fr.utc.assos.uvweb.adapters;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.util.GravatarUtils;
import fr.utc.assos.uvweb.util.ThreadPreconditionsUtils;

import java.util.Collections;
import java.util.List;

/**
 * An adapter used all together with the {@link fr.utc.assos.uvweb.ui.NewsFeedFragment}'s ListView.
 * It relies on a standard ViewHolder pattern implemented in the {@link UVwebHolder}
 * class and thus allows UVs recycling.
 * It is used to show the actions that can occur in UVweb as a dynamic feed
 */
public class NewsFeedEntryAdapter extends UVAdapter {
	private final String mDatePresentation;
	private final Context mContext;
	private final int mAvatarPixelSize;
	private List<UVwebContent.NewsFeedEntry> mNewsFeedEntries = Collections.emptyList();

	public NewsFeedEntryAdapter(Context context) {
		super(context);
		mContext = context;
		mAvatarPixelSize = context.getResources().getDimensionPixelSize(R.dimen.avatar_image_view_size);
		mDatePresentation = context.getString(R.string.date_presentation);
	}

	@Override
	public int getCount() {
		return mNewsFeedEntries.size();
	}

	@Override
	public UVwebContent.NewsFeedEntry getItem(int position) {
		return mNewsFeedEntries.get(position);
	}

	public void updateNewsFeedEntries(List<UVwebContent.NewsFeedEntry> newsFeedEntries) {
		ThreadPreconditionsUtils.checkOnMainThread();

		mNewsFeedEntries = newsFeedEntries;
		notifyDataSetChanged();
	}

	public List<UVwebContent.NewsFeedEntry> getNewsfeedEntries() {
		return mNewsFeedEntries;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.newsfeed_entry, null);
		}

		final ImageView userAvatarImageView = UVwebHolder.get(convertView, R.id.user_avatar);
		final TextView userIdView = UVwebHolder.get(convertView, R.id.user_id_action);
		final TextView dateView = UVwebHolder.get(convertView, R.id.date);
		final TextView commentView = UVwebHolder.get(convertView, R.id.comment);

		final UVwebContent.NewsFeedEntry entry = getItem(position);
		final UVwebContent.User owner = entry.getOwner();
		userIdView.setText(Html.fromHtml(String.format(UVwebContent.NEWSFEED_ACTION_FORMAT,
				owner.getName(), " " + entry.getAction())));
		dateView.setText(mDatePresentation + " " + entry.getTimeDifference());
		Picasso.with(mContext).load(GravatarUtils.computerUrlRequest(owner.getGravatarHash(), mAvatarPixelSize))
				.placeholder(R.drawable.ic_contact_picture)
				.error(R.drawable.ic_contact_picture)
				.into(userAvatarImageView);
		commentView.setText(entry.getComment());

		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
