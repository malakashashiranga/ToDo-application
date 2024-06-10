package com.example.todomo.profile_activity;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

public class ProfileScreenActionHandler {

    public void handleBackButtonClick(@NonNull Activity activity) {
        Intent intent = new Intent(activity, ProfileScreenActivity.class); // Navigate back to home screen
        activity.startActivity(intent);
        activity.finish();
    }

    public void saveChangeNamesButtonClick(@NonNull Activity activity) {
        Intent intent = new Intent(activity, ProfileScreenActivity.class); // Navigate to update profile screen
        activity.startActivity(intent);
        activity.finish();
    }

    public void saveChangePasswordButtonClick(@NonNull Activity activity) {
        // Start activity to change password (assuming you have a separate activity for this)
        Intent intent = new Intent(activity, ProfileScreenActivity.class); // Replace with actual activity
        activity.startActivity(intent);
        activity.finish();
    }

    public void logOutButtonClick(@NonNull Activity activity) {
        Intent intent = new Intent(activity, ProfileScreenActivity.class); // Navigate to sign-in screen
        activity.startActivity(intent);
        activity.finish();
    }

    public void devInfoClick(@NonNull Activity activity) {
        Intent intent = new Intent(activity, ProfileScreenActivity.class); // Navigate to developer info screen
        activity.startActivity(intent);
        activity.finish();
    }
}
