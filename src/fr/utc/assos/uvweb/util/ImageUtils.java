package fr.utc.assos.uvweb.util;

import android.content.Context;

import fr.utc.assos.uvweb.R;

public final class ImageUtils {
	private static int sAvatarPixelSize = -1;

	private static int computePixelSize(Context context, int dimenId) {
		return context.getResources().getDimensionPixelSize(dimenId);
	}

	public static int computeAvatarPixelSize(Context context) {
		if (sAvatarPixelSize == -1) {
			sAvatarPixelSize = computePixelSize(context, R.dimen.avatar_image_view_size);
		}
		return sAvatarPixelSize;
	}
}
