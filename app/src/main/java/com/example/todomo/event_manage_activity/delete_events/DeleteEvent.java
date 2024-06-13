package com.example.todomo.event_manage_activity.delete_events;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import common_classes.database_create.DatabaseManage;

public class DeleteEvent {

    private final DatabaseManage dbManage;
    private SQLiteDatabase database;

    public DeleteEvent(Context context) {
        dbManage = new DatabaseManage(context);
    }

    public void open() {
        database = dbManage.getWritableDatabase();
    }

    public void close() {
        dbManage.close();
    }

    public void deleteEvent(String eventId, EventDeletionCallback callback) {
        if (database != null) {
            try {
                String selection = DatabaseManage.COLUMN_ID + " = ?";
                String[] selectionArgs = {eventId};

                int rowsDeleted = database.delete(DatabaseManage.TABLE_EVENTS, selection, selectionArgs);

                if (rowsDeleted > 0) {
                    callback.onDeleteSuccess("Event deleted successfully!");
                } else {
                    callback.onDeleteFailure("Event not found or could not be deleted");
                }
            } catch (Exception e) {
                e.printStackTrace();
                callback.onDeleteFailure("Failed to delete event: " + e.getMessage());
            }
        } else {
            callback.onDeleteFailure("Database is not open");
        }
    }

    public interface EventDeletionCallback {
        void onDeleteSuccess(String message);
        void onDeleteFailure(String message);
    }
}
