package fr.utc.assos.uvweb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 25/01/13
 * Time: 20:19
 * To change this template use File | Settings | File Templates.
 */
public class UVDetailDefaultFragment extends SherlockFragment {
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UVDetailDefaultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_uv_detail_default,
                container, false);
    }
}
