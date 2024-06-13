package com.example.todomo.event_manage_activity;

import static common_classes.check_edit_text_empty_utility.CheckEditTextEmptyUtility.isEditTextEmpty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.todomo.R;
import com.example.todomo.event_manage_activity.delete_events.DeleteEvent;
import com.example.todomo.event_manage_activity.update_event.UpdateEvent;
import com.example.todomo.home_activity.HomeScreenActivity;

import common_classes.button_clicking_animation.ButtonClickingAnimation;
import common_classes.confirmation_dialogue.ConfirmationDialogueBox;
import common_classes.database_create.DatabaseManage;
import common_classes.view_utility.ViewUtility;

public class EventManageScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static Button editSaveButton;
    @SuppressLint("StaticFieldLeak")
    private static Button deleteButton;
    @SuppressLint("StaticFieldLeak")
    private static ImageButton backButton;
    @SuppressLint("StaticFieldLeak")
    private static ImageButton calendarIcon;
    @SuppressLint("StaticFieldLeak")
    private static ImageButton clockIcon;
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
    private static String eventID;

    public static void initializeViews(@NonNull Button button1, @NonNull Button button2,
                                       @NonNull ImageButton imageButton1, @NonNull ImageButton imageButton2, @NonNull ImageButton imageButton3,
                                       @NonNull EditText eText1 ,@NonNull EditText eText2, @NonNull EditText eText3, @NonNull EditText eText4,
                                       @NonNull SeekBar pSeekBar) {

        editSaveButton = button1;
        deleteButton = button2;
        backButton = imageButton1;
        calendarIcon = imageButton2;
        clockIcon = imageButton3;
        titleBar = eText1;
        detailsBar = eText2;
        scheduleDateText = eText3;
        scheduleTimeText = eText4;
        prioritySeekBar = pSeekBar;
    }

    public static void handleIntents(@NonNull Activity activity) {
        Intent getIntent = activity.getIntent();

        if (getIntent != null && getIntent.hasExtra("event_id")) {
            eventID = getIntent.getStringExtra("event_id");
            fetchAndDisplayEventDetails(activity);
            disableAllViews();
            resetScreen();
        } else {
            handleBackButtonClick(activity);
        }
    }

    public static void handleBackButtonClick(@NonNull Activity activity) {
        disableAllViews();
        Intent intent = new Intent(activity, HomeScreenActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        activity.finish();
    }

    public static void handleEditSaveButton(@NonNull Activity activity, @NonNull ProgressBar progressBar) {
        editSaveButton.setOnClickListener(v -> {
            ButtonClickingAnimation.animateButtonClick(editSaveButton);

            if (editSaveButton.getText().equals(activity.getString(R.string.edit))) {
                editSaveButton.setText(activity.getString(R.string.save));
                enableAllViews();
            } else if (editSaveButton.getText().equals(activity.getString(R.string.save))) {
                progressBar.setVisibility(View.VISIBLE);

                String title = titleBar.getText().toString().trim();
                String details = detailsBar.getText().toString().trim();
                String date = scheduleDateText.getText().toString().trim();
                String time = scheduleTimeText.getText().toString().trim();
                int priority = prioritySeekBar.getProgress();

                updateEventDetails(activity, eventID, title, details, date, time, priority);

                editSaveButton.setText(activity.getString(R.string.edit));
                progressBar.setVisibility(View.INVISIBLE);
                fetchAndDisplayEventDetails(activity);
                disableAllViews();
                resetScreen();
            }
        });
    }


    public static void handleDeleteButton(@NonNull Activity activity, @NonNull ProgressBar progressBar) {
        deleteButton.setOnClickListener(v -> {
            ButtonClickingAnimation.animateButtonClick(deleteButton);
            progressBar.setVisibility(View.VISIBLE);

            new ConfirmationDialogueBox(activity, "Do you want to delete this event?", "Yes", "No", new ConfirmationDialogueBox.ConfirmationDialogueBoxListener() {
                @Override
                public void onDeleteConfirmed() {
                    DeleteEvent deleteEvent = new DeleteEvent(activity);
                    deleteEvent.open();

                    deleteEvent.deleteEvent(eventID, new DeleteEvent.EventDeletionCallback() {
                        @Override
                        public void onDeleteSuccess(String message) {
                            progressBar.setVisibility(View.INVISIBLE);
                            handleBackButtonClick(activity);
                            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onDeleteFailure(String message) {
                            progressBar.setVisibility(View.INVISIBLE);
                            fetchAndDisplayEventDetails(activity);
                            disableAllViews();
                            resetScreen();
                            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                        }
                    });
                    deleteEvent.close();
                }

                @Override
                public void onDeleteCanceled() {
                    progressBar.setVisibility(View.INVISIBLE);
                    fetchAndDisplayEventDetails(activity);
                    disableAllViews();
                    resetScreen();
                }
            }).show();
        });
    }

    private static void fetchAndDisplayEventDetails(@NonNull Activity activity) {
        DatabaseManage dbHelper = new DatabaseManage(activity);
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();

            String[] projection = {
                    DatabaseManage.COLUMN_TITLE,
                    DatabaseManage.COLUMN_DETAILS,
                    DatabaseManage.COLUMN_DATE,
                    DatabaseManage.COLUMN_TIME,
                    DatabaseManage.COLUMN_PRIORITY
            };

            String selection = DatabaseManage.COLUMN_ID + " = ?";
            String[] selectionArgs = new String[]{eventID};

            cursor = db.query(
                    DatabaseManage.TABLE_EVENTS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManage.COLUMN_TITLE));
                String details = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManage.COLUMN_DETAILS));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManage.COLUMN_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManage.COLUMN_TIME));
                int priority = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseManage.COLUMN_PRIORITY));

                title = title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();

                titleBar.setText(title);
                detailsBar.setText(details);
                scheduleDateText.setText(date);
                scheduleTimeText.setText(time);
                prioritySeekBar.setProgress(priority);
            } else {
                Toast.makeText(activity, "No event found with the given ID", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(activity, "Error fetching event details: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    private static void updateEventDetails(Activity activity, String eventID, String title, String details, String date, String time, int priority) {

        if (isEditTextEmpty(titleBar) || isEditTextEmpty(detailsBar) || isEditTextEmpty(scheduleDateText) || isEditTextEmpty(scheduleTimeText)) {
            Toast.makeText(activity, "Input fields cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        UpdateEvent updateEvent = new UpdateEvent(activity);
        updateEvent.open();

        updateEvent.updateEvent(eventID, title, details, date, time, priority, new UpdateEvent.EventUpdateCallback() {
            @Override
            public void onUpdateSuccess(String message) {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onUpdateFailure(String message) {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            }
        });
        updateEvent.close();
    }

    private static void resetScreen() {
        disableAllViews();
        backButton.setEnabled(true);
        editSaveButton.setEnabled(true);
        deleteButton.setEnabled(true);
    }

    private static void disableAllViews() {
        ViewUtility.disableViews(editSaveButton, deleteButton, backButton, calendarIcon, clockIcon, titleBar, detailsBar, scheduleDateText, scheduleTimeText, prioritySeekBar);
    }

    private static void enableAllViews() {
        ViewUtility.enableViews(editSaveButton, deleteButton, backButton, calendarIcon, clockIcon, titleBar, detailsBar, scheduleDateText, scheduleTimeText, prioritySeekBar);
    }
}
