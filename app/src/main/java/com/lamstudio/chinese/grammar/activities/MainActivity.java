package com.lamstudio.chinese.grammar.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lamstudio.chinese.grammar.R;
import com.lamstudio.chinese.grammar.data.DataApp;
import com.lamstudio.chinese.grammar.data.MyPref;
import com.lamstudio.chinese.grammar.util.Constant;
import com.lamstudio.chinese.grammar.util.NetworkController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AdministratorPC on 1/1/2016.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String TAG = MainActivity.class.getSimpleName();
    private AdView mBannerAdView;
    //add mainbranch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.img_grammar).setOnClickListener(this);
        findViewById(R.id.btn_grammar).setOnClickListener(this);
        findViewById(R.id.img_rate).setOnClickListener(this);
        findViewById(R.id.btn_rate).setOnClickListener(this);
        findViewById(R.id.img_store).setOnClickListener(this);
        findViewById(R.id.btn_store).setOnClickListener(this);
        findViewById(R.id.img_share).setOnClickListener(this);
        findViewById(R.id.btn_share_app).setOnClickListener(this);


        mBannerAdView = (AdView) findViewById(R.id.ad_view);
        if (NetworkController.isNetworkAvailable(this)) {
            loadAdView(mBannerAdView);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_grammar:
                Intent intent1 = new Intent(MainActivity.this, GrammarListActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_grammar:
                Intent intent2 = new Intent(MainActivity.this, GrammarListActivity.class);
                startActivity(intent2);
                break;
            case R.id.img_rate:
                rateMyApp();
                break;
            case R.id.btn_rate:
                rateMyApp();
                break;
            case R.id.img_store:
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.PLAYSTORE_URL));
                startActivity(webIntent);
                break;
            case R.id.btn_store:
                Intent webIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.PLAYSTORE_URL));
                startActivity(webIntent1);
                break;
            case R.id.img_share:
                shareApp();
            case R.id.btn_share_app:
                shareApp();
                break;
            default:
                break;
        }
    }

    public void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, Constant.PLAYSTORE_URL);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "share"));

    }

    public void rateMyApp() {
        Uri uri = Uri.parse("market://details?id=" + getApplication().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplication().getPackageName())));
        }
    }

    private void showRewardedVideo() {

    }



    @Override
    protected void onResume() {
        super.onResume();
        DataApp.getInstance().date_Ads = MyPref.getInstance().getDateAds(MainActivity.this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDateandTime = sdf.format(new Date());
        if( !(DataApp.getInstance().date_Ads.equals(currentDateandTime))){
            //TODO khac ngay thi reset lai gia tri ads
            MyPref.getInstance().saveFullAds(0,this);
            MyPref.getInstance().saveVideoAds(0,this);
            MyPref.getInstance().saveDateAds(currentDateandTime.toString(),this);

        }
        DataApp.getInstance().num_Full_Ads = MyPref.getInstance().getFullAds(this);
        DataApp.getInstance().num_Video_Ads =  MyPref.getInstance().getVideoAds(this);
        if (NetworkController.isNetworkAvailable(this)) {
            loadAdView(mBannerAdView);
        }
//        SharedPreferences settings = getSharedPreferences(Constant.PREF_ADS, Context.MODE_PRIVATE);
//        settings.edit().clear().commit();
    }
    private void loadAdView(AdView mAdView) {
        Log.e(TAG, "==========> load Adview");
        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MyPref.getInstance().saveFullAds(DataApp.getInstance().num_Full_Ads,this);
        MyPref.getInstance().saveVideoAds(DataApp.getInstance().num_Video_Ads,this);
    }
}
