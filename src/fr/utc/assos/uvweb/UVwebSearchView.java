package fr.utc.assos.uvweb;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import com.actionbarsherlock.widget.SearchView;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 13/03/13
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
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
