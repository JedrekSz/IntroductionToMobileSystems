package com.example.introductiontomobilesystems;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private RecyclerView recyclerView;
    private HabitsStorage storage;
    private HabitAdapter adapter;

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private TextView tvSteps;
    private boolean isSensorPresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = new HabitsStorage(this);

        recyclerView = findViewById(R.id.rvHabits);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageButton cbutton = findViewById(R.id.cbutton);
        ImageButton kbutton = findViewById(R.id.kbutton);
        ImageButton mbutton = findViewById(R.id.mbutton);
        ImageButton hbutton = findViewById(R.id.btn_home_back);

        hbutton.setOnClickListener(v -> {});

        mbutton.setOnClickListener(v -> startActivity(new Intent(this, ManagerActivity.class)));

        cbutton.setOnClickListener(v -> startActivity(new Intent(this, NewHabitActivity.class)));

        kbutton.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));

        tvSteps = findViewById(R.id.tvSteps);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent = true;
        } else {
            tvSteps.setText("-");
            isSensorPresent = false;
        }
        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                        100);
            }
        }

        PeriodicWorkRequest habitCheckRequest =
                new PeriodicWorkRequest.Builder(HabitWorker.class, 15, TimeUnit.MINUTES)
                        .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "HABIT_CHECKER_WORK",
                ExistingPeriodicWorkPolicy.KEEP,
                habitCheckRequest
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView myTextView = findViewById(R.id.tvBubble);
        String[] messages = getResources().getStringArray(R.array.home_tips);
        if (messages.length > 0) {
            int randomIndex = new Random().nextInt(messages.length);
            myTextView.setText(messages[randomIndex]);
        }

        List<Habit> list = storage.load();
        List<Habit> activeHabits = new ArrayList<>();
        for (Habit h : list) {
            if (h.active) {
                activeHabits.add(h);
            }
        }

        adapter = new HabitAdapter(this, activeHabits);
        recyclerView.setAdapter(adapter);

        ImageView myImage = findViewById(R.id.imgFrog);
        TextView myTextView2 = findViewById(R.id.tvSubtitle);
        String[] feedback = getResources().getStringArray(R.array.isYourHabitGood);
        if (list.size() <= 1){
            myTextView2.setText(feedback[0]);
            myImage.setImageResource(R.drawable.kenbad);}
        else if (list.size() <= 3){
            myTextView2.setText(feedback[1]);
            myImage.setImageResource(R.drawable.kenfine);}
        else if (list.size() <= 5){
            myTextView2.setText(feedback[2]);
            myImage.setImageResource(R.drawable.kengood);}
        else if (list.size() <= 7){
            myTextView2.setText(feedback[3]);
            myImage.setImageResource(R.drawable.kengreat);}
        else{
            myTextView2.setText(feedback[4]);
            myImage.setImageResource(R.drawable.kenthrive);}

        if (isSensorPresent) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorPresent) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int totalSteps = (int) event.values[0];
            tvSteps.setText(String.valueOf(totalSteps));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}