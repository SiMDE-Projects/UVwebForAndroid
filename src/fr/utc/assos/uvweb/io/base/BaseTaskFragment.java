package fr.utc.assos.uvweb.io.base;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * A base UI-less fragment to easily manage async queries while being tight
 * to the corresponding Activity or Fragment lifecycle.
 * If the attached Activity or Fragment gets destroyed by a configuration
 * change (like a rotation), the {@link Callbacks} reference needs to be updated
 * using the {@code setCallCallbacks()} method.
 */
public abstract class BaseTaskFragment extends SherlockFragment {
	public static final int THREAD_DEFAULT_POLICY = 0;
	public static final int THREAD_POOL_EXECUTOR_POLICY = 1;
	protected FragmentTask mTask;
	private Callbacks mCallbacks;
	private boolean mIsRunning;

	public BaseTaskFragment() {
	}

	/**
	 * This method will only be called once when the retained
	 * Fragment is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Retain this fragment across configuration changes.
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return null;
	}

	/**
	 * Set the callback to null so we don't accidentally leak the
	 * Fragment instance.
	 */
	@Override
	public void onDetach() {
		mCallbacks = null;

		super.onDetach();
	}

	protected abstract void start();

	protected abstract void startOnThreadPoolExecutor();

	// Public API
	public void startNewTask() {
		startNewTask(THREAD_DEFAULT_POLICY);
	}

	public void startNewTask(final int threadMode) {
		if (mTask != null) {
			mTask.cancel(true);
		}
		if (threadMode == THREAD_DEFAULT_POLICY) {
			start();
		} else if (threadMode == THREAD_POOL_EXECUTOR_POLICY) {
			startOnThreadPoolExecutor();
		} else {
			throw new IllegalArgumentException("threadMode must be either THREAD_DEFAULT_POLICY" +
					"or THREAD_POOL_EXECUTOR_POLICY");
		}
	}

	public void setCallbacks(Callbacks callbacks) {
		mCallbacks = callbacks;
	}

	public boolean isRunning() {
		return mIsRunning;
	}

	public interface Callbacks<Result> {
		void onPreExecute();

		void onCancelled();

		void onPostExecute(Result result);
	}

	public abstract class FragmentTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
		protected static final String API_ENDPOINT = "http://192.168.1.6/Uvweb/web/app_dev.php/uv/app/";
		@Override
		protected void onPreExecute() {
			if (mCallbacks != null) {
				mCallbacks.onPreExecute();
			}
			mIsRunning = true;
		}

		@Override
		protected void onCancelled() {
			mIsRunning = false;
			if (mCallbacks != null) {
				mCallbacks.onCancelled();
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		protected void onPostExecute(Result result) {
			mIsRunning = false;
			if (mCallbacks != null) {
				mCallbacks.onPostExecute(result);
			}
		}
	}
}
