package com.example.hongzebin.beanmusic.util;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 创建通知渠道
 * Created By Mr.Bean
 */
public class NotificationChannelUtil {

    public static void setUpNotificationChannel(String channelId, String channelName, int importance){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(channelId, channelName, importance);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private static void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) BeanMusicApplication.getContext().getSystemService(
                NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }
}
