package fr.utc.assos.uvweb;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;

/**
 * A list view that inherits StickyHeadersListView, in order to easily implement sticky headers.
 * It also implements the fastscroll workaround to fix wrong fastscroll color on devices running Android < 3.0.
 * See https://gist.github.com/DHuckaby/d6b1d9c8e7f9d70c39de for more information.
 */
public class FastscrollThemedStickyListHeadersListView extends StickyListHeadersListView {
	public FastscrollThemedStickyListHeadersListView(Context context) {
		super(new ContextThemeWrapper(context, R.style.FastscrollThemedListView));
	}

	public FastscrollThemedStickyListHeadersListView(Context context, AttributeSet attrs) {
		super(new ContextThemeWrapper(context, R.style.FastscrollThemedListView), attrs);
	}
}
