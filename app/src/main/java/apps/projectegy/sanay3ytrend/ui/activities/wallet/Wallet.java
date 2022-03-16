package apps.projectegy.sanay3ytrend.ui.activities.wallet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.profile.GetProfileResponse;
import apps.projectegy.sanay3ytrend.utils.RegularTextView;


public class Wallet extends AppCompatActivity implements WalletInterface {

    WalletPresenter walletPresenter;
    private AppCompatImageView arrowBackPageTwo;
    private FrameLayout walletFrame;
    private RegularTextView btnLogin;
    private RegularTextView walletTxt;
    private RegularTextView walletDetails;
    private RegularTextView btnChargeWallet;
    private ProgressBar progressBar;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        MobileAds.initialize(this);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        initViews();
    }

    private void initViews() {

        arrowBackPageTwo = findViewById(R.id.arrow_back_page_two);
        walletFrame = findViewById(R.id.wallet_frame);
        btnLogin = findViewById(R.id.btn_login);
        walletTxt = findViewById(R.id.wallet_txt);
        walletDetails = findViewById(R.id.wallet_details);
        btnChargeWallet = findViewById(R.id.btn_charge_wallet);
        progressBar = findViewById(R.id.progress_bar);

        arrowBackPageTwo.setOnClickListener(view -> {
            finish();
        });

        walletPresenter = new WalletPresenter(this, this);
        walletPresenter.getWalletData();
        walletFrame.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void GetProfileResponse(GetProfileResponse response) {
        walletFrame.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        if (response.getData() != null) {
            walletTxt.setText(response.getData().getWallet() + "");
        }
    }
}