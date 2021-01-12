package com.example.coursenotekeeper;

public final class NoteKeeperDatabaseContract {
    private NoteKeeperDatabaseContract(){} // make non-creatable

    public static final class CourseInfoEntry{
        public static final String TABLE_NAME = "course_info";
        public static final String COLUMN_COURSE_ID = "course_id";
        public static final String COLUMN_COURSE_TITLE = "course_title";

        // CREATE TABLE course_info (course_id, course_title)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_COURSE_ID + ", " +
                        COLUMN_COURSE_TITLE + ")";
    }
}
