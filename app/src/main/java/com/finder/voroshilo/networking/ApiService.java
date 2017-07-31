package com.finder.voroshilo.networking;

import com.finder.voroshilo.model.networking.data.MainDataBody;
import com.finder.voroshilo.model.networking.settings.SettingsBody;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("store/api/v1/newgeneration/settings")
    Call<SettingsBody> getSettings();

    @GET("store/api/v1/newgeneration/data")
    Call<MainDataBody> getData();
}
