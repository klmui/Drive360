package com.example.drive360;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Config {
    private static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    public static DatabaseReference appStatsRef = rootRef.child("admin_info").child("stats");
    public static DatabaseReference adminTestsRef = rootRef.child("admin_tests");
    public static DatabaseReference classroomsRef = rootRef.child("classrooms");
    public static DatabaseReference userTestsRef = rootRef.child("user_tests");
    public static DatabaseReference feedbacksRef = rootRef.child("feedbacks");
    public static DatabaseReference tipsRef = rootRef.child("tips");
    public static DatabaseReference usersRef = rootRef.child("users");
    public static DatabaseReference invitationsRef = rootRef.child("invitations");
    public static DatabaseReference discussionsRef = rootRef.child("discussions");
}
