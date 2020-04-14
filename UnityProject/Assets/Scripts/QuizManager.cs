using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Video;

public class QuizManager : MonoBehaviour
{

    // Question where the canvas appears
    public GameObject questionCanvas;

    public Text questionTitle;

    public Text scoreText;

    public VideoPlayer player;

    // Flag to indicate whether we are showing questions
    bool isShowingQuestions;

    Quiz quiz;

    // Time (used to show questions)
    float elapsedTime = 0;

    Question nextQuestion;

    int nextQuestionIndex;

    int totalCorrect = 0;

    int questionsShown = 0;

    // Start is called before the first frame update
    void Start()
    {
        scoreText.text = "Score: 0 / 0";

        // Pause quiz and don't show question canvas
        pauseQuiz();

        // Hide question canvas
        questionCanvas.SetActive(false);

        // Prepare quiz first
        quiz = new Quiz();
        quiz.questions = new Question[2];

        // Initialize each question
        for (int i = 0; i < 2; i++)
        {
            quiz.questions[i] = new Question();
        }

        quiz.questions[0].time = 3;
        quiz.questions[0].title = "What's up?";
        quiz.questions[0].correct = false;

        quiz.questions[1].time = 8;
        quiz.questions[1].title = "Is this correct?";
        quiz.questions[1].correct = true;


        // Prepare next question
        prepareNext();
    }

    // Update is called once per frame
    void Update()
    {
        // Check that we should be showing questions
        if (!isShowingQuestions) return;

        // Increase elaptsed time based on last update (loop)
        elapsedTime += Time.deltaTime;

        // Check time, if a question is due, show it
        if (elapsedTime > nextQuestion.time)
        {
            // Show question

            // 1) Show question canvas
            questionCanvas.SetActive(true);

            // 2) Set question title
            questionTitle.text = nextQuestion.title;

            // 3) Pause the quiz
            pauseQuiz();
        }
    }

    void pauseQuiz()
    {
        // Video paused
        player.Pause();

        // No showing questions
        isShowingQuestions = false;
    }

    void resumeQuiz()
    {
        // Continue playing video
        player.Play();

        // Continue measuring elapsed time
        isShowingQuestions = true;
    }

    private void prepareNext()
    {
        if (nextQuestion == null)
        {
            nextQuestionIndex = 0;
            nextQuestion = quiz.questions[nextQuestionIndex];
        } else
        {
            nextQuestionIndex++;

            // Check that there are more questions left
            if (nextQuestionIndex < quiz.questions.Length)
            {
                nextQuestion = quiz.questions[nextQuestionIndex];
            } else
            {
                // No more questions left
                print("Completed quiz");
                questionCanvas.SetActive(false);
                scoreText.text += "\nYou completed the quiz!";
                player.Play();
                return;
            }
        }

        resumeQuiz();
    }

    /**
     * Method to handle an answer. Needs to be public so a button can access it
     * Occurs every time the user selects a response
     */
    public void handleAnswer(bool response)
    {
        print("responded : " + response + " the correct answer: " + nextQuestion.correct.ToString());
        questionsShown++;
        questionCanvas.SetActive(false);

        // Check if the answer was correct
        if (response == nextQuestion.correct)
        {
            totalCorrect++;
            scoreText.text = "Correct!";
        } else
        {
            scoreText.text = "Wrong answer!";
        }

        scoreText.text += "\nScore: " + totalCorrect + " / " + questionsShown;

        // Move on to next question
        prepareNext();
    }

}
