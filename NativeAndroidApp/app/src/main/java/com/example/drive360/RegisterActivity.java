package com.example.drive360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    // Log the user in.
    public void login(View view) {
        // Get username and password.
        EditText usernameInput = findViewById(R.id.usernameInput);
        String username = usernameInput.getText().toString();
        EditText passwordInput = findViewById(R.id.passwordInput);
        String password = passwordInput.getText().toString();

        // Check for valid input.
        if (username != null && !username.equals("") && password != null && !password.equals("")) {
            // Check if login credentials matches.
            if (checkUniqueUsername(username)) {
                goToMainScreen(username, password);
            } else {

            }
        } else {

        }
    }

    // Check if username already exists.
    public boolean checkUniqueUsername(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Get password from given username, default to empty string if username does not exist.
        String password = sharedPreferences.getString(username, "");

        // Check if password is empty which means username is not yet taken.
        return password.equals("");
    }

    // Redirect the user to main screen.
    public void goToMainScreen(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Save username and password.
        sharedPreferences.edit().putString(username, password).apply();

        // Set isAuthenticated to true and pass in username for main screen.
        sharedPreferences.edit().putBoolean("isAuthenticated", true).apply();
        sharedPreferences.edit().putString("username", username).apply();

        // Redirect the user to main screen.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Redirect the user to login screen.
    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
