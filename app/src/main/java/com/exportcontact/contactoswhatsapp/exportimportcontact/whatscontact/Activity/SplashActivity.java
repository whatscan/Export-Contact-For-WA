package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Advertisement;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.BuildConfig;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;



@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    TextView tv_version, tv_name, tv_name_1;
    RelativeLayout rl_splash;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.splash_activty);
        setStatusBar();
        rl_splash = findViewById(R.id.rl_splash);
        tv_name = findViewById(R.id.tv_name);
        tv_name_1 = findViewById(R.id.tv_name_1);
        tv_version = findViewById(R.id.tv_version);

        Advertisement.preLoadAds(SplashActivity.this);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent i = new Intent(SplashActivity.this, TabActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 4 * 1000);


        tv_version.setText(Html.fromHtml("App Version" + " : " + BuildConfig.VERSION_NAME));

        if (Preference.getBooleanTheme(false)) {
            String text = "<font color=#6DA188>Send</font> <font color=#FFFFFF>More,</font> <font color=#6DA188>Grow</font>  <font color=#FFFFFF>More</font>";
            tv_name_1.setText(Html.fromHtml(text));

        } else {
            String text = "<font color=#6DA188>Send</font> <font color=#000000>More,</font> <font color=#6DA188>Grow</font>  <font color=#000000>More</font>";
            tv_name_1.setText(Html.fromHtml(text));
        }


        if (Preference.getBooleanTheme(false)) {
            Window window = getWindow();
            tv_name.setTextColor(ContextCompat.getColor(SplashActivity.this, R.color.colorWhite));
            tv_version.setTextColor(ContextCompat.getColor(SplashActivity.this, R.color.colorWhite));
            rl_splash.setBackgroundColor(ContextCompat.getColor(SplashActivity.this, R.color.darkBlack));
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorBlack));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorBlack));
        } else {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorWhite));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorWhite));
            rl_splash.setBackgroundColor(ContextCompat.getColor(SplashActivity.this, R.color.colorWhite));
            tv_name.setTextColor(ContextCompat.getColor(SplashActivity.this, R.color.colorBlack));
            tv_version.setTextColor(ContextCompat.getColor(SplashActivity.this, R.color.colorBlack));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 23) {
            window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorWhite));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorWhite));
        } else if (Build.VERSION.SDK_INT == 21 || Build.VERSION.SDK_INT == 22) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorTools));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorTools));
        } else {
            window.clearFlags(0);
        }
    }
}