package com.example.drive360.pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drive360.R;
import com.example.drive360.auth.LoginActivity;
import com.example.drive360.forms.AddTestActivity;
import com.example.drive360.models.Test;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.drive360.Config.userTestsRef;
import static com.example.drive360.Config.adminTestsRef;

public class TestActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private DatabaseReference singleUserTestRef;
    private List<String> tests;
    private List<String> firebaseIds;
    private List<Boolean> isAdminTests;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Get current user's username.
        String username = sharedPreferences.getString("username", "");
        // Initialize user test references.
        singleUserTestRef = userTestsRef.child(username);

        setupListView();
        setupTestItemListener();
    }

    private void setupListView() {
        tests = new ArrayList<String>();
        firebaseIds = new ArrayList<String>();
        isAdminTests = new ArrayList<Boolean>();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tests);
        listView = findViewById(R.id.testList);

        adminTestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot d1 : dataSnapshot.getChildren()) {
                        Test test = d1.getValue(Test.class);
                        tests.add(test.name);
                        firebaseIds.add(d1.getKey());
                        isAdminTests.add(true);
                    }

                    listView.setAdapter(adapter);

                    singleUserTestRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for(DataSnapshot d2 : dataSnapshot.getChildren()) {
                                    Test test = d2.getValue(Test.class);
                                    tests.add(test.name);
                                    firebaseIds.add(d2.getKey());
                                    isAdminTests.add(false);
                                }

                                listView.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void setupTestItemListener() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Initialize intent to take user to TestQuestionsActivity.
            Intent intent = new Intent(getApplicationContext(), TestQuestionsActivity.class);

            String testId = firebaseIds.get(position);
            boolean isAdminTest = isAdminTests.get(position);
            sharedPreferences.edit().putString("testId", testId).apply();
            sharedPreferences.edit().putBoolean("isAdminTest", isAdminTest).apply();

            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
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
            sharedPreferences.edit().putBoolean("isAuthenticated", false).apply();
            sharedPreferences.edit().putBoolean("isAdmin", false).apply();
            sharedPreferences.edit().putBoolean("isInstructor", false).apply();
            sharedPreferences.edit().putBoolean("isAdminTest", false).apply();
            sharedPreferences.edit().remove("username").apply();
            sharedPreferences.edit().remove("testId").apply();
            sharedPreferences.edit().remove("questionId").apply();

            // Redirect the user to login screen.
            goToLoginScreen();
            Toast.makeText(TestActivity.this, "Sign out successful!", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.admin_dashboard) {
            goToAdminDashboardScreen();
        }

        return super.onOptionsItemSelected(item);
    }

    // Transition to login screen.
    private void goToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Transition to admin dashboard screen.
    private void goToAdminDashboardScreen() {
        Intent intent = new Intent(this, AdminDashboardActivity.class);
        startActivity(intent);
    }

    // Transition to add test screen.
    public void goToAddTestScreen(View view) {
        Intent intent = new Intent(this, AddTestActivity.class);
        startActivity(intent);
    }
}
