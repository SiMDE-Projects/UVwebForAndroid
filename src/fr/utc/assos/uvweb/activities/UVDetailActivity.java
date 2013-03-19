package fr.utc.assos.uvweb.activities;

import android.os.Bundle;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.UVDetailFragment;

/**
 * An activity representing a single UV detail screen. This activity is only
 * used on handset devices. On tablet-size devices, UV details are presented
 * side-by-side with a list of UVs in a {@link UVListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link fr.utc.assos.uvweb.UVDetailFragment}.
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
			Bundle arguments = new Bundle();
			arguments.putString(UVDetailFragment.ARG_UV_ID, getIntent().getStringExtra(UVDetailFragment.ARG_UV_ID));
			UVDetailFragment fragment = new UVDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.uv_detail_container, fragment).commit();
		}
	}

	@Override
	public void onDestroy() {
		Crouton.cancelAllCroutons();

		super.onDestroy();
	}
}
