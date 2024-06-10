package com.example.todomo.event_manage_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;

public class EventManageScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_manage);

        ProgressBar progressBar = findViewById(R.id.EventSpinnerBar);
        progressBar.setVisibility(View.INVISIBLE);

        Button editSaveButton = findViewById(R.id.EditSaveButton);
        Button deleteButton = findViewById(R.id.DeleteButton);

        ImageButton backButton = findViewById(R.id.BackArrow);

        EventManageScreenActionHandler.initializeViews(editSaveButton, deleteButton);

        backButton.setOnClickListener(v -> EventManageScreenActionHandler.handleBackButtonClick(this));
        EventManageScreenActionHandler.handleEditSaveButton(this, progressBar);
        EventManageScreenActionHandler.handleDeleteButton(this, progressBar);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                EventManageScreenActionHandler.handleBackButtonClick(EventManageScreenActivity.this);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}