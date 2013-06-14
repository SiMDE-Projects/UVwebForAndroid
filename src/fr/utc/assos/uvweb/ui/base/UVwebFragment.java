package fr.utc.assos.uvweb.ui.base;

import android.app.Activity;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;

import java.util.concurrent.atomic.AtomicBoolean;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.LifecycleCallback;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.util.ConnectionUtils;

/**
 * Base Fragment that must be extended by any {@link SherlockFragment} that has a UI.
 */
public abstract class UVwebFragment extends SherlockFragment implements LifecycleCallback {
    protected static final String STATE_NETWORK_ERROR = "network_error";
    private static final AtomicBoolean sIsNetworkCroutonDisplayed = new AtomicBoolean(false);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            sIsNetworkCroutonDisplayed.set(false); // Workaround as the Crouton LifecycleCallback isn't called
            // during a configuration change (like a screen rotation for instance)
        }
    }

    protected void handleNetworkError() {
        handleNetworkError(getSherlockActivity());
    }

    protected void handleNetworkError(Activity context) {
        if (!sIsNetworkCroutonDisplayed.get()) {
            sIsNetworkCroutonDisplayed.set(true);
            final Crouton networkCrouton = Crouton.makeText(context,
                    context.getString(R.string.network_error_message),
                    ConnectionUtils.NETWORK_ERROR_STYLE);
            networkCrouton.setLifecycleCallback(this);
            networkCrouton.show();
        }
    }

    @Override
    public void onDestroy() {
        Crouton.clearCroutonsForActivity(getSherlockActivity());

        super.onDestroy();
    }

    @Override
    public void onDisplayed() {
    }

    @Override
    public void onRemoved() {
        sIsNetworkCroutonDisplayed.set(false);
    }
}
