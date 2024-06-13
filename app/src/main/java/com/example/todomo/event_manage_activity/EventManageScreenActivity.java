package com.example.todomo.event_manage_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;
import com.example.todomo.new_event_activity.NewEventScreenActionHandler;

import common_classes.pick_date.DatePicker;
import common_classes.pick_time.TimePicker;
import common_classes.responsive_seek_bar.SeekBarChanges;

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
        ImageButton calendarIcon = findViewById(R.id.CalenderIcon);
        ImageButton clockIcon = findViewById(R.id.ClockIcon);

        EditText titleBar = findViewById(R.id.TitleContent);
        EditText detailsBar = findViewById(R.id.DetailsContent);
        EditText scheduleDateText = findViewById(R.id.ScheduleDateText);
        EditText scheduleTimeText = findViewById(R.id.ScheduleTimeText);

        SeekBar prioritySeekBar = findViewById(R.id.PrioritySeekBar);
        SeekBarChanges.setCustomColorSeekBar(prioritySeekBar);

        DatePicker.setDatePicker(this, scheduleDateText, calendarIcon);
        TimePicker.setTimePicker(this, scheduleTimeText, clockIcon);

        EventManageScreenActionHandler.initializeViews(editSaveButton, deleteButton, backButton, calendarIcon, clockIcon, titleBar, detailsBar,scheduleDateText, scheduleTimeText, prioritySeekBar);

        EventManageScreenActionHandler.handleIntents(this);
        backButton.setOnClickListener(v -> EventManageScreenActionHandler.handleBackButtonClick(this));
        EventManageScreenActionHandler.handleEditSaveButton(this, progressBar);
        EventManageScreenActionHandler.handleDeleteButton(this, progressBar);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NewEventScreenActionHandler.handleBackButton(EventManageScreenActivity.this);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}