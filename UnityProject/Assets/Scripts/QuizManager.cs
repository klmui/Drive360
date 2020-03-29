using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Video;

public class QuizManager : MonoBehaviour
{

    // Question where the canvas appears
    public GameObject questionCanvas;

    // Question title
    public Text questionTitle;

    // Score text
    public Text scoreText;

    // Video player
    public VideoPlayer player;

    // Flag to indicate whether we are showing questions
    bool isShowingQuestions;

    Quiz quiz;

    // Time (used to show questions)
    float elapsedTime = 0;

    // Start is called before the first frame update
    void Start()
    {
        // Video paused
        player.Pause();

        // No showing questions
        isShowingQuestions = false;

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

        // Initiate showing questions process and play video
        player.Play();
        isShowingQuestions = true;
    }

    // Update is called once per frame
    void Update()
    {
        // Check that we should be showing questions
        if (!isShowingQuestions) return;

        // Increase elaptsed time based on last update (loop)
        elapsedTime += Time.deltaTime;

        // Check time, if a question is due, show it
        if (elapsedTime > quiz.questions[0].time)
        {
            // Show question

            // Show question canvas
            questionCanvas.SetActive(true);

            // Set question title
            questionTitle.text = quiz.questions[0].title;
        }
    }
}
