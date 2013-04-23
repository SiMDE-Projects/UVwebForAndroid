package fr.utc.assos.uvweb.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import com.actionbarsherlock.widget.SearchView;
import fr.utc.assos.uvweb.R;

import static fr.utc.assos.uvweb.util.LogUtils.LOGD;
import static fr.utc.assos.uvweb.util.LogUtils.makeLogTag;

/**
 * A very simple custom {@link SearchView} that allows to block some callbacks from being automatically called.
 * This is particularly useful on tablets (two-pane mode), as we don't want the SearchView to clear it's
 * content when we open an {@link UVDetailFragment}, which contains optionsMenu of its own.
 */
public class UVwebSearchView extends SearchView {
	private static final String TAG = makeLogTag(UVwebSearchView.class);
	private boolean mIsLoadingUV = true;

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
		LOGD(TAG, "onActionViewExpanded called, mIsLoadingUV == " + String.valueOf(mIsLoadingUV));
		if (mIsLoadingUV) {
			setIconified(false);
			clearFocus();
		} else {
			super.onActionViewExpanded();
		}
	}

	public void setIsLoadingUV(boolean isLoadingUV) {
		LOGD(TAG, "setIsLoadingUV called, isLoadingUV = " + isLoadingUV);
		mIsLoadingUV = isLoadingUV;
	}

	private void setupSearchView() {
		setQueryHint(getResources().getString(R.string.search_uv_hint));
	}
}
