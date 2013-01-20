package fr.utc.assos.uvweb.activities;

import android.content.Intent;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

/**
 * Base activity for the whole application. Implements basic up navigation
 * Every activity of the application must extend this class
 */
public abstract class UVwebActivity extends SherlockFragmentActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, UVListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
