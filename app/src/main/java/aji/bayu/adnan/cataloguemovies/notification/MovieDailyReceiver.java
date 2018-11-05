package aji.bayu.adnan.cataloguemovies.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.Calendar;

import aji.bayu.adnan.cataloguemovies.MainActivity;
import aji.bayu.adnan.cataloguemovies.R;

/**
 * Created by Jihad044 on 26/10/2018.
 */

public class MovieDailyReceiver extends BroadcastReceiver {

    private void sendNotification(Context context, String title, String desc, int id) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(context, MainActivity.class);

        PendingIntent mPendingIntent = PendingIntent.getActivity(context, id, mIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(mPendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mNotificationChannel = new NotificationChannel(
                    "11001", "NOTIFICATION_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationChannel.enableLights(true);
            mNotificationChannel.setLightColor(Color.YELLOW);
            mNotificationChannel.enableVibration(true);
            mNotificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId("11001");
            mNotificationManager.createNotificationChannel(mNotificationChannel);
        }
        mNotificationManager.notify(id, builder.build());

    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent mIntent = new Intent(context, MovieDailyReceiver.class);
        return PendingIntent.getBroadcast(context, 1001, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public void setAlarm(Context context) {
        cancelAlarm(context);
        AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, 7);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,getPendingIntent(context));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(), getPendingIntent(context));
        }



        Toast.makeText(context, "Daily Notif ON", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context) {
        AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.cancel(getPendingIntent(context));

        Toast.makeText(context, "Daily Notif OFF", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent mIntent) {
        sendNotification(context, context.getString(R.string.judul_daily),
                context.getString(R.string.desk_daily), 1001);
    }

}
