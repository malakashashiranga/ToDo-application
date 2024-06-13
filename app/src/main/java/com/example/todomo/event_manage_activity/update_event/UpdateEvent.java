package com.example.todomo.event_manage_activity.update_event;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import common_classes.database_create.DatabaseManage;


public class UpdateEvent {

    private final DatabaseManage dbManage;
    private SQLiteDatabase database;
    public UpdateEvent(Context context) {
        dbManage = new DatabaseManage(context);
    }
    public void open() {
        database = dbManage.getWritableDatabase();
    }
    public void close() {
        dbManage.close();
    }

    public void updateEvent(String eventId, String newTitle, String newDetails, String newDate, String newTime, int newPriority, EventUpdateCallback callback) {
        if (database != null) {
            try {
                // Create content values with the new event details
                ContentValues values = new ContentValues();
                values.put(DatabaseManage.COLUMN_TITLE, newTitle);
                values.put(DatabaseManage.COLUMN_DETAILS, newDetails);
                values.put(DatabaseManage.COLUMN_DATE, newDate);
                values.put(DatabaseManage.COLUMN_TIME, newTime);
                values.put(DatabaseManage.COLUMN_PRIORITY, newPriority);

                // Define where clause to update event with specific ID
                String selection = DatabaseManage.COLUMN_ID + " = ?";
                String[] selectionArgs = {eventId};

                int rowsUpdated = database.update(DatabaseManage.TABLE_EVENTS, values, selection, selectionArgs);

                if (rowsUpdated > 0) {
                    callback.onUpdateSuccess("Event updated successfully!");
                } else {
                    callback.onUpdateFailure("Event not found or could not be updated");
                }
            } catch (Exception e) {
                e.printStackTrace();
                callback.onUpdateFailure("Failed to update event: " + e.getMessage());
            }
        } else {
            callback.onUpdateFailure("Database is not open");
        }
    }

    public interface EventUpdateCallback {
        void onUpdateSuccess(String message);
        void onUpdateFailure(String message);
    }
}