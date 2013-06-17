package fr.utc.assos.uvweb.io;

import android.support.v4.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.io.base.BaseTaskFragment;
import fr.utc.assos.uvweb.util.HttpHelper;
import fr.utc.assos.uvweb.util.ThreadedAsyncTaskHelper;

/**
 * A UI-less fragment that loads the uv list.
 */
public class NewsfeedTaskFragment extends BaseTaskFragment {
	private static final String NEWSFEED_TASK_TAG = "NewsfeedTaskFragment_TAG";

	// Public API
	public static NewsfeedTaskFragment get(FragmentManager fm, Callbacks cb) {
		NewsfeedTaskFragment newsfeedTaskFragment =
				(NewsfeedTaskFragment) fm.findFragmentByTag(NEWSFEED_TASK_TAG);
		if (newsfeedTaskFragment == null) {
			newsfeedTaskFragment = new NewsfeedTaskFragment();
			fm.beginTransaction().add(newsfeedTaskFragment, NEWSFEED_TASK_TAG).commit();
		}
		newsfeedTaskFragment.setCallbacks(cb);
		return newsfeedTaskFragment;
	}

	@Override
	protected void start() {
		mTask = new LoadNewsfeedTask();
		((LoadNewsfeedTask) mTask).execute();
	}

	@Override
	protected void startOnThreadPoolExecutor() {
		mTask = new LoadNewsfeedTask();
		ThreadedAsyncTaskHelper.execute((LoadNewsfeedTask) mTask);
	}

	private final class LoadNewsfeedTask extends FragmentTask<Void, Void, List<UVwebContent.NewsFeedEntry>> {
		private static final String API_URL = "http://thomaskeunebroek.fr/newsfeed.json";

		@Override
		protected List<UVwebContent.NewsFeedEntry> doInBackground(Void... params) {
			final JSONArray newsfeedEntriesArray = HttpHelper.loadJSON(API_URL);
			if (newsfeedEntriesArray == null) return null; // TODO: improve
			final int nNewsfeedEntries = newsfeedEntriesArray.length();

			final List<UVwebContent.NewsFeedEntry> newsfeedEntries = new ArrayList<UVwebContent.NewsFeedEntry>(nNewsfeedEntries);

			try {
				for (int i = 0; i < nNewsfeedEntries; i++) {
					final JSONObject newsfeedEntryInfo = (JSONObject) newsfeedEntriesArray.get(i);
					newsfeedEntries.add(new UVwebContent.NewsFeedEntry(
							newsfeedEntryInfo.getString("author"),
							newsfeedEntryInfo.getString("email"),
							newsfeedEntryInfo.getString("date"),
							newsfeedEntryInfo.getString("content"),
							newsfeedEntryInfo.getString("action")
					));
				}
			} catch (JSONException ignored) {
			}

			//SystemClock.sleep(4000);

			return newsfeedEntries;
		}
	}
}
