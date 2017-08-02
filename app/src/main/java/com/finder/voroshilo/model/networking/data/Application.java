package com.finder.voroshilo.model.networking.data;

import com.google.gson.annotations.SerializedName;

public class Application {
    @SerializedName("title")
    private String title;

    @SerializedName("icon_url")
    private String iconUrl;

    @SerializedName("package_name")
    private String packageName;

    @SerializedName("developer_name")
    private String developerName;

    @SerializedName("rating")
    private Double rating;

    @SerializedName("category_id")
    private Integer categoryId;

    public Application(String title, String iconUrl, String packageName, String developerName, Double rating, Integer categoryId) {
        this.title = title;
        this.iconUrl = iconUrl;
        this.packageName = packageName;
        this.developerName = developerName;
        this.rating = rating;
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
