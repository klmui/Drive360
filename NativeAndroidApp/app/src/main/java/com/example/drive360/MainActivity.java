package com.example.drive360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;

import static com.example.drive360.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAuthentication();

        // Set up weekly notifications
        alarm();
    }

    // Check if user is authenticate/login.
    private void checkAuthentication () {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Check if user is authenticated.
        boolean isAuthenticated = sharedPreferences.getBoolean("isAuthenticated", false);
        if (isAuthenticated) {
            setContentView(R.layout.activity_main);
        } else {
            goToLoginScreen();
        }
    }

    // Redirect the user to login first.
    public void goToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

            // Set isAuthenticated to false and remove username form sharedPreferences.
            sharedPreferences.edit().putBoolean("isAuthenticated", false).apply();
            sharedPreferences.edit().remove("username").apply();

            // Redirect the user to login screen.
            goToLoginScreen();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Get username and set text of menu item to welcome user.
        String username = sharedPreferences.getString("username", "");
        if (username != null && !username.equals("")) {
            MenuItem item = menu.findItem(R.id.welcome);
            item.setTitle("Welcome " + username);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public void alarm() {
        System.out.println("Alarm");
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 11);
        calendar.set(Calendar.SECOND, 0);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // Makes sure alarm will occur even in sleep mode (1st arg)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

//    public void testNotif(View v) {
//        System.out.println("BUTTON CLICKED");
////        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
////        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        notificationManager = NotificationManagerCompat.from(this);
//
//        // Notification sent on channel 1 to remind user to learn
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
//                .setSmallIcon(R.drawable.ic_directions_car_black_24dp)
//                .setContentTitle("Drive360")
//                .setContentText("This is a friendly reminder to keep up the good work!")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .setColor(Color.BLUE)
//                .setAutoCancel(true) // Dismisses message after clicked
//                .build();
//
//        notificationManager.notify(1, notification);
//    }

    public void btnLoadUnity(View v) {
        Intent intent = new Intent(this, MainUnityActivity.class);
        startActivity(intent);
    }

//    @Override
//    public void onBackPressed() {
//        finishAffinity();
//    }
}
