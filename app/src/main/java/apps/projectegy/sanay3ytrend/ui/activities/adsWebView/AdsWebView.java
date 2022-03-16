package apps.projectegy.sanay3ytrend.ui.activities.adsWebView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.webkit.WebSettings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.utils.Constant;
import im.delight.android.webview.AdvancedWebView;

public class AdsWebView extends AppCompatActivity implements AdvancedWebView.Listener {

    Boolean flag = false;
    int remainSeconds = 15;
    private AppCompatImageView arrowBackPageTwo;
    @SuppressLint("SetJavaScriptEnabled")
    private AdvancedWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_web_view);


        arrowBackPageTwo = findViewById(R.id.arrow_back_page_two);
        arrowBackPageTwo.setOnClickListener(v -> {

            if (flag) {
                finish();
            } else {
                if (Constant.getLng(this).equals("en")) {
                    Toast.makeText(this, "Waiting for " + remainSeconds + " Sec", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "انتظر " + remainSeconds + " ثانية ", Toast.LENGTH_LONG).show();
                }
            }
        });


        String url = "";

        if (getIntent().getStringExtra("url") != null) {
            url = getIntent().getStringExtra("url");
        }

        new CountDownTimer(15000, 1000) {

            public void onTick(long millisUntilFinished) {
//                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
                remainSeconds = (int) (millisUntilFinished / 1000);
            }

            public void onFinish() {
                flag = true;
            }

        }.start();

        mWebView = findViewById(R.id.webview);
        mWebView.setListener(this, this);
        mWebView.setMixedContentAllowed(false);
        mWebView.loadUrl(url);

//        // Javascript inabled on webview
        mWebView.getSettings().setJavaScriptEnabled(true);
//
//        // Other webview options
//        web.getSettings().setLoadWithOverviewMode(true);
//
//        //webView.getSettings().setUseWideViewPort(true);
//
        //Other webview settings
//        mWebView.setScrollBarStyle(WebView.TELEPHONY_SUBSCRIPTION_SERVICE);
        mWebView.setScrollbarFadingEnabled(true);
//        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setAllowFileAccess(true);
//        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setDesktopMode(true);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

/*
    @Override
    public void onBackPressed() {
//        if (!mWebView.onBackPressed()) { return; }
//        // ...
        super.onBackPressed();
        finish();
    }
*/

    @Override
    public void onBackPressed() {
        if (flag) {
            finish();
        } else {
            if (Constant.getLng(this).equals("en")) {
                Toast.makeText(this, "Waiting for " + remainSeconds + " Sec", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "انتظر " + remainSeconds + " ثانية ", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
    }

    @Override
    public void onPageFinished(String url) {
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
    }

    @Override
    public void onExternalPageRequest(String url) {

    }


}