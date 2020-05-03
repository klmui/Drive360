package com.example.drive360.pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drive360.R;

public class ViewTipActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tip);
        sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        // get data
        String user = sharedPreferences.getString("user", "");
        String category = sharedPreferences.getString("category", "");
        String text = sharedPreferences.getString("text", "");

        // set data
        TextView userTV = findViewById(R.id.userTV);
        TextView categoryTV = findViewById(R.id.categoryTV);
        TextView textTV = findViewById(R.id.textTV);

        // display data
        userTV.setText("Username: " + user);
        categoryTV.setText("Category: " + category);
        textTV.setText(text);

    }

}
