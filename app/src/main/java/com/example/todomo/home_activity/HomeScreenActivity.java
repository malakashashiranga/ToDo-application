package com.example.todomo.home_activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;

import common_classes.text_gradient_utility.TextGradientUtility;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ImageButton profileImageButton = findViewById(R.id.ProfileImageButton);

        Button createButton = findViewById(R.id.CreateButton);

        LinearLayout scrollLayout = findViewById(R.id.ScrollLayout);

        TextView eventAlert = findViewById(R.id.EventAlert);
        final TextView loginGreet = findViewById(R.id.HomeScreenAppName);
        TextGradientUtility.applyGradientOnLayout(this, loginGreet);

        HomeScreenActionHandler.initializeViews(profileImageButton, createButton);

        HomeScreenActionHandler.handleProfileImageButtonClick(this);
        HomeScreenActionHandler.handleCreateButtonClick(this);
        HomeScreenActionHandler.handleLoadingTodoList(this, scrollLayout, eventAlert);

    }
}