package com.example.todomo.new_event_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;

public class NewEventScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_screen);

        ProgressBar progressBar = findViewById(R.id.EventSpinnerBar);
        progressBar.setVisibility(View.INVISIBLE);
    }
}