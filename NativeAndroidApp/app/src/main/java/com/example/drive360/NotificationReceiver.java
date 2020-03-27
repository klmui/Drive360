package com.example.drive360;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.drive360.App.CHANNEL_1_ID;

public class NotificationReceiver extends BroadcastReceiver {

    /**
     * Notification to remind user to learn
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context, MainActivity.class);
        // Remove previous instance
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Notification sent on channel 1 to remind user to learn
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_directions_car_black_24dp)
                .setContentTitle("Drive360")
                .setContentText("This is a friendly reminder to keep up the good work!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(pendingIntent) // Sets action when notification clicked
                .setAutoCancel(true) // Dismisses message after clicked
                .build();

        notificationManager.notify(1, notification);
    }
}