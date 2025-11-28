package com.example.introductiontomobilesystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ImageButton cbutton = findViewById(R.id.cbutton), kbutton = findViewById(R.id.kbutton), mbutton = findViewById(R.id.mbutton), hbutton = findViewById(R.id.btn_home_back);
        //Button btnList = findViewById(R.id.btnHabitList);

        //btnList.setOnClickListener(v -> startActivity(new Intent(this, HabitListActivity.class)));
        //hbutton.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        mbutton.setOnClickListener(v -> startActivity(new Intent(this, ManagerActivity.class)));
        cbutton.setOnClickListener(v -> startActivity(new Intent(this, NewHabitActivity.class)));
        kbutton.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));


    }
    @Override
    protected void onResume(){
        super.onResume();

        TextView myTextView = findViewById(R.id.tvBubble);


        String[] messages = getResources().getStringArray(R.array.home_tips);

        int randomIndex = new Random().nextInt(messages.length);

        myTextView.setText(messages[randomIndex]);
    }

}