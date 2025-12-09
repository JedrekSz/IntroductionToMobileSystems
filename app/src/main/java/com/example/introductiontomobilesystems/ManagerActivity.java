package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HabitsStorage storage;
    private ManagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        storage = new HabitsStorage(this);

        recyclerView = findViewById(R.id.rvHabitList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btnCleanup = findViewById(R.id.btnCleanup);
        btnCleanup.setOnClickListener(v -> {
            // 1. Load current list
            List<Habit> allHabits = storage.load();
            List<Habit> habitsToKeep = new ArrayList<>();

            boolean removando = false;
            for (Habit h : allHabits) {
                if (h.active) {
                    habitsToKeep.add(h);
                } else {
                    removando = true;
                }
            }

            if (removando) {
                storage.save(habitsToKeep);

                adapter = new ManagerAdapter(this, habitsToKeep);
                recyclerView.setAdapter(adapter);
            }
        });

        ImageButton cbutton = findViewById(R.id.cbutton);
        ImageButton kbutton = findViewById(R.id.kbutton);
        //ImageButton mbutton = findViewById(R.id.mbutton);
        ImageButton hbutton = findViewById(R.id.btn_home_back);

        hbutton.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        cbutton.setOnClickListener(v -> startActivity(new Intent(this, NewHabitActivity.class)));
        kbutton.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Habit> list = storage.load();

        adapter = new ManagerAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
}