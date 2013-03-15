package fr.utc.assos.uvweb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import fr.utc.assos.uvweb.adapters.NewsFeedEntryAdapter;
import fr.utc.assos.uvweb.data.UVwebContent;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 15/03/13
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public class NewsFeedFragment extends SherlockFragment {
	private static final String TAG = "NewsFeedFragment";

	/**
	 * The ListView containing all comment items.
	 */
	private ListView mListView;

	private boolean mTwoPane;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public NewsFeedFragment() {
		this(false);
	}

	public NewsFeedFragment(boolean twoPane) {
		mTwoPane = twoPane;
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

		mListView = (ListView) rootView.findViewById(android.R.id.list);

		NewsFeedEntryAdapter adapter = new NewsFeedEntryAdapter(getSherlockActivity());
		adapter.updateNewsFeedEntries(UVwebContent.NEWS_ENTRIES);

		mListView.setAdapter(adapter);

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_uv_detail, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh:
				Toast.makeText(getActivity(), "Refresh clicked", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
