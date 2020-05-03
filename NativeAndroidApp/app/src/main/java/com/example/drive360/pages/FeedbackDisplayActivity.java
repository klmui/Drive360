package com.example.drive360.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.drive360.R;
import com.example.drive360.models.Feedback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import static com.example.drive360.Config.feedbacksRef;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDisplayActivity extends AppCompatActivity {
    private List<String> feedback;
    private ListView feedbackList;


    private void setupListView() {
        feedback = new ArrayList<String>();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, feedback);
        feedbacksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        Feedback f = d.getValue(Feedback.class);
                        String content = "\n>>> " + f.category
                                + "\n--------------------------------------------------------------"
                                + "\nFeedback: " + f.message
                                + "\n--------------------------------------------------------------"
                                + "\nRated " + f.rating + " stars by " + f.user + "\n";
                        feedback.add(content);
                    }
                    feedbackList.setAdapter(adapter);
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
        setContentView(R.layout.activity_feedback_display);
        feedbackList = findViewById(R.id.feedbackList);
        setupListView();


    }
}


