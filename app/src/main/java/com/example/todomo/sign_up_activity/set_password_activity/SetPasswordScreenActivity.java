package com.example.todomo.sign_up_activity.set_password_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;


public class SetPasswordScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password_screen);

        ProgressBar progressBar = findViewById(R.id.PasswordSpinnerBar);

        progressBar.setVisibility(View.INVISIBLE);
    }
}