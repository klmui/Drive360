package com.example.drive360.forms;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.drive360.R;
import com.example.drive360.auth.LoginActivity;
import com.example.drive360.models.Discussion;
import com.example.drive360.pages.AdminDashboardActivity;
import com.example.drive360.pages.ClassroomActivity;
import com.example.drive360.pages.ClassroomDashboardActivity;
import com.google.firebase.database.DatabaseReference;

import static com.example.drive360.Config.discussionsRef;

public class AddDiscussionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private boolean validDiscussionCategory;
    private static final String[] discussionCategories
            = {"Please choose discussion category", "Logistics question", "Class content", "Driving-related question", "Others"};
    private DatabaseReference classroomDiscussionRef;
    private SharedPreferences sharedPreferences;
    private String classroomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussion);
        sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        setupSpinner();

        classroomId = sharedPreferences.getString("classroomId", "");
        if (classroomId.equals("")) {
            goToClassroomDashboardActivity();
        }

        classroomDiscussionRef = discussionsRef.child(classroomId);
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
        spinner = findViewById(R.id.discussionCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, discussionCategories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (position) {
            case 0:
                validDiscussionCategory = false;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                validDiscussionCategory = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // Handle discussion submission.
    public void submitDiscussion(View v) {
        // Construct discussion from user inputs.
        Discussion discussion = constructDiscussion();

        if (discussion != null) {
            // Generate id;
            String id = classroomDiscussionRef.push().getKey();
            // Send data to discussions branch on Firebase.
            classroomDiscussionRef.child(id).setValue(discussion);
            // Redirect the user to classroom screen.
            goToClassroomActivity();
        }
    }

    // Validate user input and return feedback object if valid, otherwise null.
    private Discussion constructDiscussion() {
        // Get current user's username.
        String username = sharedPreferences.getString("username", "");

        // Get discussion category from spinner/dropdown.
        Spinner spinner = findViewById(R.id.discussionCategory);
        String category = spinner.getSelectedItem().toString().trim();

        // Get content from text field.
        EditText contentInput = findViewById(R.id.discussionContent);
        String content = contentInput.getText().toString().trim();

        // Check for valid input.
        if (category != null && !category.equals("") && content != null && !content.equals("") && validDiscussionCategory) {
            // Construct discussion object.
            return new Discussion(username, classroomId, category, content);
        } else {
            // Notify invalid input using toast and return null.
            Toast.makeText(this, "Please make sure to select category and fill out the content!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void goToClassroomActivity() {
        Intent intent = new Intent(this, ClassroomActivity.class);
        startActivity(intent);
    }

    private void goToClassroomDashboardActivity() {
        Intent intent = new Intent(this, ClassroomDashboardActivity.class);
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
