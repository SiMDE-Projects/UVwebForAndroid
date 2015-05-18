package fr.utc.assos.uvweb.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import fr.utc.assos.uvweb.R;

public class LicensesActivity extends ToolbarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/licenses.html");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_licenses;
    }
}
