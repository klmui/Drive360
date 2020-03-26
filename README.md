# Drive 360

## Team Members
Kenneth Mui, [klmui@wisc.edu](mailto:klmui@wisc.edu), GitHub: klmui
Monica Schmidt, [meschmidt@wisc.edu](mailto:meschmidt@wisc.edu), GitHub: monica22schmidt
Benjamin Carpenter, [blcarpenter@wisc.edu](mailto:blcarpenter@wisc.edu), GitHub: blcarpenter
Khoa Thien Le (Harry), [ktle4@wisc.edu](mailto:ktle4@wisc.edu), GitHub: khoa165

## Summary

Drive 360 helps students in driver’s education classes learn the rules of driving by immersing them into interactive VR environments.

## Abstract/Introduce App

1.  “16-year olds have higher crash rates than drivers of any age”. With new advances in VR technology, people have sought to use it for educational purposes. In this case, we will use it to help people drive by providing an immersive VR experience.
    
2.  Users will be asked questions while the video is going on. The 360 video will pause when the app wants the user to complete a task. For example, to continue the 360 video, users will be required to look left, right, and left while they are at a stop sign, shoulder check while merging onto a highway or changing lanes, and look at mirrors when performing actions. Users will also be able to take a 360 tour of the inside of a car to get familiar with it.
    
3.  It is understandable that some people might be scared to get started with learning how to drive. The transition from reading manuals and rule books to actually driving the car might be uneasy for beginners. Aiming to alleviate the fear some might have, the app targets teenagers at the age of learning how to drive as well as people having anxious feelings while driving.

## Mobile innovation

The app is very mobile-driven innovative since it focuses on turning tedious driving tutorials into a more interactive and dynamic platform that excites the users and encourages their engagement with the app.  This app can be used in driver’s education classes and as a learning resource for people learning how to drive since the equipment is not too expensive for classrooms.
    
## Main modules

The application will be built with VR technology, programmed on Unity and its framework. Afterwards, the Unity application will be exported to Android Studio and embedded in an Android Studio application. The Android Studio application will allow users to select different scenarios and provide suggestions and feedback on driving tutorials. The majority of work would be processing the videos and turning it into VR driving tutorial content. The application controller would probably be built in Android Studio and allow the users to interact with the app. We will also be using Josh’s framework for VR streaming.
  

## Wireframes

<img src="Images/image3.png" width="450" height="250">

<img src="Images/image2.png" width="450" height="250">

<img src="Images/image5.png" width="450" height="250">

<img src="Images/image4.png" width="450" height="250">

<img src="Images/image6.png" width="450" height="250">

<img src="Images/image1.png" width="450" height="250">


## VR Wireframes Description

1.  A description of the 'flow' of your app. This should describe how users will navigate between the VR Videos and the interactive experiences:

    When users load the Android app, they will be presented with a big button that says “Drive”. Once they click that button, they will be loaded into a “VR lobby”. If they want to go back into the Android application, they can just click the back button on their Android device. The VR lobby includes cubes with labels for each driving scenario. If the user looks at a certain cube for a couple of seconds, that scenario will play.

2.  A description of each of the different locations/activities that you will make VR videos in/about:

    One of our team members, Monica, has a car. So we probably meet together and take VR videos while driving on the highway and in residential areas. We plan to have a camera with 360 capability facing the driving direction and simulate different driving scenarios. We also plan on taking a 360 picture in Monica’s car so users can interact with it and see where things are located.

3.  A description of each of the VR experiences you are going to create:
    We plan to simulate the following scenarios:
-   Driving on highway
-   Driving with snow weather
-   Driving in rain weather
-   Encountering traffic signs and road signs

4.  A description of the heads-up display elements:

    We will set up the camera to face the road to simulate driving experience. The user using VR app will look mostly to the front, which focuses on the road. However, since we are going to use a camera with 360 capability, the user has options to look around, checking the left and right side and turning around to look at the back. The app will require the user to do shoulder checks and make sure that they are clear to pass each intersection.

## Milestone 1 (Due March 15th)

-   Record 360 videos for merging onto the highway and lane changing.
-   Take a 360 picture inside of the car.
-   Unity:
    -  Learn from Unity resources on how to add interactivity in Unity with C# scripts.
    
    -   Import the 360 videos and make the VR lobby.
    
    -   Load the specific 360 video when users look at its associated cube in the VR lobby.
    

-   Android:
    

    -   Set up the dummy project to link between the VR section on Unity and landing screen on Android Studio.
    
    -   Set up the home page like the wireframe above.
    

## Milestone 2 (Due March 29th)

-   Unity:
    

    -   Add more VR videos that simulate different scenarios in driving.
    
    -   Pause the videos at certain times to present the user with questions.
    
    -   Indicate whether the user was right or wrong after answering the question.
    
    -   Optional: show the user a list of questions they got right or wrong.
    

-   Android:
    

    -   Set up activities for login, register, learn, feedback, and test (layouts).
    
    -   Setup menu for login and register.
    
    -   Link activities.
    
    -   Add driving manuals to the learn section.
    

## Milestone 3 (Due April 12th)

-   Unity:
    

    -   Continue to work on VR user experiences.
    
    -   Work on creating the 360 environment for the car so users can interact with it see where things are located inside of the car.
    

-   Android:
    

    -   Set up authentication and a database to store the user’s progress in previous test attempts.
    
    -   Create tests for the “Tests” portion of the app and present their score at the end.
    

## Milestone 4 (Due April 26th)

-   Unity:
    

    -   Finalize the VR videos and link it to the Android app.
    
    -   Fix any bugs.
    
    -   Optional: Track the user’s progress within the Unity application.
    

-   Android:
    

    -   Improve user interface and user experiences.
    
    -   Fix any bugs that might crash the app.
    
    -   Do comprehensive app testing.
    
    -   Prepare for demo.
    
    -   Optional: allow users to share their scores on social media.


## Contribution Guidelines

- Make sure you have Git LFS installed and set up if you are a developer since the videos take up a lot of space and GitHub is not happy with it.

    - Run: "git lfs track '*.mp4' in GitBash (working directory is the project).

    - Run "git lfs track" to make sure mp4 files are being tracked.

    - Run "git lfs ls-files" to make sure the correct video files are tracked.

    - If GitHub doesn't let you push still, refer to git lfs migrate import.

- Open up the corresponding AndroidNativeApp or UnityProject folder in either Android Studio or Unity.

- Make a branch when you want to add, commit, or push code in the Android portion. If you're working in Unity, working in master is fine since we want to avoid any merge conflicts with Unity.

- After that, test your changes in the emulator or an Android device. **Note that Unity will not run in the emulator.**

- Push your code out to your branch and make a pull request.

- Have at least one other teammate review your code before merging.

- Have fun!


## Running and Testing

- Make sure you have Git LFS installed and set up if you are a developer since the videos take up a lot of space and GitHub is not happy with it.

- To run the full app as a developer, make sure you have the videos.

    - Go on this repository -> UnityProject -> assests -> videos and for each video, click "View Raw" to download the video.

    - Next, copy and paste each video into your local videos directory and you should be all set to go to run the full application.

- This application will hopefully get pushed to the Play Store by the end of April or early May.