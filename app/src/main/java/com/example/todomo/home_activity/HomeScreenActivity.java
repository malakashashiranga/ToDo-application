package com.example.todomo.home_activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ImageButton profileImageButton = findViewById(R.id.ProfileImageButton);

        Button createButton = findViewById(R.id.CreateButton);

        LinearLayout scrollLayout = findViewById(R.id.ScrollLayout);

        TextView eventAlert = findViewById(R.id.EventAlert);

        HomeScreenActionHandler.initializeViews(profileImageButton, createButton);

        HomeScreenActionHandler.handleProfileImageButtonClick(this);
        HomeScreenActionHandler.handleCreateButtonClick(this);
        HomeScreenActionHandler.handleLoadingTodoList(this, scrollLayout, eventAlert);
    }
}