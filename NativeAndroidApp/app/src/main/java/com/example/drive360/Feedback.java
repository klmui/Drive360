package com.example.drive360;

public class Feedback {
    private String username;
    private String message;
    private int rating;

    public Feedback() {}

    public Feedback(String username, String message, int rating) {
        this.username = username;
        this.message = message;
        this.rating = rating;
    }
}
