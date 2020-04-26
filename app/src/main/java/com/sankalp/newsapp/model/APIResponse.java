package com.sankalp.newsapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class APIResponse {

    @SerializedName("articles")
    private ArrayList<Article> articles;
    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private int totalResults;

    public APIResponse(String status, int totalResults, ArrayList<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
