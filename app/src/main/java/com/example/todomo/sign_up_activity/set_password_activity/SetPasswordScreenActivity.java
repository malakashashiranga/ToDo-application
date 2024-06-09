package com.example.todomo.sign_up_activity.set_password_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;

public class SetPasswordScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password_screen);

        ProgressBar progressBar = findViewById(R.id.PasswordSpinnerBar);
        progressBar.setVisibility(View.INVISIBLE);

        Button finishButton = findViewById(R.id.FinishButton);

        SetPasswordScreenActionHandler.initializeViews(finishButton, progressBar);
        SetPasswordScreenActionHandler.handleFinishButtonClick(this);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                SetPasswordScreenActionHandler.handleBackButtonClick(SetPasswordScreenActivity.this);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}