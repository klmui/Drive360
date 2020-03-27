package com.example.drive360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ValueEventListener {
    private FirebaseDatabase firebaseDB;
    private DatabaseReference rootRef;
    private DatabaseReference feedbackRef;
    private Spinner spinner;
    private static final String[] feedbackTypes
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
                android.R.layout.simple_spinner_item, feedbackTypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (position) {
            case 0:
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void submitFeedback(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Get current user's username.
        String username = sharedPreferences.getString("username", "");

        // Get feedback category from spinner/dropdown.
        Spinner spinner = findViewById(R.id.feedbackCategory);
        String category = spinner.getSelectedItem().toString();

        // Get message from text field.
        EditText messageInput = findViewById(R.id.feedbackMessage);
        String message = messageInput.getText().toString();

        // Get rating from stars rating bar.
        RatingBar ratingBar = findViewById(R.id.feedbackRating);
        float rating = ratingBar.getRating();

//        String key = firebaseDB.child("feedbacks").push().getKey();
//        Feedback feedback = new Feedback(username, category, message, rating);
//        Map<String, Object> feedbackValues = feedback.toMap();

//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/feedbacks/" + key, feedbackValues);
        Log.e("username for firebase", username);
        DatabaseReference fbRef = feedbackRef.child("abc");
        fbRef.setValue(username);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
