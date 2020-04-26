package com.sankalp.newsapp.repository;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;
import com.google.gson.JsonElement;
import com.sankalp.newsapp.model.Article;
import com.sankalp.newsapp.network.NewsAPIService;
import com.sankalp.newsapp.network.NewsApi;
import java.util.ArrayList;
import io.reactivex.Observable;

public class NewsRepository {

    private static NewsRepository newsRepository;
    private NewsApi service;

    private MutableLiveData<ArrayList<Article>> headlines;
    private MutableLiveData<Integer> resultCount;

    public static NewsRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    public NewsRepository() {
        service = NewsAPIService.createService(NewsApi.class);
        headlines = new MutableLiveData<>();
        resultCount = new MutableLiveData<>();
    }

    public Observable<JsonElement> fetchHeadline(String country) {
        return service.getHeadlines(country, null, null, null, null, null);
    }

    public Observable<JsonElement> fetchHeadlineNew(@Nullable String country, @Nullable String category,
                                                    @Nullable String source, @Nullable String keywords,
                                                    @Nullable Integer pageSize, @Nullable Integer page) {
        return service.getHeadlines(country, category, source, keywords, pageSize, page);
    }

    public Observable<JsonElement> getEverything(@Nullable String keyWord,
                                           @Nullable String sources,
                                           @Nullable String domains,
                                           @Nullable String excludeDomains,
                                           @Nullable String oldestDate,
                                           @Nullable String newestDate,
                                           @Nullable String language,
                                           @Nullable String sortBy,
                                           @Nullable Integer pageSize,
                                           @Nullable Integer page) {
        return service.getEverything(keyWord, sources, domains, excludeDomains, oldestDate, newestDate, language, sortBy, pageSize, page);
    }

    public MutableLiveData<Integer> getResultCount() {
        return resultCount;
    }
}
