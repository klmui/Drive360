package com.example.drive360.forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drive360.R;
import com.example.drive360.models.Classroom;
import com.example.drive360.pages.ClassroomActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.drive360.Config.appStatsRef;
import static com.example.drive360.Config.classroomsRef;

public class AddClassroomActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classroom);
        sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);
    }

    public void createClassroom(View view) {
        // Construct classroom from user inputs.
        Classroom classroom = constructClassroom();

        if (classroom != null) {
            // Generate id;
            String id = classroomsRef.push().getKey();
            // Send data to classrooms branch on Firebase.
            classroomsRef.child(classroom.instructor).child(id).setValue(classroom);
            sharedPreferences.edit().putString("classroomId", id).apply();
            incrementClassroomCount();
            // Transition to the classroom screen.
            goToClassroomScreen();
        }
    }

    private void incrementClassroomCount() {
        appStatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long num_classrooms = (Long) dataSnapshot.child("num_classrooms").getValue();
                    appStatsRef.child("num_classrooms").setValue(num_classrooms + 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    // Validate user input and return classroom object if valid, otherwise null.
    private Classroom constructClassroom() {
        // Get current user's username.
        String username = sharedPreferences.getString("username", "");

        // Get classroom name from text field.
        EditText nameInput = findViewById(R.id.classroomName);
        String name = nameInput.getText().toString().trim();

        // Get classroom description from text field.
        EditText descriptionInput = findViewById(R.id.classroomDescription);
        String description = descriptionInput.getText().toString().trim();

        // Get classroom website from text field.
        EditText websiteInput = findViewById(R.id.classroomWebsite);
        String website = websiteInput.getText().toString().trim();

        // Check for valid input.
        if (name != null && !name.equals("") && description != null && !description.equals("") && website != null) {
            // Construct classroom object.
            return new Classroom(username, name, description, website);
        } else {
            // Notify invalid input using toast and return null.
            Toast.makeText(this, "Please make sure to fill out name and description!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public void goToClassroomScreen() {
        Intent intent = new Intent(this, ClassroomActivity.class);
        startActivity(intent);
    }
}
