package com.finder.voroshilo.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import com.finder.voroshilo.R;
import com.finder.voroshilo.application.FinderApplication;
import com.finder.voroshilo.service.NotificationService;

public class NotificationUtil {
    private static final int APK_DOWNLOAD_NOTIFICATION_ID = 100;

    public static void showNotification(String title, String path) {
        Context context = FinderApplication.getInstance().getApplicationContext();
        Notification notification = new NotificationCompat
                .Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setContentIntent(createPendingIntent(path))
                .build();
        NotificationManagerCompat.from(FinderApplication.getInstance().getApplicationContext())
                .notify(APK_DOWNLOAD_NOTIFICATION_ID, notification);
    }

    private static PendingIntent createPendingIntent(String path) {
        Context context = FinderApplication.getInstance().getApplicationContext();
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(NotificationService.APK_DOWNLOAD_ACTION);
        intent.putExtra(NotificationService.APK_DOWNLOAD_PATH, path);
        return PendingIntent.getService(context, APK_DOWNLOAD_NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}