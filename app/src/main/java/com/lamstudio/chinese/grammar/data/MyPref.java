package com.lamstudio.chinese.grammar.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.lamstudio.chinese.grammar.util.Constant;

/**
 * Created by VS9 X64Bit on 29/04/2017.
 */

public class MyPref  {
    public static MyPref instance = new MyPref();

    public MyPref() {
    }
    public static MyPref getInstance(){
        return instance;
    }
    public void saveFullAds(int fullAds, Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Constant.PREF_ADS, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt(Constant.NUMBER_FULL_ADS, fullAds);
        mEditor.commit();
    }
    public void saveVideoAds(int videoAds, Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Constant.PREF_ADS, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt(Constant.NUMBER_VIDEO_ADS, videoAds);
        mEditor.commit();
    }
    public int getFullAds(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Constant.PREF_ADS, Context.MODE_PRIVATE);
        return mSharedPreferences.getInt(Constant.NUMBER_FULL_ADS, 0);
    }
    public int getVideoAds(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Constant.PREF_ADS, Context.MODE_PRIVATE);
        return mSharedPreferences.getInt(Constant.NUMBER_VIDEO_ADS, 0);
    }
    public String getDateAds(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Constant.PREF_ADS, Context.MODE_PRIVATE);
        if(mSharedPreferences != null){
            return mSharedPreferences.getString(Constant.DATE_ADS, "");
        }
        return "";
    }

    public void saveDateAds(String date_ads, Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(Constant.PREF_ADS, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(Constant.DATE_ADS, date_ads);
        mEditor.commit();
    }
}
