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
public class UvListTaskFragment extends BaseTaskFragment {
	@Override
	protected void start() {
		mTask = new LoadUvListTask();
		((LoadUvListTask) mTask).execute();
	}

	@Override
	protected void startOnThreadPoolExecutor() {
		mTask = new LoadUvListTask();
		ThreadedAsyncTaskHelper.execute((LoadUvListTask) mTask);
	}

	private final class LoadUvListTask extends FragmentTask<Void, Void, List<UVwebContent.UV>> {
		private static final String URL = API_ENDPOINT + "list";

		@Override
		protected List<UVwebContent.UV> doInBackground(Void... params) {
			final JSONArray uvsArray = HttpHelper.loadJSON(URL);
			if (uvsArray == null || isCancelled()) return null;

			final int nUvs = uvsArray.length();
			final List<UVwebContent.UV> uvs = new ArrayList<UVwebContent.UV>(nUvs);
			try {
				for (int i = 0; !isCancelled() && i < nUvs; i++) {
					final JSONObject uvsInfo = (JSONObject) uvsArray.get(i);
					final UVwebContent.UV uv = new UVwebContent.UV(
							uvsInfo.getString("name").trim(),
							uvsInfo.getString("title").trim()
					);
					uvs.add(uv);
				}
			} catch (JSONException ignored) {
			}

			SystemClock.sleep(4000);

			return uvs;
		}
	}
}
