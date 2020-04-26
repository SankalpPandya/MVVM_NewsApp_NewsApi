package com.sankalp.newsapp.network;

import com.google.gson.JsonElement;
import com.sankalp.newsapp.model.APIResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NewsApi {

     String authHeader = "X-Api-Key: " + "67be9bcd32524f5fbda59716b61b4743";

    @GET("/v2/top-headlines")
    @Headers(authHeader)
    Observable<JsonElement> getHeadlines(@Query("country") String country,
                                         @Query("category") String category,
                                         @Query("sources") String sources,
                                         @Query("q") String keywords,
                                         @Query("pageSize") Integer pageSize,
                                         @Query("page") Integer page);

    @Headers(authHeader)
    @GET("/v2/everything")
    Observable<JsonElement> getEverything(@Query("q") String keyword,
                                    @Query("sources") String sources,
                                    @Query("domains") String domains,
                                    @Query("excludeDomains") String excludeDomains,
                                    @Query("from") String oldestDate,
                                    @Query("to") String newestDate,
                                    @Query("language") String language,
                                    @Query("sortBy") String sortBy,
                                    @Query("pageSize") Integer pageSize,
                                    @Query("page") Integer page);

}
