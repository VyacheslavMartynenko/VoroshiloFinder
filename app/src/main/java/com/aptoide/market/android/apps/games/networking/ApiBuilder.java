package com.aptoide.market.android.apps.games.networking;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {
    private static final String BASE_API_URL = "http://mp3download.guru/";

    private static ApiBuilder apiBuilder;
    private final ApiService apiService;

    private static ApiBuilder getInstance() {
        if (apiBuilder == null) {
            apiBuilder = new ApiBuilder();
        }
        return apiBuilder;
    }

    private ApiBuilder() {
        apiService = getRetrofit().create(ApiService.class);
    }

    public static ApiService getApiService() {return getInstance().apiService;}

    private Retrofit getRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()));

        return builder.build();
    }
}
