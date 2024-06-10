package com.example.todomo.new_event_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;

public class NewEventScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_screen);

        ProgressBar progressBar = findViewById(R.id.EventSpinnerBar);
        progressBar.setVisibility(View.INVISIBLE);

        Button saveButton = findViewById(R.id.SaveButton);

        ImageButton backButton = findViewById(R.id.BackArrow);

        NewEventScreenActionHandler.initializeViews(saveButton);

        backButton.setOnClickListener(v -> NewEventScreenActionHandler.handleBackButton(this));
        NewEventScreenActionHandler.handleSaveButtonClick(this, progressBar);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NewEventScreenActionHandler.handleBackButton(NewEventScreenActivity.this);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}