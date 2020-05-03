package com.example.drive360.forms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drive360.MainActivity;
import com.example.drive360.R;
import com.example.drive360.auth.LoginActivity;
import com.example.drive360.models.Feedback;
import com.example.drive360.pages.AdminDashboardActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.drive360.Config.appStatsRef;

public class FeedbackActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseDatabase firebaseDB;
    private DatabaseReference rootRef;
    private DatabaseReference feedbackRef;
    private Spinner spinner;
    private boolean validFeedbackCategory;
    private static final String[] feedbackCategories
            = {"Please choose feedback category", "Report error", "General feedback", "Suggestions", "Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Initialize database, root and feedback references.
        firebaseDB = FirebaseDatabase.getInstance();
        rootRef = firebaseDB.getReference();
        feedbackRef = rootRef.child("feedbacks");

        setupSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
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

        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);
        if (!isAdmin) {
            menu.findItem(R.id.admin_dashboard).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

            sharedPreferences.edit().putBoolean("isAuthenticated", false).apply();
            sharedPreferences.edit().putBoolean("isAdmin", false).apply();
            sharedPreferences.edit().putBoolean("isInstructor", false).apply();
            sharedPreferences.edit().putBoolean("isAdminTest", false).apply();
            sharedPreferences.edit().remove("username").apply();
            sharedPreferences.edit().remove("testId").apply();
            sharedPreferences.edit().remove("questionId").apply();

            // Redirect the user to login screen.
            goToLoginScreen();
            return true;
        } else if (item.getItemId() == R.id.admin_dashboard) {
            goToAdminDashboardScreen();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupSpinner () {
        spinner = findViewById(R.id.feedbackCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, feedbackCategories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (position) {
            case 0:
                validFeedbackCategory = false;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                validFeedbackCategory = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // Handle feedback submission.
    public void submitFeedback(View v) {
        // Construct feedback from user inputs.
        Feedback feedback = constructFeedback();

        if (feedback != null) {
            // Generate id;
            String id = feedbackRef.push().getKey();
            // Send data to feedbacks branch on Firebase.
            feedbackRef.child(id).setValue(feedback);
            incrementFeedbackCount();
            // Redirect the user to main screen.
            goToMainScreen();
        }
    }

    private void incrementFeedbackCount() {
        appStatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long num_feedbacks = (Long) dataSnapshot.child("num_feedbacks").getValue();
                    appStatsRef.child("num_feedbacks").setValue(num_feedbacks + 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    // Validate user input and return feedback object if valid, otherwise null.
    private Feedback constructFeedback() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Get current user's username.
        String username = sharedPreferences.getString("username", "");

        // Get feedback category from spinner/dropdown.
        Spinner spinner = findViewById(R.id.feedbackCategory);
        String category = spinner.getSelectedItem().toString().trim();

        // Get message from text field.
        EditText messageInput = findViewById(R.id.feedbackMessage);
        String message = messageInput.getText().toString().trim();

        // Get rating from stars rating bar.
        RatingBar ratingBar = findViewById(R.id.feedbackRating);
        double rating = ratingBar.getRating();

        // Check for valid input.
        if (category != null && !category.equals("") && message != null && !message.equals("") && validFeedbackCategory) {
            // Construct feedback object.
            return new Feedback(username, category, message, rating);
        } else {
            // Notify invalid input using toast and return null.
            Toast.makeText(this, "Please make sure to select category, fill out the feedback and give us a rating!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    // Redirect the user to main screen.
    public void goToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Redirect the user to login first.
    public void goToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Transition to admin dashboard screen.
    public void goToAdminDashboardScreen() {
        Intent intent = new Intent(this, AdminDashboardActivity.class);
        startActivity(intent);
    }
}
