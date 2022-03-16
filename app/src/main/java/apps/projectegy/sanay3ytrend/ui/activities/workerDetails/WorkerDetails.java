package apps.projectegy.sanay3ytrend.ui.activities.workerDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.workerDetails.WorkerDetailsResponse;
import apps.projectegy.sanay3ytrend.utils.BoldTextView;
import apps.projectegy.sanay3ytrend.utils.Constant;

public class WorkerDetails extends AppCompatActivity implements WorkerDetailsInterface {

    String fb_link;
    String whats_link;
    String twitter_link;
    WorkerDetailsPresenter workerDetails;
    private LinearLayoutCompat linBack;
    private LinearLayoutCompat linParent;
    private AppCompatImageView imgProfile;
    private BoldTextView workerName;
    private BoldTextView departName;
    private BoldTextView phone;
    private BoldTextView address;
    private AppCompatImageView whats;
    private AppCompatImageView twitter;
    private AppCompatImageView face;
    private ProgressBar progressBar;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_details);
        MobileAds.initialize(this);
        AdView mAdView = findViewById(R.id.adView);
//        mAdView.setAdSize(AdSize.BANNER);
//
//        mAdView.setAdUnitId("ca-app-pub-2593694641695339/7530730419");

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        initViews();
    }

    private void initViews() {

        linBack = findViewById(R.id.lin_back);
        linParent = findViewById(R.id.lin_parent);
        imgProfile = findViewById(R.id.img_profile);
        workerName = findViewById(R.id.worker_name);
        departName = findViewById(R.id.depart_name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        whats = findViewById(R.id.whats);
        twitter = findViewById(R.id.twitter);
        face = findViewById(R.id.face);
        progressBar = findViewById(R.id.progress_bar);

        workerDetails = new WorkerDetailsPresenter(this, this);
        workerDetails.GetWorkerDetails(getIntent().getStringExtra(Constant.WorkerId));
        linParent.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        linBack.setOnClickListener(v -> {
            finish();
        });


        face.setOnClickListener(view -> {
            if (!fb_link.startsWith("http://") && !fb_link.startsWith("https://"))
                fb_link = "http://" + fb_link;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fb_link));
            startActivity(browserIntent);
        });
        address.setOnClickListener(view -> {
            if (!fb_link.startsWith("http://") && !fb_link.startsWith("https://"))
                fb_link = "http://" + fb_link;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fb_link));
            startActivity(browserIntent);
        });

        twitter.setOnClickListener(view -> {
            if (!twitter_link.startsWith("http://") && !twitter_link.startsWith("https://"))
                twitter_link = "http://" + twitter_link;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter_link));
            startActivity(browserIntent);
        });
        whats.setOnClickListener(view -> {

            String url = "https://api.whatsapp.com/send?phone=+2" + whats_link;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
        phone.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone.getText().toString()));
            startActivity(callIntent);
        });


    }

    @Override
    public void WorkerDetailsResponse(WorkerDetailsResponse getWorkersResponse) {

        linParent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        if (getWorkersResponse.getData().getImageUrl() != null)
            if (!getWorkersResponse.getData().getImageUrl().equals("")) {
                Picasso.get().load(Constant.BASE_URL_HTTP + getWorkersResponse.getData().getImageUrl()).placeholder(R.drawable.ic_profile_avatar)
                        .error(R.drawable.ic_profile_avatar).into(imgProfile);
            }

        if (getWorkersResponse.getData().getUserName() != null)
            workerName.setText(getWorkersResponse.getData().getUserName());

        if (getWorkersResponse.getData().getDepartmentName() != null)
            departName.setText(getWorkersResponse.getData().getDepartmentName());

        if (getWorkersResponse.getData().getAddress() != null)
            address.setText(getWorkersResponse.getData().getAddress());

        if (getWorkersResponse.getData().getWhatsAppNumber() != null)
            phone.setText(getWorkersResponse.getData().getWhatsAppNumber());


        if (getWorkersResponse.getData().getFacebook() != null && !getWorkersResponse.getData().getFacebook().equals(""))
            fb_link = getWorkersResponse.getData().getFacebook();
        else {
            face.setVisibility(View.GONE);
        }

        if (getWorkersResponse.getData().getWhatsAppNumber() != null)
            whats_link = getWorkersResponse.getData().getWhatsAppNumber();

        else {
            whats.setVisibility(View.GONE);
        }

        if (getWorkersResponse.getData().getTwitter() != null)
            twitter_link = getWorkersResponse.getData().getTwitter();
        else {
            twitter.setVisibility(View.GONE);
        }

        address.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + getWorkersResponse.getData().getLatitude() +
                    "," + getWorkersResponse.getData().getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });


    }
}
