package com.example.drive360;

import java.util.HashMap;
import java.util.Map;

public class Feedback {
    private String username;
    private String category;
    private String message;
    private float rating;

    public Feedback() {}

    public Feedback(String username, String category, String message, float rating) {
        this.username = username;
        this.category = category;
        this.message = message;
        this.rating = rating;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("category", category);
        result.put("message", message);
        result.put("rating", rating);

        return result;
    }
}
