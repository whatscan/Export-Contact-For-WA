package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Advertisement;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet.Contect;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.adpter.MyContactListAdpter;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.CommonStuffs;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.DatabaseHandler;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatWithNumber extends AppCompatActivity implements View.OnClickListener {
    public static EditText moEdMobNum;
    public MyContactListAdpter adapter;
    public List<Contect> mArrList_contect = new ArrayList();
    RelativeLayout relativeChat, rl_toolbar;
    LinearLayout ll_country;
    CardView card_country;
    private Button mButtonOpenChat;
    private CountryCodePicker mCountryCodePicker;
    private DatabaseHandler mDatabaseHandler;
    private RecyclerView mRecycler_contect;
    private RelativeLayout mRelativeChat;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_chat_with_num);
        SetStatusBar();


        LinearLayout ll_banner = findViewById(R.id.ll_banner);
        Advertisement.showBanner(ChatWithNumber.this, ll_banner);

        mDatabaseHandler = new DatabaseHandler(this);


        relativeChat = findViewById(R.id.relativeChat);
        rl_toolbar = findViewById(R.id.ic_include);
        card_country = findViewById(R.id.card_country);
        ll_country = findViewById(R.id.ll_country);
        if (!isFinishing()) {
            init();
        }


        if (Preference.getBooleanTheme(false)) {
            setStatusBarTheme();
            relativeChat.setBackgroundColor(ContextCompat.getColor(ChatWithNumber.this, R.color.darkBlack));
            rl_toolbar.setBackgroundColor(ContextCompat.getColor(ChatWithNumber.this, R.color.darkBlack));
            ll_country.setBackground(ContextCompat.getDrawable(ChatWithNumber.this, R.drawable.shape_black_dark));
            moEdMobNum.setBackground(ContextCompat.getDrawable(ChatWithNumber.this, R.drawable.shape_black_dark));
            card_country.setCardBackgroundColor(ContextCompat.getColor(ChatWithNumber.this, R.color.colorShape));
            moEdMobNum.setTextColor(ContextCompat.getColor(ChatWithNumber.this, R.color.colorWhite));

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
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
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
        getWindow().setStatusBarColor(ContextCompat.getColor(ChatWithNumber.this, R.color.darkBlack));
    }

    private void init() {
        moEdMobNum = findViewById(R.id.EdMobNum);
        mButtonOpenChat = findViewById(R.id.btnOpenChat);
        mCountryCodePicker = findViewById(R.id.countrycode);
        mRelativeChat = findViewById(R.id.relativeChat);
        mRecycler_contect = findViewById(R.id.recycler_contect);
        onClickEvents();
    }

    private void onClickEvents() {
        if (!isFinishing()) {
            mButtonOpenChat.setOnClickListener(this);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnOpenChat) {
            if (moEdMobNum.getText().toString().trim().equals("")) {
                CommonStuffs.hideKeyboardFrom(this, mRelativeChat);
                CommonStuffs.SnackBar(view, "Please enter valid number", -1);
            } else if (moEdMobNum.getText().toString().length() < 6 || moEdMobNum.getText().toString().length() > 15) {
                CommonStuffs.hideKeyboardFrom(this, mRelativeChat);
                CommonStuffs.SnackBar(view, "Please enter valid number", -1);
            } else {
                openWhatsApp();
                moEdMobNum.setText("");
            }
        }
    }

    private void openWhatsApp() {
        String obj = moEdMobNum.getText().toString();
        if (obj.matches("\\d+")) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("whatsapp://send?phone=+" + String.valueOf(mCountryCodePicker.getSelectedCountryCodeAsInt()) + obj)));
                if (contains(mArrList_contect, obj)) {
                    System.out.println("Data Exist(s)");
                } else {
                    mDatabaseHandler.addContact(new Contect(obj));
                }
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean contains(List<Contect> list, String str) {
        for (Contect contect : list) {
            if (contect.getContect().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            getAll();
        }
    }

    public void getAll() {
        mArrList_contect.clear();
        mArrList_contect.addAll(mDatabaseHandler.getAllContacts());
        Collections.reverse(mArrList_contect);
        if (mArrList_contect.size() > 0) {
            adapter = new MyContactListAdpter(mArrList_contect, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(1);
            mRecycler_contect.setLayoutManager(linearLayoutManager);
            mRecycler_contect.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}