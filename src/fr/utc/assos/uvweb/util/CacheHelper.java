package fr.utc.assos.uvweb.util;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class CacheHelper {
	public static JSONArray loadJSON(File cacheFile) throws JSONException, IOException {
		FileInputStream stream = null;
		FileChannel fc = null;
		try {
			stream = new FileInputStream(cacheFile);
			fc = stream.getChannel();
			final MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			return new JSONArray(Charset.forName("UTF8").decode(bb).toString());
		} finally {
			if (fc != null) {
				fc.close();
			}
			if (stream != null) {
				stream.close();
			}
		}
	}

	public static void writeToCache(File cacheFile, JSONArray JSONData) throws IOException {
		FileOutputStream fos = null;
		OutputStreamWriter writer = null;
		try {
			fos = new FileOutputStream(cacheFile);
			writer = new OutputStreamWriter(fos);
			writer.write(JSONData.toString());
		} finally {
			if (writer != null) {
				writer.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}
}
