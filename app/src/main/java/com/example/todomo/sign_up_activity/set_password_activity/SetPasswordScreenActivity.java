package com.example.todomo.sign_up_activity.set_password_activity;

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

public class SetPasswordScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password_screen);

        ProgressBar progressBar = findViewById(R.id.PasswordSpinnerBar);
        progressBar.setVisibility(View.INVISIBLE);

        final TextView setPasswordGreet = findViewById(R.id.SetPasswordGreet);
        TextGradientUtility.applyGradientOnLayout(this, setPasswordGreet);

        Button finishButton = findViewById(R.id.FinishButton);

        EditText newPasswordEditText = findViewById(R.id.NewPassword);
        EditText confirmNewPasswordEditText = findViewById(R.id.ConfirmNewPassword);

        SetPasswordScreenActionHandler.initializeViews(finishButton, progressBar, newPasswordEditText, confirmNewPasswordEditText);
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