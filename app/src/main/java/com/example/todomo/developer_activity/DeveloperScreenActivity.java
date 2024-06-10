package com.example.todomo.developer_activity;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;

public class DeveloperScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_screen);


        ImageButton backButton = findViewById(R.id.BackArrow);

        backButton.setOnClickListener(v -> DeveloperScreenActionHandler.handleBackButtonClick(this));

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                DeveloperScreenActionHandler.handleBackButtonClick(DeveloperScreenActivity.this);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}