package com.example.multinotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    public static final String CHANNEL_ID = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("position", 1);
        String title = "Title";
        String description = "Content";

        if (intent.getStringExtra("title") != null) {
            title = intent.getStringExtra("title");
        }

        if (intent.getStringExtra("description") != null) {
            description = intent.getStringExtra("description");
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        Log.d("NotificationId", String.valueOf(notificationId));
        notificationManagerCompat.notify(notificationId, builder.build());
    }

}

