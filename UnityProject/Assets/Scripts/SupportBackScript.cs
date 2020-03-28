using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SupportBackScript: MonoBehaviour {
    void Start() {

    }

    // Update is called once per frame
    void Update() {
        if (Application.platform == RuntimePlatform.Android)
            if (Input.GetKeyDown(KeyCode.Escape)) Application.Quit();
    }

}