package fr.utc.assos.uvweb.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GravatarUtils {
	public static final int IMAGE_QUALITY_MIN_THRESHOLD = 50;

	public static String convertEmailToHash(String email) {
		final MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(email.getBytes(Charset.forName("UTF8")));
			final byte[] resultBytes = messageDigest.digest();
			final StringBuilder sb = new StringBuilder();
			for (byte resultByte : resultBytes) {
				sb.append(Integer.toHexString((resultByte & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return email;
		}
	}
}
