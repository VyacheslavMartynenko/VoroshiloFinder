package com.aptoide.market.android.apps.games.model.networking.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApplicationsDataBody {
    @SerializedName("categories")
    List<Category> categoriesList;

    @SerializedName("apps")
    List<Application> applicationsList;

    public ApplicationsDataBody(List<Category> categoriesList, List<Application> applicationsList) {
        this.categoriesList = categoriesList;
        this.applicationsList = applicationsList;
    }

    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public List<Application> getApplicationsList() {
        return applicationsList;
    }

    public void setApplicationsList(List<Application> applicationsList) {
        this.applicationsList = applicationsList;
    }
}
