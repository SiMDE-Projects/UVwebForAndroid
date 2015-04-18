package fr.utc.assos.uvweb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {
    private static final int FRAGMENT_COUNT = 2;
    private static final int FRAGMENT_POSITION_NEWSFEED = 0;
    private static final int FRAGMENT_POSITION_UVLIST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager()));
    }

    private class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {
        public HomeFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FRAGMENT_POSITION_NEWSFEED:
                    return new Fragment();
                case FRAGMENT_POSITION_UVLIST:
                    return new Fragment();
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }
    }
}
