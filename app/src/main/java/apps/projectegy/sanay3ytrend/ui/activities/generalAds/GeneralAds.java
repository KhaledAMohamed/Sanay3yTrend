package apps.projectegy.sanay3ytrend.ui.activities.generalAds;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.data.models.generalAds.GeneralAdsResponse;
import apps.projectegy.sanay3ytrend.ui.adapters.AdsAdapter;

public class GeneralAds extends AppCompatActivity implements GeneralAdsInterface {

    GeneralAdsPresenter generalAdsPresenter;
    private LinearLayoutCompat linBack;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private LinearLayoutCompat linNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_ads);

        linBack = (LinearLayoutCompat) findViewById(R.id.lin_back);
        rv = (RecyclerView) findViewById(R.id.rv);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        linNoData = (LinearLayoutCompat) findViewById(R.id.lin_no_data);


        linBack.setOnClickListener(v -> finish());

        generalAdsPresenter = new GeneralAdsPresenter(this, this);
        generalAdsPresenter.GetGeneralAds();
        progressBar.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
        linNoData.setVisibility(View.GONE);

    }

    @Override
    public void GeneralAdsResponse(GeneralAdsResponse generalAdsResponse) {

        if (generalAdsResponse.getData() != null) {
            if (generalAdsResponse.getData().size() > 0) {
                progressBar.setVisibility(View.GONE);
                linNoData.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);

                AdsAdapter adsAdapter = new AdsAdapter(this, generalAdsResponse.getData());
                rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                rv.setAdapter(adsAdapter);
            } else {
                progressBar.setVisibility(View.GONE);
                linNoData.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);

            }
        } else {
            progressBar.setVisibility(View.GONE);
            linNoData.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);

        }
    }
}