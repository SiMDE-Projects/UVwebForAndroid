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
import de.keyboardsurfer.android.widget.crouton.Crouton;
import fr.utc.assos.uvweb.adapters.NewsFeedEntryAdapter;
import fr.utc.assos.uvweb.data.UVwebContent;

/**
 * A list fragment representing a list of {@link UVwebContent.NewsFeedEntry}s.
 */
public class NewsFeedFragment extends SherlockFragment {
	private static final String TAG = "NewsFeedFragment";
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

		setRetainInstance(true);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_newsfeed,
				container, false);

		final ListView mListView = (ListView) rootView.findViewById(android.R.id.list);

		final NewsFeedEntryAdapter adapter = new NewsFeedEntryAdapter(getSherlockActivity());
		adapter.updateNewsFeedEntries(UVwebContent.NEWS_ENTRIES);

		mListView.setAdapter(adapter);

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
				if (!ConnectionCheckerHelper.isOnline(context)) {
					Crouton.makeText(context, context.getString(R.string.network_error_message), ConnectionCheckerHelper.NETWORK_ERROR_STYLE).show();
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
