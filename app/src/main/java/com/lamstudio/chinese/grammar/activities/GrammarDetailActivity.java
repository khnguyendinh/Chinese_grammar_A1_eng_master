package com.lamstudio.chinese.grammar.activities;

import android.content.Intent;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.lamstudio.chinese.grammar.R;
import com.lamstudio.chinese.grammar.data.DataApp;
import com.lamstudio.chinese.grammar.db.GrammarDBHelper;
import com.lamstudio.chinese.grammar.db.GrammarLikeHelper;
import com.lamstudio.chinese.grammar.object.GrammarObj;
import com.lamstudio.chinese.grammar.util.Constant;
import com.lamstudio.chinese.grammar.util.NetworkController;
import com.lamstudio.chinese.grammar.util.Utils;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by AdministratorPC on 12/30/2015.
 */
public class GrammarDetailActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private static final String TAG = "DetailActivity_111";
    private Toolbar toolbar;
    private HashMap<Integer, GrammarObj> mMap;
    private GrammarDBHelper mGHelper;
    private TextView mGrammarObjTitle;
    private GrammarObj grammarObj;
    private int grId;
    private GrammarLikeHelper mLikeHelper;
    private AdView mBannerAds;
    InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grammar_detail_activity);

        initView();
        grId = getIntent().getIntExtra(Constant.KEY_ID, 0);
        mGHelper = new GrammarDBHelper(GrammarDetailActivity.this);
        displayTitle();
        Log.d(TAG, "onCreate: ");
    }



    public void initView() {
        mGrammarObjTitle = (TextView) findViewById(R.id.txt_grammar_title);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(GrammarDetailActivity.this, android.R.color.white));
        toolbar.setBackgroundColor(ContextCompat.getColor(GrammarDetailActivity.this, R.color.my_awesome_color));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        webView = (WebView) findViewById(R.id.web_grammar_detail);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mBannerAds = (AdView) findViewById(R.id.ads_view);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(this.getString(R.string.full_ad_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                showFullAds();
            }
        });
        showAds();
    }

    private void loadAdView(AdView mAdView) {
        Log.e(TAG, "==========> load Adview");
        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Utils.loadFullScreenAdview(GrammarDetailActivity.this, "");
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, grammarObj.getDetail());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "share"));
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        GrammarDetailActivity.this.finish();
    }

    public void displayTitle() {
        try {
            mGHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mGHelper.openDataBase();
        } catch (SQLException sqlE) {
            throw sqlE;
        }
        Log.e(TAG, "--------Grammar id got ---> " + grId);
        grammarObj = mGHelper.selectGrammarObjById(grId);
        mGrammarObjTitle.setText(this.getString(R.string.lesson)+" " + grId + " " + grammarObj.getTitle());
        webView.loadUrl("file:///android_asset/grammar/grammar_" + grId + ".html");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webView.loadUrl("file:///android_asset/grammar/grammar_" + grId + ".HTML");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    webView.loadUrl("file:///android_asset/grammar/grammar_" + grId + ".HTML");
                }

            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkController.isNetworkAvailable(this)) {
            loadAdView(mBannerAds);
        }
    }


    @Override
    protected void onDestroy() {
        if (mGHelper != null) {
            mGHelper.close();
        }
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private void loadFullAd() {
        if (NetworkController.isNetworkAvailable(this)) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                    .build();
            mInterstitialAd.loadAd(adRequest);
        }
    }
    private void showRewardedVideo() {
        Log.d(TAG, "showRewardedVideo: ");
        if (DataApp.getInstance().num_Video_Ads < DataApp.getInstance().inforAds.getNumvideo()) {
            DataApp.getInstance().num_Video_Ads++;
            if (mRewardedVideoAd.isLoaded()) {
                DataApp.getInstance().justShowVideo = true;
                mRewardedVideoAd.show();
            }
        }
    }
    private void loadRewardedVideoAd() {
        if (NetworkController.isNetworkAvailable(this)) {
            if (!mRewardedVideoAd.isLoaded()) {
                mRewardedVideoAd.loadAd(getString(R.string.video_full_ad_id), new AdRequest.Builder().build());
                DataApp.getInstance().justShowVideo = true;
            }
        }
    }
    private void showFullAds() {
        if (DataApp.getInstance().num_Full_Ads < DataApp.getInstance().inforAds.getNumfull()) {
            DataApp.getInstance().num_Full_Ads++;
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
    }
    @Override
    public void onRewardedVideoAdLoaded() {
        showRewardedVideo();
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }
    public void showAds(){
        if((DataApp.getInstance().num_Full_Ads  == 5 && DataApp.getInstance().num_Video_Ads == 0) ||
                DataApp.getInstance().num_Full_Ads  == 5 * DataApp.getInstance().num_Video_Ads){
            loadRewardedVideoAd();
        }else {
            if(DataApp.getInstance().justShowVideo == false){
                loadFullAd();
            }else {
                DataApp.getInstance().justShowVideo = false;
            }
        }
    }
}
