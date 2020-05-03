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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drive360.R;
import com.example.drive360.auth.LoginActivity;
import com.example.drive360.forms.AddTipActivity;
import com.example.drive360.models.Tip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.drive360.Config.tipsRef;

import java.util.ArrayList;

public class TipDisplayActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDB;
    private DatabaseReference ref;
    private ListView listView;
    private SharedPreferences sharedPreferences;

    private ArrayList<Tip> tipList = new ArrayList<>();
    private ArrayList<String> viewTips = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tip);
        sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // Initialize database and tip child references.
        firebaseDB = FirebaseDatabase.getInstance();
        ref = firebaseDB.getReference().child("tips");

        setupListView();
        setupTestItemListener();
    }

    private void setupListView() {

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewTips);

        listView = (ListView) findViewById(R.id.listView);

        tipsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        Tip t = d.getValue(Tip.class);
                        tipList.add(t);
                    }
                }

                // Adjust listview display to show username and category
                for (Tip tip: tipList){
                    viewTips.add(String.format("Username: %s\nCategory: %s", tip.user, tip.category));
                }
                listView.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void setupTestItemListener() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Initialize intent to take user to ViewTipActivity
            Intent intent = new Intent(getApplicationContext(), ViewTipActivity.class);

            String category = tipList.get(position).category;
            String user = tipList.get(position).user;
            String text = tipList.get(position).text;
            sharedPreferences.edit().putString("category", category).apply();
            sharedPreferences.edit().putString("user", user).apply();
            sharedPreferences.edit().putString("text", text).apply();

            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
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

            // Set isAuthenticated to false and remove username from sharedPreferences.
            sharedPreferences.edit().putBoolean("isAuthenticated", false).apply();
            sharedPreferences.edit().remove("username").apply();

            // Redirect the user to login screen.
            goToLoginScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goToAddTipScreen(View v) {
        Intent intent = new Intent(this, AddTipActivity.class);
        startActivity(intent);
    }
}
