package com.finder.voroshilo.networking.request;

import android.support.annotation.NonNull;

import com.finder.voroshilo.model.networking.data.DataBody;
import com.finder.voroshilo.model.networking.data.MainDataBody;
import com.finder.voroshilo.networking.ApiBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationsRequest {
    public interface ApplicationCallback {
        void onSuccess(DataBody data);

        void onError(Throwable throwable);
    }

    public void requestApplications(final ApplicationsRequest.ApplicationCallback callback) {
        ApiBuilder.getApiService().getData().enqueue(new Callback<MainDataBody>() {
            @Override
            public void onResponse(@NonNull Call<MainDataBody> call, @NonNull Response<MainDataBody> response) {
                if (response.isSuccessful()) {
                    MainDataBody mainDataBody = response.body();
                    if (mainDataBody != null) {
                        DataBody dataBody = mainDataBody.getData();
                        callback.onSuccess(dataBody);
                    } else {
                        callback.onError(new NullPointerException());
                    }
                } else {
                    callback.onError(new Exception(String.valueOf(response.code())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainDataBody> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }
}
