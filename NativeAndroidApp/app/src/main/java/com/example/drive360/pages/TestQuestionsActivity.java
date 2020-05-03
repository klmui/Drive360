package com.example.drive360.pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.drive360.MainActivity;
import com.example.drive360.R;
import com.example.drive360.auth.LoginActivity;
import com.example.drive360.forms.AddQuestionActivity;
import com.example.drive360.models.Question;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.drive360.Config.userTestsRef;
import static com.example.drive360.Config.adminTestsRef;

public class TestQuestionsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private DatabaseReference questionsRef;
    private String testId;
    private boolean isAdminTest = false;
    private boolean editable = true;
    private List<String> questions;
    private Button startQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_questions);
        sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        setupEditAuthorization();
        setupListView();
    }

    private void setupEditAuthorization() {
        String username = sharedPreferences.getString("username", "");
        testId = sharedPreferences.getString("testId", "");
        isAdminTest = sharedPreferences.getBoolean("isAdminTest", false);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);
        startQuizButton = (Button) findViewById(R.id.startQuizButton);

        if (testId.equals("")) {
            goToTestScreen();
        }

        if (!isAdmin && isAdminTest) {
            editable = false;

            // Disable add button if user does not have authorization.
            FloatingActionButton addQuestionButton = (FloatingActionButton) findViewById(R.id.floatingAddButton);
            CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) addQuestionButton.getLayoutParams();
            p.setAnchorId(View.NO_ID);
            addQuestionButton.setLayoutParams(p);
            addQuestionButton.setVisibility(View.GONE);
        }

        if (isAdminTest) {
            questionsRef = adminTestsRef.child(testId).child("questions");
        } else {
            questionsRef = userTestsRef.child(username).child(testId).child("questions");
        }
    }

    private void setupListView() {
        questions = new ArrayList<String>();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, questions);
        ListView listView = findViewById(R.id.questionList);
        listView.setAdapter(adapter);

        questionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        Question question = d.getValue(Question.class);
                        questions.add(question.title);
                    }
                    if (questions.size() == 0) {
                        startQuizButton.setText("Unfortunately no question available!");
                    }

                    listView.setAdapter(adapter);
                } else {
                    if (questions.size() == 0) {
                        startQuizButton.setText("Unfortunately no question available!");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
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
            Toast.makeText(TestQuestionsActivity.this, "Sign out successful!", Toast.LENGTH_LONG).show();
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
    public void goToAddQuestionScreen(View view) {
        if (editable) {
            Intent intent = new Intent(this, AddQuestionActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid credentials. Please try again!", Toast.LENGTH_LONG).show();
        }
    }

    // Transition to add test screen.
    public void goToQuizScreen(View view) {
        if (questions.size() > 0) {
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
        }
    }

    // Transition to test screen.
    public void goToTestScreen() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }
}
