package fr.utc.assos.uvweb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
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
}
