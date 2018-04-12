package com.example.davidmaceda.chronometerexample;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;   //USED TO CALCULATE THE TIME DIFFERENCE BETWEEN THE TIME IT STARTED AND THE TIME WE PAUSED IT
    private boolean running;    // Indicates if the chronometer is running or if it's paused

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer); // reference the XML chronometer
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime()); //Returns milliseconds since boot, including time spent in sleep.

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(MainActivity.this, "Restart..!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
        // Method  to  Start
    public void startChronometer(View v) {
        if (!running) {     // check if the chronometer is running
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset); // sets the base back to the "time" when the clock start'd
            chronometer.start();
            running = true;
        }
    }
        // Method  to  Pause
    public void pauseChronometer(View v) {
        if (running) {      // check if the chronometer is running
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase(); // returns the base from when we started it
            running = false;
        }
    }
        // Method  to  Reset
    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime()); // This resets it back to zero
        pauseOffset = 0;
    }
}
