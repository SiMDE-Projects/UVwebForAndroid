package fr.utc.assos.uvweb;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 28/02/13
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
public class FastscrollThemedStickyListHeadersListView extends StickyListHeadersListView {
	public FastscrollThemedStickyListHeadersListView(Context context) {
		super(new ContextThemeWrapper(context, R.style.FastscrollThemedListView));
	}

	public FastscrollThemedStickyListHeadersListView(Context context, AttributeSet attrs) {
		super(new ContextThemeWrapper(context, R.style.FastscrollThemedListView), attrs);
	}
}
