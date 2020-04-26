package com.sankalp.newsapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.sankalp.newsapp.R;
import com.sankalp.newsapp.utils.CustomWebViewClient;

public class NewsPageWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        WebView webView = findViewById(R.id.news_webview);
        String url = getIntent().getStringExtra("url");

        webView.getSettings().setJavaScriptEnabled(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.setWebViewClient(new CustomWebViewClient());
        webView.loadUrl(url);
    }
}
