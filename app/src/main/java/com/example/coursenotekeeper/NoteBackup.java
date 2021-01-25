package com.example.coursenotekeeper;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.coursenotekeeper.CourseNoteKeeperProviderContract.Notes;

public class NoteBackup {
    public static final String ALL_COURSES = "ALL_COURSES";
    private static final String TAG = NoteBackup.class.getSimpleName();

    public static void doBackup(Context context, String backupCourseId){
        String columns[] = {
                Notes.COLUMN_COURSE_ID,
                Notes.COLUMN_NOTE_TITLE,
                Notes.COLUMN_NOTE_TEXT
        };

        String selection = null;
        String[] selectionArgs = null;
        if (!backupCourseId.equals(ALL_COURSES)){
            selection = Notes.COLUMN_COURSE_ID + " = ?";
            selectionArgs = new String[] {backupCourseId};
        }

        Cursor cursor = context.getContentResolver().query(Notes.CONTENT_EXPANDED_URI,
                columns, selection, selectionArgs, null);
        int courseIdPos = cursor.getColumnIndex(Notes.COLUMN_COURSE_ID);
        int noteTitlePos = cursor.getColumnIndex(Notes.COLUMN_NOTE_TITLE);
        int noteTextPos = cursor.getColumnIndex(Notes.COLUMN_NOTE_TEXT);

        Log.i(TAG, ">>>>>****   BACKUP START - Thread: " + Thread.currentThread().getId() +
                "   ****<<<<<");
        while (cursor.moveToNext()){
            String courseId = cursor.getString(courseIdPos);
            String noteTitle = cursor.getString(noteTitlePos);
            String noteText = cursor.getString(noteTextPos);

            if (!noteTitle.equals("")){
                Log.i(TAG, ">>>>Backing up Note<<<<" +  courseId + "|" + noteTitle +
                        "|" + noteText);
                simulateLongRunningWork();
            }
        }
        Log.i(TAG, ">>>>>****   BACKUP COMPLETE     ****<<<<<");
        cursor.close();
    }

    private static void simulateLongRunningWork() {
        Thread thread = Thread.currentThread();
        try {
            thread.sleep(1400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
