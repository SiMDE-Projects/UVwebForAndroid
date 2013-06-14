package fr.utc.assos.uvweb.activities;

import android.content.Intent;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.activities.base.UVwebActivity;

/**
 * Base activity implementing simple menu items, that are consistent through the whole application
 * Every activity of the application requiring such a menu must extend this class
 */
public abstract class UVwebMenuActivity extends UVwebActivity {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activities_global, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.about:
				startActivity(new Intent(this, AboutActivity.class));
				return true;
			case R.id.licenses:
				startActivity(new Intent(this, LicensesActivity.class));
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
