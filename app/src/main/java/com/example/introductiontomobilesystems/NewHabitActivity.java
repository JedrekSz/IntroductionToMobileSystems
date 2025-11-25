package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

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
        SwitchCompat swNotifs = findViewById(R.id.switchNotifs);
        Button btnAdd = findViewById(R.id.btnAdd);

        ImageButton cbutton = findViewById(R.id.cbutton), kbutton = findViewById(R.id.kbutton);

        cbutton.setOnClickListener(v -> startActivity(new Intent(this, NewHabitActivity.class)));
        kbutton.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));

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


    }
}