package com.example.drive360.models;

import java.util.List;
import java.util.Map;

public class Classroom {
    public String instructor;
    Map<String, Boolean> learners;
    public String name;
    public String description;
    public String website;

    public Classroom() {
    }

    public Classroom(String instructor, String name, String description, String website) {
        this.instructor = instructor;
        this.name = name;
        this.description = description;
        this.website = website;
    }

}
