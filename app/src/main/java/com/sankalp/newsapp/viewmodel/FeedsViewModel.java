package com.sankalp.newsapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;


import com.sankalp.newsapp.model.Article;
import com.sankalp.newsapp.repository.NewsRepository;
import com.sankalp.newsapp.utils.ApiResponse;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FeedsViewModel extends ViewModel {

    private NewsRepository newsRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<ApiResponse> headlineApiResponse = new MutableLiveData<>();
    private final MutableLiveData<ApiResponse> everythingApiResponse = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Article>> headlineArticles = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Article>> everythingArticles = new MutableLiveData<>();

    public void init() {

        if (newsRepository != null) {
            return;
        }
        newsRepository = NewsRepository.getInstance();
    }


    public void hitHeadlineApi(String country) {

        disposables.add(newsRepository.fetchHeadline(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> headlineApiResponse.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> headlineApiResponse.setValue(ApiResponse.success(result)),
                        throwable -> headlineApiResponse.setValue(ApiResponse.error(throwable))
                ));
    }

    public void hitHeadlineApi(@Nullable String country,
                               @Nullable String category,
                               @Nullable String source,
                               @Nullable String keywords,
                               @Nullable Integer pageSize,
                               @Nullable Integer page) {
        disposables.add(newsRepository.fetchHeadlineNew(country, category, source, keywords, pageSize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> headlineApiResponse.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> headlineApiResponse.setValue(ApiResponse.success(result)),
                        throwable -> headlineApiResponse.setValue(ApiResponse.error(throwable))
                ));
    }

    public void hitGetEverythingApi(@Nullable String keyWord,
                                    @Nullable String sources,
                                    @Nullable String domains,
                                    @Nullable String excludeDomains,
                                    @Nullable String oldestDate,
                                    @Nullable String newestDate,
                                    @Nullable String language,
                                    @Nullable String sortBy,
                                    @Nullable Integer pageSize,
                                    @Nullable Integer page) {
        disposables.add(newsRepository.getEverything(keyWord, sources, domains, excludeDomains,
                oldestDate, newestDate, language
                , sortBy, pageSize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> everythingApiResponse.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> everythingApiResponse.setValue(ApiResponse.success(result)),
                        throwable -> everythingApiResponse.setValue(ApiResponse.error(throwable))
                ));
    }

    public MutableLiveData<ApiResponse> getHeadlineApiResponse() {
        return headlineApiResponse;
    }

    public MutableLiveData<ApiResponse> getEverythingApiResponse() {
        return everythingApiResponse;
    }

    public MutableLiveData<ArrayList<Article>> getHeadlineArticles() {
        return headlineArticles;
    }

    public MutableLiveData<ArrayList<Article>> getEverythingArticles() {
        return everythingArticles;
    }

    public MutableLiveData<Integer> getResultCount() {
        return newsRepository.getResultCount();
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

}
