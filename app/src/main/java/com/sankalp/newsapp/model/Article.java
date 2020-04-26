package com.sankalp.newsapp.model;

import com.google.gson.annotations.SerializedName;

public class Article {

    class Source {

        @SerializedName("id")
        private String sourceId;
        @SerializedName("name")
        private String sourceName;
    }

    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("publisedAt")
    private String publishedAt;
    @SerializedName("content")
    private String content;
    @SerializedName("source")
    private Source source;
    @SerializedName("author")
    private String author;
    @SerializedName("url")
    private String url;
    @SerializedName("urlToImage")
    private String urlToImage;

    public Article(Source source, String author, String title, String description,
                   String url, String urlToImage, String publishedAt, String content) {

        this.source = source;
        this.author = author;
        this.title =title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "source: " + source
                + "\nurl: " + url
                + "\nurlToImage: " + urlToImage
                + "\nauthor: " + author
                + "\ntitle: " + title
                + "\ndescription: " + description
                + "\npublishedAt: " + publishedAt
                + "\ncontent: " + content;
    }
}
