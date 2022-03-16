package apps.projectegy.sanay3ytrend.ui.activities.settings;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.ui.activities.changePassword.ChangePassword;
import apps.projectegy.sanay3ytrend.ui.activities.home.Home;
import apps.projectegy.sanay3ytrend.ui.activities.login.Login;
import apps.projectegy.sanay3ytrend.ui.activities.profile.Profile;
import apps.projectegy.sanay3ytrend.ui.activities.wallet.Wallet;
import apps.projectegy.sanay3ytrend.utils.Constant;

public class Settings extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private LinearLayoutCompat linBack;
    private LinearLayoutCompat linProfile;
    private LinearLayoutCompat linChangePassword;
    private LinearLayoutCompat linChangeWallet;
    private LinearLayoutCompat linChangeLanguage;
    private LinearLayoutCompat linRate;
    private LinearLayoutCompat linShare;
    private LinearLayoutCompat linLogout;
    private SharedPreferences.Editor editor;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();

        MobileAds.initialize(this);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void initViews() {


        linBack = (LinearLayoutCompat) findViewById(R.id.lin_back);
        linProfile = (LinearLayoutCompat) findViewById(R.id.lin_profile);
        linChangePassword = (LinearLayoutCompat) findViewById(R.id.lin_change_password);
        linChangeWallet = (LinearLayoutCompat) findViewById(R.id.lin_change_wallet);
        linChangeLanguage = (LinearLayoutCompat) findViewById(R.id.lin_change_language);
        linRate = (LinearLayoutCompat) findViewById(R.id.lin_rate);
        linShare = (LinearLayoutCompat) findViewById(R.id.lin_share);
        linLogout = (LinearLayoutCompat) findViewById(R.id.lin_logout);


        sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        linBack.setOnClickListener(v -> {
            finish();
        });


        linShare.setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String shareMessage = getString(R.string.let_me_recomment);
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        });

        linRate.setOnClickListener(view1 -> {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        });


        linChangeLanguage.setOnClickListener(v -> {
            changeLanguage();
        });


        linLogout.setOnClickListener(v -> {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.TOKEN, "");
            editor.putString(Constant.PHONE_NUMBER, "");
            editor.putString(Constant.NAME, "");
            editor.putString(Constant.areaId, "");
            editor.putString(Constant.areaAr, "");
            editor.putString(Constant.Email, "");
            editor.putString(Constant.ProfileImg, "");
            editor.putString(Constant.Password, "");
            editor.apply();

            Intent intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });

        linChangeWallet.setOnClickListener(v -> {
            startActivity(new Intent(this, Wallet.class));
        });

        linChangePassword.setOnClickListener(v -> {
            startActivity(new Intent(this, ChangePassword.class));
        });
        linProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, Profile.class));
        });

    }

    private void changeLanguage() {
        Dialog dialog;
        Rect displayRectangle = new Rect();
        Window window = Objects.requireNonNull(getWindow());
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        //  builder                        = new AlertDialog.Builder(getContext());
        @SuppressLint("InflateParams")
        View mview = getLayoutInflater().inflate(R.layout.dialog_change_lang, null);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(mview);
        dialog.show();
        AppCompatTextView arabic = dialog.findViewById(R.id.arabic);
        AppCompatTextView english = dialog.findViewById(R.id.english);
        Button cancel = dialog.findViewById(R.id.cancel);
        arabic.setOnClickListener(v1 -> {

            Log.d("lang", "onCreate: " + "you click me");
            editor.putString(Constant.LANGUAGE, "ar");
            editor.apply();

            Constant.changeLang(this, sharedPreferences.getString(Constant.LANGUAGE, "ar"));


            dialog.dismiss();

            Intent intent = new Intent(this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //Animatoo.animateZoom(this);


        });
        english.setOnClickListener(v12 -> {

            Log.d("lang", "onCreate: " + "you click me");

            editor.putString(Constant.LANGUAGE, "en");
            editor.apply();

            Constant.changeLang(this, sharedPreferences.getString(Constant.LANGUAGE, "en"));

            dialog.dismiss();

            Intent intent = new Intent(this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //Animatoo.animateZoom(this);


        });
        cancel.setOnClickListener(view -> dialog.cancel());

    }


}