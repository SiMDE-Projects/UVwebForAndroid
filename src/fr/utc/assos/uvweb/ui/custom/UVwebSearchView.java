package fr.utc.assos.uvweb.ui.custom;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import com.actionbarsherlock.widget.SearchView;

import fr.utc.assos.uvweb.R;

import static fr.utc.assos.uvweb.util.LogUtils.makeLogTag;

/**
 * A very simple custom {@link SearchView} that allows to block some callbacks from being automatically called.
 * This is particularly useful on tablets (two-pane mode), as we don't want the SearchView to clear it's
 * content when we open an {@link fr.utc.assos.uvweb.ui.UVDetailFragment}, which contains optionsMenu of its own.
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
		if (mIsLoadingUV) {
			setIconified(false);
			clearFocus();
		} else {
			super.onActionViewExpanded();
		}
	}

	public void setIsLoadingUV(boolean isLoadingUV) {
		mIsLoadingUV = isLoadingUV;
	}

	private void setupSearchView() {
		final Resources resources = getResources();
		if (resources != null) {
			setQueryHint(resources.getString(R.string.search_uv_hint));
		}
	}
}
