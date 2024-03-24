package com.mobileapp.wowapp.listener;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.DriverHome;


public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    @Override
    public void onNewToken(@NonNull String s)
    {
        super.onNewToken(s);
    }
    @Override
    public void onMessageReceived(@NonNull final RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);

        Intent notificationIntent = new Intent(this, DriverHome.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        }

        displayNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(), pendingIntent);
    }

    private void displayNotification(String title, String messageBody,PendingIntent intent)
    {
        String CHANNEL_ID = "10002";
          Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setContentIntent(intent)
                .setChannelId(CHANNEL_ID)
                .setSound(defaultSoundUri);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "WowChannel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name, importance);
            notificationManager.createNotificationChannel(channel);
        }


        notificationManager.notify((int)System.currentTimeMillis(), notificationBuilder.build());
    }

}

