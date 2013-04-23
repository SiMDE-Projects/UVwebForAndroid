package fr.utc.assos.uvweb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;

import static fr.utc.assos.uvweb.util.LogUtils.makeLogTag;

/**
 * A wrapper fragment containing a nested {@link UVListFragment}. It is used in two-pane mode only
 * and ensures that the {@link UVListFragment} state is properly saved and restored.
 */
public class WrapperFragment extends SherlockFragment {
	private static final String TAG = makeLogTag(WrapperFragment.class);

	public WrapperFragment() {
	}

	public static WrapperFragment newInstance() {
		return new WrapperFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setHasOptionsMenu(true); // Workaround for https://code.google.com/p/android/issues/detail?id=45722
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_wrapper,
				container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if (savedInstanceState == null) {
			getChildFragmentManager()
					.beginTransaction()
					.replace(R.id.uv_list, UVListFragment.newInstance(true))
					.commit();
		}
	}
}