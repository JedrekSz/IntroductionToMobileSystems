package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView; // 1. Import ImageView
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    private ImageView imgFrog; // 2. Declare the variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Navigation Buttons
        ImageButton cbutton = findViewById(R.id.cbutton);
        ImageButton kbutton = findViewById(R.id.kbutton);
        ImageButton mbutton = findViewById(R.id.mbutton);
        ImageButton hbutton = findViewById(R.id.btn_home_back);

        hbutton.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        mbutton.setOnClickListener(v -> startActivity(new Intent(this, ManagerActivity.class)));
        cbutton.setOnClickListener(v -> startActivity(new Intent(this, NewHabitActivity.class)));

        // Views
        TextView myTextView = findViewById(R.id.funFact);
        imgFrog = findViewById(R.id.imgFrog); // 3. Find the ImageView

        String[] habitFacts = getResources().getStringArray(R.array.habit_facts);
        String[] frogFacts = getResources().getStringArray(R.array.frog_facts);

        // Text To Speech Setup
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.ENGLISH);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    System.out.println("Language not supported");
                }
            }
        });

        // Habit Facts Button
        findViewById(R.id.btn_habits).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomIndex = new Random().nextInt(habitFacts.length);
                myTextView.setText(habitFacts[randomIndex]);

                // 4. Change image to talking frog
                imgFrog.setImageResource(R.drawable.kenspeech);

                textToSpeech.speak(habitFacts[randomIndex], TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        // Frog Facts Button
        findViewById(R.id.btn_frog_facts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomIndex = new Random().nextInt(frogFacts.length);
                myTextView.setText(frogFacts[randomIndex]);

                // 4. Change image to talking frog
                imgFrog.setImageResource(R.drawable.kenspeech);

                textToSpeech.speak(frogFacts[randomIndex], TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}