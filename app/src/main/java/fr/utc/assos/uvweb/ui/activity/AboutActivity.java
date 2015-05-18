package fr.utc.assos.uvweb.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import fr.utc.assos.uvweb.BuildConfig;
import fr.utc.assos.uvweb.R;

public class AboutActivity extends ToolbarActivity {

    private static final String SUFFIX_VERSION_NAME = ".dev";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView versionView = (TextView) findViewById(R.id.version);
        String versionName = BuildConfig.VERSION_NAME;
        if (BuildConfig.DEBUG) {
            versionName += SUFFIX_VERSION_NAME;
        }
        versionView.setText(versionName);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }
}
