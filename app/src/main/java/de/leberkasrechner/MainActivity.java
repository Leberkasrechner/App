package de.leberkasrechner;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.app.Activity;
import android.view.View;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView mWebView = (WebView) findViewById(R.id.webview);
        View mOfflineView = findViewById(R.id.offline_view);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Disable cache in WebView
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (isNetworkConnected()) {
            mWebView.loadUrl("https://leberkasrechner.de");
            mOfflineView.setVisibility(View.GONE);
        } else {
            mWebView.loadUrl("file:///android_asset/fallback.html");
            mOfflineView.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            Network activeNetwork = cm.getActiveNetwork();
            if (activeNetwork != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(activeNetwork);
                return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        }
        return false;
    }
}
