package com.aptoide.market.android.apps.games.model.networking.data;

import com.google.gson.annotations.SerializedName;

public class MainDataBody {
    @SerializedName("result")
    private String result;

    @SerializedName("data")
    private ApplicationsDataBody data;

    public MainDataBody(String result, ApplicationsDataBody data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ApplicationsDataBody getData() {
        return data;
    }

    public void setData(ApplicationsDataBody data) {
        this.data = data;
    }
}
