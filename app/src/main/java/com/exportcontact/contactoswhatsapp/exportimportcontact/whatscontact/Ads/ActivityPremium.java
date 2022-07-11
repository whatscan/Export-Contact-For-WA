package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads;


import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetailsParams;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity.TabActivity;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityPremium extends AppCompatActivity {
    RelativeLayout rl_classic, rl_premium, rl_master, bt_subscribe, rl_premium_main, rl_premium_plan;
    public TextView duration_classic, duration_premium, duration_master, price_classic, price_premium, price_master;
    TextView tv_classic, tv_premium, tv_master;
    String strWeek, strMonth, strYear;
    TextView text_title, text_sub_title, faq_title, chat_us_title;
    RelativeLayout rl_bottom;
    BillingClient billingClient;
    int purchase_type = 0;

    PurchasesUpdatedListener purchasesUpdatedListener = (billingResult, purchases) -> {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_plan);
        setStatusBar();
        strWeek = "weekly_key";
        strMonth = "monthly_key";
        strYear = "yearly_key";

        RelativeLayout rl_premium_main = findViewById(R.id.rl_premium_main);
        rl_premium_plan = findViewById(R.id.rl_premium_plan);
        RelativeLayout rl_faq = findViewById(R.id.rl_faq);
        RelativeLayout rl_chat_us = findViewById(R.id.rl_chat_us);
        ImageView la_back = findViewById(R.id.la_back);

        rl_bottom = findViewById(R.id.rl_bottom);

        text_title = findViewById(R.id.text_title);
        text_sub_title = findViewById(R.id.text_sub_title);
        faq_title = findViewById(R.id.faq_title);
        chat_us_title = findViewById(R.id.chat_us_title);


        rl_classic = findViewById(R.id.rl_classic);
        rl_premium = findViewById(R.id.rl_premium);
        rl_master = findViewById(R.id.rl_master);
        bt_subscribe = findViewById(R.id.bt_subscribe);

        duration_classic = findViewById(R.id.duration_classic);
        duration_premium = findViewById(R.id.duration_premium);
        duration_master = findViewById(R.id.duration_master);
        price_classic = findViewById(R.id.price_classic);
        price_premium = findViewById(R.id.price_premium);
        price_master = findViewById(R.id.price_master);

        tv_premium = findViewById(R.id.tv_premium);
        tv_classic = findViewById(R.id.tv_classic);
        tv_master = findViewById(R.id.tv_master);
        setPrice();
        if (Preference.getBooleanTheme(false)) { // Dark
            setStatusBarTheme();
            rl_premium_plan.setBackgroundColor(ContextCompat.getColor(ActivityPremium.this, R.color.darkBlack));
            rl_premium_main.setBackgroundColor(ContextCompat.getColor(ActivityPremium.this, R.color.darkBlack));
            text_title.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
            text_sub_title.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
            faq_title.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
            chat_us_title.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
            rl_chat_us.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.shape_black));
            rl_bottom.setBackgroundColor(ContextCompat.getColor(ActivityPremium.this, R.color.darkBlack_1));

            rl_faq.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.shape_black));
        } else { // Light
            setStatusBar();
            rl_premium_plan.setBackgroundColor(ContextCompat.getColor(ActivityPremium.this, R.color.card_color));
            rl_premium_main.setBackgroundColor(ContextCompat.getColor(ActivityPremium.this, R.color.card_color));
            text_title.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
            text_sub_title.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
            faq_title.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
            chat_us_title.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
            rl_chat_us.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.shape_white));
            rl_faq.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.shape_white));
            rl_bottom.setBackgroundColor(ContextCompat.getColor(ActivityPremium.this, R.color.card_color));

        }

        rl_faq.setOnClickListener(v -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorTools));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage("com.android.chrome");
            customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            customTabsIntent.launchUrl(ActivityPremium.this, Uri.parse("https://whatscan.co/faqs/"));
        });

        rl_chat_us.setOnClickListener(v -> {
            try {
                if (Constant.whatsappInstalledOrNot(ActivityPremium.this)) {
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91" + "7203927842") + "@s.whatsapp.net");
                    startActivity(sendIntent);
                } else {
                    Uri uri = Uri.parse("market://details?id=com.whatsapp");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    Toast.makeText(ActivityPremium.this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                    startActivity(goToMarket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        la_back.setOnClickListener(v -> onBackPressed());


        rl_classic.setOnClickListener(v -> {
            purchase_type = 1;
            rl_classic.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom_select));

            if (Preference.getBooleanTheme(false)) {
                rl_premium.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom_1));
                rl_master.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom_1));
                tv_premium.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                tv_master.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                price_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                duration_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                price_premium.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                duration_premium.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                tv_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));

            } else {
                rl_premium.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom));
                rl_master.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom));
                tv_premium.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                tv_master.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                price_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                duration_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                price_premium.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                duration_premium.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                tv_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
            }
        });


        rl_premium.setOnClickListener(v -> {
            purchase_type = 2;
            rl_premium.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom_select1));
            if (Preference.getBooleanTheme(false)) {
                rl_classic.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom_1));
                rl_master.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom_1));
                tv_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                tv_master.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));

                price_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                duration_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                price_master.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                duration_master.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
            } else {
                rl_classic.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom));
                rl_master.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom));
                tv_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                tv_master.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));

                price_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                duration_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                price_master.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                duration_master.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
            }
        });


        rl_master.setOnClickListener(v -> {
            purchase_type = 3;
            rl_master.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom_select2));
            if (Preference.getBooleanTheme(false)) {
                rl_classic.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom_1));
                rl_premium.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom_1));
                tv_premium.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                tv_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                price_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                duration_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                price_premium.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
                duration_premium.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorWhite));
            } else {
                rl_classic.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom));
                rl_premium.setBackground(ContextCompat.getDrawable(ActivityPremium.this, R.drawable.border_bottom));
                tv_premium.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                tv_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                price_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                duration_classic.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
                duration_premium.setTextColor(ContextCompat.getColor(ActivityPremium.this, R.color.colorBlack));
            }
        });

        bt_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (purchase_type == 1) {
                    if (Preference.getActive_Weekly().equals("true")) {
                        Toast.makeText(ActivityPremium.this, "Your Weekly Plan Already Activated", Toast.LENGTH_SHORT).show();
                    } else {
                        purchase(strWeek);
                    }
                } else if (purchase_type == 2) {
                    if (Preference.getActive_Monthly().equals("true")) {
                        Toast.makeText(ActivityPremium.this, "Your Monthly Plan Already Activated", Toast.LENGTH_SHORT).show();
                    } else {
                        purchase(strMonth);
                    }
                } else if (purchase_type == 3) {
                    if (Preference.getActive_Yearly().equals("true")) {
                        Toast.makeText(ActivityPremium.this, "Your Yearly Plan Already Activated", Toast.LENGTH_SHORT).show();
                    } else {
                        purchase(strYear);
                    }
                } else {
                    purchase(strYear);
                }
            }
        });

        billingClient = BillingClient.newBuilder(ActivityPremium.this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    getSkuDetails();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {

            }
        });

    }

    private void getSkuDetails() {
        List<String> skuList_sub = new ArrayList<>();
        skuList_sub.add(strWeek);
        skuList_sub.add(strMonth);
        skuList_sub.add(strYear);
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList_sub).setType(BillingClient.SkuType.SUBS);
        billingClient.querySkuDetailsAsync(params.build(), (billingResult, list) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                if (Preference.getWeekly_price().isEmpty()) {
                    Preference.setWeekly_price(list.get(1).getPrice());
                } else if (Preference.getMonthly_price().isEmpty()) {
                    Preference.setMonthly_price(list.get(0).getPrice());
                } else if (Preference.getYearly_price().isEmpty()) {
                    Preference.setYearly_price(list.get(2).getPrice());
                }
                setPrice();
            }
        });
    }

    private void setPrice() {
        price_classic.setText(Preference.getWeekly_price());
        price_premium.setText(Preference.getMonthly_price());
        price_master.setText(Preference.getYearly_price());
    }

    private void purchase(String stringSku) {
        List<String> skuList = new ArrayList<>();
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        if (billingClient != null) {
            skuList.add(stringSku);
            params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
        } else {
            Toast.makeText(ActivityPremium.this, "Billing not initialized", Toast.LENGTH_SHORT).show();
        }

        billingClient.querySkuDetailsAsync(params.build(), (billingResult, list) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder().setSkuDetails(list.get(0)).build();
                int responseCode = billingClient.launchBillingFlow(ActivityPremium.this, billingFlowParams).getResponseCode();
            }
        });
    }

    private void handlePurchase(Purchase purchase) {
        List<String> strWeekNew = new ArrayList<>();
        strWeekNew.add("weekly_key");

        List<String> strMonthNew = new ArrayList<>();
        strMonthNew.add("monthly_key");

        List<String> strYearNew = new ArrayList<>();
        strYearNew.add("yearly_key");

        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        if (strWeekNew.equals(purchase.getSkus())) {
                            Preference.setActive_Weekly("true");
                        } else if (strMonthNew.equals(purchase.getSkus())) {
                            Preference.setActive_Monthly("true");
                        } else if (strYearNew.equals(purchase.getSkus())) {
                            Preference.setActive_Yearly("true");
                        } else {
                            Preference.setActive_Weekly("");
                            Preference.setActive_Monthly("");
                            Preference.setActive_Yearly("");
                        }
                    }
                });
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBar() {
        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= 23) {
            window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.card_color));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.card_color));
        } else if (Build.VERSION.SDK_INT == 21 || Build.VERSION.SDK_INT == 22) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.card_color));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.card_color));
        } else {
            window.clearFlags(0);
        }
    }

    public void setStatusBarTheme() {
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(ActivityPremium.this, R.color.darkBlack));
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityPremium.this, TabActivity.class);
        startActivity(i);
        finish();
    }
}
