package com.poepoemyintswe.specialtips.models;

/**
 * Created by poepoe on 10/7/14.
 */

import java.util.ArrayList;
import java.util.List;


import com.google.gson.annotations.Expose;

public class Feed {

    @Expose
    private Integer id;
    @Expose
    private String title;
    @Expose
    private String permalink;
    @Expose
    private String content;
    @Expose
    private String excerpt;
    @Expose
    private String date;
    @Expose
    private String author;
    @Expose
    private List<String> categories = new ArrayList<String>();
    @Expose
    private List<Object> tags = new ArrayList<Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

}