package in.ace.pardeep.org.acev2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Random;

/**
 * Created by hp 8 on 22-03-2016.
 */
public class GcmMessageHandler extends GcmListenerService {
    private static final String  MESSAGE_NOTIFICATION_ID= "ACE";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message=data.getString("message");
        Log.d("TAG from    :",from);
        Log.d("Gcm      :","Message   :"+message);
        createNotification(message);
    }

    private void createNotification(String message) {
        Intent intent=new Intent(this,NotificationShowingActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message",message);
        Random numb=new Random();

        PendingIntent pendingIntent=PendingIntent.getActivities(this,numb.nextInt() , new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);



        PendingIntent contentIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                new Intent(),
                PendingIntent.FLAG_CANCEL_CURRENT);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

       /* NotificationCompat.InboxStyle notification=new NotificationCompat.InboxStyle();
        notification.setBigContentTitle(message);*/
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder=  new NotificationCompat.Builder(this);
               notificationBuilder .setSmallIcon(R.drawable.ic_launcher_notify)
                .setTicker("Ace")
                .setContentTitle("Ambala College Of Engineering And Applied Research")
                .setContentText(message)
                .setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                       .setFullScreenIntent(contentIntent, true)
               .setContentIntent(pendingIntent);




       /* NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this);
        Notification notification = mBuilder.setSmallIcon(R.drawable.cloud).setTicker("Ace").setWhen(0)
                .setAutoCancel(true)
                .setContentTitle("Ace Notification")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.cloud))
                .setContentText(message).build();
*/


        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(numb.nextInt(),notificationBuilder.build());


    }


}
