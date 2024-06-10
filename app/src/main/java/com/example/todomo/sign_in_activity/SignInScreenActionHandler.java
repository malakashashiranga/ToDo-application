package com.example.todomo.sign_in_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.todomo.home_activity.HomeScreenActivity;
import com.example.todomo.sign_up_activity.get_details_activity.GetDetailsScreenActivity;

public class SignInScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static Button signButton;
    @SuppressLint("StaticFieldLeak")
    private static TextView signUpLinkTextView;

    @SuppressLint("StaticFieldLeak")
    private static ProgressBar progressBar;

    public static void initializeViews(@NonNull Button button, @NonNull TextView signUpLink, @NonNull ProgressBar progress) {
        signButton = button;
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
            Intent intent = new Intent(activity, GetDetailsScreenActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });
    }
}
