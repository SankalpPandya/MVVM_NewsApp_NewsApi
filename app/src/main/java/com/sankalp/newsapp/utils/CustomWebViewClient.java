package com.sankalp.newsapp.utils;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, final String url) {
        return true;
    }
}
