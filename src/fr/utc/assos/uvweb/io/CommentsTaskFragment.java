package fr.utc.assos.uvweb.io;

import android.os.SystemClock;
import android.support.v4.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.io.base.BaseTaskFragment;
import fr.utc.assos.uvweb.util.HttpHelper;
import fr.utc.assos.uvweb.util.ThreadedAsyncTaskHelper;

/**
 * A UI-less fragment that loads the comments of the corresponding {@code mUvId}.
 */
public class CommentsTaskFragment extends BaseTaskFragment {
	private static final String COMMENTS_TASK_TAG = "CommentsTaskFragment_TAG";
	private String mUvId;

	// Public API
	public static CommentsTaskFragment get(FragmentManager fm, Callbacks cb, String uvId) {
		CommentsTaskFragment commentsTaskFragment =
				(CommentsTaskFragment) fm.findFragmentByTag(COMMENTS_TASK_TAG);
		if (commentsTaskFragment == null) {
			commentsTaskFragment = new CommentsTaskFragment();
			fm.beginTransaction().add(commentsTaskFragment, COMMENTS_TASK_TAG).commit();
		}
		commentsTaskFragment.setCallbacks(cb);
		commentsTaskFragment.setUvId(uvId);
		return commentsTaskFragment;
	}

	public String getUvId() {
		return mUvId;
	}

	public void setUvId(String uvId) {
		mUvId = uvId;
	}

	@Override
	protected void start() {
		mTask = new LoadUvCommentsTask();
		((LoadUvCommentsTask) mTask).execute(mUvId);
	}

	@Override
	protected void startOnThreadPoolExecutor() {
		mTask = new LoadUvCommentsTask();
		ThreadedAsyncTaskHelper.execute((LoadUvCommentsTask) mTask, mUvId);
	}

	private final class LoadUvCommentsTask extends FragmentTask<String, Void, List<UVwebContent.UVComment>> {
		private static final String API_URL = "http://192.168.1.5/Uvweb/web/app_dev.php/uv/app/";

		@Override
		protected List<UVwebContent.UVComment> doInBackground(String... params) {
			final JSONArray uvCommentsArray = HttpHelper.loadJSON(API_URL + params[0]);
			if (isCancelled()) return Collections.emptyList(); // TODO: improve
			if (uvCommentsArray == null) return null;
			final int nUvComments = uvCommentsArray.length();

			final List<UVwebContent.UVComment> uvComments = new ArrayList<UVwebContent.UVComment>(nUvComments);

			try {
				for (int i = 0; !isCancelled() && i < nUvComments; i++) {
					final JSONObject uvCommentsInfo = (JSONObject) uvCommentsArray.get(i);
					String email; // Fake data to display images
					if ((i+1) % 4 == 0) {
						email = "thomas.keunebroek@gmail.com";
					} else if ((i+1) % 3 == 0) {
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

			SystemClock.sleep(4000);

			return uvComments;
		}
	}
}
