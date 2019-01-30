package com.app.amyal.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.app.amyal.R;
import com.app.amyal.activities.MainActivity;
import com.app.amyal.global.AppConstants;
import com.app.amyal.helpers.BasePreferenceHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private static final String MESSAGE = "message";
    private static final String ORDER_ACCEPTED = "order_accepted";
    private static final String ORDER_PROGRESS = "service_progress";
    private static final String ORDER_DONE = "service_done";
    private static final String ORDER_COMPLETION = "order_completion";
    public static final String ON_WAY = "on_way";
    public static final String AUTO_CANCEL = "auto_cancel";

    private BasePreferenceHelper preferenceHelper;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        BasePreferenceHelper prefHelper = new BasePreferenceHelper(this);
        Map<String, String> msgData = remoteMessage.getData();
        String msg = msgData.get("Message");
        String title = msgData.get("Title");
        int Type = Integer.parseInt(msgData.get("Type"));
        Log.e(TAG, "onMessageReceived: status->"+Type+" msg->"+msg+" title->");

        if(Type == PushNotificationType.UserDeleted.ordinal()){
            prefHelper.setLoginStatus(false);
            prefHelper.putUser(null);
            logOutBrodcast();
            //sendCustomNotification(title, msg, type);
        }
        else if (prefHelper.isLogin()){
            sendDefaultNotification(title,msg);
        }


        Log.e(TAG, "onMessageReceived: title->" + title + " message->" + msg + " senderId->" + " STATUS->" + Type);
    }

    public enum PushNotificationType
    {
        None,
        Announcement,
        WelcomeMessage,
        WarningNotice,
        FeedbackReply,
        UserDeleted,
    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param title
     * @param msg   FCM message body received.
     */
    private void sendDefaultNotification(String title, String msg) {
        if (TextUtils.isEmpty(title)) title = getResources().getString(R.string.app_name);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("msg", msg);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setDefaults(Notification.DEFAULT_ALL)  //to show alert notification
                .setPriority(Notification.PRIORITY_MAX)  //on top of app
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Random rn = new Random();
        int range = 10000 - 1 + 1;
        int randomNum =  rn.nextInt(range) + 1;

        notificationManager.notify(randomNum /* ID of notification */, notificationBuilder.build());
    }

    /*private void sendCustomNotification(String title, String msg, String senderId, String orderId,
                                        String receiverId, String status) {
        if (TextUtils.isEmpty(title)) title = getResources().getString(R.string.app_name);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("sender_id", senderId);
        intent.putExtra("receiver_id", receiverId);
        intent.putExtra("order_id", orderId);
        intent.putExtra("status", status);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setDefaults(Notification.DEFAULT_ALL)  //to show alert notification
                .setPriority(Notification.PRIORITY_MAX)  //on top of app
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Random rn = new Random();
        int range = 10000 - 1 + 1;
        int randomNum =  rn.nextInt(range) + 1;

        notificationManager.notify(randomNum *//* ID of notification *//*, notificationBuilder.build());
    }*/

   /* private void orderAcceptedBroadCast(String message, String sender_id, String order_id, String receiver_id) {
        Intent intent = new Intent();
        intent.setAction(AppConstants.ORDER_ACCEPT_RECEIVER);
//        intent.putExtra("message", message);
        intent.putExtra("sender_id", sender_id);
        intent.putExtra("order_id", order_id);
        intent.putExtra("receiver_id", receiver_id);
        this.sendBroadcast(intent);
    }*/



    private void logOutBrodcast() {
        Intent intent = new Intent();
        intent.setAction(AppConstants.LOG_OUT_RECEIVER);
        this.sendBroadcast(intent);
    }
}