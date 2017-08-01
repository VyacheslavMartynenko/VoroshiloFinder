package com.finder.voroshilo.model.networking.data;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("title")
    private String title;

    @SerializedName("id")
    private Integer id;

    public Category(String title, Integer id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
