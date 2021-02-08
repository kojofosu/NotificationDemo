package com.mcdev.notificationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "MCDEV_1234";
    private static final int NOTIFICATION_ONE_ID = 111;
    Button notif1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        /*Before you can deliver the notification on Android 8.0 and higher,
        you must register your app's notification channel with the system by passing an instance of NotificationChannel to createNotificationChannel().*/
        createNotificationChannel();

        notif1.setOnClickListener(view -> {
            notificationOne();
        });
    }

    private void notificationOne() {
        //setting the notification content and channel
        /*Notice that the NotificationCompat.Builder constructor requires that you provide a channel ID.
         This is required for compatibility with Android 8.0 (API level 26) and higher, but is ignored by older versions.*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification One")
                .setContentText("A notification in its most basic and compact form (also known as collapsed form) displays an icon, a title, and a small amount of content text.")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("A notification in its most basic and compact form (also known as collapsed form) displays an icon, a title, and a small amount of content text.")) //By default, the notification's text content is truncated to fit one line. If you want your notification to be longer, you can enable an expandable notification by adding a style template with setStyle().
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);//The priority determines how intrusive the notification should be on Android 7.1 and lower. (For Android 8.0 and higher, you must instead set the channel importance)




        //showing the notification
        NotificationManagerCompat.from(MainActivity.this)
                .notify(NOTIFICATION_ONE_ID, builder.build());
    }

    private void createNotificationChannel() {
        /*Creating the NotificationChannel, but only on API 26+
        * because the NotificationChannel class is new and not in the support library*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(description);

            /*Register the channel with the system; you cannot change the importance
            * or notification behaviours after this*/
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(notificationChannel);
        }
    }

    private void init() {
        notif1 = findViewById(R.id.notif_1);
    }
}