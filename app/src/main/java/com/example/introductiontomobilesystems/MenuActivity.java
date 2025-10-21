package com.example.introductiontomobilesystems;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    Button backButton = findViewById(R.id.button_back_main);


        backButton.setOnClickListener(v -> {
        finish();
    });
}
}
