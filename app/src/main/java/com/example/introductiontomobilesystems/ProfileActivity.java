package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton cbutton = findViewById(R.id.cbutton), kbutton = findViewById(R.id.kbutton), mbutton = findViewById(R.id.mbutton), hbutton = findViewById(R.id.btn_home_back);




        hbutton.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        mbutton.setOnClickListener(v -> startActivity(new Intent(this, ManagerActivity.class)));
        cbutton.setOnClickListener(v -> startActivity(new Intent(this, NewHabitActivity.class)));

        TextView myTextView = findViewById(R.id.funFact);

        String[] habitFacts = getResources().getStringArray(R.array.habit_facts);
        String[] frogFacts = getResources().getStringArray(R.array.frog_facts);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.ENGLISH);


                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    System.out.println("Language not supported");
                }
            }
        });


        findViewById(R.id.btn_habits).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomIndex = new Random().nextInt(habitFacts.length);
                myTextView.setText(habitFacts[randomIndex]);
                textToSpeech.speak(habitFacts[randomIndex], TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        findViewById(R.id.btn_frog_facts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomIndex = new Random().nextInt(frogFacts.length);
                myTextView.setText(frogFacts[randomIndex]);
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