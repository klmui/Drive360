package com.example.drive360.forms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drive360.R;
import com.example.drive360.pages.TestQuestionsActivity;
import com.example.drive360.models.Test;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static com.example.drive360.Config.appStatsRef;
import static com.example.drive360.Config.userTestsRef;
import static com.example.drive360.Config.adminTestsRef;

public class AddTestActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private boolean isAdminTest = false;
    private DatabaseReference testsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);
        sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        setSwitchVisibility();
    }

    private void setSwitchVisibility() {
        TextView adminTestQuestion = (TextView) findViewById(R.id.isAdminTest);
        Switch adminTestSwitch = (Switch) findViewById(R.id.adminTestSwitch);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);
        if (!isAdmin) {
            adminTestQuestion.setVisibility(View.GONE);
            adminTestSwitch.setVisibility(View.GONE);
        }
    }

    public void createTest(View view) {
        // Construct test from user inputs.
        Test test = constructTest();

        if (isAdminTest) {
            testsRef = adminTestsRef;
        } else {
            testsRef = userTestsRef.child(test.author);
        }

        if (test != null) {
            // Generate id;
            String id = testsRef.push().getKey();
            // Send data to tests branch on Firebase.
            testsRef.child(id).setValue(test);
            incrementTestCount();

            sharedPreferences.edit().putString("testId", id).apply();
            sharedPreferences.edit().putBoolean("isAdminTest", isAdminTest).apply();

            // Transition the user to add question screen.
            goToTestQuestionScreen();
        }
    }

    private void incrementTestCount() {
        appStatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long num_tests = (Long) dataSnapshot.child("num_tests").getValue();
                    appStatsRef.child("num_tests").setValue(num_tests + 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    // Validate user input and return test object if valid, otherwise null.
    private Test constructTest() {
        // Get current user's username.
        String username = sharedPreferences.getString("username", "");

        // Check whether this is admin test.
        Switch adminTestSwitch = (Switch) findViewById(R.id.adminTestSwitch);
        isAdminTest = adminTestSwitch.isChecked();

        // Get test name from text field.
        EditText nameInput = findViewById(R.id.testName);
        String name = nameInput.getText().toString().trim();

        // Get test description from text field.
        EditText descriptionInput = findViewById(R.id.testDescription);
        String description = descriptionInput.getText().toString().trim();

        // Check for valid input.
        if (name != null && !name.equals("") && description != null && !description.equals("")) {
            // Construct test object.
            return new Test(username, isAdminTest, name, description);
        } else {
            // Notify invalid input using toast and return null.
            Toast.makeText(this, "Please make sure to fill out name and description!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public void goToTestQuestionScreen() {
        Intent intent = new Intent(this, TestQuestionsActivity.class);
        startActivity(intent);
    }
}
