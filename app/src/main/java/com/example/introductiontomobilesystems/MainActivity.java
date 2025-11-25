package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton cbutton = findViewById(R.id.cbutton), kbutton = findViewById(R.id.kbutton);
        Button btnList = findViewById(R.id.btnHabitList);

        btnList.setOnClickListener(v -> startActivity(new Intent(this, HabitListActivity.class)));
        cbutton.setOnClickListener(v -> startActivity(new Intent(this, NewHabitActivity.class)));
        kbutton.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));


    }
}