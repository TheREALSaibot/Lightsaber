package com.example.blintbence.lightsaber;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Lightsaber extends AppCompatActivity
{
    private MediaPlayer lsabersound;
    private TextView lsaber;
    private boolean isOn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightsaber);
        isOn = false;

        TextView myTextView=(TextView)findViewById(R.id.title);
        Typeface typeFace= Typeface.createFromAsset(getAssets(), "fonts/starjedi.ttf");
        myTextView.setTypeface(typeFace);

        lsabersound = MediaPlayer.create(this, R.raw.lf);
        lsaber = (TextView) findViewById(R.id.lsaber);
        lsaber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isOn)
                {
                    lsaber.setBackgroundResource(R.drawable.lsaberoff);
                } else
                {
                    lsaber.setBackgroundResource(R.drawable.lsaberon);
                }

                lsabersound.start();
            }
        });

    }

    private void switchSaber()
    {
        if (isOn)
        {
            isOn = false;
        } else
        {
            isOn = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        lsabersound.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lightsaber, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
