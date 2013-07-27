package fr.utc.assos.uvweb.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import fr.tkeunebr.androidlazyasync.acl.AsyncFragment;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.adapters.UVListAdapter;
import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.io.UvListTaskFragment;
import fr.utc.assos.uvweb.ui.base.UVwebFragment;
import fr.utc.assos.uvweb.ui.custom.UVwebListView;
import fr.utc.assos.uvweb.ui.custom.UVwebSearchView;
import fr.utc.assos.uvweb.util.ConnectionUtils;

import static fr.utc.assos.uvweb.util.LogUtils.makeLogTag;

/**
 * A list fragment representing a list of {@link UVwebContent.UV}s. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link UVDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks} interface.
 */
public class UVListFragment extends UVwebFragment implements AdapterView.OnItemClickListener,
		UVListAdapter.SearchCallbacks, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener,
		AsyncFragment.AsyncCallbacks<List<UVwebContent.UV>, Void> {
	private static final String STATE_DISPLAYED_UV = "displayed_uv";
	private static final String STATE_SEARCH_QUERY = "search_query";
	private static final String STATE_UV_LIST = "uv_list";
	/**
	 * The fragment argument representing whether the layout is in twoPane mode or not.
	 */
	private static final String STATE_TWO_PANE = "two_pane";
	private static final String TAG = makeLogTag(UVListFragment.class);
	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static final Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(UVwebContent.UV uv) {
		}

		@Override
		public void showDefaultDetailFragment() {
		}
	};
	private boolean mNetworkError;
	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;
	/**
	 * The associated ListView object
	 */
	private UVwebListView mListView;
	/**
	 * The {@link fr.utc.assos.uvweb.adapters.base.UVAdapter} ListAdapter instance
	 */
	private UVListAdapter mAdapter;
	/**
	 * The displayed UV name
	 */
	private String mDisplayedUVName;
	private boolean mTwoPane;
	private UVwebSearchView mSearchView;
	private String mSearchQuery;
	private ProgressBar mProgressBar;
	private ViewStub mRetryViewStub;
	private UvListTaskFragment mTaskFragment;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public UVListFragment() {
	}

	/**
	 * Create a new instance of {@link UVListFragment} that will be initialized
	 * with the given arguments.
	 */
	public static UVListFragment newInstance(boolean twoPane) {
		final Bundle arguments = new Bundle();
		arguments.putBoolean(STATE_TWO_PANE, twoPane);
		final UVListFragment f = new UVListFragment();
		f.setArguments(arguments);
		return f;
	}

	public static UVListFragment newInstance() {
		return newInstance(false);
	}

	// Fragment Lifecycle management
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;

		mTaskFragment = AsyncFragment.get(getSherlockActivity().getSupportFragmentManager(),
				UvListTaskFragment.class);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Fragment configuration
		setHasOptionsMenu(true);

		mTwoPane = getArguments().getBoolean(STATE_TWO_PANE, false);

		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(STATE_SEARCH_QUERY)) {
				mSearchQuery = savedInstanceState.getString(STATE_SEARCH_QUERY);
			}
			if (savedInstanceState.containsKey(STATE_DISPLAYED_UV)) {
				mDisplayedUVName = savedInstanceState.getString(STATE_DISPLAYED_UV);
			}
		}
		mTaskFragment.setTargetFragment(this, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		final View rootView = inflater.inflate(R.layout.fragment_uv_list, container, false);

		mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress);

		// ListView setup
		mListView = (UVwebListView) rootView.findViewById(android.R.id.list);
		mListView.setOnItemClickListener(this);
		mRetryViewStub = (ViewStub) rootView.findViewById(R.id.empty_retry);
		mRetryViewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
			@Override
			public void onInflate(ViewStub viewStub, View view) {
				view.findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						loadUvList();
					}
				});
				mRetryViewStub = null;
			}
		});
		mListView.setEmptyView(rootView.findViewById(android.R.id.empty));

		// Adapter setup
		mAdapter = new UVListAdapter(getSherlockActivity());
		mAdapter.setSearchCallbacks(this);
		mListView.setAdapter(mAdapter);

		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(STATE_UV_LIST)) {
				final ArrayList<UVwebContent.UV> savedUvs = savedInstanceState.getParcelableArrayList(STATE_UV_LIST);
				mAdapter.updateUVs(savedUvs);
			} else {
				// In this case, we have a configuration change
				if (savedInstanceState.containsKey(STATE_NETWORK_ERROR)) {
					loadUvList();
				} else {
					// The task wasn't complete and is still running, we need to show the ProgressBar again
					onPreExecute();
				}
			}
		} else {
			loadUvList();
		}

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		setupTwoPaneUi();
	}

	@Override
	public void onDetach() {
		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
		mAdapter.resetSearchCallbacks();

		super.onDetach();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		performClick(position);
	}

	private void performClick(int position) {
		final UVwebContent.UV toBeDisplayed = mAdapter.getItem(position);
		if (toBeDisplayed != null) {
			final String toBeDisplayedName = toBeDisplayed.getName();
			if (!mTwoPane || !TextUtils.equals(toBeDisplayedName, mDisplayedUVName)) {
				// If in tablet mode and the dislayed UV is not the same as the UV clicked, or in phone mode
				// Lazy load the selected UV
				mCallbacks.onItemSelected(toBeDisplayed);
				mDisplayedUVName = toBeDisplayedName;
			}
		}
	}

	private void loadUvList() {
		final SherlockFragmentActivity context = getSherlockActivity();
		if (!ConnectionUtils.isOnline(context)) {
			handleNetworkError(context);
		} else {
			if (!mTaskFragment.isRunning()) {
				mTaskFragment.startNewTask(AsyncFragment.THREAD_POOL_EXECUTOR_POLICY);
			}
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupTwoPaneUi() {
		if (mTwoPane) {
			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setVerticalScrollbarPosition(ListView.SCROLLBAR_POSITION_LEFT);
			if (mDisplayedUVName == null) {
				mCallbacks.showDefaultDetailFragment();
			}
			getSherlockActivity().getWindow().setBackgroundDrawable(null);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.fragment_uv_list, menu);

		// SearchView configuration
		final MenuItem searchMenuItem = menu.findItem(R.id.menu_search);

		// We can't use onCloseListener() as it is broken on ICS+
		searchMenuItem.setOnActionExpandListener(this);

		mSearchView = (UVwebSearchView) searchMenuItem.getActionView();
		mSearchView.setOnQueryTextListener(this);
		if (mSearchQuery != null && !TextUtils.isEmpty(mSearchQuery)) {
			mSearchView.setQuery(mSearchQuery, false);
			searchMenuItem.expandActionView();
			mSearchView.requestFocus();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_search:
				mSearchView.setIsLoadingUV(false);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * {@link UVListAdapter} interface callbacks for search implementation
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onItemsFound(List<UVwebContent.UV> results) {
		if (mTwoPane && results.size() == 1) {
			final String toBeDisplayed = results.get(0).getName();
			if (!TextUtils.equals(toBeDisplayed, mDisplayedUVName)) {
				mListView.setItemChecked(0, true);
				performClick(0);
			}
		}
	}

	@Override
	public void onNothingFound() {
		if (mDisplayedUVName != null) {
			if (mTwoPane) {
				mCallbacks.showDefaultDetailFragment(); // TODO: prevent keyboard from being hidden here
			}
			mDisplayedUVName = null;
		}
	}

	/**
	 * {@link UVwebSearchView} interface callbacks for text submission
	 */
	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		if (mListView != null) {
			mListView.setFastScrollEnabled(TextUtils.isEmpty(newText)); // Workaround to avoid broken fastScroll
			// when in search mode
			mAdapter.getFilter().filter(newText);
			mSearchQuery = newText;
		}
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if (mSearchQuery != null && !TextUtils.isEmpty(mSearchQuery)) {
			outState.putString(STATE_SEARCH_QUERY, mSearchQuery);
		}
		if (mDisplayedUVName != null) {
			outState.putString(STATE_DISPLAYED_UV, mDisplayedUVName);
		}
		if (mAdapter.hasUvs()) {
			outState.putParcelableArrayList(STATE_UV_LIST, (ArrayList) mAdapter.getUVs());
		}
		if (mNetworkError) {
			outState.putBoolean(STATE_NETWORK_ERROR, true);
		}
	}

	/**
	 * {@link MenuItem.OnActionExpandListener} interface callbacks
	 */
	@Override
	public boolean onMenuItemActionExpand(MenuItem item) {
		getSherlockActivity().getSupportActionBar().setIcon(R.drawable.uvweb_logo);
		// Workaround to prevent SearchView from using the app_logo, which is green on a green ActionBar and thus
		// bad looking
		return true;
	}

	@Override
	public boolean onMenuItemActionCollapse(MenuItem item) {
		mSearchView.setQuery(null, false);
		mListView.setFastScrollEnabled(true);
		mSearchQuery = null;
		return true;
	}

	@Override
	protected void handleNetworkError(Activity context) {
		super.handleNetworkError(context);

		mListView.getEmptyView().setVisibility(View.GONE);
		mProgressBar.setVisibility(View.GONE);
		if (mRetryViewStub != null) {
			mRetryViewStub.inflate();
		}
	}

	@Override
	protected void handleNetworkError() {
		handleNetworkError(getSherlockActivity());
	}

	@Override
	public void onPreExecute() {
		mListView.getEmptyView().setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void onProgressUpdate(Void... values) {
	}

	@Override
	public void onPostExecute(List<UVwebContent.UV> uvs) {
		mAdapter.updateUVs(uvs);
		mListView.getEmptyView().setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void onError() {
		mNetworkError = true;
		handleNetworkError();
	}

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(UVwebContent.UV uv);

		/**
		 * Callback to display the default DetailFragment.
		 */
		public void showDefaultDetailFragment();
	}
}