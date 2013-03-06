package fr.utc.assos.uvweb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/**
 * This is the BaseAdapter used through the whole application.
 * It simply initializes the required LayoutInflater and defines
 * mandatory BaseAdapter methods that won't be used
 */
public abstract class UVAdapter extends BaseAdapter {
	protected final LayoutInflater mLayoutInflater;

	public UVAdapter(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
