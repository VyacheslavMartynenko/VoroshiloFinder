package com.finder.voroshilo.model.networking.settings;

import com.google.gson.annotations.SerializedName;

public class SettingsBody {
    @SerializedName("result")
    private String result;

    @SerializedName("data")
    private SettingsDataBody data;

    public SettingsBody(String result, SettingsDataBody data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public SettingsDataBody getData() {
        return data;
    }

    public void setData(SettingsDataBody data) {
        this.data = data;
    }
}
