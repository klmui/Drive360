package com.example.drive360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TestPage extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private String userName;

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
        menu.findItem(R.id.add).setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.logout) {
//            SharedPreferences sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);
//
//            // Set isAuthenticated to false and remove username form sharedPreferences.
//            sharedPreferences.edit().putBoolean("isAuthenticated", false).apply();
//            sharedPreferences.edit().remove("username").apply();
//
//            // Redirect the user to login screen.
//            goToLoginScreen();
//            return true;
//        } else if (item.getItemId() == R.id.admin_dashboard) {
//            goToAdminDashboardScreen();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        ArrayList<String> test = new ArrayList<>();
        test.add(String.format("%s", "General"));
        test.add(String.format("%s", "Intersections"));
        test.add(String.format("%s", "Mechanics"));
        test.add(String.format("%s", "Multilane/Highways"));
        test.add(String.format("%s", "Parking"));
        test.add(String.format("%s", "Safety"));

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, test);
        ListView listView = findViewById(R.id.testList);
        listView.setAdapter(adapter);
    }
}
