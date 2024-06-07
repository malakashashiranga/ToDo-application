package com.example.todomo.sign_in_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;


public class SignInScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);

        ProgressBar progressBar = findViewById(R.id.LoginSpinnerBar);
        progressBar.setVisibility(View.INVISIBLE);

        Button signButton = findViewById(R.id.SignButton);

        TextView signUpLinkTextView = findViewById(R.id.SignUpLink);

        SignInScreenActionHandler.initializeViews(signButton, signUpLinkTextView, progressBar);

        SignInScreenActionHandler.handleSignButtonClick(this);
        SignInScreenActionHandler.handleSignUpLinkClick(this);

    }
}