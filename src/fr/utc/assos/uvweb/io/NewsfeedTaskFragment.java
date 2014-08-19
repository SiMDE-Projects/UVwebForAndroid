package fr.utc.assos.uvweb.io;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.tkeunebr.androidlazyasync.acl.AsyncFragment;
import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.util.HttpHelper;

/**
 * A UI-less fragment that loads the uv list.
 */
public class NewsfeedTaskFragment extends AsyncFragment {
    private static final String TAG = "NewsfeedTaskFragment";

    public NewsfeedTaskFragment() {
	}

	@Override
	protected void execute() {
		new LoadNewsfeedTask().exec();
	}

	private final class LoadNewsfeedTask extends FragmentTask<Void, Void, List<UVwebContent.NewsFeedEntry>> {
		private static final String URL = "http://assos.utc.fr/uvweb/app/recentactivity";

		@Override
		protected List<UVwebContent.NewsFeedEntry> doInBackground(Void... params) {
			final String newsfeedEntriesString = HttpHelper.loadJSONString(URL);
			if (newsfeedEntriesString == null || isCancelled()) return null;

			final List<UVwebContent.NewsFeedEntry> newsfeedEntries = new ArrayList<UVwebContent.NewsFeedEntry>();

			try {

                JSONObject root = new JSONObject(newsfeedEntriesString);
                JSONArray newsfeedEntriesArray = root.getJSONArray("comments");

				for (int i = 0; !isCancelled() && i < newsfeedEntriesArray.length(); i++) {
					final JSONObject newsfeedEntryInfo = (JSONObject) newsfeedEntriesArray.get(i);

                    // Fake data to display images
                    String email = "coucou@coucou.coucou";

					newsfeedEntries.add(new UVwebContent.NewsFeedEntry(
							newsfeedEntryInfo.getString("identity"),
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
                Log.e(TAG, ignored.toString());
			}

			return newsfeedEntries;
		}
	}
}
