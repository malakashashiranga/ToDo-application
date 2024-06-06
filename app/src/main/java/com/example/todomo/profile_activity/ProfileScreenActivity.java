package com.example.todomo.profile_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;

public class ProfileScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        ProgressBar progressBar = findViewById(R.id.EventSpinnerBar);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
