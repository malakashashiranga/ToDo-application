package com.example.todomo.sign_in_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;

import common_classes.text_gradient_utility.TextGradientUtility;


public class SignInScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);

        ProgressBar progressBar = findViewById(R.id.LoginSpinnerBar);
        progressBar.setVisibility(View.INVISIBLE);

        final TextView loginGreet = findViewById(R.id.LoginGreet);
        TextGradientUtility.applyGradientOnLayout(this, loginGreet);

        Button signButton = findViewById(R.id.SignButton);

        EditText userEmailEditText = findViewById(R.id.LoginEmail);
        EditText passwordEditText = findViewById(R.id.LoginPassword);

        TextView signUpLinkTextView = findViewById(R.id.SignUpLink);

        SignInScreenActionHandler.initializeViews(signButton, userEmailEditText, passwordEditText, signUpLinkTextView, progressBar);

        SignInScreenActionHandler.handleSignButtonClick(this);
        SignInScreenActionHandler.handleSignUpLinkClick(this);

    }
}