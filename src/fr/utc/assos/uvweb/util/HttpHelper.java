package fr.utc.assos.uvweb.util;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class HttpHelper {
	private static final OkHttpClient sClient = new OkHttpClient();

	private static String convertStreamToString(InputStream is) throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		final StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	public static JSONArray loadJSON(String url) {
		InputStream is = null;
		final JSONArray json;

		try {
			HttpURLConnection connection = new OkUrlFactory(sClient).open(new URL(url));
			is = connection.getInputStream();
			json = new JSONArray(convertStreamToString(is));
		} catch (IOException ioe) {
			return null;
		} catch (JSONException je) {
			return null;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException ioe) {
			}
		}

		return json;
	}
}
