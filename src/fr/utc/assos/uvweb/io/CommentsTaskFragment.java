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
 * A UI-less fragment that loads the comments of the corresponding {@code mUvId}.
 */
public class CommentsTaskFragment extends AsyncFragment {
    private static final String TAG = "CommentsTaskFragment";
    private String mUvId;

	public CommentsTaskFragment() {
	}

	// Public API
	public String getUvId() {
		return mUvId;
	}

	public void setUvId(String uvId) {
		mUvId = uvId;
	}

	@Override
	protected void execute() {
		new LoadUvCommentsTask().exec(mUvId);
	}

	private final class LoadUvCommentsTask extends FragmentTask<String, Void, List<UVwebContent.UVComment>> {
		private static final String URL = "https://assos.utc.fr/uvweb/uv/app/details/";

		@Override
		protected List<UVwebContent.UVComment> doInBackground(String... params) {
			final String uvCommentsString = HttpHelper.loadJSONString(URL + params[0]);
			if (uvCommentsString == null || isCancelled()) return null;

			final List<UVwebContent.UVComment> uvComments = new ArrayList<UVwebContent.UVComment>();

			try {
                JSONObject root = new JSONObject(uvCommentsString);
                JSONObject details = root.getJSONObject("details");
                JSONArray uvCommentsArray = details.getJSONArray("comments");


				for (int i = 0; !isCancelled() && i < uvCommentsArray.length(); i++) {
					final JSONObject uvCommentsInfo = (JSONObject) uvCommentsArray.get(i);
					String email = "coucou@coucou.com"; // Fake data to display images
					uvComments.add(new UVwebContent.UVComment(
							uvCommentsInfo.getString("identity"),
							email,
							"21/03/2012",
							uvCommentsInfo.getString("comment"),
							uvCommentsInfo.getInt("globalRate"),
							uvCommentsInfo.getString("semester")
					));
				}
			} catch (JSONException ignored) {
                Log.e(TAG, ignored.toString());
			}

			return uvComments;
		}
	}
}
