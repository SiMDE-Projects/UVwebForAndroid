package fr.utc.assos.uvweb.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GravatarUtils {
	public static String convertEmailToHash(String email) {
		final MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(email.getBytes(Charset.forName("UTF8")));
			final byte[] resultByte = messageDigest.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < resultByte.length; ++i) {
				sb.append(Integer.toHexString((resultByte[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}
}
