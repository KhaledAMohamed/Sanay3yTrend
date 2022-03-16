package apps.projectegy.sanay3ytrend.ui.activities.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.countries.GetAllCountriesResponse;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
import apps.projectegy.sanay3ytrend.data.models.workersAds.GetWorkersAdsResponse;
import apps.projectegy.sanay3ytrend.ui.activities.generalAds.GeneralAds;
import apps.projectegy.sanay3ytrend.ui.activities.settings.Settings;
import apps.projectegy.sanay3ytrend.ui.activities.workersNoMap.WorkersNoMap;
import apps.projectegy.sanay3ytrend.ui.adapters.CategoryAdapter;
import apps.projectegy.sanay3ytrend.ui.adapters.SliderAdapterExample;
import apps.projectegy.sanay3ytrend.utils.BoldTextView;
import apps.projectegy.sanay3ytrend.utils.Constant;

public class Home extends AppCompatActivity implements HomeInterface, CategoryAdapter.OnClickHandler {

    HomePresenter homePresenter;
    SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private LinearLayoutCompat linNotification;
    private AppCompatImageView notificationImg;
    private BoldTextView countNotification;
    private LinearLayoutCompat linShare;
    private LinearLayoutCompat linParentTop;
    private SliderView imageSlider;
    private RecyclerView rv;
    private ProgressBar progressBar;


    private LinearLayoutCompat linSeeAndEarn;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        MobileAds.initialize(this);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        linNotification = (LinearLayoutCompat) findViewById(R.id.lin_settings);
        notificationImg = (AppCompatImageView) findViewById(R.id.notification_img);
        countNotification = (BoldTextView) findViewById(R.id.count_notification);
        linShare = (LinearLayoutCompat) findViewById(R.id.lin_share);
        linParentTop = (LinearLayoutCompat) findViewById(R.id.lin_parent_top);
        imageSlider = (SliderView) findViewById(R.id.imageSlider);
        rv = (RecyclerView) findViewById(R.id.rv);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        linSeeAndEarn = (LinearLayoutCompat) findViewById(R.id.lin_see_and_earn);

        sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);

        homePresenter = new HomePresenter(this, this);
        homePresenter.getDepartments();
        progressBar.setVisibility(View.VISIBLE);
        linParentTop.setVisibility(View.GONE);
        imageSlider.setVisibility(View.GONE);

        homePresenter.GetSliderWorker();

        linSeeAndEarn.setOnClickListener(v -> {
            Intent intent = new Intent(this, GeneralAds.class);
            startActivity(intent);
        });
        linNotification.setOnClickListener(v -> {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        });
        linShare.setOnClickListener(v -> {
            ShareWithFriends();
        });
    }

    @Override
    public void GetAllCountries(GetAllCountriesResponse getAllCountriesResponse) {

    }

    @Override
    public void GetDepartmentsResponse(GetDepartmentsResponse getDepartmentsResponse) {

        progressBar.setVisibility(View.GONE);
        linParentTop.setVisibility(View.VISIBLE);

        if (getDepartmentsResponse.getData() != null && getDepartmentsResponse.getData().size() > 0) {
            CategoryAdapter categoryAdapter = new CategoryAdapter(this, getDepartmentsResponse.getData(), this);
            rv.setLayoutManager(new GridLayoutManager(this, 3));
            rv.setAdapter(categoryAdapter);
            Constant.runLayoutAnimation(rv);
            rv.setVisibility(View.VISIBLE);
        } else {
            rv.setVisibility(View.GONE);
        }


    }

    @Override
    public void GetWorkersAdsResponse(GetWorkersAdsResponse getWorkersAdsResponse) {
        if (getWorkersAdsResponse.getData() != null && getWorkersAdsResponse.getData().size() > 0) {
            List<String> images = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            for (int i = 0; i < getWorkersAdsResponse.getData().size(); i++) {
                images.add(getWorkersAdsResponse.getData().get(i).getImageUrl());
                ids.add(String.valueOf(getWorkersAdsResponse.getData().get(i).getWorkerId()));
            }

            SliderAdapterExample adapter = new SliderAdapterExample(this, images, ids);
            imageSlider.setSliderAdapter(adapter);
            imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM);
            //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            imageSlider.setIndicatorSelectedColor(this.getResources().getColor(R.color.colorAccent));
            imageSlider.setIndicatorUnselectedColor(this.getResources().getColor(R.color.colorAccent));
            imageSlider.setScrollTimeInSec(5); //set scroll delay in seconds :
            imageSlider.startAutoCycle();
            imageSlider.setVisibility(View.VISIBLE);

        } else {
            imageSlider.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClicked(String id) {
        Intent intent = new Intent(this, WorkersNoMap.class);
        intent.putExtra(Constant.DepartId, id);
        startActivity(intent);
    }

    private void ShareWithFriends() {
        //        elyamany.page.link

       /* DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("http://projectegy-001-site45.gtempurl.com/"))
                .setDomainUriPrefix("https://elyamany.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink();*/

//        Uri dynamicLinkUri = dynamicLink.getUri();

        String sharelinktext = "https://sanay3ytrend.page.link?" +
                "link=https://project-egy.com?mycode=" + sharedPreferences.getString(Constant.PHONE_NUMBER, "") +
                "&apn=" + getPackageName() +
                "&st=" + getString(R.string.My_Refer_Link) +
                "&sd=" + getString(R.string.use_this_link) +
                "&si=" + "http://projectegy-002-site11.gtempurl.com/content/images/web_hi_res_512.png";
        // shorten the link
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                //.setLongLink(dynamicLink.getUri())
                .setLongLink(Uri.parse(sharelinktext))  // manually
                .buildShortDynamicLink()
                .addOnCompleteListener(Home.this, task -> {
                    if (task.isSuccessful()) {
                        // Short link created
                        Uri shortLink = task.getResult().getShortLink();
                        Uri flowchartLink = task.getResult().getPreviewLink();
                        Log.e("main ", "short link " + shortLink.toString());
                        // share app dialog
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                        intent.setType("text/plain");
                        startActivity(intent);
                    } else {
                        // Error
                        // ...
                        Log.e("main", " error " + task.getException());
                    }
                });

    }

}