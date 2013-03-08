package fr.utc.assos.uvweb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.UVDetailDefaultFragment;
import fr.utc.assos.uvweb.UVDetailFragment;
import fr.utc.assos.uvweb.UVListFragment;

/**
 * An activity representing a list of UVs. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of UVs, which when touched, lead to a
 * {@link fr.utc.assos.uvweb.activities.UVDetailActivity} representing UV details. On tablets, the activity
 * presents the list of UVs and UV details side-by-side using two vertical
 * panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of UVs is a
 * {@link fr.utc.assos.uvweb.UVListFragment} and the UV details (if present) is a
 * {@link fr.utc.assos.uvweb.UVDetailFragment}.
 * <p/>
 * This activity also implements the required {@link fr.utc.assos.uvweb.UVListFragment.Callbacks}
 * interface to listen for UV selections.
 */
public class UVListActivity extends UVwebMenuActivity implements
		UVListFragment.Callbacks {
	private static final String TAG = "UVListActivity";
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	/**
	 *
	 */
	private UVListFragment mUVListFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uv_list);

		getSupportActionBar().setHomeButtonEnabled(false);

		if (findViewById(R.id.uv_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-sw720dp and
			// res/values-sw600dp-land). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			mUVListFragment = (UVListFragment) getSupportFragmentManager().findFragmentById(R.id.uv_list);
			mUVListFragment.configureListView(true);
		}
	}

	/**
	 * Callback method from {@link UVListFragment.Callbacks} indicating that the
	 * UV with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		final boolean displayDefaultDetailFragment = id.equals(UVListFragment.DEFAULT_DETAIL_FRAGMENT);
		if (mTwoPane) {
			if (!mUVListFragment.getDisplayedUVName().equalsIgnoreCase(id)) {
				if (displayDefaultDetailFragment) {
					// Default detail fragment management
					UVDetailDefaultFragment fragment = new UVDetailDefaultFragment();
					getSupportFragmentManager().beginTransaction()
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
							.replace(R.id.uv_detail_container, fragment).commit();
				} else {
					// In two-pane mode, show the detail view in this activity by
					// adding or replacing the detail fragment using a
					// fragment transaction, if the new item is not already displayed.
					Bundle arguments = new Bundle();
					arguments.putString(UVDetailFragment.ARG_UV_ID, id);
					UVDetailFragment fragment = new UVDetailFragment();
					fragment.setArguments(arguments);
					getSupportFragmentManager().beginTransaction()
							.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
									android.R.anim.fade_in, android.R.anim.fade_out)
							.replace(R.id.uv_detail_container, fragment).commit();
				}
			}
		} else if (!displayDefaultDetailFragment) {
			Intent detailIntent = new Intent(this, UVDetailActivity.class);
			detailIntent.putExtra(UVDetailFragment.ARG_UV_ID, id);
			startActivity(detailIntent);
		}
	}
}
