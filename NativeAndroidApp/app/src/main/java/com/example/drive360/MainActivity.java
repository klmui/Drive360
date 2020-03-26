package com.example.drive360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

    public void btnLoadUnity(View v) {
        Intent intent = new Intent(this, MainUnityActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        finishAffinity();
//    }
}
