package com.example.drive360.pages;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drive360.MainActivity;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.unity3d.player.UnityPlayerActivity;

public class MainUnityActivity extends UnityPlayerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addControlsToUnityFrame();
        //Intent intent = new Intent(this, UnityPlayerActivity.class);
        //startActivity(intent);
    }

    protected void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onUnityPlayerUnloaded() {
        showMainActivity();
    }

    public void addControlsToUnityFrame() {
        FrameLayout layout = mUnityPlayer;
        // Not used because it will save battery if we unload the unity player activity and then show main
//        {
//            Button myButton = new Button(this);
//            myButton.setText("Show Main");
//            myButton.setX(10);
//            myButton.setY(500);
//
//            myButton.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    showMainActivity();
//                }
//            });
//            layout.addView(myButton, 300, 200);
//        }

        {
            Button myButton = new Button(this);
            myButton.setText("X");
            myButton.setX(0);
            myButton.setY(0);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Closes the Unity app we hid from the user
                    mUnityPlayer.unload();
                }
            });
            layout.addView(myButton, 105, 105);
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent goToMain = new Intent(this, MainActivity.class);
            startActivity(goToMain);
            return true;
        } else
            return false;
    }

}
