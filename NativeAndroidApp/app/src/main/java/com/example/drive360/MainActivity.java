package com.example.drive360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnLoadUnity(View v) {
        Intent intent = new Intent(this, MainUnityActivity.class);
        startActivity(intent);
    }

//    @Override
//    public void onBackPressed() {
//        finishAffinity();
//    }
}
