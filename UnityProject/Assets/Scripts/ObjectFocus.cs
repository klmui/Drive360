using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Events;
using System;

public class ObjectFocus : MonoBehaviour
{

    // Need to use System for Serializable (show up in Unity)
    [Serializable]
    public class FloatEvent : UnityEvent<float> { }

    [SerializeField] Transform reference;

    [SerializeField] float minAngle = 10; // Distance to camera is 10 or less, label will be fully opaque
    [SerializeField] float maxAngle = 30;

    [SerializeField] AnimationCurve curve;
    [SerializeField] FloatEvent curveValueChanged;
    [SerializeField] FloatEvent rawValueChanged;

    [SerializeField] UnityEvent onGotFocus;
    [SerializeField] UnityEvent onLostFocus;

    [SerializeField] UnityEvent onActionTrigger;

    private float _fadeAmount = -1; // Update at start
    public float fadeAmount
    {
        get { return _fadeAmount; }
        set
        {
            if (value != _fadeAmount)
            {
                _fadeAmount = value;
                curveValueChanged.Invoke(curve.Evaluate(_fadeAmount));
                rawValueChanged.Invoke(_fadeAmount);
            }
        }
    }

    private float _delta = -1;
    public float delta
    {
        get { return _delta; }
        set
        {
            if (value != _delta)
            {
                _delta = value;
                // Update fade amount value whenever delta changes
                // Between 10 and 30 degrees, fadeAmount is 1. Bigger is 0.
                fadeAmount = Mathf.InverseLerp(maxAngle, minAngle, _delta);

                if (_delta <= minAngle)
                {
                    // This refers to current instance of the object focus 
                    ObjectFocusManager.Add(this);
                }
                else
                {
                    ObjectFocusManager.Remove(this);
                }
            }
        }
    }

    public void GotFocus()
    {
        onGotFocus.Invoke(); // invoke the event
    }

    public void LostFocus()
    {
        onLostFocus.Invoke(); // invoke the event
    }

    public void ActionTrigger()
    {
        onActionTrigger.Invoke();
    }

    void Fade()
    {
        // Update delta
        //Debug.DrawLine(reference.position, transform.position, Color.yellow);
        // Shows forward direction of camera(reference.position) blue axis is z-index
        Vector3 d = (transform.position - reference.position).normalized; // source to dest

        Debug.DrawRay(reference.position, d, Color.yellow);
        Debug.DrawRay(reference.position, reference.forward, Color.cyan);

        // Normalize makes it so that the sum of all of its components = 1
        delta = Vector3.Angle(d.normalized, reference.forward);
    }

    // Start is called before the first frame update
    void Awake()
    {
        //reference = GameObject.FindGameObjectWithTag("MainCamera").transform;
        //reference = Camera.main.transform;
        if (!reference)
        {
            reference = Camera.main.transform;
        }
    }

    // Update is called once per frame
    void Update()
    {
        Fade();
    }

}
