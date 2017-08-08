package com.finder.voroshilo.model.networking.settings;

import android.support.annotation.IntDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SettingsDataBody {
    public static final int NO = 0;
    public static final int START_APP = 1;
    public static final int APPODEAL = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NO, START_APP, APPODEAL})
    public @interface AdMode {
    }

    @SerializedName("net_type")
    private int netType;

    @SerializedName("popup")
    private int popup;

    @SerializedName("popup_text")
    private String popupText;

    @SerializedName("popup_url")
    private String popupUrl;

    @SerializedName("is_burst")
    private int burstStatus;

    @SerializedName("burst_text")
    private String burstText;

    @SerializedName("burst_url")
    private String burstUrl;

    @SerializedName("show_tutorial")
    private int tutorialStatus;

    @SerializedName("appodeal_key")
    private String appodealKey;

    @SerializedName("startapp_key")
    private String startappKey;

    public SettingsDataBody(int netType, int popup, String popupText, String popupUrl, int burstStatus, String burstText, String burstUrl, int tutorialStatus, String appodealKey, String startappKey) {
        this.netType = netType;
        this.popup = popup;
        this.popupText = popupText;
        this.popupUrl = popupUrl;
        this.burstStatus = burstStatus;
        this.burstText = burstText;
        this.burstUrl = burstUrl;
        this.tutorialStatus = tutorialStatus;
        this.appodealKey = appodealKey;
        this.startappKey = startappKey;
    }

    public int getNetType() {
        return netType;
    }

    public void setNetType(int netType) {
        this.netType = netType;
    }

    public int getPopup() {
        return popup;
    }

    public void setPopup(int popup) {
        this.popup = popup;
    }

    public String getPopupText() {
        return popupText;
    }

    public void setPopupText(String popupText) {
        this.popupText = popupText;
    }

    public String getPopupUrl() {
        return popupUrl;
    }

    public void setPopupUrl(String popupUrl) {
        this.popupUrl = popupUrl;
    }

    public int getBurstStatus() {
        return burstStatus;
    }

    public void setBurstStatus(int burstStatus) {
        this.burstStatus = burstStatus;
    }

    public String getBurstText() {
        return burstText;
    }

    public void setBurstText(String burstText) {
        this.burstText = burstText;
    }

    public String getBurstUrl() {
        return burstUrl;
    }

    public void setBurstUrl(String burstUrl) {
        this.burstUrl = burstUrl;
    }

    public int getTutorialStatus() {
        return tutorialStatus;
    }

    public void setTutorialStatus(int tutorialStatus) {
        this.tutorialStatus = tutorialStatus;
    }

    public String getAppodealKey() {
        return appodealKey;
    }

    public void setAppodealKey(String appodealKey) {
        this.appodealKey = appodealKey;
    }

    public String getStartappKey() {
        return startappKey;
    }

    public void setStartappKey(String startappKey) {
        this.startappKey = startappKey;
    }
}
