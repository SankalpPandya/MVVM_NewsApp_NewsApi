package com.sankalp.newsapp.view;

import android.app.Application;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class NewsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InitPicasso();
    }

    private void InitPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
    }
}
