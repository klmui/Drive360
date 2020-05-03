package com.example.drive360.models;

import java.util.List;

public class Question {
    public String title;
    public String correctAnswer;
    public List<String> answerChoices;

    public Question() {
    }

    public Question(String title, String correctAnswer, List<String> answerChoices) {
        this.title = title;
        this.correctAnswer = correctAnswer;
        this.answerChoices = answerChoices;
    }
}
