package fr.utc.assos.uvweb.holders;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import fr.utc.assos.uvweb.R;

/**
 * A holder using the ViewHolder pattern. This class keeps ListView's headers views references using tags,
 * which allows recycling. If the view is not stored yet, is it found using View#fiendViewById()
 * It automatically casts the resulting View into a TextView, since we know the View hierarchy contains also one TextView
 * {@link fr.utc.assos.uvweb.UVAdapter}.
 */
public class UVHeaderHolder {
    @SuppressWarnings("unchecked")
    public static TextView get(View view) {
        SparseArray<TextView> viewHolder = (SparseArray<TextView>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<TextView>();
            view.setTag(viewHolder);
        }
        TextView sectionView = viewHolder.get(R.id.header_text);
        if (sectionView == null) {
            sectionView = (TextView) view.findViewById(R.id.header_text);
            viewHolder.put(R.id.header_text, sectionView);
        }
        return sectionView;
    }
}
