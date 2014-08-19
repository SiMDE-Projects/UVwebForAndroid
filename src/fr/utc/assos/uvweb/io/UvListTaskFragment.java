package fr.utc.assos.uvweb.io;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.tkeunebr.androidlazyasync.acl.AsyncFragment;
import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.util.HttpHelper;

/**
 * A UI-less fragment that loads the uv list.
 */
public class UvListTaskFragment extends AsyncFragment {
    public static final String TAG = "UvListTaskFragment";

	public UvListTaskFragment() {
	}

	@Override
	protected void execute() {
		new LoadUvListTask().exec();
	}

	private final class LoadUvListTask extends FragmentTask<Void, Void, List<UVwebContent.UV>> {
        private static final String URL = "http://assos.utc.fr/uvweb/uv/app/all";

        @Override
        protected List<UVwebContent.UV> doInBackground(Void... params) {
            final String uvsArrayStr = HttpHelper.loadJSONString(URL);
            if (uvsArrayStr == null || isCancelled()) return null;
            try {
                final JSONObject root = new JSONObject(uvsArrayStr);
                final Iterator iterator = root.keys();

                final List<UVwebContent.UV> uvs = new ArrayList<UVwebContent.UV>();


                while (!isCancelled() && iterator.hasNext()) {
                    String key = (String) iterator.next();
                    JSONArray array = root.getJSONArray(key);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject uvInfo = (JSONObject) array.get(i);
                        final UVwebContent.UV uv = new UVwebContent.UV(
                                uvInfo.getString("name").trim(),
                                uvInfo.getString("title").trim()
                        );
                        uvs.add(uv);
                    }
                }

                return uvs;

            } catch (JSONException ignored) {
                Log.e(TAG, ignored.toString());
            }

            return null;
        }
    }
}
