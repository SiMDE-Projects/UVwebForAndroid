package fr.utc.assos.uvweb.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.ui.base.UVwebFragment;

/**
 * A fragment representing the default detail screen. This fragment is
 * contained in a {@link fr.utc.assos.uvweb.activities.MainActivity} and
 * used in tablet-mode only.
 */
public class UVDetailDefaultFragment extends UVwebFragment {
	public static final String DEFAULT_FRAGMENT_TAG = "UVDetailDefaultFragment_TAG";

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public UVDetailDefaultFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		return inflater.inflate(R.layout.fragment_uv_detail_default,
				container, false);
	}
}
