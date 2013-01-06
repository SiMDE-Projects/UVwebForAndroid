package fr.utc.assos.uvweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import fr.utc.assos.uvweb.data.UVwebContent;

/**
 * An activity representing a single UV detail screen. This activity is only
 * used on handset devices. On tablet-size devices, UV details are presented
 * side-by-side with a list of UVs in a {@link UVListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link UVDetailFragment}.
 */
public class UVDetailActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_uv_detail);
        // If we want to override default Activity animation
        //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        super.onCreate(savedInstanceState);

		// Show the Up button in the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Show the UV title
        String uvId = getIntent().getStringExtra(UVDetailFragment.ARG_UV_ID);
        actionBar.setTitle(UVwebContent.UV_MAP.get(uvId).toString());

        // savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		// http://developer.android.com/guide/components/fragments.html
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(UVDetailFragment.ARG_UV_ID, uvId);
			UVDetailFragment fragment = new UVDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.uv_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this, new Intent(this, UVListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
