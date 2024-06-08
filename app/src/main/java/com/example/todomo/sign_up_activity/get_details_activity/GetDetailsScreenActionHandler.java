package com.example.todomo.sign_up_activity.get_details_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class GetDetailsScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static Button nextButton;
    @SuppressLint("StaticFieldLeak")
    private static TextView signInLinkTextView;
    @SuppressLint("StaticFieldLeak")

    public static void initializeViews(@NonNull Button button, @NonNull TextView signInLink) {
        nextButton = button;
        signInLinkTextView = signInLink;
    }

    public static void handleSignInLinkClick(@NonNull Activity activity, @NonNull ProgressBar progressBar) {
        signInLinkTextView.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> {
                Intent intent = new Intent(activity, GetDetailsScreenActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }, 1500);
        });
    }

    public static void handleNextButtonClick(@NonNull Activity activity, @NonNull ProgressBar progressBar) {
        nextButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> {
                Intent intent = new Intent(activity, GetDetailsScreenActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }, 1000);
        });
    }

    public static void handleBackButtonClick(@NonNull Activity activity) {
        Intent intent = new Intent(activity, GetDetailsScreenActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
