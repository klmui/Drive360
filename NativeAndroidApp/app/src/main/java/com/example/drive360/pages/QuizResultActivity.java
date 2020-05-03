package com.example.drive360.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.drive360.MainActivity;
import com.example.drive360.R;

public class QuizResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        setupResult();
    }

    private void setupResult() {
        Intent intent = getIntent();
        int firstAttempt = intent.getIntExtra("firstAttemptCorrect", -1);
        int secondAttempt = intent.getIntExtra("secondAttemptCorrect", -1);
        int numQuestions = intent.getIntExtra("numQuestions", -1);

        if (firstAttempt >= 0 && secondAttempt >= 0 && numQuestions > 0 && (firstAttempt + secondAttempt <= numQuestions)) {
            double rawScore = firstAttempt + secondAttempt * 0.5;
            double score = (rawScore / numQuestions) * 100;
            int numWrong = numQuestions - secondAttempt - firstAttempt;

            TextView firstAttemptStatement = (TextView) findViewById(R.id.correctFirstAttempt);
            TextView secondAttemptStatement = (TextView) findViewById(R.id.correctSecondAttempt);
            TextView numberIncorrectQuestionsStatement = (TextView) findViewById(R.id.numberIncorrectQuestions);
            TextView totalQuizScore = (TextView) findViewById(R.id.totalQuizScore);

            firstAttemptStatement.setText("You got " + firstAttempt + " questions correct on the first attempt.");
            secondAttemptStatement.setText("You got " + secondAttempt + " questions correct on the second attempt.");
            if (numWrong > 0) {
                numberIncorrectQuestionsStatement.setText("You got " + numWrong + " questions wrong unfortunately.");
            } else {
                numberIncorrectQuestionsStatement.setText("Yay, you got all " + numQuestions + " questions correct.");
            }

            String formattedScore = String.format("%.2f", score);
            totalQuizScore.setText("Your total score is " + formattedScore + "/100!");
        }
    }

    public void goToMainScreen(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
