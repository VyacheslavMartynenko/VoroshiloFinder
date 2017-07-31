package com.finder.voroshilo.model.networking.data;

import com.google.gson.annotations.SerializedName;

public class MainDataBody {
    @SerializedName("result")
    private String result;

    @SerializedName("data")
    private DataBody data;

    public MainDataBody(String result, DataBody data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public DataBody getData() {
        return data;
    }

    public void setData(DataBody data) {
        this.data = data;
    }
}
