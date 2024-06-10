package com.example.todomo.new_event_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

public class NewEventScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static Button saveButton;

    public static void initializeViews(@NonNull Button Button1) {
        saveButton = Button1;
    }

    public static void handleBackButton(@NonNull Activity activity) {
        Intent intent = new Intent(activity, NewEventScreenActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }


    public static void handleSaveButtonClick(@NonNull Activity activity, @NonNull ProgressBar progressBar) {

        saveButton.setOnClickListener(v -> {
            Intent intent = new Intent(activity, NewEventScreenActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });
    }
}
