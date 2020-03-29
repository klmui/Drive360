using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using VRStandardAssets.Utils;

[RequireComponent(typeof(VRInteractiveItem))]
public class DestinationPinController : MonoBehaviour

{
    // Name of scene we want to go to
    public string sceneName;

    VRInteractiveItem vrInteractive;

    // Called when script is loaded
    private void Awake()
    {
        vrInteractive = GetComponent<VRInteractiveItem>();
    }

    // Send player to specified scene
    void changeScene()
    {
        SceneManager.LoadScene(sceneName);
    }

    void OnEnable()
    {
        // Add method
        vrInteractive.OnClick += changeScene;
    }

    void OnDisable()
    {
        // Remove method
        vrInteractive.OnClick -= changeScene;
    }
}
