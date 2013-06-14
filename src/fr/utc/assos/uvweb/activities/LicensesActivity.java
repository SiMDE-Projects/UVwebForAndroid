package fr.utc.assos.uvweb.activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.actionbarsherlock.view.Window;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.activities.base.UVwebActivity;

/**
 * An simple "Licenses" activity, whose aim it to present the open source projects
 * used to build the application
 */
public class LicensesActivity extends UVwebActivity {
	private static final String LICENSES_URL = "file:///android_asset/licenses.html";

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.licenses_activity);

		final WebView webView = (WebView) findViewById(R.id.web_view);
		webView.setWebViewClient(new UVwebClient());

		setSupportProgressBarIndeterminateVisibility(true);

		webView.loadUrl(LICENSES_URL);
	}

	private class UVwebClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			setSupportProgressBarIndeterminateVisibility(false);
		}
	}
}
