package com.lamstudio.chinese.grammar.data;

import com.lamstudio.chinese.grammar.object.InforAds;

/**
 * Created by VS9 X64Bit on 29/04/2017.
 */

public class DataApp {
    public InforAds inforAds;
    public int num_Full_Ads = 0;
    public int num_Video_Ads = 0;
    public String date_Ads ;
    public boolean justShowVideo = false;
    public static DataApp instance = new DataApp();

    public DataApp() {
    }

    public static DataApp getInstance() {
        return instance;
    }
}
