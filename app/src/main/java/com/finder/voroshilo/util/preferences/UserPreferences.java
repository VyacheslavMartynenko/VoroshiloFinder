package com.finder.voroshilo.util.preferences;

import android.content.Context;

import com.finder.voroshilo.model.networking.settings.SettingsDataBody;

public class UserPreferences extends AbstractPreferences {
    private static final String PREFERENCES = "UserPreferences";
    private static final String IS_APP_RATED = "IsFirstLaunch";
    private static final String POP_UP_URL = "PopUpUrl";
    private static final String BURST_STATUS = "BurstStatus";
    private static final String AD_STATUS = "AdStatus";
    private static final String POP_UP_STATUS = "PopUpStatus";
    private static final String TUTORIAL_STATUS = "TutorialStatus";
    private static final String POPUP_TEXT = "PopupText";
    private static final String BURST_TEXT = "BurstText";
    private static final String BURST_URL = "BurstUrl";
    private static final String STARTAPP_KEY = "StartappKey";
    private static final String APPODEAL_KEY = "AppodealKey";

    private static UserPreferences instance;

    public static UserPreferences getInstance() {
        return instance;
    }

    private UserPreferences(Context context, String name) {
        super(context, name);
    }

    static void init(Context context) {
        instance = new UserPreferences(context, PREFERENCES);
    }

    public String getPopUpUrl() {
        return preferences.getString(POP_UP_URL, null);
    }

    public void setPopUpUrl(String popUpUrl) {
        preferences.edit().putString(POP_UP_URL, popUpUrl).apply();
    }

    public boolean isAppRated() {
        return preferences.getBoolean(IS_APP_RATED, false);
    }

    public void setIsAppRated() {
        preferences.edit().putBoolean(IS_APP_RATED, true).apply();
    }

    public int getBurstStatus() {
        return preferences.getInt(BURST_STATUS, SettingsDataBody.NO);
    }

    public void setBurstStatus(int burstStatus) {
        preferences.edit().putInt(BURST_STATUS, burstStatus).apply();
    }

    public int getAdStatus() {
        return preferences.getInt(AD_STATUS, SettingsDataBody.APPODEAL);
    }

    public void setAdStatus(int adStatus) {
        preferences.edit().putInt(AD_STATUS, adStatus).apply();
    }

    public int getPopUpStatus() {
        return preferences.getInt(POP_UP_STATUS, SettingsDataBody.APPODEAL);
    }

    public void setPopUpStatus(int popUpStatus) {
        preferences.edit().putInt(POP_UP_STATUS, popUpStatus).apply();
    }
    public int getTutorialStatus() {
        return preferences.getInt(TUTORIAL_STATUS, SettingsDataBody.NO);
    }

    public void setTutorialStatus(int tutorialStatus) {
        preferences.edit().putInt(TUTORIAL_STATUS, tutorialStatus).apply();
    }

    public String getPopupText() {
        return preferences.getString(POPUP_TEXT, null);
    }

    public void setPopupText(String popupText) {
        preferences.edit().putString(POPUP_TEXT, popupText).apply();
    }

    public String getBurstText() {
        return preferences.getString(BURST_TEXT, null);
    }

    public void setBurstText(String burstText) {
        preferences.edit().putString(BURST_TEXT, burstText).apply();
    }

    public String getBurstUrl() {
        return preferences.getString(BURST_URL, null);
    }

    public void setBurstUrl(String burstUrl) {
        preferences.edit().putString(BURST_URL, burstUrl).apply();
    }

    public String getStartappKey() {
        return preferences.getString(STARTAPP_KEY, "207089338");
    }

    public void setStartappKey(String burstText) {
        preferences.edit().putString(STARTAPP_KEY, burstText).apply();
    }

    public String getAppodealKey() {
        return preferences.getString(APPODEAL_KEY, "f3bd05344243630841b7a4ce38db7f2a67d1076d8233d28b");
    }

    public void setAppodealKey(String burstUrl) {
        preferences.edit().putString(APPODEAL_KEY, burstUrl).apply();
    }
}
