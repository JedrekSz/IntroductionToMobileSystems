package com.example.introductiontomobilesystems;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

public class HabitWorker extends Worker {

    public HabitWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        HabitsStorage storage = new HabitsStorage(context);

        List<Habit> list = storage.load();
        boolean listChanged = false;
        long currentTime = System.currentTimeMillis();

        for (Habit h : list) {

            if (h.active && currentTime > h.dueDate) {

                h.done = false;

                while (currentTime > h.dueDate) {
                    h.dueDate = Habit.calculateNextDueDate(h.dueDate, h.freq);
                }

                if (h.notifs) {
                    sendNotification(context, h.name);
                }

                listChanged = true;
            }
        }

        if (listChanged) {
            storage.save(list);
        }

        return Result.success();
    }

    private void sendNotification(Context context, String habitName) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "habit_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Habit Reminders", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.kenny)
                .setContentTitle("Help your habitat prosper!")
                .setContentText("Time to " + habitName)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        manager.notify(habitName.hashCode(), builder.build());
    }
}