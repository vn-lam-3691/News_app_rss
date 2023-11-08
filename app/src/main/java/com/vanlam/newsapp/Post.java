package com.vanlam.newsapp;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private String title, description;
    private String link, imageUrl;
    private String pubDate;

    public Post() {
    }

    public Post(String title, String description, String link, String imageUrl) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.imageUrl = imageUrl;
    }

    public Post(String title, String description, String link, String imageUrl, String pubDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.imageUrl = imageUrl;
        this.pubDate = pubDate;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
