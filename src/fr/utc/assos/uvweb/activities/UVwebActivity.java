package fr.utc.assos.uvweb.activities;

import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

/**
 * Base activity for the whole application. Implements basic up navigation
 * Every activity of the application must extend this class
 */
public abstract class UVwebActivity extends SherlockFragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // TODO: understand why it's needed on Android < 4.2 (the Theme should be enough)
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, UVListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // TODO: understand why not | Intent.FLAG_ACTIVITY_NEW_TASK
                // http://developer.android.com/training/implementing-navigation/ancestral.html
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
