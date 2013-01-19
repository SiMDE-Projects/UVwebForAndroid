package fr.utc.assos.uvweb;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import fr.utc.assos.uvweb.data.UVwebContent;

/**
 * A fragment representing a single UV detail screen. This fragment is either
 * contained in a {@link UVListActivity} in two-pane mode (on tablets) or a
 * {@link UVDetailActivity} on handsets.
 */
public class UVDetailFragment extends SherlockFragment {
	/**
	 * The fragment argument representing the UV ID that this fragment
	 * represents.
	 */
	public static final String ARG_UV_ID = "item_id";

	/**
	 * The UV this fragment is presenting.
	 */
	private UVwebContent.UV mUV;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public UVDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // Fragment configuration
        setRetainInstance(true);
        setHasOptionsMenu(true);

		if (getArguments().containsKey(ARG_UV_ID)) {
			// Load the UV specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mUV = UVwebContent.UV_MAP.get(getArguments().getString(
					ARG_UV_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_uv_detail,
				container, false);

		// Show the UV as text in a TextView.
		if (mUV != null) {
            ((TextView)rootView.findViewById(R.id.uvcode)).setText(Html.fromHtml(String.format(UVwebContent.UV_TITLE_FORMAT, mUV.getLetterCode(), mUV.getNumberCode())));
            ((TextView)rootView.findViewById(R.id.desc)).setText(mUV.getDescription());

            //Testing if null because rate view is not implemented for xlarge yet
            TextView rate = (TextView)rootView.findViewById(R.id.rate);
            if (rate != null) {
                rate.setText(mUV.getFormattedRate());
            }

		}

		return rootView;
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_uv_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                Toast.makeText(getActivity(), "Refresh clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
