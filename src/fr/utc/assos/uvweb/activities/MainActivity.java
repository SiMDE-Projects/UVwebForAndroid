package fr.utc.assos.uvweb.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.ActionBar;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.ui.NewsFeedFragment;
import fr.utc.assos.uvweb.ui.UVDetailDefaultFragment;
import fr.utc.assos.uvweb.ui.UVDetailFragment;
import fr.utc.assos.uvweb.ui.UVListFragment;
import fr.utc.assos.uvweb.ui.custom.WrapperFragment;

import static fr.utc.assos.uvweb.util.LogUtils.makeLogTag;

/**
 * An activity representing a list of UVs. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of UVs, which when touched, lead to a
 * {@link fr.utc.assos.uvweb.activities.UVDetailActivity} representing UV details. On tablets, the activity
 * presents the list of UVs and UV details side-by-side using two vertical
 * panes.
 * The activity makes heavy use of fragments. The list of UVs is a
 * {@link fr.utc.assos.uvweb.ui.UVListFragment} and the UV details (if present) is a
 * {@link fr.utc.assos.uvweb.ui.UVDetailFragment}.
 * This activity also implements the required {@link fr.utc.assos.uvweb.ui.UVListFragment.Callbacks}
 * interface to listen for UV selections.
 */

public class MainActivity extends UVwebMenuActivity implements
		UVListFragment.Callbacks, ActionBar.TabListener {
	private static final String TAG = makeLogTag(MainActivity.class);
	private static final String PERSISTENT_LAST_TAB = "UVwebLastTab";
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private SharedPreferences mPrefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uv_list);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(false);

		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		mTwoPane = findViewById(R.id.uv_container) != null;
		// The detail container view will be present only in the
		// large-screen layouts (res/values-sw600dp). If this view is present, then the
		// activity should be in two-pane mode.

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);

		// When swiping between different sections, select the corresponding
		// tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
				saveLastTabPreference(position);
			}
		});

		// Create the adapter that will return a fragment for each of the two
		// primary sections of the app.
		mViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), mTwoPane));

		// We add our tabs
		actionBar.addTab(actionBar.newTab()
				.setText(getString(R.string.tab_title_feed))
				.setTabListener(this));

		actionBar.addTab(actionBar.newTab()
				.setText(getString(R.string.tab_title_uvs))
				.setTabListener(this));

		// Restore saved tab position.
		actionBar.setSelectedNavigationItem(loadLastTabPreference());
	}

	/**
	 * Callback method from {@link UVListFragment.Callbacks} indicating that the
	 * default DetailFragment must be displayed (no UV has been selected yet).
	 */
	@Override
	public void showDefaultDetailFragment() {
		if (mTwoPane) {
			// Default detail fragment management
			final FragmentManager fm = getSupportFragmentManager();
			if (fm.findFragmentByTag(UVDetailDefaultFragment.DEFAULT_FRAGMENT_TAG) == null) {
				fm.beginTransaction().replace(R.id.uv_detail_container,
						new UVDetailDefaultFragment(),
						UVDetailDefaultFragment.DEFAULT_FRAGMENT_TAG).commit();
			}
		}
	}

	/**
	 * Callback method from {@link UVListFragment.Callbacks} indicating that the
	 * UV with the given ID was selected.
	 */
	@Override
	public void onItemSelected(UVwebContent.UV uv) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction, if the new item is not already displayed.
			getSupportFragmentManager().beginTransaction()
					.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
							android.R.anim.fade_in, android.R.anim.fade_out)
					.replace(R.id.uv_detail_container, UVDetailFragment.newInstance(uv, true))
					.commit();
		} else {
			final Intent detailIntent = new Intent(this, UVDetailActivity.class);
			detailIntent.putExtra(UVDetailFragment.ARG_UV_ID, uv);
			startActivity(detailIntent);
		}
	}

	/**
	 * Last selected tab save and restore management, using SharedPreferences
	 */
	private void saveLastTabPreference(int tab) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			mPrefs.edit().putInt(PERSISTENT_LAST_TAB, tab).apply();
		} else {
			mPrefs.edit().putInt(PERSISTENT_LAST_TAB, tab).commit();
		}
	}

	private int loadLastTabPreference() {
		try {
			return mPrefs.getInt(PERSISTENT_LAST_TAB, 0);
		} catch (IllegalArgumentException e) {
			// Preference is corrupt?
			return 0;
		}
	}

	/**
	 * {@link ActionBar.TabListener}'s interface.
	 * For each callback, method:
	 *
	 * @param tab The tab that was selected
	 * @param ft  A {@link FragmentTransaction} for queuing fragment operations to execute
	 *            during a tab switch. The previous tab's unselect and this tab's select will be
	 *            executed in a single transaction.
	 */
	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
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
	private static class SectionsPagerAdapter extends FragmentPagerAdapter {
		private final boolean mTwoPane;

		public SectionsPagerAdapter(FragmentManager fm, boolean twoPane) {
			super(fm);
			mTwoPane = twoPane;
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			switch (position) {
				case 0:
					return new NewsFeedFragment();
				default:
					return mTwoPane ? WrapperFragment.newInstance() : UVListFragment.newInstance();
			}
		}

		@Override
		public int getCount() {
			return 2;
		}
	}
}
