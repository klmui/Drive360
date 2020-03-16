package com.example.drive360;

import androidx.appcompat.app.AppCompatActivity;
import com.unity3d.player.UnityPlayerActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainUnityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_unity);
        Intent intent = new Intent(this, UnityPlayerActivity.class);
        startActivity(intent);
    }

    // User quit Unity application
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }
}
