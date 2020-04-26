using UnityEngine;

[RequireComponent(typeof(Renderer))] // Tells Unity that this requires a Renderer, safety
public class MaterialGradientModifier : MonoBehaviour
{
    private Renderer _renderer; // Don't want it to be accessable to other scripts

    // public Color myColor;
    [SerializeField] private Gradient gradient; // Tell Unity to serialize field, only available from Unity

    // Disappear from inspector if no public
    float _gradientPosition = -1;
    public float gradientPosition
    {
        get { return _gradientPosition; }
        set
        {
            if (_gradientPosition != value)
            {
                _gradientPosition = value;
                _renderer.material.color = gradient.Evaluate(_gradientPosition);
            }
        }
    }

    // Called before start
    private void Awake()
    {
        // Find first renderer that is in same game object
        _renderer = GetComponent<Renderer>();
    }


}