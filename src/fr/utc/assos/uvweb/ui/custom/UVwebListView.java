package fr.utc.assos.uvweb.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import fr.utc.assos.uvweb.R;

/**
 * A very simple custom ListView that inherits {@link StickyListHeadersListView}.
 * It adds the fastscroll workaround to fix wrong fastscroll color on devices running Android < 3.0.
 * See https://gist.github.com/DHuckaby/d6b1d9c8e7f9d70c39de for more information.
 */
public class UVwebListView extends StickyListHeadersListView {
	public UVwebListView(Context context) {
		super(new ContextThemeWrapper(context, R.style.ListView_UVweb_fastScrollThemed));
	}

	public UVwebListView(Context context, AttributeSet attrs) {
		super(new ContextThemeWrapper(context, R.style.ListView_UVweb_fastScrollThemed), attrs);
	}
}
