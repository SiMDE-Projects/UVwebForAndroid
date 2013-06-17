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
	public static final int THREAD_DEFAULT = 0;
	public static final int THREAD_POOL_EXECUTOR = 1;
	private final int mThreadMode;
	protected FragmentTask mTask;
	private Callbacks mCallbacks;
	private boolean mIsRunning;

	public BaseTaskFragment() {
		this(THREAD_DEFAULT);
	}

	public BaseTaskFragment(final int threadMode) {
		if (threadMode != THREAD_DEFAULT && threadMode != THREAD_POOL_EXECUTOR) {
			throw new IllegalArgumentException("threadMode must be either THREAD_DEFAULT or THREAD_POOL_EXECUTOR");
		}
		mThreadMode = threadMode;
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

		if (mThreadMode == THREAD_DEFAULT) {
			startNewTask();
		} else if (mThreadMode == THREAD_POOL_EXECUTOR) {
			startNewTaskOnThreadPoolExecutor();
		}
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
		if (mIsRunning && mTask != null) {
			mTask.cancel(true);
			mIsRunning = false;
		}
		start();
	}

	public void startNewTaskOnThreadPoolExecutor() {
		if (mIsRunning && mTask != null) {
			mTask.cancel(true);
			mIsRunning = false;
		}
		startOnThreadPoolExecutor();
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
		@Override
		protected void onPreExecute() {
			mIsRunning = true;
			if (mCallbacks != null) {
				mCallbacks.onPreExecute();
			}
		}

		@Override
		protected void onCancelled() {
			if (mCallbacks != null) {
				mCallbacks.onCancelled();
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		protected void onPostExecute(Result result) {
			if (mCallbacks != null) {
				mCallbacks.onPostExecute(result);
			}
			mIsRunning = false;
		}
	}
}
