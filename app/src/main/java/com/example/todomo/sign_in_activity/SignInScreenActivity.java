package com.example.todomo.sign_in_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;


public class SignInScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);

        ProgressBar progressBar = findViewById(R.id.LoginSpinnerBar);

        progressBar.setVisibility(View.INVISIBLE);

    }
}