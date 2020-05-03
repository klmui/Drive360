package com.example.drive360.models;

import com.example.drive360.auth.PasswordHash;

public class User {
    public String username;
    public String password;
    public int age;
    public boolean isAdmin;
    public String role;
    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        hashPassword(password);
        this.role = role;
        this.age = -1;
        this.isAdmin = false;
    }

    private void hashPassword(String password) {
        try {
            this.password = PasswordHash.hashWithoutSalt(password);
        } catch (Exception e) {
            this.password = password;
        }
    }
}
