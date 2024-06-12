package com.example.todomo.new_event_activity;

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

import common_classes.edit_text_expand.TextExpandUtils;
import common_classes.pick_date.DatePicker;
import common_classes.pick_time.TimePicker;
import common_classes.responsive_seek_bar.SeekBarChanges;

public class NewEventScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_screen);

        ProgressBar progressBar = findViewById(R.id.EventSpinnerBar);
        progressBar.setVisibility(View.INVISIBLE);

        Button saveButton = findViewById(R.id.SaveButton);

        ImageButton backButton = findViewById(R.id.BackArrow);
        ImageButton calendarIcon = findViewById(R.id.CalenderIcon);
        ImageButton clockIcon = findViewById(R.id.ClockIcon);

        EditText titleBar = findViewById(R.id.TitleContent);
        EditText detailsBar = findViewById(R.id.DetailsContent);
        EditText scheduleDateText = findViewById(R.id.ScheduleDateText);
        EditText scheduleTimeText = findViewById(R.id.ScheduleTimeText);

        SeekBar prioritySeekBar = findViewById(R.id.PrioritySeekBar);

        TextExpandUtils.setDynamicLayoutParams(NewEventScreenActivity.this, R.id.TitleContent);
        TextExpandUtils.setDynamicLayoutParams(NewEventScreenActivity.this, R.id.DetailsContent);

        // Call the method from DatePickerHelper and pass the EditText and ImageButton IDs
        DatePicker.setDatePicker(this, scheduleDateText, calendarIcon);

        // Set click listener for the clock icon to show the time picker
        TimePicker.setTimePicker(this, scheduleTimeText, clockIcon);

        SeekBarChanges.setCustomColorSeekBar(prioritySeekBar);

        NewEventScreenActionHandler.initializeViews(saveButton, backButton, titleBar, detailsBar, scheduleDateText, scheduleTimeText, prioritySeekBar);

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