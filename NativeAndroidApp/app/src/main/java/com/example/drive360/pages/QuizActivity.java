package com.example.drive360.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drive360.R;
import com.example.drive360.models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.drive360.Config.userTestsRef;
import static com.example.drive360.Config.adminTestsRef;

public class QuizActivity extends AppCompatActivity {
    private List<Question> questions;
    private List<Integer> correctAnswers;
    private String testId;
    private SharedPreferences sharedPreferences;
    private int currentQuestion = 0;
    private boolean firstAttemptUsed = false;
    private boolean secondAttemptUsed = false;
    private int firstAttemptCorrect = 0;
    private int secondAttemptCorrect = 0;
    private boolean questionDone = false;
    private boolean quizDone = false;

    private TextView questionTitle;
    private List<Button> answerButtons;
    private Button firstChoiceButton, secondChoiceButton, thirdChoiceButton, fourthChoiceButton;
    private Button nextQuestionButton;

    private DatabaseReference questionsRef;
    private boolean isAdminTest = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        sharedPreferences = getSharedPreferences("com.example.drive360", Context.MODE_PRIVATE);

        setupQuizPath();
        setupInterface();
        getQuestions();
    }

    private void setupQuizPath() {
        String username = sharedPreferences.getString("username", "");
        testId = sharedPreferences.getString("testId", "");

        isAdminTest = sharedPreferences.getBoolean("isAdminTest", false);

        if (testId.equals("")) {
            goToTestScreen();
        }

        if (isAdminTest) {
            questionsRef = adminTestsRef.child(testId).child("questions");
        } else {
            questionsRef = userTestsRef.child(username).child(testId).child("questions");
        }
    }

    private void setupInterface() {
        questionTitle = (TextView) findViewById(R.id.quizQuestionTitle);
        firstChoiceButton = (Button) findViewById(R.id.firstChoiceButton);
        secondChoiceButton = (Button) findViewById(R.id.secondChoiceButton);
        thirdChoiceButton = (Button) findViewById(R.id.thirdChoiceButton);
        fourthChoiceButton = (Button) findViewById(R.id.fourthChoiceButton);
        nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);

        nextQuestionButton.setVisibility(View.GONE);

        answerButtons = new ArrayList<Button>();
        answerButtons.add(firstChoiceButton);
        answerButtons.add(secondChoiceButton);
        answerButtons.add(thirdChoiceButton);
        answerButtons.add(fourthChoiceButton);
    }

    private void getQuestions() {
        questions = new ArrayList<Question>();

        questionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot d : dataSnapshot.getChildren()) {
                        Question question = d.getValue(Question.class);
                        questions.add(question);
                    }

                    extractCorrectAnswers();
                    if (questions.size() > 0) {
                        setupQuestion();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void extractCorrectAnswers() {
        correctAnswers = new ArrayList<Integer>();
        for (Question q : questions) {
            switch (q.correctAnswer) {
                case "A":
                    correctAnswers.add(0);
                    break;
                case "B":
                    correctAnswers.add(1);
                    break;
                case "C":
                    correctAnswers.add(2);
                    break;
                case "D":
                    correctAnswers.add(3);
                    break;
            }

        }
    }

    private void setupQuestion() {
        Question q = questions.get(currentQuestion);
        questionTitle.setText(q.title);
        firstChoiceButton.setText(q.answerChoices.get(0));
        secondChoiceButton.setText(q.answerChoices.get(1));
        thirdChoiceButton.setText(q.answerChoices.get(2));
        fourthChoiceButton.setText(q.answerChoices.get(3));
    }

    public void firstAnswerClicked(View view) {
        processUserAnswer(0);
    }

    public void secondAnswerClicked(View view) {
        processUserAnswer(1);
    }

    public void thirdAnswerClicked(View view) {
        processUserAnswer(2);
    }

    public void fourthAnswerClicked(View view) {
        processUserAnswer(3);
    }

    private void processUserAnswer(int choice) {
        if (!secondAttemptUsed && !questionDone) {
            boolean isCorrect = choice == correctAnswers.get(currentQuestion);
            if (!isCorrect) {
                Button incorrectButton = answerButtons.get(choice);
                incorrectButton.setBackgroundColor(getResources().getColor(R.color.red));
                if (firstAttemptUsed) {
                    Toast.makeText(this, "Keep practicing. You will conquer this next time!", Toast.LENGTH_LONG).show();
                    Button correctButton = answerButtons.get(correctAnswers.get(currentQuestion));
                    correctButton.setBackgroundColor(getResources().getColor(R.color.green));
                    checkNextQuestionAvailable();
                } else {
                    Toast.makeText(this, "Incorrect answer. Please try again!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Nice job! You answered correctly!", Toast.LENGTH_LONG).show();
                Button correctButton = answerButtons.get(choice);
                correctButton.setBackgroundColor(getResources().getColor(R.color.green));
                if (firstAttemptUsed) {
                    secondAttemptCorrect++;
                } else {
                    firstAttemptCorrect++;
                }
                checkNextQuestionAvailable();
            }
        }

        if (firstAttemptUsed) {
            secondAttemptUsed = true;
        } else {
            firstAttemptUsed = true;
        }
    }

    private void checkNextQuestionAvailable() {
        questionDone = true;
        nextQuestionButton.setVisibility(View.VISIBLE);
        if (currentQuestion == questions.size() - 1) {
            nextQuestionButton.setText("See results");
            quizDone = true;
        }
    }

    public void goToNextQuestion(View view) {
        if (quizDone) {
            Intent intent = new Intent(this, QuizResultActivity.class);
            intent.putExtra("firstAttemptCorrect", firstAttemptCorrect);
            intent.putExtra("secondAttemptCorrect", secondAttemptCorrect);
            intent.putExtra("numQuestions", questions.size());
            startActivity(intent);
        } else {
            questionDone = false;
            currentQuestion++;
            setupQuestion();
            resetFields();
        }
    }

    private void resetFields() {
        for (Button b : answerButtons) {
            b.setBackgroundResource(android.R.drawable.btn_default);
        }
        firstAttemptUsed = false;
        secondAttemptUsed= false;
        nextQuestionButton.setVisibility(View.GONE);
    }

    // Transition to test screen.
    public void goToTestScreen() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }
}
