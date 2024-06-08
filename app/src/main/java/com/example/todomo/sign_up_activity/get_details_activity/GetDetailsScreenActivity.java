package com.example.todomo.sign_up_activity.get_details_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;

public class GetDetailsScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_details_screen);

        ProgressBar progressBar = findViewById(R.id.SignUpSpinnerBar);
        progressBar.setVisibility(View.INVISIBLE);

        Button nextButton = findViewById(R.id.NextStepButton);

        TextView signInLinkTextView = findViewById(R.id.SigInLink);

        GetDetailsScreenActionHandler.initializeViews(nextButton, signInLinkTextView);
        GetDetailsScreenActionHandler.handleSignInLinkClick(this, progressBar);
        GetDetailsScreenActionHandler.handleNextButtonClick(this, progressBar);

        // Handle back button press
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                GetDetailsScreenActionHandler.handleBackButtonClick(GetDetailsScreenActivity.this);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
