package com.example.practica30gritsakovichandrey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.practica30gritsakovichandrey.models.ChemElement;
import com.example.practica30gritsakovichandrey.models.SpecLine;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity
{

    Activity context;
    Spinner sp;
    SpectraView sv;
    ArrayAdapter <ChemElement> adp;
    Button b;

    public  void zoom_in(View v)
    {
        float wlen_center= (sv.wlen_max+ sv.wlen_min)/ 2.0f;
        float wlen_dist = wlen_center - sv.wlen_min;
        float zoom_percent = 0.1f;
        sv.wlen_min += wlen_dist * zoom_percent;
        sv.wlen_max -= wlen_dist * zoom_percent;
        sv.have_background = false;
        sv.invalidate();

    }

    public void zoom_out(View v)
    {
        float wlen_center= (sv.wlen_max+ sv.wlen_min)/ 2.0f;
        float wlen_dist = wlen_center - sv.wlen_min;
        float zoom_percent = 0.1f;
        sv.wlen_min -= wlen_dist * zoom_percent;
        sv.wlen_max += wlen_dist * zoom_percent;
        sv.have_background = false;
        sv.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv= findViewById(R.id.spectraView2);
        context = this;

        sp=findViewById(R.id.spinner);
        adp=new ArrayAdapter<ChemElement>(this, android.R.layout.simple_expandable_list_item_1);

        ApiHelper req= new ApiHelper(this)
        {
            @Override
            public void on_ready(String res)
            {
                try
                {
                    JSONArray arr= new JSONArray(res);
                    for(int i =0; i<arr.length();i++)
                        adp.add(new ChemElement(arr.getJSONObject(i)));
                    sp.setAdapter(adp);
                }
                catch (JSONException ex){}
            }
        };
        req.send("http://spectra.spbcoit.ru/lab/spectra/api/rpc/get_elements","{}");

        b= findViewById(R.id.buttonLoad);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ChemElement el = (ChemElement) sp.getSelectedItem();

                ApiHelper req =new ApiHelper(context)
                {
                    @Override
                    public  void on_ready(String res)
                    {
                        try
                        {
                            JSONArray arr= new JSONArray(res);
                            for(int i =0; i<arr.length();i++)
                                sv.lines.add(new SpecLine(arr.getJSONObject(i)));
                            sv.invalidate();
                        }
                        catch (JSONException ex){}
                    }
                };
                sv.lines.clear();
                req.send("http://spectra.spbcoit.ru/lab/spectra/api/rpc/get_lines","{\"atomic_num\": "
                        + String.valueOf(el.atomic_num) + "}");
            }
        });
        sv.ctx=this;
        sv.setWillNotDraw(false);
        sv.invalidate();
    }
}