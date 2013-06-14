package fr.utc.assos.uvweb.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.activities.base.UVwebActivity;

/**
 * A simple "About" activity. Here we are supposed to introduce ourselves as well as the project
 */
public class AboutActivity extends UVwebActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_about);

		((TextView) findViewById(R.id.credits))
				.setText(Html.fromHtml(getResources().getString(R.string.credits)));
	}

	public void openUVwebSite(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://assos.utc.fr/uvweb/")));
	}

	public void openUTCwebSite(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.utc.fr/")));
	}

	public void contactUs(View v) {
		final Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:uvweb@assos.utc.fr"));
		startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.intent_chooser_title)));
	}
}