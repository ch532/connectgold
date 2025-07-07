package com.connectgold.app;

import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.util.Log;

public class MyWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String clickedUrl = request.getUrl().toString();
        Log.d("WebView", "Clicked URL: " + clickedUrl);

        // Add your tracking logic here
        if (clickedUrl.contains("utm_source=facebook")) {
            Log.d("Analytics", "Facebook traffic detected");
        }

        view.loadUrl(clickedUrl);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.d("WebView", "Loading: " + url);
        // You can show a loader here
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.d("WebView", "Finished: " + url);
        // You can hide the loader here
    }
}
