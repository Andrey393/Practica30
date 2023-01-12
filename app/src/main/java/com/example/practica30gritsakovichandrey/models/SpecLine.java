package com.example.practica30gritsakovichandrey.models;

import android.graphics.Paint;

import org.json.JSONException;
import org.json.JSONObject;

public class SpecLine
{
    public float wavelenght;
    float rel_intensity;

    int red;
    int green;
    int blue;

    public SpecLine(JSONObject obj) throws JSONException
    {
        wavelenght = (float) obj.getDouble("wavelenght");
        rel_intensity = (float) obj.getDouble("rel_intensity");

        red=(int) (obj.getDouble("red")*255.0f);
        green=(int) (obj.getDouble("green")*255.0f);
        blue=(int) (obj.getDouble("blue")*255.0f);

    }
    public void setPaintColor(Paint p)
    {
        p.setARGB(255,red,green,blue);
    }
}
