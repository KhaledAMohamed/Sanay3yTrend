package apps.projectegy.sanay3ytrend.data.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import apps.projectegy.sanay3ytrend.utils.Constant;

import static android.content.Context.MODE_PRIVATE;

public class Token {
    private static final String TAG = "TOKEN";
    private SharedPreferences mSharedPreferences;

    public void getToken(Context context) {

        mSharedPreferences = context.getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
//                        String token = task.getResult().getToken();


                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putString(Constant.devicetoken, token);
                        Log.d(TAG, "getToken: " + token);
                        Log.d(TAG, "getTokenShared: " + mSharedPreferences.getString(Constant.devicetoken, ""));
                        editor.apply();
                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
