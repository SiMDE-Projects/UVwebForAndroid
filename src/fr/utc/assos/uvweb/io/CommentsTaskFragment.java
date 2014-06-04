package fr.utc.assos.uvweb.io;

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
		private static final String URL = "http://s370803768.onlinehome.fr/uvweb/uvdetail.json";

		@Override
		protected List<UVwebContent.UVComment> doInBackground(String... params) {
			final JSONArray uvCommentsArray = HttpHelper.loadJSON(URL);
			if (uvCommentsArray == null || isCancelled()) return null;
			final int nUvComments = uvCommentsArray.length();

			final List<UVwebContent.UVComment> uvComments = new ArrayList<UVwebContent.UVComment>(nUvComments);

			try {
				for (int i = 0; !isCancelled() && i < nUvComments; i++) {
					final JSONObject uvCommentsInfo = (JSONObject) uvCommentsArray.get(i);
					String email; // Fake data to display images
					if ((i + 1) % 4 == 0) {
						email = "thomas.keunebroek@gmail.com";
					} else if ((i + 1) % 3 == 0) {
						email = "alexandre.masciulli@gmail.com";
					} else {
						email = "coucou@coucou.coucou";
					}
					uvComments.add(new UVwebContent.UVComment(
							uvCommentsInfo.getString("authorName"),
							email,
							"21/03/2012",
							uvCommentsInfo.getString("comment"),
							uvCommentsInfo.getInt("globalRate"),
							uvCommentsInfo.getString("semester")
					));
				}
			} catch (JSONException ignored) {
			}

			//SystemClock.sleep(4000);

			return uvComments;
		}
	}
}
