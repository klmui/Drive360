package com.example.drive360.models;

import java.util.ArrayList;
import java.util.List;

public class Tip {
    public String user;
    public String text;
    public String category;
    public int votes;
    public List<String> voters = new ArrayList<String>();

    public Tip(String user, String text, String category) {
        this.user = user;
        this.text = text;
        this.category = category;
        this.votes = 1;
        this.voters.add(user);
    }

    public Tip(){}
}
