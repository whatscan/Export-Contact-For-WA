package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Advertisement;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.CommonStuffs;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.SingleClickListener;

public class ChatWithMsg extends AppCompatActivity {
    public Button mButtonSendMsg;
    public EditText mEdtMsg;
    public RelativeLayout mRelativeMsg, ic_include;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_chat_with_msg);
        SetStatusBar();


        FrameLayout fl_native = findViewById(R.id.fl_native);
        Advertisement.shoNativeAds(ChatWithMsg.this, fl_native);

        ic_include = findViewById(R.id.ic_include);

        if (!isFinishing()) {
            init();
        }

        if (Preference.getBooleanTheme(false)) {
            setStatusBarTheme();
            mRelativeMsg.setBackgroundColor(ContextCompat.getColor(ChatWithMsg.this, R.color.darkBlack));
            ic_include.setBackgroundColor(ContextCompat.getColor(ChatWithMsg.this, R.color.darkBlack));
            mEdtMsg.setBackground(ContextCompat.getDrawable(ChatWithMsg.this, R.drawable.shape_black));

        }

        ImageView la_back = findViewById(R.id.la_back);
        la_back.setOnClickListener(v -> onBackPressed());
    }

    private void SetStatusBar() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        if (Build.VERSION.SDK_INT >= 23) {
            window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.card_color));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.card_color));
        } else if (Build.VERSION.SDK_INT == 21 || Build.VERSION.SDK_INT == 22) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorBlack));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorBlack));
        } else {
            window.clearFlags(0);
        }
    }

    public void setStatusBarTheme() {
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(ChatWithMsg.this, R.color.darkBlack));
    }

    private void init() {
        mEdtMsg = findViewById(R.id.edtMsg);
        mButtonSendMsg = findViewById(R.id.btnsendMsg);
        mRelativeMsg = findViewById(R.id.relativeMsg);

        mButtonSendMsg.setOnClickListener(new SingleClickListener() {
            public void performClick(View view) {
                sendMSG();
            }
        });
    }

    public void sendMSG() {
        if (mEdtMsg.getText().toString().trim().equals("")) {
            CommonStuffs.hideKeyboardFrom(this, mRelativeMsg);
            CommonStuffs.SnackBar(mRelativeMsg, "Please enter message", -1);
        } else if (appInstalledOrNot("com.whatsapp") && appInstalledOrNot("com.whatsapp.w4b")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.wa_wb_dialouge_msg));
            builder.setPositiveButton("Go to Whatsapp", (dialogInterface, i) -> {
                try {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("text/plain");
                    intent.setPackage("com.whatsapp");
                    intent.putExtra("android.intent.extra.TEXT", mEdtMsg.getText().toString());
                    startActivity(Intent.createChooser(intent, "Share using"));
                } catch (Exception ignored) {
                }
            });
            builder.setNegativeButton("Go to Whatsapp Business", (dialogInterface, i) -> {
                try {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("text/plain");
                    intent.setPackage("com.whatsapp.w4b");
                    intent.putExtra("android.intent.extra.TEXT", mEdtMsg.getText().toString());
                    startActivity(Intent.createChooser(intent, "Share using"));
                } catch (Exception ignored) {
                }
            });
            builder.setNeutralButton("Cancel", null);
            builder.create().show();
        } else if (appInstalledOrNot("com.whatsapp.w4b")) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.setPackage("com.whatsapp.w4b");
            intent.putExtra("android.intent.extra.TEXT", mEdtMsg.getText().toString());
            startActivity(Intent.createChooser(intent, "Share using"));
        } else if (appInstalledOrNot("com.whatsapp")) {
            Intent intent2 = new Intent("android.intent.action.SEND");
            intent2.setType("text/plain");
            intent2.setPackage("com.whatsapp");
            intent2.putExtra("android.intent.extra.TEXT", mEdtMsg.getText().toString());
            startActivity(Intent.createChooser(intent2, "Share using"));
        } else {
            Toast.makeText(this, "LLLLL", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean appInstalledOrNot(String str) {
        try {
            getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }
}
