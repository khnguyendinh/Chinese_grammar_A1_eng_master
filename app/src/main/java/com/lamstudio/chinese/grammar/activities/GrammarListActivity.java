package com.lamstudio.chinese.grammar.activities;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.lamstudio.chinese.grammar.R;
import com.lamstudio.chinese.grammar.adapter.GrammarTitleAdapter;
import com.lamstudio.chinese.grammar.db.GrammarDBHelper;
import com.lamstudio.chinese.grammar.object.GrammarObj;
import com.lamstudio.chinese.grammar.util.NetworkController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AdministratorPC on 1/1/2016.
 */
public class GrammarListActivity extends AppCompatActivity {
    private static final String TAG = GrammarListActivity.class.getSimpleName();
    private Toolbar toolbar;
    private GrammarDBHelper mGHelper;
    private TextView mGrammarObjTitle;
    private GrammarObj grammarObj;
    private HashMap<Integer, GrammarObj> mMap;
    private ArrayList<GrammarObj> grammarObjs;
    private GrammarTitleAdapter mAdapter;
    private RecyclerView mListGrammarObjTitle;
    private AdView mBannerAdView;
    private RewardedVideoAd mAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grammar_activity);
        initView();
        // Use an activity context to get the rewarded video instance.
//        mAd = MobileAds.getRewardedVideoAdInstance(this);
//        mAd.setRewardedVideoAdListener((RewardedVideoAdListener) this);
//        loadRewardedVideoAd();
        // get data from database
        mGHelper = new GrammarDBHelper(GrammarListActivity.this);
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
        mMap = new HashMap<>();
        mMap = mGHelper.selectAllGrammarObj();
        grammarObjs = new ArrayList<>();
//        for (int key : mMap.keySet()) {
//            Log.d("khoand ","key "+key);
//            grammarObjs.add(mMap.get(key));
//        }
        for (int i = 1; i < mMap.size() + 1; i++) {
            grammarObjs.add(mMap.get(i));
        }

        mAdapter = new GrammarTitleAdapter(grammarObjs, GrammarListActivity.this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(GrammarListActivity.this,1);
        mListGrammarObjTitle.setLayoutManager(layoutManager);
        mListGrammarObjTitle.setAdapter(mAdapter);


    }
    private void loadRewardedVideoAd() {
        mAd.loadAd(getString(R.string.video_full_ad_id), new AdRequest.Builder().build());
    }
    private void loadAdView(AdView mAdView) {
        Log.e(TAG, "==========> load Adview");
        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void initView() {
        mGrammarObjTitle = (TextView) findViewById(R.id.txt_grammar_title);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        mListGrammarObjTitle = (RecyclerView) findViewById(R.id.list_grammar_obj_title);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.my_awesome_color));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBannerAdView = (AdView) findViewById(R.id.ad_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkController.isNetworkAvailable(this)) {
            loadAdView(mBannerAdView);
        }
    }
}
