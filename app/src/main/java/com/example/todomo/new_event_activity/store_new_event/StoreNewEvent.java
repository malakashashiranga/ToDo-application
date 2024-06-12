package com.example.todomo.new_event_activity.store_new_event;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import common_classes.database_create.DatabaseManage;

public class StoreNewEvent {
    private final DatabaseManage dbManage;
    private SQLiteDatabase database;

    public StoreNewEvent(Context context) {
        dbManage = new DatabaseManage(context);
    }

    public void open() {
        database = dbManage.getWritableDatabase();
    }

    public void close() {
        dbManage.close();
    }

    public void saveEvent(String title, String details, String date, String time, int priority, String email, EventSaveCallback callback) {
        ContentValues values = new ContentValues();
        values.put(DatabaseManage.USER_EMAIL, email);
        values.put(DatabaseManage.COLUMN_TITLE, title);
        values.put(DatabaseManage.COLUMN_DETAILS, details);
        values.put(DatabaseManage.COLUMN_DATE, date);
        values.put(DatabaseManage.COLUMN_TIME, time);
        values.put(DatabaseManage.COLUMN_PRIORITY, priority);

        long result = -1;
        try {
            result = database.insert(DatabaseManage.TABLE_EVENTS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result == -1) {
            callback.onFailure("Failed to save event");
        } else {
            callback.onSuccess("Event saved successfully!");
        }
    }

    public interface EventSaveCallback {
        void onSuccess(String message);
        void onFailure(String message);
    }
}
