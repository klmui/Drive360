package com.example.drive360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateTest extends AppCompatActivity {
    public void onSubmitClicked(View view){
        goToCreateTest();
    }
    public void goToCreateTest(){
        Intent intent = new Intent(this, TestQuestions.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
    }
}
