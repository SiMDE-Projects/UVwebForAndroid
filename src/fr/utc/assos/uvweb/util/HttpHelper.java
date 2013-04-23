package fr.utc.assos.uvweb.util;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
	private static String convertStreamToString(InputStream is) {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		final StringBuilder sb = new StringBuilder();

		try {
			String line;

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return sb.toString();
	}

	public static JSONArray loadJSON(String url) {
		HttpURLConnection connection = null;
		JSONArray json = null;
		InputStream is = null;

		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(5000);

			is = new BufferedInputStream(connection.getInputStream());
			json = new JSONArray(convertStreamToString(is));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (JSONException je) {
			je.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			if (connection != null) {
				connection.disconnect();
			}
		}

		return json;
	}

	public static InputStream loadImage(String url) {
		HttpURLConnection connection = null;
		InputStream is = null;

		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(15000);

			is = new BufferedInputStream(connection.getInputStream());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			if (connection != null) {
				connection.disconnect();
			}
		}

		return is;
	}
}
