package fr.utc.assos.uvweb;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import fr.utc.assos.uvweb.adapters.NewsFeedEntryAdapter;
import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.util.ConnectionUtils;
import fr.utc.assos.uvweb.util.AnimationUtils;

import static fr.utc.assos.uvweb.util.LogUtils.makeLogTag;

/**
 * A list fragment representing a list of {@link UVwebContent.NewsFeedEntry}s.
 */
public class NewsFeedFragment extends SherlockFragment {
	private static final String TAG = makeLogTag(NewsFeedFragment.class);
	private final Handler mHandler = new Handler();

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public NewsFeedFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_newsfeed,
				container, false);

		final ListView listView = (ListView) rootView.findViewById(android.R.id.list);

		final NewsFeedEntryAdapter adapter = new NewsFeedEntryAdapter(getSherlockActivity());

		final SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter
				(adapter, AnimationUtils.CARD_ANIMATION_DELAY_MILLIS, AnimationUtils.CARD_ANIMATION_DURATION_MILLIS);
		swingBottomInAnimationAdapter.setListView(listView);

		adapter.updateNewsFeedEntries(UVwebContent.NEWS_ENTRIES);

		listView.setAdapter(swingBottomInAnimationAdapter);

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_newsfeed, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh:
				final SherlockFragmentActivity context = getSherlockActivity();
				if (!ConnectionUtils.isOnline(context)) {
					Crouton.makeText(context, context.getString(R.string.network_error_message), ConnectionUtils.NETWORK_ERROR_STYLE).show();
				} else {
					final MenuItem refresh = item;
					refresh.setActionView(R.layout.progressbar);
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							refresh.setActionView(null);
						}
					}, 2000);
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
