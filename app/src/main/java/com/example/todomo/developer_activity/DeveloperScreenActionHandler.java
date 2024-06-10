package com.example.todomo.developer_activity;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

public class DeveloperScreenActionHandler {

    public static void handleBackButtonClick(@NonNull Activity activity) {
        Intent intent = new Intent(activity, DeveloperScreenActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
