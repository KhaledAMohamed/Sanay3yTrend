package apps.projectegy.sanay3ytrend.ui.activities.workersNoMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.countries.GetAllCountriesResponse;
import apps.projectegy.sanay3ytrend.data.models.departments.GetDepartmentsResponse;
import apps.projectegy.sanay3ytrend.data.models.workers.GetWorkersResponse;
import apps.projectegy.sanay3ytrend.ui.activities.workers.WorkersActivity;
import apps.projectegy.sanay3ytrend.ui.activities.workers.WorkersInterface;
import apps.projectegy.sanay3ytrend.ui.activities.workers.WorkersPresenter;
import apps.projectegy.sanay3ytrend.ui.adapters.WorkersAdapter;
import apps.projectegy.sanay3ytrend.ui.adapters.areaDialog.areaDialogAdapter;
import apps.projectegy.sanay3ytrend.ui.adapters.areaDialog.areaDialogItem;
import apps.projectegy.sanay3ytrend.utils.BoldTextView;
import apps.projectegy.sanay3ytrend.utils.Constant;



public class WorkersNoMap extends AppCompatActivity implements WorkersInterface {

    private final ArrayList<areaDialogItem> DialogList = new ArrayList<>();
    WorkersPresenter workersPresenter;
    String cityId = "-1";
    SharedPreferences.Editor editor;
    GetAllCountriesResponse getAllCountriesResponse;
    GetWorkersResponse getWorkersResponse;
    private LinearLayoutCompat linBack;
    private LinearLayoutCompat linParentTop;
    private RecyclerView rv;
    private LinearLayoutCompat linNoData;
    private ProgressBar progressBar;
    private BoldTextView etSearch;
    private SharedPreferences sharedPreferences;
    private RecyclerView DialogRecyclerView;
    private areaDialogAdapter filterAreaAdapter1;
    private CardView cardOnMap;
    private InterstitialAd mInterstitialAd;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_no_map);
