using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Video;

public class QuizManagerIntersection : MonoBehaviour
{

    // Question where the canvas appears
    public GameObject questionCanvas;

    public GameObject scoreCanvas;

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

    // For checking if video is over
    double time;
    double currentTime;

    // Start is called before the first frame update
    void Start()
    {

        time = player.clip.length;

        scoreText.text = "Score: 0 / 0";

        // Pause quiz and don't show question canvas
        pauseQuiz();

        // Hide question canvas
        questionCanvas.SetActive(false);

        // Hide score canvas until the end
        scoreCanvas.SetActive(false);

        // Prepare quiz first
        quiz = new Quiz();
        quiz.questions = new Question[3];

        // Initialize each question
        for (int i = 0; i < 3; i++)
        {
            quiz.questions[i] = new Question();
        }

        quiz.questions[0].time = 15;
        quiz.questions[0].title = "Sudden lane changes does not increase the chance of hydroplaning.";
        quiz.questions[0].correct = false;

        quiz.questions[1].time = 27;
        quiz.questions[1].title = "You should begin to slow down here.";
        quiz.questions[1].correct = true;

        quiz.questions[2].time = 35;
        quiz.questions[2].title = "Turning right at any red stoplight is allowed.";
        quiz.questions[2].correct = false;

        // Prepare next question
        prepareNext();
    }

    // Update is called once per frame
    void Update()
    {
        // Increase elaptsed time based on last update (loop)
        elapsedTime += Time.deltaTime;

        // Check time, if a question is due, show it
        if ((elapsedTime > nextQuestion.time) && isShowingQuestions)
        {

            // 1) Show question canvas
            questionCanvas.SetActive(true);
            // Make question canvas face camera
            questionCanvas.transform.position = Camera.main.transform.position + Camera.main.transform.forward * 5;
            questionCanvas.transform.LookAt(transform.position - Camera.main.transform.position,
                Camera.main.transform.rotation * Vector3.up);

            //get direction (pos of the canvas - pos of the camera)
            Vector3 direction = questionCanvas.transform.position - Camera.main.transform.position;

            //set forward of the canvas
            questionCanvas.transform.forward = direction;

            // Make score canvas face camera
            scoreCanvas.transform.position = Camera.main.transform.position + Camera.main.transform.forward * 5;
            scoreCanvas.transform.LookAt(transform.position - Camera.main.transform.position,
                Camera.main.transform.rotation * Vector3.up);

            //get direction (pos of the canvas - pos of the camera)
            Vector3 directionScore = scoreCanvas.transform.position - Camera.main.transform.position;

            //set forward of the canvas
            scoreCanvas.transform.forward = directionScore;

            // 2) Set question title
            questionTitle.text = nextQuestion.title;

            // 3) Pause the quiz
            pauseQuiz();
        }

        // Check if the video is over and then show results
        currentTime = player.time;
        if (((currentTime + .05) >= time) && !isShowingQuestions)
        {
            scoreCanvas.SetActive(true);
            return;
        }

    }

    void pauseQuiz()
    {
        player.Pause();
        isShowingQuestions = false;
    }

    void resumeQuiz()
    {
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
        }
        else
        {
            nextQuestionIndex++;

            // Check that there are more questions left
            if (nextQuestionIndex < quiz.questions.Length)
            {
                nextQuestion = quiz.questions[nextQuestionIndex];
            }
            else
            {
                // No more questions left
                print("Completed quiz");
                questionCanvas.SetActive(false);
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
        }

        scoreText.text = "You completed this tutorial!\nScore: " + totalCorrect + " / " + questionsShown;

        // Move on to next question
        prepareNext();
    }

}
