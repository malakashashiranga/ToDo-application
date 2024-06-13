package com.example.todomo.developer_activity;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.todomo.R;
import com.example.todomo.profile_activity.ProfileScreenActivity;

public class DeveloperScreenActionHandler {

    public static void handleBackButtonClick(@NonNull Activity activity) {
        Intent intent = new Intent(activity, ProfileScreenActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        activity.finish();
    }
}
