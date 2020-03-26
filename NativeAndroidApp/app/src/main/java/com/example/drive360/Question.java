package com.example.drive360;

public class Question {
    private String category;
    private String title;
    private String correctAnswer;
    private String[] answerChoices;

    public Question(String category, String title, String correctAnswer, String[] answerChoices) {
        this.category = category;
        this.title = title;
        this.correctAnswer = correctAnswer;
        this.answerChoices = answerChoices;
    }

    public String getCategory() {  return category; }
    public String getTitle() {  return title; }
    public String getCorrectAnswer() {  return correctAnswer; }
    public String[] getAnswerChoices() {  return answerChoices; }
}
