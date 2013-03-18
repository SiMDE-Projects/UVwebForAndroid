package fr.utc.assos.uvweb;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import com.actionbarsherlock.widget.SearchView;

/**
 * A very simple custom {@link SearchView} that allows to block some callbacks from being automatically called.
 * This is particularly useful on tablets (two-pane mode), as we don't want the SearchView to clear it's
 * content when we open an {@link UVDetailFragment}, which contains optionsMenu of its own.
 */
public class UVwebSearchView extends SearchView {
	private static final String TAG = "UVwebSearchViewTest";
	private boolean mIsLoadingUV = false;

	public UVwebSearchView(Context context) {
		super(context);
		setupSearchView();
	}
	public UVwebSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupSearchView();
	}

	@Override
	public void onActionViewExpanded() {
		Log.d(TAG, "onActionViewExpanded called, mIsLoadingUV == " + String.valueOf(mIsLoadingUV));
		if (mIsLoadingUV) {
			setIconified(false);
			clearFocus();
		}
		else {
			super.onActionViewExpanded();
		}
	}

	public void setIsLoadingUV(boolean isLoadingUV) {
		Log.d(TAG, "setIsLoadingUV called with isLoadingUV == " + String.valueOf(isLoadingUV));
		if (mIsLoadingUV != isLoadingUV) {
			mIsLoadingUV = isLoadingUV;
		}
	}

	private void setupSearchView() {
		setQueryHint(getResources().getString(R.string.search_uv_hint));
	}
}
