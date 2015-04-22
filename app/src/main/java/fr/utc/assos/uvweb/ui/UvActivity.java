package fr.utc.assos.uvweb.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.model.UvListItem;

public class UvActivity extends ToolbarActivity {
    public static final String ARG_UV = "arg_uv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_uv;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
