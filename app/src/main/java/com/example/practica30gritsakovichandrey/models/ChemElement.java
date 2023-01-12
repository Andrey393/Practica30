package com.example.practica30gritsakovichandrey.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ChemElement
{
    public int atomic_num;
    String full_name;

    public ChemElement(JSONObject obj) throws JSONException
    {
        atomic_num= obj.getInt("atomic_num");
        full_name= obj.getString("full_name");
    }

    @Override
    public String toString(){return full_name;}
}
