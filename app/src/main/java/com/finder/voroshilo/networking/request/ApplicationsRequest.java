package com.finder.voroshilo.networking.request;

import android.support.annotation.NonNull;

import com.finder.voroshilo.model.networking.data.ApplicationsDataBody;
import com.finder.voroshilo.model.networking.data.MainDataBody;
import com.finder.voroshilo.networking.ApiBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationsRequest {
    public interface ApplicationCallback {
        void onSuccess(ApplicationsDataBody data);

        void onError(Throwable throwable);
    }

    public static void requestApplications(final ApplicationsRequest.ApplicationCallback callback) {
        ApiBuilder.getApiService().getData().enqueue(new Callback<MainDataBody>() {
            @Override
            public void onResponse(@NonNull Call<MainDataBody> call, @NonNull Response<MainDataBody> response) {
                if (response.isSuccessful()) {
                    MainDataBody mainDataBody = response.body();
                    if (mainDataBody != null) {
                        ApplicationsDataBody applicationsDataBody = mainDataBody.getData();
                        callback.onSuccess(applicationsDataBody);
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
