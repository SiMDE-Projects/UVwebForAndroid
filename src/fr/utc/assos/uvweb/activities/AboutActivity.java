package fr.utc.assos.uvweb.activities;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import fr.utc.assos.uvweb.R;

/**
 * An simple "About" activity. Here we are supposed to introduce ourselves as well as the project
 */
public class AboutActivity extends UVwebActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        ((TextView) findViewById(R.id.credits)).setText(Html.fromHtml(getResources().getString(R.string.credits)));

        // TODO: click on buttons
    }
}