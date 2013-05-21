package fr.utc.assos.uvweb.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GravatarUtils {
	private static final int IMAGE_QUALITY_MIN_THRESHOLD = 50;
	private static final String BASE_GRAVATAR_URL = "http://www.gravatar.com/avatar/";
	private static final StringBuilder sStringBuilder = new StringBuilder();

	public static String convertEmailToHash(String email) {
		final MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(email.getBytes(Charset.forName("UTF8")));
			final byte[] resultBytes = messageDigest.digest();
			sStringBuilder.setLength(0);
			for (byte resultByte : resultBytes) {
				sStringBuilder.append(Integer.toHexString((resultByte & 0xFF) | 0x100).substring(1, 3));
			}
			return sStringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			return email;
		}
	}

	public static String computeUrlRequest(String hash, final int requestedSize) {
		sStringBuilder.setLength(0);
		sStringBuilder.append(BASE_GRAVATAR_URL);
		sStringBuilder.append(hash);
		sStringBuilder.append("?&d=404");
		sStringBuilder.append("&size=");
		sStringBuilder.append(String.valueOf(requestedSize + IMAGE_QUALITY_MIN_THRESHOLD));
		return sStringBuilder.toString();
	}
}
