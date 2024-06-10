package com.example.todomo.sign_up_activity.get_details_activity;

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

import com.example.todomo.sign_in_activity.SignInScreenActivity;

import common_classes.view_utility.ViewUtility;

public class GetDetailsScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static Button nextButton;
    @SuppressLint("StaticFieldLeak")
    private static TextView signInLinkTextView;
    @SuppressLint("StaticFieldLeak")
    private static EditText enterFirstNameEditText;
    @SuppressLint("StaticFieldLeak")
    private static EditText enterLastNameEditText;
    @SuppressLint("StaticFieldLeak")
    private static EditText enterEmailEditText;
    @SuppressLint("StaticFieldLeak")

    public static void initializeViews(@NonNull Button button, @NonNull TextView signInLink, @NonNull EditText FirstName, @NonNull EditText LastName, @NonNull EditText Email) {
        nextButton = button;
        signInLinkTextView = signInLink;
        enterFirstNameEditText = FirstName;
        enterLastNameEditText = LastName;
        enterEmailEditText = Email;
    }

    public static void handleSignInLinkClick(@NonNull Activity activity, @NonNull ProgressBar progressBar) {
        signInLinkTextView.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            disableAllViews();

            new Handler().postDelayed(() -> {
                enableAllViews();
                progressBar.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(activity, SignInScreenActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }, 1500);
        });
    }

    public static void handleNextButtonClick(@NonNull Activity activity, @NonNull ProgressBar progressBar) {
        nextButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> {
                Intent intent = new Intent(activity, SignInScreenActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }, 1000);
        });
    }

    public static void handleBackButtonClick(@NonNull Activity activity) {
        Intent intent = new Intent(activity, SignInScreenActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private static void disableAllViews() {
        ViewUtility.disableViews(nextButton, signInLinkTextView, enterFirstNameEditText, enterLastNameEditText, enterEmailEditText);
    }

    private static void enableAllViews() {
        ViewUtility.enableViews(nextButton, signInLinkTextView, enterFirstNameEditText, enterLastNameEditText, enterEmailEditText);
    }
}
