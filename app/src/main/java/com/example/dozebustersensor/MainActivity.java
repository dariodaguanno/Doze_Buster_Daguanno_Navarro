package com.example.dozebustersensor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(proximitySensor == null){
            Toast.makeText(this, "No proximity sensor", Toast.LENGTH_LONG).show();
            finish();
        }

        proximitySensorListener = new SensorEventListener(){

            @SuppressLint("SetTextI18n")
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.values[0] < proximitySensor.getMaximumRange()){
                    ImageView imageview = (ImageView) findViewById(R.id.proximityImage);
                    imageview.setVisibility(imageview.VISIBLE);
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);



                }else {
                    ImageView imageview = (ImageView) findViewById(R.id.proximityImage);
                    imageview.setVisibility(imageview.INVISIBLE);
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    TextView textview = (TextView)  findViewById(R.id.proximityText);
                    textview.setText("Come back and study!");

                    //play sound
                    //MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.mixkit_double_beep_tone_alert_2868);
                    //mediaPlayer.start();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(proximitySensorListener, proximitySensor, 2 * 1000 * 1000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
    }
}