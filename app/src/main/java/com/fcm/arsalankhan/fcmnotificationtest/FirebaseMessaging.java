package com.fcm.arsalankhan.fcmnotificationtest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Arsalan khan on 9/14/2017.
 */

public class FirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title,message;
        if(remoteMessage.getData()!=null){

             title = remoteMessage.getData().get("title");
             message = remoteMessage.getData().get("message");

            Log.d("TAG","title: "+title);
            Log.d("TATG","message: "+message);

            Intent intent = new Intent(this,MainActivity.class);

            NotificationCompat.Builder notification = new NotificationCompat.Builder(this);

            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notification.setSound(soundUri);
            notification.setAutoCancel(true);
            notification.setContentTitle(title);
            notification.setContentText(message);
            notification.setSmallIcon(R.mipmap.ic_launcher);

            PendingIntent pendingIntent =PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            notification.setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(2,notification.build());

        }
    }
}
