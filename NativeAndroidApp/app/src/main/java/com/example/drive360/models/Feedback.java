package com.example.drive360.models;

public class Feedback {
    public String user;
    public String category;
    public String message;
    public double rating;

    public Feedback() {
    }

    public Feedback(String user, String category, String message, double rating) {
        this.user = user;
        this.category = category;
        this.message = message;
        this.rating = rating;
    }
}
