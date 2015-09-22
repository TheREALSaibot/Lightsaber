package com.example.blintbence.lightsaber;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Lightsaber extends AppCompatActivity implements SensorEventListener
{
    private MediaPlayer lsabersound;
    private MediaPlayer swing;
    private TextView lsaber;
    private boolean isOn;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    private SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightsaber);
        isOn = false;

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){ //chcking for version of android
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0)
                {
                }
            }
        });

        TextView myTextView=(TextView)findViewById(R.id.title);
        Typeface typeFace= Typeface.createFromAsset(getAssets(), "fonts/Starjedi.ttf");
        myTextView.setTypeface(typeFace);

        lsabersound = MediaPlayer.create(this, R.raw.hf);
        swing = MediaPlayer.create(this, R.raw.saberswing);
        lsaber = (TextView) findViewById(R.id.lsaber);

        lsaber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switchSaber();
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



    @TargetApi(21)
    protected void createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        soundPool = new SoundPool.Builder().setAudioAttributes(attributes).build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool(){
        soundPool = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
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
        senSensorManager.unregisterListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 50) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    swing.start();
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
