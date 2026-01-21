package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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

        setupNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Habit> list = storage.load();

        adapter = new ManagerAdapter(this, list, () -> checkImageVisibility(list));
        recyclerView.setAdapter(adapter);

        checkImageVisibility(list);
    }

    private void checkImageVisibility(List<Habit> list) {
        android.widget.ImageView imgMore = findViewById(R.id.imgMore);
        if (imgMore != null) {
            if (list.size() < 3) {
                imgMore.setVisibility(android.view.View.VISIBLE);
            } else {
                imgMore.setVisibility(android.view.View.GONE);
            }
        }
    }

    private void setupNavigation() {
        ImageButton cbutton = findViewById(R.id.cbutton);
        ImageButton kbutton = findViewById(R.id.kbutton);
        ImageButton mbutton = findViewById(R.id.mbutton);
        ImageButton hbutton = findViewById(R.id.btn_home_back);

        hbutton.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        cbutton.setOnClickListener(v -> startActivity(new Intent(this, NewHabitActivity.class)));
        kbutton.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
    }
}