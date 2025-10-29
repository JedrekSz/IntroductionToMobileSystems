package com.example.introductiontomobilesystems;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class NewHabitActivity extends AppCompatActivity {
    HabitsStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);

        storage = new HabitsStorage(this);
        EditText etName = findViewById(R.id.etHabitName);
        Spinner spinner = findViewById(R.id.spinnerFreq);
        Switch swNotifs = findViewById(R.id.switchNotifs);
        Button btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String freq = spinner.getSelectedItem().toString();
            boolean notifs = swNotifs.isChecked();
            if (name.isEmpty()) {
                Toast.makeText(this, "Enter habit name", Toast.LENGTH_SHORT).show();
                return;
            }
            List<Habit> list = storage.load();
            list.add(new Habit(name, freq, true, notifs));
            storage.save(list);
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            finish();
        });

        View nav = findViewById(R.id.bottom_nav);
        nav.findViewById(R.id.btn_nav_home).setOnClickListener(v -> startActivity(new android.content.Intent(this, MainActivity.class)));
        nav.findViewById(R.id.btn_nav_list).setOnClickListener(v -> startActivity(new android.content.Intent(this, HabitListActivity.class)));
        nav.findViewById(R.id.btn_nav_new).setOnClickListener(v -> {});
        nav.findViewById(R.id.btn_nav_profile).setOnClickListener(v -> startActivity(new android.content.Intent(this, ProfileActivity.class)));
    }
}