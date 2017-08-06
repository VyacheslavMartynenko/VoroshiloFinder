package com.finder.voroshilo.networking.request;

import android.support.annotation.NonNull;

import com.finder.voroshilo.model.networking.settings.SettingsDataBody;
import com.finder.voroshilo.model.networking.settings.SettingsBody;
import com.finder.voroshilo.networking.ApiBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsRequest {
    public interface SettingsCallback {
        void onSuccess(SettingsDataBody data);

        void onError(Throwable throwable);
    }

    public static void requestSettings(final SettingsCallback callback) {
        ApiBuilder.getApiService().getSettings().enqueue(new Callback<SettingsBody>() {
            @Override
            public void onResponse(@NonNull Call<SettingsBody> call, @NonNull Response<SettingsBody> response) {
                if (response.isSuccessful()) {
                    SettingsBody settingsBody = response.body();
                    if (settingsBody != null) {
                        SettingsDataBody settingsDataBody = settingsBody.getData();
                        callback.onSuccess(settingsDataBody);
                    } else {
                        callback.onError(new NullPointerException());
                    }
                } else {
                    callback.onError(new Exception(String.valueOf(response.code())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SettingsBody> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }
}
