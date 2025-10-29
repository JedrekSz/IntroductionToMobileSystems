package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnList = findViewById(R.id.btnHabitList);
        btnList.setOnClickListener(v -> startActivity(new Intent(this, HabitListActivity.class)));

        View nav = findViewById(R.id.bottom_nav);
        nav.findViewById(R.id.btn_nav_home).setOnClickListener(v -> {});
        nav.findViewById(R.id.btn_nav_list).setOnClickListener(v -> startActivity(new Intent(this, HabitListActivity.class)));
        nav.findViewById(R.id.btn_nav_new).setOnClickListener(v -> startActivity(new Intent(this, NewHabitActivity.class)));
        nav.findViewById(R.id.btn_nav_profile).setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
    }
}