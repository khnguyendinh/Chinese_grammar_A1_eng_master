package com.lamstudio.chinese.grammar.util;

import android.app.Activity;
import android.util.Log;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class Utils {

    public static final String TAG = "utils";

    public static void loadFullScreenAdview(Activity activity, String adsUnit) {
        Log.e(TAG, "==========> load full Adview");
        final InterstitialAd mAdView;
        mAdView = new InterstitialAd(activity);
        mAdView.setAdUnitId(adsUnit);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.e(TAG, "=========> loaded");
                super.onAdLoaded();
                mAdView.show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.e(TAG, "=========> opened");
            }
        });
    }
}
