package com.example.drive360.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

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

import com.example.drive360.R;
import com.example.drive360.auth.LoginActivity;
import com.example.drive360.forms.AddClassroomActivity;
import com.example.drive360.models.Classroom;
import com.example.drive360.models.Test;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.drive360.Config.classroomsRef;

public class ClassroomDashboardActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private DatabaseReference singleUserClassroomRef;
    private List<String> classrooms;
    private List<String> firebaseIds;
    private ListView listView;
    private boolean isInstructor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_dashboard);
        sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Get current user's username.
        String username = sharedPreferences.getString("username", "");
        // Initialize user test references.
        singleUserClassroomRef = classroomsRef.child(username);

        setupAddClassAuthorization();
        setupListView();
        setupClassroomItemListener();
    }

    private void setupAddClassAuthorization() {
        isInstructor =  sharedPreferences.getBoolean("isInstructor", false);

        if (!isInstructor) {
            // Disable add button if user does not have authorization.
            FloatingActionButton addClassroomButton = (FloatingActionButton) findViewById(R.id.floatingAddButton);
            CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) addClassroomButton.getLayoutParams();
            p.setAnchorId(View.NO_ID);
            addClassroomButton.setLayoutParams(p);
            addClassroomButton.setVisibility(View.GONE);
        }
    }

    private void setupListView() {
        classrooms = new ArrayList<String>();
        firebaseIds = new ArrayList<String>();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, classrooms);
        listView = findViewById(R.id.classroomList);

        singleUserClassroomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot d1 : dataSnapshot.getChildren()) {
                        Classroom classroom = d1.getValue(Classroom.class);
                        classrooms.add(classroom.name);
                        firebaseIds.add(d1.getKey());
                    }

                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void setupClassroomItemListener() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Initialize intent to take user to TestQuestionsActivity.
            Intent intent = new Intent(getApplicationContext(), ClassroomActivity.class);

            String classroomId = firebaseIds.get(position);
            sharedPreferences.edit().putString("classroomId", classroomId).apply();

            startActivity(intent);
        });
    }

    // Transition to add test screen.
    public void goToAddClassroomScreen(View view) {
        Intent intent = new Intent(this, AddClassroomActivity.class);
        startActivity(intent);
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
}
