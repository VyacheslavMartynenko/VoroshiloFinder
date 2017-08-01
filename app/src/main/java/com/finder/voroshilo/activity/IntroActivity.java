package com.finder.voroshilo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.finder.voroshilo.R;
import com.finder.voroshilo.model.networking.settings.DataBody;
import com.finder.voroshilo.networking.request.SettingsRequest;
import com.finder.voroshilo.util.preferences.UserPreferences;

import java.lang.ref.WeakReference;

public class IntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        new SettingsRequest().requestSettings(new SettingsRequestCallback(this));
    }

    private void showNewActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private static class SettingsRequestCallback implements SettingsRequest.SettingsCallback {
        WeakReference<IntroActivity> introActivityWeakReference;

        SettingsRequestCallback(IntroActivity introActivity) {
            this.introActivityWeakReference = new WeakReference<>(introActivity);
        }

        @Override
        public void onSuccess(DataBody data) {
            UserPreferences.getInstance().setBurstStatus(data.getBurstStatus());
            UserPreferences.getInstance().setMarketUrl(data.getBurstUrl());
            UserPreferences.getInstance().setAdStatus(data.getNetType());
            UserPreferences.getInstance().setPopUpUrl(data.getPopupUrl());
            UserPreferences.getInstance().setPopUpStatus(data.getPopup());
            UserPreferences.getInstance().setTutorialStatus(data.getTutorialStatus());

            IntroActivity introActivity = introActivityWeakReference.get();
            if (introActivity != null) {
                introActivity.showNewActivity();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            Log.e("onResponse: ", Log.getStackTraceString(throwable));

            IntroActivity introActivity = introActivityWeakReference.get();
            if (introActivity != null) {
                introActivity.showNewActivity();
            }
        }
    }
}
