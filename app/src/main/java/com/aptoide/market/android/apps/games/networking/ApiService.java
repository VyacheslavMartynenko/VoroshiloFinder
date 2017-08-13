package com.aptoide.market.android.apps.games.networking;

import com.aptoide.market.android.apps.games.model.networking.data.MainDataBody;
import com.aptoide.market.android.apps.games.model.networking.settings.SettingsBody;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("store/api/v1/newgeneration/settings")
    Call<SettingsBody> getSettings();

    @GET("store/api/v1/newgeneration/data")
    Call<MainDataBody> getData();
}
