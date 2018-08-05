package com.pashkobohdan.multiwindowbrowser;

import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewClientImpl extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return false;

//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        activity.startActivity(intent);
//        return true;
    }

}