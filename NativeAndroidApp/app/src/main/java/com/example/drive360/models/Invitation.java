package com.example.drive360.models;

public class Invitation {
    public String instructor;
    public String learner;
    public String date;
    public boolean accepted;
    public String classroomId;

    public Invitation() {
    }

    public Invitation(String instructor, String learner, String date, String classroomId) {
        this.instructor = instructor;
        this.learner = learner;
        this.date = date;
        this.classroomId = classroomId;
        this.accepted = false;
    }
}
