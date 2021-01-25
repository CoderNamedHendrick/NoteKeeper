package com.example.coursenotekeeper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.coursenotekeeper.NoteActivity.CHANNEL_ID;

public class NoteReminderNotification {

    public static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviours after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void reminderNotification(Context context,
                                            String noteTitle, String noteText, int noteId) {
        final Resources res = context.getResources();

        final Bitmap picture = BitmapFactory.decodeResource(res, R.drawable.logo);

        Intent noteActivityIntent = new Intent(context, NoteActivity.class);
        noteActivityIntent.putExtra(NoteActivity.NOTE_ID, noteId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                noteActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent backupServiceIntent = new Intent(context, NoteBackupService.class);
        backupServiceIntent.putExtra(NoteBackupService.EXTRA_COURSE_ID, NoteBackup.ALL_COURSES);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context
                , CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_note_reminder)
                .setLargeIcon(picture)
                .setContentTitle("Review note")
                .setContentText(noteText)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(noteText)
                        .setBigContentTitle(noteTitle)
                        .setSummaryText("Review note")
                )
                .setContentIntent(pendingIntent)
                .addAction(
                        0,
                        "View all notes",
                        PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(context, MainActivity.class),
                                PendingIntent.FLAG_UPDATE_CURRENT)
                )
                .addAction(
                        0,
                        "Backup notes",
                        PendingIntent.getService(
                                context,
                                0,
                                backupServiceIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT)
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setTicker("Review note")
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(NoteActivity.notificationId, builder.build());
    }

}
