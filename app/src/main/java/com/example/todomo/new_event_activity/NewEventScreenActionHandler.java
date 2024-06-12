package com.example.todomo.new_event_activity;

import static common_classes.check_edit_text_empty_utility.CheckEditTextEmptyUtility.isEditTextEmpty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.todomo.R;
import com.example.todomo.home_activity.HomeScreenActivity;
import com.example.todomo.new_event_activity.store_new_event.StoreNewEvent;

import common_classes.button_clicking_animation.ButtonClickingAnimation;
import common_classes.view_utility.ViewUtility;

public class NewEventScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static Button saveButton;
    @SuppressLint("StaticFieldLeak")
    private static ImageButton backButton;
    @SuppressLint("StaticFieldLeak")
    private static EditText titleBar;
    @SuppressLint("StaticFieldLeak")
    private static EditText detailsBar;
    @SuppressLint("StaticFieldLeak")
    private static EditText scheduleDateText;
    @SuppressLint("StaticFieldLeak")
    private static EditText scheduleTimeText;
    @SuppressLint("StaticFieldLeak")
    private static SeekBar prioritySeekBar;

    public static void initializeViews(@NonNull Button Button1, @NonNull ImageButton Button2,
                                       @NonNull EditText titleBar1, @NonNull EditText detailsBar1, @NonNull EditText scheduleDateText1, @NonNull EditText scheduleTimeText1,
                                       @NonNull SeekBar prioritySeekBar1) {
        saveButton = Button1;
        backButton = Button2;
        titleBar = titleBar1;
        detailsBar = detailsBar1;
        scheduleDateText = scheduleDateText1;
        scheduleTimeText = scheduleTimeText1;
        prioritySeekBar = prioritySeekBar1;
    }

    public static void handleBackButton(@NonNull Activity activity) {
        disableAllViews();
        Intent intent = new Intent(activity, HomeScreenActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        activity.finish();
    }


    public static void handleSaveButtonClick(@NonNull Activity activity, @NonNull ProgressBar progressBar) {
        saveButton.setOnClickListener(v -> {
            ButtonClickingAnimation.animateButtonClick(saveButton);
            progressBar.setVisibility(View.VISIBLE);
            disableAllViews();

            String titleBarText = titleBar.getText().toString().trim();
            String detailsBarText = detailsBar.getText().toString().trim();
            String scheduleDate = scheduleDateText.getText().toString().trim();
            String scheduleTime = scheduleTimeText.getText().toString().trim();
            int priority = prioritySeekBar.getProgress();

            if (isEditTextEmpty(titleBar) || isEditTextEmpty(detailsBar) || isEditTextEmpty(scheduleDateText) || isEditTextEmpty(scheduleTimeText)) {
                Toast.makeText(activity, "Input fields cannot be empty", Toast.LENGTH_LONG).show();
                enableAllViews();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }

            SharedPreferences sharedPreferences = activity.getSharedPreferences("isLoggedInApp", Context.MODE_PRIVATE);
            String email = sharedPreferences.getString("userEmail", "");

            StoreNewEvent storeNewEvent = new StoreNewEvent(activity);
            storeNewEvent.open();
            storeNewEvent.saveEvent(titleBarText, detailsBarText, scheduleDate, scheduleTime, priority, email, new StoreNewEvent.EventSaveCallback() {
                @Override
                public void onSuccess(String message) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    handleBackButton(activity);
                }

                @Override
                public void onFailure(String message) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    enableAllViews();
                }
            });
            storeNewEvent.close();
        });
    }

    private static void disableAllViews() {
        ViewUtility.disableViews(saveButton, backButton, titleBar, detailsBar, scheduleDateText, scheduleTimeText, prioritySeekBar);
    }

    private static void enableAllViews() {
        ViewUtility.enableViews(saveButton, backButton, titleBar, detailsBar, scheduleDateText, scheduleTimeText, prioritySeekBar);
    }
}
