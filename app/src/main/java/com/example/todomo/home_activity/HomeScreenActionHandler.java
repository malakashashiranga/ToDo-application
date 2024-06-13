package com.example.todomo.home_activity;

import static common_classes.database_create.DatabaseManage.USER_EMAIL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.todomo.R;
import com.example.todomo.event_manage_activity.EventManageScreenActivity;
import com.example.todomo.new_event_activity.NewEventScreenActivity;
import com.example.todomo.profile_activity.ProfileScreenActivity;

import common_classes.button_clicking_animation.ButtonClickingAnimation;
import common_classes.database_create.DatabaseManage;
import common_classes.view_utility.ViewUtility;

public class HomeScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static ImageButton profileImageButton;
    @SuppressLint("StaticFieldLeak")
    private static Button createButton;

    public static void initializeViews(@NonNull ImageButton profile, @NonNull Button create) {
        profileImageButton = profile;
        createButton = create;
    }

    public static void handleProfileImageButtonClick(@NonNull Activity activity) {
        profileImageButton.setOnClickListener(v -> {
            ButtonClickingAnimation.animateButtonClick(profileImageButton);
            disableAllViews();
            Intent intent = new Intent(activity, ProfileScreenActivity.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            activity.finish();
        });
    }

    public static void handleCreateButtonClick(@NonNull Activity activity) {
        createButton.setOnClickListener(v -> {
            ButtonClickingAnimation.animateButtonClick(createButton);
            disableAllViews();
            Intent intent = new Intent(activity, NewEventScreenActivity.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            activity.finish();
        });
    }

    public static void handleLoadingTodoList(@NonNull Activity activity, @NonNull LinearLayout scrollLayout, @NonNull TextView eventAlert) {
        scrollLayout.removeAllViews(); // Clear previous views
        eventAlert.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPreferences = activity.getSharedPreferences("isLoggedInApp", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", "");

        DatabaseManage dbHelper = new DatabaseManage(activity);

        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            String selection = USER_EMAIL + " = ?";
            String[] selectionArgs = { email };
            try (Cursor cursor = db.query(DatabaseManage.TABLE_EVENTS, null, selection, selectionArgs, null, null, DatabaseManage.COLUMN_DATE + " ASC, " + DatabaseManage.COLUMN_TIME + " ASC")) {

                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int eventId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseManage.COLUMN_ID)); // Get event ID
                        String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManage.COLUMN_TITLE));
                        String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManage.COLUMN_DATE));
                        String time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManage.COLUMN_TIME));
                        int priority = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseManage.COLUMN_PRIORITY));

                        LinearLayout eventContainer = createEventContainer(activity, title, date, time, priority);

                        eventContainer.setOnClickListener(v -> {
                            Intent intent = new Intent(activity, EventManageScreenActivity.class);
                            intent.putExtra("event_id", String.valueOf(eventId));
                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        });

                        scrollLayout.addView(eventContainer);

                        // Add a separator line if it's not the last event
                        if (!cursor.isLast()) {
                            View line = new View(activity);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    1); // 1px height
                            layoutParams.setMargins(0, 0, 0, 1);
                            line.setLayoutParams(layoutParams);
                            line.setBackgroundColor(ContextCompat.getColor(activity, R.color.gray));
                            scrollLayout.addView(line);
                        }
                    }
                } else {
                    // If no events, display eventAlert
                    eventAlert.setVisibility(View.VISIBLE);
                    eventAlert.setText(R.string.no_events_saved_yet);
                }
            }
        } catch (Exception e) {
            Toast.makeText(activity, "Error loading events: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private static LinearLayout createEventContainer(Activity activity, String title, String date, String time, int priority) {
        // Create a container for each event
        LinearLayout eventContainer = new LinearLayout(activity);
        eventContainer.setOrientation(LinearLayout.VERTICAL);
        eventContainer.setPadding(0, 40, 10, 10);

        eventContainer.setClickable(true);
        eventContainer.setFocusable(true);
        eventContainer.setBackground(ContextCompat.getDrawable(activity, R.drawable.bg_clickable));

        // Create a horizontal LinearLayout to hold the title and priority circle
        LinearLayout titleContainer = new LinearLayout(activity);
        titleContainer.setOrientation(LinearLayout.HORIZONTAL);

        // Ensure the first letter of the title is uppercase
        title = title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();

        TextView titleView = new TextView(activity);
        titleView.setText(activity.getString(R.string.event_title_format, title));
        titleView.setTextSize(20);
        titleView.setTypeface(ResourcesCompat.getFont(activity, R.font.arimo_medium));
        titleView.setTextColor(ContextCompat.getColor(activity, R.color.todo_text_color));

        // Create and add a colored circle based on priority
        View priorityCircle = new View(activity);
        int circleSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, activity.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams circleParams = new LinearLayout.LayoutParams(circleSize, circleSize);
        circleParams.setMargins(0, 0, 16, 0);
        priorityCircle.setLayoutParams(circleParams);

        switch (priority) {
            case 0:
                priorityCircle.setBackgroundResource(R.drawable.circle_green);
                break;
            case 1:
                priorityCircle.setBackgroundResource(R.drawable.circle_blue);
                break;
            case 2:
                priorityCircle.setBackgroundResource(R.drawable.circle_red);
                break;
            default:
                priorityCircle.setBackgroundResource(R.drawable.circle_gray);
                break;
        }

        // Add the priority circle and title to the titleContainer
        titleContainer.addView(priorityCircle);
        titleContainer.addView(titleView);

        // Create RelativeLayout to position date and time
        RelativeLayout dateTimeContainer = new RelativeLayout(activity);
        RelativeLayout.LayoutParams dateParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dateParams.addRule(RelativeLayout.ALIGN_PARENT_START);

        RelativeLayout.LayoutParams timeParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        timeParams.addRule(RelativeLayout.ALIGN_PARENT_END);

        // Create TextView for event date
        TextView dateView = new TextView(activity);
        dateView.setText(activity.getString(R.string.event_date_time_format, date));
        dateView.setTextSize(18);
        dateView.setTypeface(ResourcesCompat.getFont(activity, R.font.arimo_medium));
        dateView.setLayoutParams(dateParams);
        dateView.setTextColor(ContextCompat.getColor(activity, R.color.todo_text_color));

        // Create TextView for event time
        TextView timeView = new TextView(activity);
        timeView.setText(time);
        timeView.setTextSize(18);
        timeView.setTypeface(ResourcesCompat.getFont(activity, R.font.arimo_medium));
        timeView.setLayoutParams(timeParams);
        timeView.setTextColor(ContextCompat.getColor(activity, R.color.todo_text_color));

        // Add date and time to dateTimeContainer
        dateTimeContainer.addView(dateView);
        dateTimeContainer.addView(timeView);

        // Add titleContainer and dateTimeContainer to eventContainer
        eventContainer.addView(titleContainer);
        eventContainer.addView(dateTimeContainer);

        return eventContainer;
    }

    private static void disableAllViews() {
        ViewUtility.disableViews(profileImageButton, createButton);
    }
}