//
//        MobileAds.initialize(this);
//        AdView mAdView = findViewById(R.id.adView);
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });


        LoadAds();
        Log.d(TAG, "onCreate: asdassddassaddasdsadasdsa");

        initViews();
    }

    private void showAds(){
        if(mInterstitialAd !=null)
        {
            mInterstitialAd.show(this);

        }else
        {
            Log.d(TAG, "showAds: ");
        }
    }

    private static final String TAG = "worTesrtkerAdsss";
    private void LoadAds(){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/8691691433", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(TAG, "onAdFailedToLoad: failed");
                mInterstitialAd = null;

            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                Log.d(TAG, "onAdLoaded: success ");
                mInterstitialAd = interstitialAd;
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        Log.d(TAG, "onAdFailedToLoad: onAdClicked");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        Log.d(TAG, "onAdFailedToLoad: onAdDismissedFullScreenContent");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.d(TAG, "onAdFailedToLoad: onAdFailedToShowFullScreenContent");
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                        Log.d(TAG, "onAdFailedToLoad: onAdImpression");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        Log.d(TAG, "onAdFailedToLoad: onAdShowedFullScreenContent");
                        mInterstitialAd =null;
                    }
                });
            }
        });
    }



    private void initViews() {
        linBack = findViewById(R.id.lin_back);
        linParentTop = findViewById(R.id.lin_parent_top);
        rv = findViewById(R.id.rv);
        linNoData = findViewById(R.id.lin_no_data);
        progressBar = findViewById(R.id.progress_bar);

        etSearch = findViewById(R.id.et_search);

        cardOnMap = findViewById(R.id.card_on_map);

        sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();


        linBack.setOnClickListener(v -> {

            finish();
            showAds();

        });
        cardOnMap.setOnClickListener(v -> {
            Intent intent = new Intent(WorkersNoMap.this, WorkersActivity.class);
            intent.putExtra(Constant.DepartId, getIntent().getStringExtra(Constant.DepartId));
            startActivity(intent);
        });
        workersPresenter = new WorkersPresenter(this, this);
        workersPresenter.GetWorkers(getIntent().getStringExtra(Constant.DepartId));
        workersPresenter.getAllCountries();
        progressBar.setVisibility(View.VISIBLE);
        linNoData.setVisibility(View.GONE);

        etSearch.setOnClickListener(view1 -> {
            chooseDepartments();
        });
        etSearch.setText(sharedPreferences.getString(Constant.areaAr, ""));
        cityId = sharedPreferences.getString(Constant.areaId, "");


    }

    @SuppressLint("SetTextI18n")
    private void chooseDepartments() {

        android.app.AlertDialog.Builder builder1;
        final android.app.AlertDialog dialog1;
        builder1 = new android.app.AlertDialog.Builder(this);
        @SuppressLint("InflateParams")
        View mview = getLayoutInflater().inflate(R.layout.area_dialog, null);

        loadCities();
        DialogRecyclerView = mview.findViewById(R.id.Recycler_Dialog_cities);
        filterAreaAdapter1 = new areaDialogAdapter(DialogList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        BoldTextView DialogHeader;

        DialogHeader = mview.findViewById(R.id.DialogHeader);

        DialogHeader.setText(getString(R.string.select_city));
        DialogRecyclerView.setLayoutManager(linearLayoutManager);
        DialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DialogRecyclerView.setAdapter(filterAreaAdapter1);
        builder1.setView(mview);
        dialog1 = builder1.create();
        Window window = dialog1.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
        }
        dialog1.show();

        filterAreaAdapter1.setOnItemClickListener(v1 ->
                {
                    int Position = DialogRecyclerView.getChildAdapterPosition(v1);
                    cityId = String.valueOf(DialogList.get(Position).id);
                    etSearch.setText(DialogList.get(Position).CityName);
                    dialog1.dismiss();

                    editor.putString(Constant.areaId, String.valueOf(cityId));
                    editor.putString(Constant.areaAr, String.valueOf(DialogList.get(Position).CityName));
                    editor.apply();
                    workersPresenter.GetWorkers(getIntent().getStringExtra(Constant.DepartId));
                    progressBar.setVisibility(View.VISIBLE);
                    linNoData.setVisibility(View.GONE);


                }
        );

    }


    private void loadCities() {
        DialogList.clear();

        if (getAllCountriesResponse != null) {
            for (int country = 0; country < getAllCountriesResponse.getData().size(); country++) {
                //"(+"+countryResponse.getData().get(i).getPhoneCode()+")"+countryResponse.getData().get(i).getName()
                DialogList.add(new areaDialogItem(
                        //"(+"+countryResponse.getData().get(i).getPhoneCode()+")"+countryResponse.getData().get(i).getName()
                        getAllCountriesResponse.getData().get(0).getCities().get(country).getCity()
                        , ""
                        , ""
                        , getAllCountriesResponse.getData().get(0).getCities().get(country).getId()));


            }
        }
    }

    @Override
    public void GetDepartmentsResponse(GetDepartmentsResponse allAdsMapResponse) {

    }

    @Override
    public void GetAllCountries(GetAllCountriesResponse getWorkersResponse) {
        this.getAllCountriesResponse = getWorkersResponse;

    }

    @Override
    public void GetWorkersResponse(GetWorkersResponse getWorkersResponse) {
        linParentTop.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        if (getWorkersResponse.getData() != null) {
            if (getWorkersResponse.getData().size() > 0) {
                WorkersAdapter workersAdapter = new WorkersAdapter(this, getWorkersResponse.getData());
                rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                rv.setAdapter(workersAdapter);
                linParentTop.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                linNoData.setVisibility(View.GONE);
            } else {
                linParentTop.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                linNoData.setVisibility(View.VISIBLE);
            }
        } else {
            linParentTop.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            linNoData.setVisibility(View.VISIBLE);
        }
    }
}