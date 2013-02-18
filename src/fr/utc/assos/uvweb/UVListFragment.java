package fr.utc.assos.uvweb;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import fr.utc.assos.uvweb.adapters.UVListAdapter;
import fr.utc.assos.uvweb.data.UVwebContent;

/**
 * A list fragment representing a list of UVs. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link UVDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class UVListFragment extends SherlockFragment implements AdapterView.OnItemClickListener {
	/**
	 * The String representing a special id for {@link fr.utc.assos.uvweb.activities.UVListActivity},
	 * indicating that the default detail fragment has to be shown.
	 */
	public static final String DEFAULT_DETAIL_FRAGMENT = "default_detail_fragment";
	private static final String TAG = "UVListFragment";
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";
	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}
	};
	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;
	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;
	/**
	 * Indicates whether the default detail fragment has to be shown.
	 */
	private boolean mShowDefaultDetailFragment;
	/**
	 * The associated ListView object
	 */
	private StickyListHeadersListView mListView;
	/**
	 * The {@link fr.utc.assos.uvweb.adapters.UVAdapter} ListAdapter instance
	 */
	private UVListAdapter mAdapter;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public UVListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Fragment configuration
		setHasOptionsMenu(true);

		// Adapter setup
		mAdapter = new UVListAdapter(getSherlockActivity());
		mAdapter.updateUVs(UVwebContent.UVS);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		} else {
			mShowDefaultDetailFragment = true;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		mCallbacks.onItemSelected(UVwebContent.UVS.get(position).getName());
		mActivatedPosition = position;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void configureListView(boolean twoPane) {
		if (twoPane) {
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setVerticalScrollbarPosition(ListView.SCROLLBAR_POSITION_LEFT);
			if (mShowDefaultDetailFragment) {
				mCallbacks.onItemSelected(DEFAULT_DETAIL_FRAGMENT);
				mShowDefaultDetailFragment = false;
			}
		} else {
			mListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
		}

	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			mListView.setItemChecked(mActivatedPosition, false);
		} else {
			mListView.setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_uv_list,
				container, false);

		// ListView setup
		mListView = (StickyListHeadersListView) rootView.findViewById(android.R.id.list);
		mListView.setOnItemClickListener(this);
		mListView.setEmptyView(rootView.findViewById(android.R.id.empty));
		mListView.setFastScrollEnabled(true);

		mListView.setAdapter(mAdapter);

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_uv_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_search:
				Toast.makeText(getActivity(), "Search clicked", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
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
		public void onItemSelected(String id);
	}
}
