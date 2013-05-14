package fr.utc.assos.uvweb.activities;

import android.os.Bundle;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.ui.UVDetailFragment;


/**
 * An activity representing a single UV detail screen. This activity is only
 * used on handset devices. On tablet-size devices, UV details are presented
 * side-by-side with a list of UVs in a {@link MainActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link fr.utc.assos.uvweb.ui.UVDetailFragment}.
 */
public class UVDetailActivity extends UVwebActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uv_detail);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			getSupportFragmentManager().beginTransaction()
					.add(R.id.uv_detail_container,
							UVDetailFragment.newInstance(getIntent().getParcelableExtra(UVDetailFragment.ARG_UV_ID),
									false))
					.commit();
		}
	}
}
