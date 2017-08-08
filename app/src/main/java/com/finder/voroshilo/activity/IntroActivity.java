package com.finder.voroshilo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.finder.voroshilo.R;
import com.finder.voroshilo.model.networking.settings.SettingsDataBody;
import com.finder.voroshilo.networking.request.SettingsRequest;
import com.finder.voroshilo.util.preferences.UserPreferences;

import java.lang.ref.WeakReference;

public class IntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        SettingsRequest.requestSettings(new SettingsRequestCallback(this));
    }

    private void showNewActivity() {
        Class<?> activityClass = UserPreferences.getInstance().getTutorialStatus() == SettingsDataBody.NO ? MainActivity.class : EnterActivity.class;
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private static class SettingsRequestCallback implements SettingsRequest.SettingsCallback {
        WeakReference<IntroActivity> introActivityWeakReference;

        SettingsRequestCallback(IntroActivity introActivity) {
            this.introActivityWeakReference = new WeakReference<>(introActivity);
        }

        @Override
        public void onSuccess(SettingsDataBody data) {
            UserPreferences.getInstance().setAdStatus(data.getNetType());
            UserPreferences.getInstance().setPopUpUrl(data.getPopupUrl());
            UserPreferences.getInstance().setPopUpStatus(data.getPopup());
            UserPreferences.getInstance().setPopupText(data.getPopupText());
            UserPreferences.getInstance().setTutorialStatus(data.getTutorialStatus());
            UserPreferences.getInstance().setBurstUrl(data.getBurstUrl());
            UserPreferences.getInstance().setBurstStatus(data.getBurstStatus());
            UserPreferences.getInstance().setBurstText(data.getBurstText());
            UserPreferences.getInstance().setStartappKey(data.getStartappKey());
            UserPreferences.getInstance().setAppodealKey(data.getAppodealKey());

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
