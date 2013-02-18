package fr.utc.assos.uvweb;

import android.os.Looper;

/**
 * This helper class ensures that the caller objects performs its operations in the main thread,
 * otherwise it throws an exception.
 * {@link fr.utc.assos.uvweb.adapters.UVAdapter}.
 */
public class ThreadPreconditions {
	public static void checkOnMainThread() {
		if (BuildConfig.DEBUG) {
			if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
				throw new IllegalStateException("This method should be called from the Main Thread");
			}
		}
	}
}
