package fr.utc.assos.uvweb.io;

import android.os.SystemClock;

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
		private static final String URL = API_ENDPOINT + "newsfeed";

		@Override
		protected List<UVwebContent.NewsFeedEntry> doInBackground(Void... params) {
			final JSONArray newsfeedEntriesArray = HttpHelper.loadJSON(URL);
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

			SystemClock.sleep(4000);

			return newsfeedEntries;
		}
	}
}
