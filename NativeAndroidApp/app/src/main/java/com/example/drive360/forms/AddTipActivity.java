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

import com.example.drive360.MainActivity;
import com.example.drive360.R;
import com.example.drive360.auth.LoginActivity;
import com.example.drive360.models.Tip;
import com.example.drive360.pages.AdminDashboardActivity;
import com.example.drive360.pages.TipDisplayActivity;
import com.example.drive360.pages.ViewTipActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.drive360.Config.appStatsRef;

public class AddTipActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseDatabase firebaseDB;
    private DatabaseReference rootRef;
    private DatabaseReference tipRef;
    private Spinner spinner;
    private boolean validTipCategory;
    private static final String[] tipCategories
            = {"Please choose tip category", "For beginners", "Traffic laws", "Driving warnings", "Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tip);

        // Initialize database, root and feedback references.
        firebaseDB = FirebaseDatabase.getInstance();
        rootRef = firebaseDB.getReference();
        tipRef = rootRef.child("tips");

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
        spinner = findViewById(R.id.tipCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tipCategories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (position) {
            case 0:
                validTipCategory = false;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                validTipCategory = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // Handle tip submission.
    public void submitTip(View v) {
        // Construct tip from user inputs.
        Tip tip = constructTip();

        if (tip != null) {
            // Generate id;
            String id = tipRef.push().getKey();
            // Send data to tips branch on Firebase.
            tipRef.child(id).setValue(tip);
            incrementTipCount();
            // Redirect the user to main screen.
            goToTipDisplayScreen();
        }
    }

    private void incrementTipCount() {
        appStatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long num_tips = (Long) dataSnapshot.child("num_tips").getValue();
                    appStatsRef.child("num_tips").setValue(num_tips + 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    // Validate user input and return tip object if valid, otherwise null.
    private Tip constructTip() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Get current user's username.
        String username = sharedPreferences.getString("username", "");

        // Get tip category from spinner/dropdown.
        Spinner spinner = findViewById(R.id.tipCategory);
        String category = spinner.getSelectedItem().toString().trim();

        // Get tip text from text field.
        EditText textInput = findViewById(R.id.tipText);
        String text = textInput.getText().toString().trim();

        // Check for valid input.
        if (text != null && !text.equals("")) {
            // Construct tip object.
            return new Tip(username, text, category);
        } else {
            // Notify invalid input using toast and return null.
            Toast.makeText(this, "Please make sure to fill out tip!", Toast.LENGTH_LONG).show();
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

    // Redirect the user to view tip screen.
    public void goToTipDisplayScreen() {
        Intent intent = new Intent(this, TipDisplayActivity.class);
        startActivity(intent);
    }

    // Transition to admin dashboard screen.
    public void goToAdminDashboardScreen() {
        Intent intent = new Intent(this, AdminDashboardActivity.class);
        startActivity(intent);
    }
}
