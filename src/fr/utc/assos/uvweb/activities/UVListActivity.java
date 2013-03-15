package fr.utc.assos.uvweb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.actionbarsherlock.app.ActionBar;
import fr.utc.assos.uvweb.*;

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
		UVListFragment.Callbacks, ActionBar.TabListener {
	private static final String TAG = "UVListActivity";
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane = false;

	private SectionsPagerAdapter mSectionsPagerAdapter;

	private FragmentManager mFragmentManager;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uv_list);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		if (findViewById(R.id.uv_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-sw720dp and
			// res/values-sw600dp-land). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;
		}

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mFragmentManager = getSupportFragmentManager();
		mSectionsPagerAdapter = new SectionsPagerAdapter(mFragmentManager);

		if (!mTwoPane) {
			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);

        	// When swiping between different sections, select the corresponding
			// tab. We can also use ActionBar.Tab#select() to do this if we have
			// a reference to the Tab.
			mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					actionBar.setSelectedNavigationItem(position);
				}
			});
		}

		Log.d(TAG, "onCreate, twoPane === " + String.valueOf(mTwoPane));

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

		if (mTwoPane) {
			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((UVListFragment) mFragmentManager.findFragmentById(R.id.uv_list)).configureListView();
		}
	}

	/**
	 * Callback method from {@link UVListFragment.Callbacks} indicating that the
	 * default DetailFragment must be displayed (no UV has been selected yet).
	 */
	@Override
	public void showDefaultDetailFragment() {
		if (mTwoPane) {
			// Default detail fragment management
			UVDetailDefaultFragment fragment = new UVDetailDefaultFragment();
			mFragmentManager.beginTransaction()
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
					.replace(R.id.uv_detail_container, fragment).commit();
		}
	}

	/**
	 * Callback method from {@link UVListFragment.Callbacks} indicating that the
	 * UV with the given ID was selected.
	 */
	@Override
	public void onItemSelected(final String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction, if the new item is not already displayed.
			Bundle arguments = new Bundle();
			arguments.putString(UVDetailFragment.ARG_UV_ID, id);
			UVDetailFragment fragment = new UVDetailFragment(true);
			fragment.setArguments(arguments);
			mFragmentManager.beginTransaction()
					.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
							android.R.anim.fade_in, android.R.anim.fade_out)
					.replace(R.id.uv_detail_container, fragment).commit();
		} else {
			Intent detailIntent = new Intent(this, UVDetailActivity.class);
			detailIntent.putExtra(UVDetailFragment.ARG_UV_ID, id);
			startActivity(detailIntent);
		}
	}


	/**
	 *
	 * @param tab The tab that was selected
	 * @param ft A {@link FragmentTransaction} for queuing fragment operations to execute
	 *        during a tab switch. The previous tab's unselect and this tab's select will be
	 *        executed in a single transaction. This FragmentTransaction does not support
	 */
	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		if (!mTwoPane) {
			mViewPager.setCurrentItem(tab.getPosition());
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment;
			switch(position) {
				case 0:
					fragment = new NewsFeedFragment();
					break;
				default:
					fragment = new UVListFragment();
					break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return getString(R.string.tab_title_feed).toUpperCase();
				case 1:
					return getString(R.string.tab_title_uvs).toUpperCase();
			}
			return null;
		}
	}
}
