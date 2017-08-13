package com.aptoide.market.android.apps.games.application;

import android.support.multidex.MultiDexApplication;

import com.aptoide.market.android.apps.games.activity.BaseActivity;
import com.aptoide.market.android.apps.games.util.preferences.SharedPreferencesProvider;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class FinderApplication extends MultiDexApplication {
    private static FinderApplication instance;
    private BaseActivity currentActivity = null;

    public static synchronized FinderApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        SharedPreferencesProvider.getInstance().initialize(getApplicationContext());
        instance = this;
    }

    public BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(BaseActivity currentActivity) {
        this.currentActivity = currentActivity;
    }
}
