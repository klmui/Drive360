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
    public static ArrayList<Tests> testList;
    private String userName;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.test_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

//        Intent intent = getIntent();
//        String str = intent.getStringExtra("message");
//
//        sharedPreferences =
//                getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
//
//        if(!sharedPreferences.getString("username", "").equals("")) {
//            userName = sharedPreferences.getString("username", "");
//        }
//
//
//        Context context = getApplicationContext();
//        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",
//                Context.MODE_PRIVATE, null);
//
////        DBHelper dbh = new DBHelper(sqLiteDatabase);
////        notes = dbh.readNotes(str);
//
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
