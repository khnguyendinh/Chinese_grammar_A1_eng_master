package com.lamstudio.chinese.grammar.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.lamstudio.chinese.grammar.R;
import com.lamstudio.chinese.grammar.data.DataApp;
import com.lamstudio.chinese.grammar.object.InforAds;
import com.lamstudio.chinese.grammar.util.Constant;
import com.lamstudio.chinese.grammar.util.NetworkController;
import com.lamstudio.chinese.grammar.volley.AppController;

import org.json.JSONObject;

/**
 * Created by AdministratorPC on 12/30/2015.
 */
public class SplashActivity extends AppCompatActivity {

    public static final String TAG = SplashActivity.class.getSimpleName();
    private boolean isFirstTime;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this, R.style.MyTheme);
        progressDialog.setMessage("Loading");
        progressDialog.getWindow().setGravity(Gravity.BOTTOM);
        progressDialog.show();

        registerReceiver(receiver, new IntentFilter(Constant.ACTION_FINISH));
        setContentView(R.layout.splash_activity);
        WindowManager.LayoutParams params = progressDialog.getWindow().getAttributes();
        final Window window = SplashActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(SplashActivity.this.getResources().getColor(R.color.header_color));
        }
        jsonObjectRequest();
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(Constant.ACTION_FINISH)) {
                ((Activity) context).finish();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mSharedPreferences = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
        isFirstTime = mSharedPreferences.getBoolean(Constant.IS_FIRST_TIME_KEY, true);
        // Logs 'install' and 'app activate' App Events.

        if (isFirstTime && NetworkController.isNetworkAvailable(this)) {
            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.clear();
            mEditor.putBoolean(Constant.IS_FIRST_TIME_KEY, false);
            mEditor.commit();
//            Utils.loadFullScreenAdview(SplashActivity.this);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!SplashActivity.this.isFinishing()) {
                    if(progressDialog != null){
                        progressDialog.dismiss();
                    }
                }
                // Start Main Activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                SplashActivity.this.finish();
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        sendBroadCast(Constant.ACTION_FINISH);
        finish();
    }
    private void sendBroadCast(String action) {
        Intent it = new Intent();
        it.setAction(action);
        sendBroadcast(it);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
    public void jsonObjectRequest(){
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constant.URL_INFOR_ADS, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        InforAds inforAds = new Gson().fromJson(response.toString(),InforAds.class);
                        DataApp.getInstance().inforAds = inforAds;
                        Log.d(TAG, "value number Full = " +DataApp.getInstance().inforAds.getNumfull());
                        Log.d(TAG, "value number video = " +DataApp.getInstance().inforAds.getNumvideo());
                        Log.d(TAG, "value number banner = " +DataApp.getInstance().inforAds.getNumbanner());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                DataApp.getInstance().inforAds = new InforAds(50000,15,3);
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, Constant.TAG_JSON_INFOR_ADS);

    }
}
