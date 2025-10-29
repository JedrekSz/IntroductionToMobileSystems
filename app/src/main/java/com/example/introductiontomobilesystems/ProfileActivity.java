package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.btn_home).setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        findViewById(R.id.btn_personalized).setOnClickListener(v -> {});
        findViewById(R.id.btn_habits).setOnClickListener(v -> startActivity(new Intent(this, HabitListActivity.class)));
        findViewById(R.id.btn_frog_facts).setOnClickListener(v -> {});

        View nav = findViewById(R.id.bottom_nav);
        nav.findViewById(R.id.btn_nav_home).setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        nav.findViewById(R.id.btn_nav_list).setOnClickListener(v -> startActivity(new Intent(this, HabitListActivity.class)));
        nav.findViewById(R.id.btn_nav_new).setOnClickListener(v -> startActivity(new Intent(this, NewHabitActivity.class)));
        nav.findViewById(R.id.btn_nav_profile).setOnClickListener(v -> {});
    }
}