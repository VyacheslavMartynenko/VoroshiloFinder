package com.aptoide.market.android.apps.games.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.appodeal.ads.Appodeal;

import com.aptoide.market.android.apps.games.application.FinderApplication;
import com.aptoide.market.android.apps.games.model.networking.settings.SettingsDataBody;
import com.aptoide.market.android.apps.games.util.preferences.UserPreferences;
import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    private FinderApplication app;
    private boolean isActivityPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (FinderApplication) this.getApplicationContext();
        @SettingsDataBody.AdMode
        int adStatus = UserPreferences.getInstance().getAdStatus();
        switch (adStatus) {
            case SettingsDataBody.APPODEAL:
                Appodeal.disableNetwork(this, "cheetah");
                String appKey = UserPreferences.getInstance().getAppodealKey();
                Appodeal.disableLocationPermissionCheck();
                Appodeal.initialize(this, appKey, Appodeal.INTERSTITIAL | Appodeal.BANNER);
                break;
            case SettingsDataBody.NO:
                break;
            case SettingsDataBody.START_APP:
                StartAppSDK.init(this, UserPreferences.getInstance().getStartappKey(), true);
                StartAppAd.disableSplash();
                StartAppAd.disableAutoInterstitial();
                break;
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected void onResume() {
        isActivityPaused = false;
        super.onResume();
        app.setCurrentActivity(this);
    }

    protected void onPause() {
        isActivityPaused = true;
        clearReferences();
        super.onPause();
    }

    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    private void clearReferences() {
        Activity currActivity = app.getCurrentActivity();
        if (this.equals(currActivity)) {
            app.setCurrentActivity(null);
        }
    }

    public boolean isVisible() {
        return !isActivityPaused;
    }

    public void showAd() {
        @SettingsDataBody.AdMode
        int adStatus = UserPreferences.getInstance().getAdStatus();
        switch (adStatus) {
            case SettingsDataBody.APPODEAL:
                Appodeal.show(this, Appodeal.INTERSTITIAL);
                break;
            case SettingsDataBody.NO:
                break;
            case SettingsDataBody.START_APP:
                StartAppAd.showAd(getApplicationContext());
                break;
        }
    }

    public void showBanner(ViewGroup viewGroup) {
        @SettingsDataBody.AdMode
        int adStatus = UserPreferences.getInstance().getAdStatus();
        switch (adStatus) {
            case SettingsDataBody.APPODEAL:
                Appodeal.show(this, Appodeal.BANNER_BOTTOM);
                break;
            case SettingsDataBody.NO:
                break;
            case SettingsDataBody.START_APP:
                Banner startAppBanner = new Banner(getApplicationContext());
                RelativeLayout.LayoutParams bannerParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
                bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                viewGroup.addView(startAppBanner, bannerParameters);
                break;
        }
    }
}
