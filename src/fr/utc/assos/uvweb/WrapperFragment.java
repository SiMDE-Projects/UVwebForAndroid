package fr.utc.assos.uvweb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;

import static fr.utc.assos.uvweb.util.LogUtils.makeLogTag;

/**
 * A wrapper fragment containing a nested {@link UVListFragment}. It is used in two-pane mode only
 * and ensures that the {@link UVListFragment} state is properly saved and restored.
 */
public class WrapperFragment extends SherlockFragment {
	private static final String TAG = makeLogTag(WrapperFragment.class);
	private static final String UVLISTFRAGMENT_TAG = "UVListFragment_TAG";
	private UVListFragment mUvListFragment;

	public WrapperFragment() {
	}

	public static WrapperFragment newInstance() {
		return new WrapperFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setHasOptionsMenu(true); // Workaround for https://code.google.com/p/android/issues/detail?id=45722
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_wrapper,
				container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position and search query.
		final Fragment f = getChildFragmentManager().findFragmentByTag(UVLISTFRAGMENT_TAG);
		if (f != null) {
			mUvListFragment = (UVListFragment) f;
			if (savedInstanceState != null) {
				if (savedInstanceState.containsKey(UVListFragment.STATE_DISPLAYED_UV)) {
					mUvListFragment.setDisplayedUV(savedInstanceState.getString(UVListFragment.STATE_DISPLAYED_UV));
				}
				if (savedInstanceState.containsKey(UVListFragment.STATE_SEARCH_QUERY)) {
					mUvListFragment.setSearchQuery(savedInstanceState.getString(UVListFragment.STATE_SEARCH_QUERY));
				}
			}
		} else if (savedInstanceState != null) {
			UVListFragment.newInstance(
					savedInstanceState.getString(UVListFragment.STATE_DISPLAYED_UV),
					savedInstanceState.getString(UVListFragment.STATE_SEARCH_QUERY),
					true
			);
		} else {
			mUvListFragment = UVListFragment.newInstance(true);
		}

		getChildFragmentManager()
				.beginTransaction()
				.replace(R.id.uv_list, mUvListFragment, UVLISTFRAGMENT_TAG)
				.commit();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Serialize and persist the search query.
		outState.putString(UVListFragment.STATE_SEARCH_QUERY, mUvListFragment.getSearchQuery());

		// Serialize and persist the displayed UV.
		outState.putString(UVListFragment.STATE_DISPLAYED_UV, mUvListFragment.getDisplayedUVName());
	}

	@Override
	public void onDestroy() {
		// Resources cleanup
		mUvListFragment = null;

		super.onDestroy();
	}
}