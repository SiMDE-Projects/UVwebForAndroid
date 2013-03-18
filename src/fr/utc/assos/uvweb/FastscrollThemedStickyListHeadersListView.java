package fr.utc.assos.uvweb;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;

/**
 * A very simple custome ListView that inherits {@link StickyListHeadersListView}.
 * It adds the fastscroll workaround to fix wrong fastscroll color on devices running Android < 3.0.
 * See https://gist.github.com/DHuckaby/d6b1d9c8e7f9d70c39de for more information.
 */
public class FastscrollThemedStickyListHeadersListView extends StickyListHeadersListView {
	public FastscrollThemedStickyListHeadersListView(Context context) {
		super(new ContextThemeWrapper(context, R.style.ListView_UVweb_fastScrollThemed));
	}

	public FastscrollThemedStickyListHeadersListView(Context context, AttributeSet attrs) {
		super(new ContextThemeWrapper(context, R.style.ListView_UVweb_fastScrollThemed), attrs);
	}
}
