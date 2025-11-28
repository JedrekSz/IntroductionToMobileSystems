package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

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


        findViewById(R.id.btn_habits).setOnClickListener(v -> {});
        findViewById(R.id.btn_frog_facts).setOnClickListener(v -> {});


    }
}