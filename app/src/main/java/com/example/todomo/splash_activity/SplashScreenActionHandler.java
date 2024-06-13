package com.example.todomo.splash_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.todomo.home_activity.HomeScreenActivity;
import com.example.todomo.sign_in_activity.SignInScreenActivity;

public class SplashScreenActionHandler {

    public static void splashDelayedNavigation(Activity activity, long delayMillis) {
        new android.os.Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = activity.getSharedPreferences("isLoggedInApp", Context.MODE_PRIVATE);
            boolean isSuccessfully = sharedPreferences.getBoolean("isSuccessfully", false);

            Intent intent;
            if (isSuccessfully) {
                intent = new Intent(activity, HomeScreenActivity.class);
            } else {
                intent = new Intent(activity, SignInScreenActivity.class);
            }
            activity.startActivity(intent);
            activity.finish();
        }, delayMillis);
    }
}
