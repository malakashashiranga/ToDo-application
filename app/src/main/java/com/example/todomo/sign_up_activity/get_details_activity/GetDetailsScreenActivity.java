package com.example.todomo.sign_up_activity.get_details_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;

import common_classes.text_gradient_utility.TextGradientUtility;

public class GetDetailsScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_details_screen);

        ProgressBar progressBar = findViewById(R.id.SignUpSpinnerBar);
        progressBar.setVisibility(View.INVISIBLE);

        final TextView getDetailsGreet = findViewById(R.id.SignUpGetDetailsGreet);
        TextGradientUtility.applyGradientOnLayout(this, getDetailsGreet);

        Button nextButton = findViewById(R.id.NextStepButton);

        TextView signInLinkTextView = findViewById(R.id.SigInLink);

        EditText enterFirstNameEditText = findViewById(R.id.EnterFirstName);
        EditText enterLastNameEditText = findViewById(R.id.EnterLastName);
        EditText enterEmailEditText = findViewById(R.id.EnterEmail);

        GetDetailsScreenActionHandler.initializeViews(nextButton, signInLinkTextView, enterFirstNameEditText, enterLastNameEditText, enterEmailEditText);
        GetDetailsScreenActionHandler.handleSignInLinkClick(this, progressBar);
        GetDetailsScreenActionHandler.handleNextButtonClick(this, progressBar);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                GetDetailsScreenActionHandler.handleBackButtonClick(GetDetailsScreenActivity.this);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
