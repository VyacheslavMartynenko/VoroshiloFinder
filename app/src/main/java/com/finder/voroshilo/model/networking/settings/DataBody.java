package com.finder.voroshilo.model.networking.settings;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataBody {
    @SerializedName("categories")
    List categoriesList;

    @SerializedName("apps")
    List applicationsList;
}
