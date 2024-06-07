package com.example.todomo.splash_activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.todomo.R;

import common_classes.text_gradient_utility.TextGradientUtility;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final TextView appName = findViewById(R.id.SplashScreenAppName);
        TextGradientUtility.applyGradientOnLayout(this, appName);

        SplashScreenActionHandler.splashDelayedNavigation(this, 3000);
    }
}
