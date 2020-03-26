package com.example.drive360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
            if (checkCredentials(username, password)) {
                goToMainScreen(username);
            } else {

            }
        } else {

        }
    }

    // Check if username and password match.
    public boolean checkCredentials(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Get expectedPassword from given username, default to empty string.
        String expectedPassword = sharedPreferences.getString(username, "");

        // Check if password matches as expected.
        return password.equals(expectedPassword);
    }

    // Redirect the user to main screen.
    public void goToMainScreen(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Set isAuthenticated to true and pass in username for main screen.
        sharedPreferences.edit().putBoolean("isAuthenticated", true).apply();
        sharedPreferences.edit().putString("username", username).apply();

        // Redirect the user to main screen.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
