package com.sankalp.newsapp.utils;
import android.os.Bundle;
import com.sankalp.newsapp.model.Article;

public  class BundleHelper {

    public static Bundle getShowNewsDescriptionIntent(Article article){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, article.getUrl());
        bundle.putString(Constants.DESCRIPTION, article.getDescription());
        bundle.putString(Constants.CONTENT, article.getContent());
        bundle.putString(Constants.TITLE, article.getTitle());
        bundle.putString(Constants.URL_TO_IMAGE, article.getUrlToImage());
        return bundle;
    }
}
