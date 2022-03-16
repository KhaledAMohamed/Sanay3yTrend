package apps.projectegy.sanay3ytrend.ui.activities.welcome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.ui.activities.home.Home;
import apps.projectegy.sanay3ytrend.ui.activities.login.Login;
import apps.projectegy.sanay3ytrend.ui.adapters.IntroSliderAdapter;
import apps.projectegy.sanay3ytrend.utils.Constant;
import apps.projectegy.sanay3ytrend.utils.ViewPagerCustomDuration;

public class Welcome extends AppCompatActivity {

    private ViewPagerCustomDuration viewPager;
    private TabLayout indicator;
    private IntroSliderAdapter sliderAdapter;
    private List<Drawable> imageViewPagerList;
    private List<String> titleList;
    private List<String> descList;
    private AppCompatImageView btnPrevious;
    private AppCompatImageView btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Constant.hideStatusBar(this);
        SharedPreferences mSharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, MODE_PRIVATE);

        if (mSharedPreferences.getString(Constant.FlagIntroSlider, "false").equals("true")) {
            if (mSharedPreferences.getString(Constant.TOKEN, "").equals("")) {
                Intent startIntent = new Intent(this, Login.class);
                startActivity(startIntent);
                finish();
            } else {
                Intent startIntent = new Intent(this, Home.class);
                startActivity(startIntent);
                finish();
            }
        } else {
            initViews();
        }

        // initViews();
    }

    private void initViews() {
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);
        viewPager.setScrollDurationFactor(1.5);

        loadSliderData();

        sliderAdapter = new IntroSliderAdapter(this, imageViewPagerList,titleList,descList);
        indicator.setupWithViewPager(viewPager, true);
        viewPager.setAdapter(sliderAdapter);

        Log.d("POSITION_", "initViews: " + viewPager.getCurrentItem());


        if (viewPager.getCurrentItem() == 0) {
            btnPrevious.setVisibility(View.GONE);
        } else {
            btnPrevious.setVisibility(View.VISIBLE);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btnPrevious.setVisibility(View.GONE);
                } else {
                    btnPrevious.setVisibility(View.VISIBLE);
                }

                if (position == sliderAdapter.getCount() - 1) {
                    btnNext.setVisibility(View.VISIBLE);
                    btnPrevious.setVisibility(View.GONE);
//                    btnPrevious.setText(getString(R.string.lets_go));
                    btnNext.setOnClickListener(v ->
                    {
                        Intent openHome = new Intent(Welcome.this, Login.class);
                        finish();
                        openHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(openHome);
                        SharedPreferences mSharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCE, MODE_PRIVATE);
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        //editor.putString(Constant.NAME, signInReponse.getUser().getFullName());
                        editor.putString(Constant.FlagIntroSlider, "true");
                        editor.apply();

                    });
                } else {
                    btnPrevious.setVisibility(View.VISIBLE);
                    // btnPrevious.setText(getString(R.string.previous));

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        btnNext.setOnClickListener(v -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        });

        btnPrevious.setOnClickListener(v -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        });

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void loadSliderData() {
        imageViewPagerList = new ArrayList<>();
        imageViewPagerList.add(getResources().getDrawable(R.drawable.welcome_1));
        imageViewPagerList.add(getResources().getDrawable(R.drawable.welcome_2));
        imageViewPagerList.add(getResources().getDrawable(R.drawable.welcome_3));
        // imageViewPagerList.add(getResources().getDrawable(R.drawable.ic_img_three_slider_bg));
        descList = new ArrayList<>();
        descList.add("Lorem Epsom is a virtual model that is placed in the designs to be presented to the customer to imagine how to put the texts in the designs, whether they are printed brochures or flyer designs, for example.");
        descList.add("Lorem Epsom is a virtual model that is placed in the designs to be presented to the customer to imagine how to put the texts in the designs, whether they are printed brochures or flyer designs, for example.");
        descList.add("Lorem Epsom is a virtual model that is placed in the designs to be presented to the customer to imagine how to put the texts in the designs, whether they are printed brochures or flyer designs, for example.");

        titleList = new ArrayList<>();
        titleList.add("تطبيق صنايعي ترند");
        titleList.add("تطبيق صنايعي ترند");
        titleList.add("تطبيق صنايعي ترند");
    }
}