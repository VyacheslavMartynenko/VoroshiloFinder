package com.aptoide.market.android.apps.games.networking.task;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.aptoide.market.android.apps.games.R;
import com.aptoide.market.android.apps.games.application.FinderApplication;
import com.aptoide.market.android.apps.games.util.NotificationUtil;

import static android.content.Context.DOWNLOAD_SERVICE;

public class ApkDownloadTask {
    public static void downloadFile(String url) {
        FinderApplication finderApplication = FinderApplication.getInstance();
        String appName = finderApplication.getString(R.string.app_name);
        String appFullName = "/" + appName + ".apk";

        DownloadManager dm = (DownloadManager) finderApplication.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(finderApplication.getString(R.string.download_progress) + appName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, appFullName);
        dm.enqueue(request);

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    NotificationUtil.showNotification(finderApplication.getString(R.string.download_complete) + appName,
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + appFullName);
                    finderApplication.unregisterReceiver(this);
                } catch (Exception e) {
                    Log.e(ApkDownloadTask.class.getSimpleName(), Log.getStackTraceString(e));
                }
            }
        };
        finderApplication.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
}