package com.example.drive360.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.drive360.R;
import androidx.annotation.NonNull;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.drive360.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import static com.example.drive360.Config.usersRef;
import java.util.ArrayList;
import java.util.List;

public class ManageUserActivity extends AppCompatActivity {
    private List<String> users;
    private ListView userList;


    private void setupListView() {
        users = new ArrayList<String>();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        User u = d.getValue(User.class);
                        users.add("\nUser: " + u.username + "\nRole: " + u.role + "\nAdmin: " + u.isAdmin + "\n");

                    }
                    userList.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        userList = findViewById(R.id.usersList);
        setupListView();
    }
}
