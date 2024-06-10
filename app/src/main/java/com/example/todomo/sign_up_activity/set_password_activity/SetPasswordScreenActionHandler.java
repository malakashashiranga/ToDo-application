package com.example.todomo.sign_up_activity.set_password_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

public class SetPasswordScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static Button finishButton;
    @SuppressLint("StaticFieldLeak")
    private static ProgressBar progressBar;

    public static void initializeViews(@NonNull Button button , @NonNull ProgressBar progressBarView) {
        finishButton = button;
        progressBar = progressBarView;
    }

    public static void handleFinishButtonClick(@NonNull Activity activity) {
        finishButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(activity, SetPasswordScreenActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });
    }

    public static void handleBackButtonClick(@NonNull Activity activity) {
        Intent intent = new Intent(activity, SetPasswordScreenActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
