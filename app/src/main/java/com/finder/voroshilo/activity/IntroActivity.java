package com.finder.voroshilo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.util.Log;

import com.finder.voroshilo.R;
import com.finder.voroshilo.model.networking.settings.DataBody;
import com.finder.voroshilo.networking.request.SettingsRequest;
import com.finder.voroshilo.util.preferences.UserPreferences;

public class IntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        requestSettings();
    }

    private void requestSettings() {
        new SettingsRequest().requestSettings(new SettingsRequest.SettingsCallback() {
            @Override
            public void onSuccess(DataBody data) {
                UserPreferences.getInstance().setBurstStatus(data.getBurstStatus());
                UserPreferences.getInstance().setMarketUrl(data.getBurstUrl());
                UserPreferences.getInstance().setAdStatus(data.getNetType());
                UserPreferences.getInstance().setPopUpUrl(data.getPopupUrl());
                UserPreferences.getInstance().setPopUpStatus(data.getPopup());
                UserPreferences.getInstance().setTutorialStatus(data.getTutorialStatus());

                showNewActivity();
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("onResponse: ", Log.getStackTraceString(throwable));

                showNewActivity();
            }
        });
    }

    private void showNewActivity() {
        Class<?> activityClass = UserPreferences.getInstance().getTutorialStatus() == DataBody.NO ? MainActivity.class : MainActivity.class;
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
