package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton cbutton = findViewById(R.id.cbutton), kbutton = findViewById(R.id.kbutton), mbutton = findViewById(R.id.mbutton), hbutton = findViewById(R.id.btn_home_back);
        //Button btnList = findViewById(R.id.btnHabitList);


        hbutton.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        mbutton.setOnClickListener(v -> startActivity(new Intent(this, ManagerActivity.class)));
        cbutton.setOnClickListener(v -> startActivity(new Intent(this, NewHabitActivity.class)));
        //kbutton.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));

        TextView myTextView = findViewById(R.id.funFact);

        String[] hf = getResources().getStringArray(R.array.habit_facts);
        String[] ff = getResources().getStringArray(R.array.frog_facts);


        findViewById(R.id.btn_habits).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomIndex = new Random().nextInt(hf.length);
                myTextView.setText(hf[randomIndex]);
            }
        });
        findViewById(R.id.btn_frog_facts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomIndex = new Random().nextInt(ff.length);
                myTextView.setText(ff[randomIndex]);
            }
        });
    }



}