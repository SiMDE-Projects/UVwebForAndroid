package fr.utc.assos.uvweb;

import android.util.SparseArray;
import android.view.View;

/**
 * A holder using the ViewHolder pattern. This class keeps ListView's UVs views references using tags,
 * which allows recycling. If the view is not stored yet, is it found using View#fiendViewById()
 * It also automatically cast the returned View in order to reduce boilerplate.
 * {@link UVAdapter}.
 */
public class UVHolder {
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}