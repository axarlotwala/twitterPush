package com.snapcode.twitterpush.Utli;

import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.snapcode.twitterpush.R;

public class NotifyingBuilder {

        public static void displayNotification(Context context,String title,String body){

            NotificationCompat.Builder builder  = new NotificationCompat.Builder(context,OpenHelper.CHANNEL_ID)
                    .setSmallIcon(R.drawable.googleg_standard_color_18)
                    .setContentTitle(OpenHelper.title)
                    .setContentText(OpenHelper.body)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
            managerCompat.notify(1,builder.build());

        }
}
