package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HabitListActivity extends AppCompatActivity {
    HabitsStorage storage;
    List<Habit> habits;
    HabitAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);

        storage = new HabitsStorage(this);
        habits = storage.load();

        RecyclerView rv = findViewById(R.id.rvHabits);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HabitAdapter(this, habits);
        rv.setAdapter(adapter);

        Button btnHome = findViewById(R.id.btn_home_back);
        ImageButton cbutton = findViewById(R.id.cbutton), kbutton = findViewById(R.id.kbutton);

        btnHome.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        cbutton.setOnClickListener(v -> startActivity(new Intent(this, NewHabitActivity.class)));
        kbutton.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));

    }




    @Override
    protected void onResume() {
        super.onResume();
        habits.clear();
        habits.addAll(storage.load());
        adapter.notifyDataSetChanged();
    }
}