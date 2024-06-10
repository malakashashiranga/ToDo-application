package com.example.todomo.new_event_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.example.todomo.home_activity.HomeScreenActivity;

public class NewEventScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static Button saveButton;

    public static void initializeViews(@NonNull Button Button1) {
        saveButton = Button1;
    }

    public static void handleBackButton(@NonNull Activity activity) {
        Intent intent = new Intent(activity, HomeScreenActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }


    public static void handleSaveButtonClick(@NonNull Activity activity, @NonNull ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        saveButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(activity, HomeScreenActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });
    }
}
