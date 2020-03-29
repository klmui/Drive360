## Contribution Guidelines

- Make sure you have Git LFS installed and set up if you are a developer since the videos take up a lot of space and GitHub is not happy with it.

    - Run: "git lfs track '*.mp4' in GitBash (working directory is the project).

    - Run: "git lfs track '*.resource' in GitBash (working directory is the project).

    - Run "git lfs track" to make sure all mp4 and resource files are being tracked.

    - Run "git lfs ls-files" to make sure the correct video files are tracked.

    - If GitHub doesn't let you push still, refer to git lfs migrate import in the resources section in README.md.

- Open up the corresponding AndroidNativeApp or UnityProject folder in either Android Studio or Unity.

- Make a branch when you want to add, commit, or push code in the Android portion. If you're working in Unity, working in master is fine sometimes since we want to avoid any merge conflicts with Unity. 

- After that, test your changes in the emulator or an Android device. **Note that Unity will not run in the emulator.**

- Push your code out to your branch and make a pull request.

- Have at least one other teammate review your code before merging.

- Have fun!

## Running and Testing

- To run the full app as a developer, make sure you have all of the Git LFS assets.

    - ~~Go on this repository -> UnityProject -> assests -> videos and for each video, click "View Raw" to download the video.~~

    - ~~Next, copy and paste each video into your local videos directory and you should be all set to go to run the full application.~~

    - Run 'git lfs fetch --all' to get all LFS assets.

    - If running the app still doesn't work try these steps:

      - Open the Unity project in Unity -> file -> build settings -> check 'export' -> click the 'export' button and export into 'androidBuild'.

      - Go to the 'unityLibrary' folder in Android Studio -> open up the manifest and comment out the intent filter.

      - Run app.
