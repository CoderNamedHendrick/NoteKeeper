package com.example.coursenotekeeper;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class NoteUploaderJobService extends JobService {

    public static final String EXTRA_DATA_URI = "com.example.coursenotekeeper.extras.DATA_URI";

    public NoteUploaderJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}