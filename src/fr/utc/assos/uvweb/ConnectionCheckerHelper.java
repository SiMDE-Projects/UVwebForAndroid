package fr.utc.assos.uvweb;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * A helper class used to determine the current network state, i.e. online or not online
 */
public class ConnectionCheckerHelper {
	public static final Style NETWORK_ERROR_STYLE = new Style.Builder()
			.setBackgroundColorValue(Style.holoRedLight)
			.setDuration(2000)
			.setTextSize(16)
			.build();

	public static boolean isOnline(final Context context) {
		final ConnectivityManager connMgr = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}
}
