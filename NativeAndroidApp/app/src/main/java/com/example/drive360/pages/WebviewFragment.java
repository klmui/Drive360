package com.example.drive360.pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.drive360.R;
import com.example.drive360.models.Classroom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;
import static com.example.drive360.Config.classroomsRef;

public class WebviewFragment extends Fragment {
    private WebView webView;
    private String website;
    private SharedPreferences sharedPreferences;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate((R.layout.fragment_webview), container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);
        String classroomId = sharedPreferences.getString("classroomId", "");
        String username = sharedPreferences.getString("username", "");
        classroomsRef.child(username).child(classroomId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Classroom c = dataSnapshot.getValue(Classroom.class);
                    website = c.website;
                    webView = (WebView) v.findViewById((R.id.webview));
                    webView.loadUrl(website);

                    // Enable JS
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);

                    // Open link in app
                    webView.setWebViewClient(new WebViewClient());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "loadClassroom:onCancelled", databaseError.toException());
            }
        });

        return v;
    }

}
