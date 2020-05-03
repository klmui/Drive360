package com.example.drive360.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drive360.MainActivity;
import com.example.drive360.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDB;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize database, root and feedback references.
        firebaseDB = FirebaseDatabase.getInstance();
        rootRef = firebaseDB.getReference();
        userRef = rootRef.child("users");
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
            checkCredentials(username, password);
        } else {
            Toast.makeText(LoginActivity.this, "Invalid input. Please try again!", Toast.LENGTH_LONG).show();
        }
    }

    // Check if username and password match.
    public void checkCredentials(final String username, final String password) {
        userRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve expected password, role, and isAdmin from Firebase.
                    boolean isAdmin = (Boolean) dataSnapshot.child("isAdmin").getValue();
                    String role = (String) dataSnapshot.child("role").getValue();
                    String expected = (String) dataSnapshot.child("password").getValue();
                    boolean isAuthenticated = PasswordHash.checkPasswordsMatch(password, expected, null);

                    // If user is authenticated, set shared preferences.
                    if (isAuthenticated) {
                        SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

                        if (role != null && !role.equals("")) {
                            boolean isInstructor = role.equals("Instructor");
                            sharedPreferences.edit().putBoolean("isInstructor", isInstructor).apply();
                        }

                        // Set isAuthenticated to true and pass in username for main screen.
                        sharedPreferences.edit().putBoolean("isAuthenticated", true).apply();
                        sharedPreferences.edit().putString("username", username).apply();
                        sharedPreferences.edit().putBoolean("isAdmin", isAdmin).apply();

                        goToMainScreen(username);
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid credentials. Please try again!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials. Please try again!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    // Redirect the user to main screen.
    public void goToMainScreen(String username) {
        // Redirect the user to main screen.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Redirect the user to register screen.
    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
