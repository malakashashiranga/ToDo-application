package com.example.todomo.splash_activity;

import android.app.Activity;
import android.content.Intent;

import com.example.todomo.sign_in_activity.SignInScreenActivity;

public class SplashScreenActionHandler {

    public static void splashDelayedNavigation(Activity activity, long delayMillis) {
        new android.os.Handler().postDelayed(() -> {
            Intent intent;
            intent = new Intent(activity, SignInScreenActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }, delayMillis);
    }
}
