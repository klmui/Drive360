package com.example.drive360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAuthentication();
    }

    // Check if user is authenticate/login.
    private void checkAuthentication () {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

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

    public void btnLoadUnity(View v) {
        Intent intent = new Intent(this, MainUnityActivity.class);
        startActivity(intent);
    }

//    @Override
//    public void onBackPressed() {
//        finishAffinity();
//    }
}
