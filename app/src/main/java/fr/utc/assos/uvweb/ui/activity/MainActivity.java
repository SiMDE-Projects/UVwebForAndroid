package fr.utc.assos.uvweb.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.Locale;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.ui.fragment.NewsfeedFragment;
import fr.utc.assos.uvweb.ui.view.SlidingTabLayout;
import fr.utc.assos.uvweb.ui.fragment.UvListFragment;

public class MainActivity extends ToolbarActivity {
    private static final int FRAGMENT_COUNT = 2;
    private static final int FRAGMENT_POSITION_NEWSFEED = 0;
    private static final int FRAGMENT_POSITION_UVLIST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        PagerAdapter adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.white));
        slidingTabLayout.setDividerColors(getResources().getColor(android.R.color.transparent));
        slidingTabLayout.setCustomTabViewTextColor(getResources().getColor(R.color.white));
        slidingTabLayout.setViewPager(pager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {
        public HomeFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FRAGMENT_POSITION_NEWSFEED:
                    return new NewsfeedFragment();
                case FRAGMENT_POSITION_UVLIST:
                    return new UvListFragment();
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale locale = Locale.getDefault();
            switch (position) {
                case FRAGMENT_POSITION_NEWSFEED:
                    return getString(R.string.title_newsfeed).toUpperCase(locale);
                case FRAGMENT_POSITION_UVLIST:
                    return getString(R.string.title_uv_list).toUpperCase(locale);
            }
            return null;
        }
    }
}
