package com.example.drive360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class FeedbackActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ValueEventListener {
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
    protected void onStart() {
        super.onStart();
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
            // Convert feedback to key-value pairs.
            Map<String, Object> feedbackValues = feedback.toMap();
            // Generate id;
            String id = feedbackRef.push().getKey();
            // Send data to feedbacks branch on Firebase.
            feedbackRef.child(id).setValue(feedbackValues);
            // Redirect the user to main screen.
            goToLoginScreen();
        }
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
        float rating = ratingBar.getRating();

        // Check for valid input.
        if (category != null && !category.equals("") && message != null && !message.equals("")) {
            // Construct feedback object.
            return new Feedback(username, category, message, rating);
        } else {
            // Notify invalid input using toast and return null.
            Toast.makeText(this, "Please make sure to select category, fill out the feedback and give us a rating!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    // Redirect the user to main screen.
    public void goToLoginScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
