package fr.utc.assos.uvweb.ui.activity;

import android.os.Bundle;

import fr.utc.assos.uvweb.R;

public class AboutActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }
}
