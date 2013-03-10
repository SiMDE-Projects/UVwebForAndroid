package fr.utc.assos.uvweb;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

/**
 * This class is a simple helper allowing to check for precise hardware configurations, thus
 * avoiding boilerplate in our code.
 */
public class ConfigHelper {
	/**
	 * @return true if the device has different UI configuration in portrait/landscape (like the Nexus 7)
	 * and if the @param orientation matches the device orientation
	 * otherwise, @return false
	 */
	public static boolean hasSeveralFragmentConfigurations(final Context context, final int orientation) {
		final Configuration config = context.getResources().getConfiguration();
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2 &&
				config.smallestScreenWidthDp == 600 && config.orientation == orientation;
	}

}
