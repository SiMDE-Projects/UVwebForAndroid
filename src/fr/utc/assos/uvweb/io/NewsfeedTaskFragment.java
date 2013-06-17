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
		//private static final String API_URL = "http://thomaskeunebroek.fr/newsfeed.json";
		private static final String API_URL =  "http://192.168.1.5/UVweb/web/app_dev.php/app/newsfeed";

		@Override
		protected List<UVwebContent.NewsFeedEntry> doInBackground(Void... params) {
			final JSONArray newsfeedEntriesArray = HttpHelper.loadJSON(API_URL);
			if (newsfeedEntriesArray == null || isCancelled()) return null;
			final int nNewsfeedEntries = newsfeedEntriesArray.length();

			final List<UVwebContent.NewsFeedEntry> newsfeedEntries = new ArrayList<UVwebContent.NewsFeedEntry>(nNewsfeedEntries);

			try {
				for (int i = 0; !isCancelled() && i < nNewsfeedEntries; i++) {
					final JSONObject newsfeedEntryInfo = (JSONObject) newsfeedEntriesArray.get(i);
					String email; // Fake data to display images
					if ((i + 1) % 4 == 0) {
						email = "thomas.keunebroek@gmail.com";
					} else if ((i + 1) % 3 == 0) {
						email = "alexandre.masciulli@gmail.com";
					} else {
						email = "coucou@coucou.coucou";
					}
					newsfeedEntries.add(new UVwebContent.NewsFeedEntry(
							newsfeedEntryInfo.getString("authorName"),
							//newsfeedEntryInfo.getString("email"),
							email,
							//newsfeedEntryInfo.getString("date"),
							"21/03/2012",
							newsfeedEntryInfo.getString("comment"),
							//newsfeedEntryInfo.getString("action")
							" a posté un commentaire"
					));
				}
			} catch (JSONException ignored) {
			}

			//SystemClock.sleep(4000);

			return newsfeedEntries;
		}
	}
}
