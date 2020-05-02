using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Video;
using VRStandardAssets.Utils;

public class LookManager : MonoBehaviour
{
    public Canvas instructionCanvas;
    public VRInteractiveItem rearView;
    public VRInteractiveItem sideView;
    public VRInteractiveItem blindspot;

    public VideoPlayer player;

    private float elapsedTime = 0;

    private bool finished = false;

    // Start is called before the first frame update
    void Awake()
    {
        instructionCanvas.enabled = true;
        rearView.enabled = false;
        sideView.enabled = false;
        blindspot.enabled = false;
    }

    // Update is called once per frame
    void Update()
    {
        elapsedTime += Time.deltaTime;

        if (elapsedTime > 23 && !finished)
        {
            player.Pause();
            instructionCanvas.enabled = true;
            rearView.enabled = true;
            sideView.enabled = true;
            blindspot.enabled = true;
        }

        if (!rearView.enabled && !sideView.enabled && !blindspot.enabled && elapsedTime > 23)
        {
            instructionCanvas.enabled = false;
            finished = true;
            player.Play();
        }
    }

    void OnEnable()
    {
        rearView.OnOver += HideDotRear;
        sideView.OnOver += HideDotSide;
        blindspot.OnOver += HideDotBlind;
    }

    void HideDotRear()
    {
        rearView.enabled = false;
    }

    void HideDotSide()
    {
        sideView.enabled = false;
    }

    void HideDotBlind()
    {
        blindspot.enabled = false;
    }
}
