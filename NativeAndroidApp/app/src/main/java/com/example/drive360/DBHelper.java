package com.example.drive360;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBHelper {
    SQLiteDatabase sqLiteDatabase;

    public DBHelper (SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void createQuestionTable() {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS questions "
                + "(id INTEGER PRIMARY KEY, category TEXT, title TEXT, correctAnswer TEXT, "
                + "choice1 TEXT, choice2 TEXT, choice3 TEXT, choice4 TEXT)");
    }

    public void createResponseTable() {
    }

    public ArrayList<Question> getQuestions() {
        createQuestionTable();
        Cursor c = sqLiteDatabase.rawQuery(
                String.format("SELECT * from questions"), null);

        int categoryIndex = c.getColumnIndex("category");
        int titleIndex = c.getColumnIndex("title");
        int correctAnswerIndex = c.getColumnIndex("correctAnswer");
        int choice1Index = c.getColumnIndex("choice1");
        int choice2Index = c.getColumnIndex("choice2");
        int choice3Index = c.getColumnIndex("choice3");
        int choice4Index = c.getColumnIndex("choice4");

        c.moveToFirst();

        ArrayList<Question> questions = new ArrayList<Question>();

        while (!c.isAfterLast()) {
            String category = c.getString(categoryIndex);
            String title = c.getString(titleIndex);
            String correctAnswer = c.getString(correctAnswerIndex);
            String[] answerChoices = {
                    c.getString(choice1Index),
                    c.getString(choice2Index),
                    c.getString(choice3Index),
                    c.getString(choice4Index)
            };

            Question question = new Question(category, title, correctAnswer, answerChoices);
            questions.add(question);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();

        return questions;
    }

    public ArrayList<Question> getQuestionsByCategory(String category) {
        createQuestionTable();
        Cursor c = sqLiteDatabase.rawQuery(
                String.format("SELECT * from questions where category like '%s'", category), null);

        int titleIndex = c.getColumnIndex("title");
        int correctAnswerIndex = c.getColumnIndex("correctAnswer");
        int choice1Index = c.getColumnIndex("choice1");
        int choice2Index = c.getColumnIndex("choice2");
        int choice3Index = c.getColumnIndex("choice3");
        int choice4Index = c.getColumnIndex("choice4");

        c.moveToFirst();

        ArrayList<Question> questions = new ArrayList<Question>();

        while (!c.isAfterLast()) {
            String title = c.getString(titleIndex);
            String correctAnswer = c.getString(correctAnswerIndex);
            String[] answerChoices = {
                    c.getString(choice1Index),
                    c.getString(choice2Index),
                    c.getString(choice3Index),
                    c.getString(choice4Index)
            };

            Question question = new Question(category, title, correctAnswer, answerChoices);
            questions.add(question);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();

        return questions;
    }

    public void createQuestion(String category, String title, String correctAnswer, String[] answerChoices) {
        createQuestionTable();
        sqLiteDatabase.execSQL(
                String.format("INSERT INTO questions (category, title, correctAnswer, " +
                                "choice1, choice2, choice3, choice4) " +
                                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                        category, title, correctAnswer,
                        answerChoices[0], answerChoices[1], answerChoices[2], answerChoices[3]));
    }
}
