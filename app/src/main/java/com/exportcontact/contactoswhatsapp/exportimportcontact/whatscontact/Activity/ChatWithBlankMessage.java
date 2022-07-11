package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Advertisement;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.CommonStuffs;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.SharedPreferenceClass;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.SingleClickListener;

public class ChatWithBlankMessage extends AppCompatActivity {
    public CheckBox chk_msg;
    public Button mButtonSend;
    public EditText mNoEdtSpace;
    public String message;
    public int putSpace = 1;
    public RelativeLayout relativeBlankChat;
    public Intent waIntent;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_chat_with_blank_msg);
        SetStatusBar();

        LinearLayout ll_banner = findViewById(R.id.ll_banner);
        Advertisement.showBanner(ChatWithBlankMessage.this, ll_banner);
        ImageView la_back = findViewById(R.id.la_back);

        RelativeLayout ic_include = findViewById(R.id.ic_include);
        LinearLayout ll_edt_msg = findViewById(R.id.ll_edt_msg);

        la_back.setOnClickListener(v -> onBackPressed());
        if (!isFinishing()) {
            init();
        }

        if (Preference.getBooleanTheme(false)) {
            setStatusBarTheme();
            relativeBlankChat.setBackgroundColor(ContextCompat.getColor(ChatWithBlankMessage.this, R.color.darkBlack));
            ic_include.setBackgroundColor(ContextCompat.getColor(ChatWithBlankMessage.this, R.color.darkBlack));
            ll_edt_msg.setBackground(ContextCompat.getDrawable(ChatWithBlankMessage.this, R.drawable.shape_black));
            mNoEdtSpace.setBackground(ContextCompat.getDrawable(ChatWithBlankMessage.this, R.drawable.shape_black_dark));
            chk_msg.setTextColor(ContextCompat.getColor(ChatWithBlankMessage.this, R.color.colorWhite));
            mNoEdtSpace.setTextColor(ContextCompat.getColor(ChatWithBlankMessage.this, R.color.colorWhite));

        }
    }

    private void SetStatusBar() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        if (Build.VERSION.SDK_INT >= 23) {
            window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.card_color));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorWhite));
        } else if (Build.VERSION.SDK_INT == 21 || Build.VERSION.SDK_INT == 22) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.card_color));
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
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.darkBlack));
    }

    private void init() {
        mNoEdtSpace = findViewById(R.id.noEdtChar);
        mButtonSend = findViewById(R.id.btnsend);
        relativeBlankChat = findViewById(R.id.relativeBlankChat);
        chk_msg = findViewById(R.id.chk_msg);

        mButtonSend.setOnClickListener(new SingleClickListener() {
            public void performClick(View view) {
                sendBlankMSG();
            }
        });

        if (SharedPreferenceClass.getBoolean(this, "chkbox_val", true)) {
            chk_msg.setChecked(true);
        } else {
            chk_msg.setChecked(false);
        }

        chk_msg.setOnCheckedChangeListener((compoundButton, z) -> {
            if (z) {
                SharedPreferenceClass.setBoolean(ChatWithBlankMessage.this, "chkbox_val", true);
            } else {
                SharedPreferenceClass.setBoolean(ChatWithBlankMessage.this, "chkbox_val", false);
            }
        });

        mNoEdtSpace.setSelection(mNoEdtSpace.getText().length());
    }

    public void sendBlankMSG() {
        if (mNoEdtSpace.getText().toString().equals("")) {
            CommonStuffs.hideKeyboardFrom(this, relativeBlankChat);
            CommonStuffs.SnackBar(relativeBlankChat, getResources().getString(R.string.empty_msg), -1);
        } else if (mNoEdtSpace.getText().toString().equals("0")) {
            CommonStuffs.hideKeyboardFrom(this, relativeBlankChat);
            CommonStuffs.SnackBar(relativeBlankChat, getResources().getString(R.string.empty_msg), -1);
        } else if (mNoEdtSpace.getText().toString().equals("00")) {
            CommonStuffs.hideKeyboardFrom(this, relativeBlankChat);
            CommonStuffs.SnackBar(relativeBlankChat, getResources().getString(R.string.empty_msg), -1);
        } else if (mNoEdtSpace.getText().toString().equals("000")) {
            CommonStuffs.hideKeyboardFrom(this, relativeBlankChat);
            CommonStuffs.SnackBar(relativeBlankChat, getResources().getString(R.string.empty_msg), -1);
        } else if (mNoEdtSpace.getText().toString().equals("0000")) {
            CommonStuffs.hideKeyboardFrom(this, relativeBlankChat);
            CommonStuffs.SnackBar(relativeBlankChat, getResources().getString(R.string.empty_msg), -1);
        } else if (mNoEdtSpace.getText().toString().equals("00000")) {
            CommonStuffs.hideKeyboardFrom(this, relativeBlankChat);
            CommonStuffs.SnackBar(relativeBlankChat, getResources().getString(R.string.empty_msg), -1);
        } else {
            if (Integer.parseInt(mNoEdtSpace.getText().toString()) < 5000) {
                putSpace = Integer.parseInt(mNoEdtSpace.getText().toString());
            } else {
                putSpace = 2000;
            }
            SpaceText();
            try {
                Intent intent = new Intent("android.intent.action.SEND");
                waIntent = intent;
                intent.setType("text/plain");
                if (chk_msg.isChecked()) {
                    message += getResources().getString(R.string.app_link);
                }
                if (appInstalledOrNot("com.whatsapp")) {
                    if (appInstalledOrNot("com.whatsapp.w4b")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(getResources().getString(R.string.wa_wb_dialouge_msg));
                        builder.setPositiveButton("Go to Whatsapp", (dialogInterface, i) -> {
                            try {
                                waIntent.setPackage("com.whatsapp");
                                waIntent.putExtra("android.intent.extra.TEXT", message);
                                startActivity(Intent.createChooser(waIntent, "Share with"));
                            } catch (Exception ignored) {
                            }
                        });
                        builder.setNegativeButton("Go to Whatsapp Business", (dialogInterface, i) -> {
                            try {
                                waIntent.setPackage("com.whatsapp.w4b");
                                waIntent.putExtra("android.intent.extra.TEXT", message);
                                startActivity(Intent.createChooser(waIntent, "Share with"));
                            } catch (Exception ignored) {
                            }
                        });
                        builder.setNeutralButton("Cancel", null);
                        builder.create().show();
                        return;
                    }
                }
                if (appInstalledOrNot("com.whatsapp.w4b")) {
                    waIntent.setPackage("com.whatsapp.w4b");
                    waIntent.putExtra("android.intent.extra.TEXT", message);
                    startActivity(Intent.createChooser(waIntent, "Share with"));
                } else if (appInstalledOrNot("com.whatsapp")) {
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra("android.intent.extra.TEXT", message);
                    startActivity(Intent.createChooser(waIntent, "Share with"));
                }
            } catch (Exception unused) {
                CommonStuffs.SnackBar(relativeBlankChat, "WhatsApp not Installed", -1);
            }
        }
    }

    private void SpaceText() {
        message = "";
        for (int i = 0; i < putSpace; i++) {
            message += " ";
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