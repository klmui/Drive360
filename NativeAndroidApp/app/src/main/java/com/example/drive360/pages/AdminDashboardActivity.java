package com.example.drive360.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drive360.R;

public class AdminDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
    }

    public void goToAppStatsScreen(View view) {
        Intent intent = new Intent(this, AppStatsActivity.class);
        startActivity(intent);
    }

    public void goToUserFeedbacksScreen(View view) {
        Intent intent = new Intent(this, FeedbackDisplayActivity.class);
        startActivity(intent);
    }

    public void goToManageUsersScreen(View view) {
        Intent intent = new Intent(this, ManageUserActivity.class);
        startActivity(intent);
    }
}
