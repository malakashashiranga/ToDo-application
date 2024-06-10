package com.example.todomo.sign_in_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.todomo.home_activity.HomeScreenActivity;
import com.example.todomo.sign_up_activity.get_details_activity.GetDetailsScreenActivity;

import common_classes.view_utility.ViewUtility;

public class SignInScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static Button signButton;
    @SuppressLint("StaticFieldLeak")
    private static EditText userEmailEditText;
    @SuppressLint("StaticFieldLeak")
    private static EditText passwordEditText;
    @SuppressLint("StaticFieldLeak")
    private static TextView signUpLinkTextView;
    @SuppressLint("StaticFieldLeak")
    private static ProgressBar progressBar;

    public static void initializeViews(@NonNull Button button, @NonNull EditText userEmail, @NonNull EditText password, @NonNull TextView signUpLink, @NonNull ProgressBar progress) {
        signButton = button;
        userEmailEditText = userEmail;
        passwordEditText = password;
        signUpLinkTextView = signUpLink;
        progressBar = progress;
    }

    public static void handleSignButtonClick(@NonNull Activity activity) {
        signButton.setOnClickListener(v -> {
            Intent intent = new Intent(activity, HomeScreenActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });
    }

    public static void handleSignUpLinkClick(@NonNull Activity activity) {
        signUpLinkTextView.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            disableAllViews();

            new Handler().postDelayed(() -> {
                enableAllViews();
                progressBar.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(activity, GetDetailsScreenActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }, 1500);
        });
    }

    private static void disableAllViews() {
        ViewUtility.disableViews(signButton, userEmailEditText, passwordEditText, signUpLinkTextView);
    }

    private static void enableAllViews() {
        ViewUtility.enableViews(signButton, userEmailEditText, passwordEditText, signUpLinkTextView);
    }
}


