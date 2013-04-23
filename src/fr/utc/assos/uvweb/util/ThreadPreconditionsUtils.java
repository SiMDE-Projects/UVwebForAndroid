package fr.utc.assos.uvweb.util;

import android.os.Looper;
import fr.utc.assos.uvweb.BuildConfig;

/**
 * This helper class ensures that the caller objects performs its operations in the main thread,
 * otherwise it throws an exception.
 * {@link fr.utc.assos.uvweb.adapters.UVAdapter}.
 */
public class ThreadPreconditionsUtils {
	public static void checkOnMainThread() {
		if (BuildConfig.DEBUG) {
			if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
				throw new IllegalStateException("This method should be called from the Main Thread");
			}
		}
	}
}
