package apps.projectegy.fiminute.data.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import apps.projectegy.sanay3ytrend.ui.activities.Splash;
import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.MyApp;
import me.leolin.shortcutbadger.ShortcutBadger;

/*
import me.leolin.shortcutbadger.ShortcutBadger;
*/


public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    private SharedPreferences mSharedPreferences;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        //Log.d(TAG, "From: " + remoteMessage.getFrom());


        // Check if message contains a data payload.
        if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            //Log.i(TAG, "Message data payload: " + remoteMessage.getData().get("body"));
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle()
                    , remoteMessage.getData().get("id")
                    , Objects.requireNonNull(remoteMessage.getData().get("notificationtype"))
            );
            //sendNotification(remoteMessage.getNotification().getBody(), "Makuk");
            //(String messageBody, String title)

        }

        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d("bodybodybody", "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, String title, String id, String type) {
        Intent intent = new Intent(this, Splash.class);
       /* Intent intent = new Intent(this, Home.class);

        Constant.NotificationType = type;
        Constant.NotificationID = id;
        switch (type) {
            case "0":
                Constant.home_position = "2";
                intent = new Intent(this, Home.class);
                break;
            case "4":
            case "2":
            case "10":
                Constant.home_position = "3";
                intent = new Intent(this, Home.class);
            case "3":
                intent = new Intent(this, MyOrderDetailsStore.class);
                intent.putExtra(Constant.OrderID, id);
                break;
            case "5":
                intent = new Intent(this, MyOrderDetailsAnyThing.class);
                intent.putExtra(Constant.OrderID, id);
                break;
            case "6":
                intent = new Intent(this, ChatNew.class);
                intent.putExtra(Constant.OrderID, id);
                intent.putExtra(Constant.ChatType, "0");
                Constant.OrderIDChat = String.valueOf(id);

                break;
            case "7":
                intent = new Intent(this, ChatNew.class);
                intent.putExtra(Constant.OrderID, id);
                intent.putExtra(Constant.ChatType, "1");
                Constant.OrderIDChat = String.valueOf(id);
                break;
            case "9":
                intent = new Intent(this, Wallet.class);
                break;
            case "11":
                intent = new Intent(this, MyOrderWorker.class);
                intent.putExtra(Constant.OrderID, id);
                break;
            case "13":
                intent = new Intent(this, ChatNew.class);
                intent.putExtra(Constant.OrderID, id);
                intent.putExtra(Constant.ChatType, "2");
                Constant.OrderIDChat = String.valueOf(id);
                break;

            default:
                intent = new Intent(this, Home.class);
                break;
        }*/

        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0  /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "Talabatk", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("approval");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(R.color.colorPrimary);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setAutoCancel(true)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                        .setShowWhen(true)
                        .setSound(defaultSoundUri)
                        //.setDefaults(Notification.DEFAULT_SOUND)
                        .setContentIntent(pendingIntent);
        /*if (notificationManager != null) {
            notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
        }*/

        notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationManager.notify(0
                /* ID of notification */
                , notificationBuilder.build());


        mSharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, MODE_PRIVATE);
        ShortcutBadger.applyCount(MyApp.getAppContext(), Integer.parseInt(mSharedPreferences.getString(Constant.NotificationsNumber, "0"))); //for 1.1.4+

    }
}
