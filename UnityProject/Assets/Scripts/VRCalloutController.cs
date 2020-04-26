using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using VRStandardAssets.Utils;

public class VRCalloutController : MonoBehaviour
{
    // Canvas that will be shown or hiddent
    public Canvas canvasToDisplay;

    public bool hoverActivated = true;

    public bool isClickActivated = false;

    // VR interactive item component
    VRInteractiveItem vrInteractive;

    void Awake()
    {
        // White interactive circle
        vrInteractive = GetComponent<VRInteractiveItem>();
        canvasToDisplay.enabled = false;
    }

    void OnEnable()
    {
        // Hook on to hovering events
        if (hoverActivated)
        {
            vrInteractive.OnOver += ShowCanvas;
            vrInteractive.OnOut += HideCanvas;
        }

        if (isClickActivated)
        {
            vrInteractive.OnClick += ToggleCanvas;
        }
    }

    void ToggleCanvas()
    {
        canvasToDisplay.enabled = !canvasToDisplay.enabled;
    }
    
    void HideCanvas()
    {
        canvasToDisplay.enabled = false;
    }

    void ShowCanvas()
    {
        canvasToDisplay.enabled = true;
    }

    void OnDisable()
    {
        // Remote hook to hovering events
        if (hoverActivated)
        {
            vrInteractive.OnOver -= ShowCanvas;
            vrInteractive.OnOut -= HideCanvas;
        }

        if (isClickActivated)
        {
            vrInteractive.OnClick -= ToggleCanvas;
        }
    }
}
