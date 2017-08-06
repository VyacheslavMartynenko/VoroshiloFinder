package com.finder.voroshilo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.appodeal.ads.Appodeal;
import com.finder.voroshilo.application.FinderApplication;
import com.finder.voroshilo.model.networking.settings.DataBody;
import com.finder.voroshilo.util.preferences.UserPreferences;
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
        @DataBody.AdMode
        int adStatus = UserPreferences.getInstance().getAdStatus();
        switch (adStatus) {
            case DataBody.APPODEAL:
                Appodeal.disableNetwork(this, "cheetah");
                String appKey = "fee50c333ff3825fd6ad6d38cff78154de3025546d47a84f";
                Appodeal.disableLocationPermissionCheck();
                Appodeal.initialize(this, appKey, Appodeal.INTERSTITIAL);
                break;
            case DataBody.NO:
                break;
            case DataBody.START_APP:
                StartAppSDK.init(this, "Your Api Key", true);
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
        @DataBody.AdMode
        int adStatus = UserPreferences.getInstance().getAdStatus();
        switch (adStatus) {
            case DataBody.APPODEAL:
                Appodeal.show(this, Appodeal.INTERSTITIAL);
                break;
            case DataBody.NO:
                break;
            case DataBody.START_APP:
                StartAppAd.showAd(getApplicationContext());
                break;
        }
    }
}
