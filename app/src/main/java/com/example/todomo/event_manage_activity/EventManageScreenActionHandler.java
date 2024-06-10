package com.example.todomo.event_manage_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

public class EventManageScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static Button editSaveButton;
    @SuppressLint("StaticFieldLeak")
    private static Button deleteButton;

    public static void initializeViews(@NonNull Button button1, @NonNull Button button2) {

        editSaveButton = button1;
        deleteButton = button2;
    }

    public static void handleBackButtonClick(@NonNull Activity activity) {
        Intent intent = new Intent(activity, EventManageScreenActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void handleEditSaveButton(@NonNull Activity activity, @NonNull ProgressBar progressBar) {
        editSaveButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(activity, EventManageScreenActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });
    }


    public static void handleDeleteButton(@NonNull Activity activity, @NonNull ProgressBar progressBar) {
        deleteButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(activity, EventManageScreenActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });
    }
}
