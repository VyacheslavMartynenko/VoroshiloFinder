package com.aptoide.market.android.apps.games.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;


import com.aptoide.market.android.apps.games.application.FinderApplication;

import java.io.File;

public class NotificationService extends IntentService {
    public static final String APK_DOWNLOAD_ACTION = "com.aptoide.market.android.apps.games.util.download";
    public static final String APK_DOWNLOAD_PATH = "com.aptoide.market.android.apps.games.util.path";

    public NotificationService() {
        super(NotificationService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null && action.equals(APK_DOWNLOAD_ACTION)) {
                String path = intent.getStringExtra(APK_DOWNLOAD_PATH);
                if (path != null) {
                    showFolder(path);
                }
            }
        }
    }

    private void showFolder(String path) {
        File file = new File(path);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(FinderApplication.getInstance().getApplicationContext(),
                    "com.aptoide.market.android.apps.games.fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        Intent chooserIntent = Intent.createChooser(intent, "Open").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (chooserIntent.resolveActivityInfo(getPackageManager(), 0) != null) {
            startActivity(chooserIntent);
        }
    }
}
