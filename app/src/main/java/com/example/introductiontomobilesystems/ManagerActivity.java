package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler; // Import Handler for the timer
import android.os.Looper;  // Import Looper
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManagerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HabitsStorage storage;
    private ManagerAdapter adapter;

    // Flag to track if we are waiting for confirmation
    private boolean isConfirmingCleanup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        storage = new HabitsStorage(this);

        recyclerView = findViewById(R.id.rvHabitList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // --- UPDATED CLEANUP BUTTON LOGIC ---
        Button btnCleanup = findViewById(R.id.btnCleanup);

        btnCleanup.setOnClickListener(v -> {
            if (!isConfirmingCleanup) {
                // FIRST CLICK: Ask for confirmation
                btnCleanup.setText("r u sure?");
                isConfirmingCleanup = true;

                // Optional: Auto-reset button if they don't click again in 3 seconds
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    // Only reset if they haven't clicked 'yes' yet (cleanup still active)
                    if (isConfirmingCleanup) {
                        isConfirmingCleanup = false;
                        btnCleanup.setText("Cleanup");
                    }
                }, 3000);

            } else {
                // SECOND CLICK: Execute the cleanup
                performCleanup();

                // Reset button state
                btnCleanup.setText("Cleanup");
                isConfirmingCleanup = false;
            }
        });
        // ------------------------------------

        setupNavigation();
    }

    private void performCleanup() {
        // 1. Load current list
        List<Habit> allHabits = storage.load();
        List<Habit> habitsToKeep = new ArrayList<>();

        // 2. Filter: Keep only ACTIVE habits
        boolean removedSomething = false;
        for (Habit h : allHabits) {
            if (h.active) {
                habitsToKeep.add(h);
            } else {
                removedSomething = true;
            }
        }

        if (removedSomething) {
            // 3. Save the filtered list
            storage.save(habitsToKeep);

            // 4. Refresh the list on screen
            adapter = new ManagerAdapter(this, habitsToKeep);
            recyclerView.setAdapter(adapter);

            Toast.makeText(this, "Inactive habits removed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No inactive habits found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Habit> list = storage.load();
        adapter = new ManagerAdapter(this, list);
        recyclerView.setAdapter(adapter);

        // Ensure button state is reset when returning to screen
        Button btnCleanup = findViewById(R.id.btnCleanup);
        if (btnCleanup != null) {
            btnCleanup.setText("Cleanup");
            isConfirmingCleanup = false;
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
        // mbutton is self, do nothing
    }
}