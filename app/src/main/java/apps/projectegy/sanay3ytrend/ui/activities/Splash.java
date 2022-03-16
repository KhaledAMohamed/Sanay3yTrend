package apps.projectegy.sanay3ytrend.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.ui.activities.home.Home;
import apps.projectegy.sanay3ytrend.ui.activities.welcome.Welcome;
import apps.projectegy.sanay3ytrend.utils.Constant;

import static apps.projectegy.sanay3ytrend.utils.Constant.changeLang;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        int SPLASH_DISPLAY_LENGTH = 4000;
        new Handler().postDelayed(() ->
        {
            SharedPreferences mSharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, MODE_PRIVATE);
            changeLang(this, mSharedPreferences.getString(Constant.LANGUAGE, "ar"));

           /* SharedPreferences mSharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, MODE_PRIVATE);
            changeLang(this, mSharedPreferences.getString(Constant.LANGUAGE, "ar"));
            Intent startIntent = new Intent(this, Welcome.class);
            startActivity(startIntent);
            finish();
*/
            FirebaseDynamicLinks.getInstance()
                    .getDynamicLink(getIntent())
                    .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                        @Override
                        public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                            // Get deep link from result (may be null if no link is found)
                            Uri deepLink = null;
                            if (pendingDynamicLinkData != null) {
                                deepLink = pendingDynamicLinkData.getLink();
                            }


                            // Handle the deep link. For example, open the linked
                            // content, or apply promotional credit to the user's
                            // account.
                            // ...

                            // ...

                            try {
                                String referLink = deepLink.toString();
                                String myCode = referLink.substring(referLink.lastIndexOf("=") + 1);
                                Log.i("myCode", myCode);

                                SharedPreferences mSharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, MODE_PRIVATE);
                                SharedPreferences.Editor editor = mSharedPreferences.edit();
                                editor.putString(Constant.SponsorPhone, myCode);
                                editor.apply();

                                Intent intent = new Intent(Splash.this, Welcome.class);
                                startActivity(intent);
                                finish();
                            } catch (Exception e) {

                                SharedPreferences mSharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, MODE_PRIVATE);
//            changeLang(this, mSharedPreferences.getString(Constant.LANGUAGE, Locale.getDefault().getDisplayLanguage()));
                                if (mSharedPreferences.getString(Constant.TOKEN, "").equals("")) {
                                    Intent startIntent = new Intent(Splash.this, Welcome.class);
                                    startActivity(startIntent);
                                    finish();
                                } else {
                                    Intent startIntent = new Intent(Splash.this, Home.class);
                                    startActivity(startIntent);
                                    finish();
                                }
                            }

                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("getDynamicLink", "getDynamicLink:onFailure", e);


                            SharedPreferences mSharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, MODE_PRIVATE);
//            changeLang(this, mSharedPreferences.getString(Constant.LANGUAGE, Locale.getDefault().getDisplayLanguage()));
                            if (mSharedPreferences.getString(Constant.TOKEN, "").equals("")) {
                                Intent startIntent = new Intent(Splash.this, Welcome.class);
                                startActivity(startIntent);
                                finish();
                            } else {
                                Intent startIntent = new Intent(Splash.this, Home.class);
                                startActivity(startIntent);
                                finish();
                            }
                        }
                    });

        }, SPLASH_DISPLAY_LENGTH);
    }
}