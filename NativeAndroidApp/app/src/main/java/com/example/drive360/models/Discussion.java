package com.example.drive360.models;

public class Discussion {
    public String user;
    public String classroomId;
    public String content;
    public String category;

    public Discussion() {
    }

    public Discussion(String user, String classroomId, String category, String content) {
        this.user = user;
        this.classroomId = classroomId;
        this.category = category;
        this.content = content;
    }
}
