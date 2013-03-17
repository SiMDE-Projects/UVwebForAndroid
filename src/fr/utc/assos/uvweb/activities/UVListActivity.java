package fr.utc.assos.uvweb.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.internal.app.ActionBarWrapper;
import fr.utc.assos.uvweb.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
	private static final String PERSISTENT_LAST_TAB = "UVweb.LastTab";
	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane = false;
	/**
	 * A reference to the Activity's own FragmentManager
	 */
	private FragmentManager mFragmentManager;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private ActionBar mActionBar;
	private int mCurrentTab = 0;
	private SharedPreferences mPrefs;
	private UVListFragment mUVListFragment;
	private NewsFeedFragment mNewsFeedFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uv_list);

		mFragmentManager = getSupportFragmentManager();

		mActionBar = getSupportActionBar();
		mActionBar.setHomeButtonEnabled(false);

		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		if (findViewById(R.id.uv_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-sw720dp and
			// res/values-sw600dp-land). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;
		}

		if (!mTwoPane) {
			// Create the adapter that will return a fragment for each of the two
			// primary sections of the app.
			SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(mFragmentManager);

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(sectionsPagerAdapter);

			// When swiping between different sections, select the corresponding
			// tab.
			mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					mActionBar.setSelectedNavigationItem(position);
					saveLastTabPreference(position);
				}
			});
		}

		mActionBar.addTab(mActionBar.newTab()
				.setText(getString(R.string.tab_title_feed))
				.setTabListener(new UVwebTabListener()));

		mActionBar.addTab(mActionBar.newTab()
				.setText(getString(R.string.tab_title_uvs))
				.setTabListener(new UVwebTabListener()));

		if (mTwoPane) {
			setTabsAreEmbedded(mActionBar, true);
		}

		mCurrentTab = loadLastTabPreference();
		mActionBar.setSelectedNavigationItem(mCurrentTab);
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
					.replace(R.id.uv_detail_container, fragment)
					.commit();
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
					.replace(R.id.uv_detail_container, fragment)
					.commit();
		} else {
			Intent detailIntent = new Intent(this, UVDetailActivity.class);
			detailIntent.putExtra(UVDetailFragment.ARG_UV_ID, id);
			startActivity(detailIntent);
		}
	}

	/**
	 * This private method uses Reflection to try and set the ActionBar's Tabs to the @param embedded
	 * By default Android sets embedded tabs in landscape only. We wan't them be embedded in portrait as well,
	 * for a nicer look and feel.
	 * It is called in two-pane mode only.
	 */
	private void setTabsAreEmbedded(Object actionBar, final boolean embedded) {
		try {
			if (actionBar instanceof ActionBarWrapper) {
				// ICS and forward
				try {
					final Field actionBarField = actionBar.getClass()
							.getDeclaredField("mActionBar");
					actionBarField.setAccessible(true);
					actionBar = actionBarField.get(actionBar);
				} catch (Exception e) {
					Log.e("", "Error enabling embedded tabs", e);
				}
			}
			final Method setHasEmbeddedTabsMethod = actionBar.getClass()
					.getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
			setHasEmbeddedTabsMethod.setAccessible(true);
			setHasEmbeddedTabsMethod.invoke(actionBar, embedded);
		} catch (Exception e) {
			Log.e("", "Error marking actionbar embedded", e);
		}
	}

	/**
	 * Last selected tab save and restore management, using SharedPreferences
	 */
	private void saveLastTabPreference(int tab) {
		mCurrentTab = tab;
		mPrefs.edit().putInt(PERSISTENT_LAST_TAB, tab).apply();
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
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 * It is used in phone-mode only (twoPane = false).
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			Fragment fragment;
			switch (position) {
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
			// We handle the page titles manually
			return null;
		}
	}

	private final class UVwebTabListener implements ActionBar.TabListener {
		/**
		 * @param tab The tab that was selected
		 * @param ft  A {@link FragmentTransaction} for queuing fragment operations to execute
		 *            during a tab switch. The previous tab's unselect and this tab's select will be
		 *            executed in a single transaction.
		 */
		@Override
		public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
			final int position = tab.getPosition();
			if (mTwoPane) {
				if (position != mCurrentTab) {
					switch (position) {
						case 0:
							if (mUVListFragment != null) {
								ft.hide(mUVListFragment);
								ft.hide(mFragmentManager.findFragmentById(R.id.uv_detail_container));
							}
							if (mNewsFeedFragment == null) {
								mNewsFeedFragment = new NewsFeedFragment();
								ft.add(android.R.id.content, mNewsFeedFragment);
							} else {
								ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
										android.R.anim.fade_in, android.R.anim.fade_out);
								ft.attach(mNewsFeedFragment);
							}

							break;
						default:
							if (mNewsFeedFragment != null) {
								ft.hide(mNewsFeedFragment);
							}
							if (mUVListFragment == null) {
								mUVListFragment = new UVListFragment(true);
								ft.add(R.id.uv_list_container, mUVListFragment);
							} else {
								ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
										android.R.anim.fade_in, android.R.anim.fade_out);
								ft.attach(mUVListFragment);
								ft.show(mFragmentManager.findFragmentById(R.id.uv_detail_container));
							}
							break;
					}
					saveLastTabPreference(position);
				}
			} else {
				mViewPager.setCurrentItem(position);
			}
		}

		@Override
		public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		}
	}
}
