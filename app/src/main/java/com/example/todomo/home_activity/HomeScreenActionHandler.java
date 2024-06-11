package com.example.todomo.home_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

public class HomeScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static ImageButton profileImageButton;
    @SuppressLint("StaticFieldLeak")
    private static Button createButton;

    public static void initializeViews(@NonNull ImageButton profile, @NonNull Button create) {
        profileImageButton = profile;
        createButton = create;
    }

    public static void handleProfileImageButtonClick(@NonNull Activity activity) {
        profileImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(activity, HomeScreenActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });
    }

    public static void handleCreateButtonClick(@NonNull Activity activity) {
        createButton.setOnClickListener(v -> {
            Intent intent = new Intent(activity, HomeScreenActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });
    }
}
