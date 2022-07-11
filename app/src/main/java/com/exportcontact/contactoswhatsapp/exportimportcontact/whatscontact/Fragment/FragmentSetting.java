package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity.TabActivity;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.ActivityPremium;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Constant;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.BuildConfig;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;


public class FragmentSetting extends Fragment {
    public Context context;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public View viewS;
    public RelativeLayout rlSetting, rl_chat_us;
    public TextView tv_about, tv_legal, txtAppVersn, txtWebsite;
    public TextView tv_premium;
    public TextView chat_us_title;
    public TextView tv_developer, tv_rate, tv_bug, tv_share, tv_darkMode;
    public TextView tv_privacy, tv_term, tv_Faq, tv_Blog, tv_Update,tv_general;
    public ImageView iv_premium;
    public ImageView iv_developer, iv_rate, iv_bug, iv_share, iv_privacy, iv_term;
    public ImageView img2, img7, img8, img10, img11, img12, img13, img17, img18, img19, icon_chat_us_next;
    public LinearLayout llGeneral, llAboutUs, llLegal;
    public SwitchCompat globalDark;
    public boolean switchOnOff;
    public String switchON;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewS = inflater.inflate(R.layout.fragment_setting, container, false);

        context = getContext();

        sharedPreferences = context.getSharedPreferences("MyPreferences", 0);
        editor = sharedPreferences.edit();
        editor.apply();

        FindView();

        return viewS;
    }

    private void FindView() {
        RelativeLayout rlDeveloper = viewS.findViewById(R.id.rlDeveloper);
        RelativeLayout rlRateUs = viewS.findViewById(R.id.rlRateUs);
        RelativeLayout rlBugReport = viewS.findViewById(R.id.rlBugReport);
        RelativeLayout rlShare = viewS.findViewById(R.id.rlShare);
        RelativeLayout rlPrivacy = viewS.findViewById(R.id.rlPrivacy);
        RelativeLayout rlTerm = viewS.findViewById(R.id.rlTerm);
        RelativeLayout rlPrePlan = viewS.findViewById(R.id.rlPrePlan);
        tv_darkMode = viewS.findViewById(R.id.tv_darkMode);

        RelativeLayout rlFaq = viewS.findViewById(R.id.rlFaq);
        RelativeLayout rlBlog = viewS.findViewById(R.id.rlBlog);
        RelativeLayout rlUpdate = viewS.findViewById(R.id.rlUpdate);
        rlSetting = viewS.findViewById(R.id.rlSetting);
        llGeneral = viewS.findViewById(R.id.llGeneral);
        llAboutUs = viewS.findViewById(R.id.llAboutUs);
        llLegal = viewS.findViewById(R.id.llLegal);
        txtAppVersn = viewS.findViewById(R.id.txtAppVersn);
        txtWebsite = viewS.findViewById(R.id.txtWebsite);
        tv_about = viewS.findViewById(R.id.tv_about);
        tv_legal = viewS.findViewById(R.id.tv_legal);
        tv_premium = viewS.findViewById(R.id.tv_premium);
        tv_general = viewS.findViewById(R.id.tv_general);
        chat_us_title = viewS.findViewById(R.id.chat_us_title);
        tv_developer = viewS.findViewById(R.id.tv_developer);
        tv_rate = viewS.findViewById(R.id.tv_rate);
        tv_bug = viewS.findViewById(R.id.tv_bug);
        tv_share = viewS.findViewById(R.id.tv_share);
        tv_privacy = viewS.findViewById(R.id.tv_privacy);
        tv_term = viewS.findViewById(R.id.tv_term);
        iv_premium = viewS.findViewById(R.id.iv_premium);
        iv_developer = viewS.findViewById(R.id.iv_developer);
        iv_rate = viewS.findViewById(R.id.iv_rate);
        iv_bug = viewS.findViewById(R.id.iv_bug);
        iv_share = viewS.findViewById(R.id.iv_share);
        iv_privacy = viewS.findViewById(R.id.iv_privacy);
        iv_term = viewS.findViewById(R.id.iv_term);
        tv_Faq = viewS.findViewById(R.id.tv_Faq);
        tv_Blog = viewS.findViewById(R.id.tv_Blog);
        tv_Update = viewS.findViewById(R.id.tv_Update);
        rl_chat_us = viewS.findViewById(R.id.rl_chat_us);
        img2 = viewS.findViewById(R.id.img2);
        img7 = viewS.findViewById(R.id.img7);
        img8 = viewS.findViewById(R.id.img8);
        img10 = viewS.findViewById(R.id.img10);
        img11 = viewS.findViewById(R.id.img11);
        img12 = viewS.findViewById(R.id.img12);
        img13 = viewS.findViewById(R.id.img13);
        img17 = viewS.findViewById(R.id.img17);
        img18 = viewS.findViewById(R.id.img18);
        img19 = viewS.findViewById(R.id.img19);
        icon_chat_us_next = viewS.findViewById(R.id.icon_chat_us_next);
        globalDark = viewS.findViewById(R.id.globalDark);

        globalDark.setChecked(Preference.getBooleanTheme(false));
        if (globalDark.isChecked()) {
            switchOnOff = Preference.getBooleanTheme(true);
        } else {
            switchOnOff = Preference.getBooleanTheme(false);
        }
        globalDark.setChecked(switchOnOff);


        globalDark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Preference.setBooleanTheme(globalDark.isChecked());
                Preference.setBooleanTheme(true);

                switchON = "yes";

                startActivity(new Intent(context, TabActivity.class), ActivityOptionsCompat.makeScaleUpAnimation(buttonView, (int) buttonView.getX(), (int) buttonView.getY(), buttonView.getWidth(), buttonView.getHeight()).toBundle());
                return;
            }
            Preference.setBooleanTheme(globalDark.isChecked());
            Preference.setBooleanTheme(false);
            switchON = "no";

            startActivity(new Intent(context, TabActivity.class), ActivityOptionsCompat.makeScaleUpAnimation(buttonView, (int) buttonView.getX(), (int) buttonView.getY(), buttonView.getWidth(), buttonView.getHeight()).toBundle());
        });


        if (Preference.getBooleanTheme(false)) {
            rlSetting.setBackgroundColor(ContextCompat.getColor(context, R.color.darkBlack));
            llGeneral.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_black));
            llAboutUs.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_black));
            llLegal.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_black));
            rl_chat_us.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_black));
            tv_about.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_legal.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            txtAppVersn.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_premium.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_developer.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_rate.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_bug.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_share.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_privacy.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_term.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_Faq.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_Blog.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_general.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_Update.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            chat_us_title.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            tv_darkMode.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            img2.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg_black));
            img7.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg_black));
            img8.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg_black));
            img10.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg_black));
            img11.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg_black));
            img12.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg_black));
            img13.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg_black));
            img17.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg_black));
            img18.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg_black));
            img19.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg_black));
            icon_chat_us_next.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg_black));
            img2.setImageResource(R.drawable.ic_baseline_navigate_next_24_white);
            img7.setImageResource(R.drawable.ic_baseline_navigate_next_24_white);
            img8.setImageResource(R.drawable.ic_baseline_navigate_next_24_white);
            img10.setImageResource(R.drawable.ic_baseline_navigate_next_24_white);
            img11.setImageResource(R.drawable.ic_baseline_navigate_next_24_white);
            img12.setImageResource(R.drawable.ic_baseline_navigate_next_24_white);
            img13.setImageResource(R.drawable.ic_baseline_navigate_next_24_white);
            img17.setImageResource(R.drawable.ic_baseline_navigate_next_24_white);
            img18.setImageResource(R.drawable.ic_baseline_navigate_next_24_white);
            img19.setImageResource(R.drawable.ic_baseline_navigate_next_24_white);
            icon_chat_us_next.setImageResource(R.drawable.ic_baseline_navigate_next_24_white);
        } else {
            rlSetting.setBackgroundColor(ContextCompat.getColor(context, R.color.card_color));
            llGeneral.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_white));
            llAboutUs.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_white));
            llLegal.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_white));
            rl_chat_us.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_white));
            tv_about.setTextColor(ContextCompat.getColor(context, R.color.darkBlack));
            tv_legal.setTextColor(ContextCompat.getColor(context, R.color.darkBlack));
            txtAppVersn.setTextColor(ContextCompat.getColor(context, R.color.darkBlack));
            tv_premium.setTextColor(ContextCompat.getColor(context, R.color.darkBlack));
            tv_developer.setTextColor(ContextCompat.getColor(context, R.color.darkBlack));
            tv_rate.setTextColor(ContextCompat.getColor(context, R.color.darkBlack));
            tv_bug.setTextColor(ContextCompat.getColor(context, R.color.darkBlack));
            tv_share.setTextColor(ContextCompat.getColor(context, R.color.darkBlack));
            tv_privacy.setTextColor(ContextCompat.getColor(context, R.color.darkBlack));
            tv_term.setTextColor(ContextCompat.getColor(context, R.color.darkBlack));
            tv_Faq.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            tv_darkMode.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            tv_Blog.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            tv_general.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            tv_Update.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            chat_us_title.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            img2.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg));
            img7.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg));
            img8.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg));
            img10.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg));
            img11.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg));
            img12.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg));
            img13.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg));
            img17.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg));
            img18.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg));
            img19.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg));
            icon_chat_us_next.setBackground(ContextCompat.getDrawable(context, R.drawable.setting_next_bg));
            img2.setImageResource(R.drawable.ic_baseline_navigate_next_24_black);
            img7.setImageResource(R.drawable.ic_baseline_navigate_next_24_black);
            img8.setImageResource(R.drawable.ic_baseline_navigate_next_24_black);
            img10.setImageResource(R.drawable.ic_baseline_navigate_next_24_black);
            img11.setImageResource(R.drawable.ic_baseline_navigate_next_24_black);
            img12.setImageResource(R.drawable.ic_baseline_navigate_next_24_black);
            img13.setImageResource(R.drawable.ic_baseline_navigate_next_24_black);
            img17.setImageResource(R.drawable.ic_baseline_navigate_next_24_black);
            img18.setImageResource(R.drawable.ic_baseline_navigate_next_24_black);
            img19.setImageResource(R.drawable.ic_baseline_navigate_next_24_black);
            icon_chat_us_next.setImageResource(R.drawable.ic_baseline_navigate_next_24_black);
        }


        rlUpdate.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName()))));


        txtAppVersn.setText(Html.fromHtml("App Version" + " : " + BuildConfig.VERSION_NAME));


        rl_chat_us.setOnClickListener(v -> {
            try {
                if (Constant.whatsappInstalledOrNot((Activity) context)) {
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91" + "7203927842") + "@s.whatsapp.net");
                    startActivity(sendIntent);
                } else {
                    Uri uri = Uri.parse("market://details?id=com.whatsapp");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                    startActivity(goToMarket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        rlFaq.setOnClickListener(v -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorTools));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage("com.android.chrome");
            customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            customTabsIntent.launchUrl(context, Uri.parse("https://whatscan.co/faqs/"));
        });
        txtWebsite.setOnClickListener(v -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorTools));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage("com.android.chrome");
            customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            customTabsIntent.launchUrl(context, Uri.parse("https://whatscan.co/"));
        });

        rlBlog.setOnClickListener(v -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorTools));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage("com.android.chrome");
            customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            customTabsIntent.launchUrl(context, Uri.parse("https://whatscan.co/blog/"));
        });


        rlRateUs.setOnClickListener(v -> showRateApp());


        rlBugReport.setOnClickListener(v -> {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("mailto:whatscantoolkit@gmail.com"));
                intent.putExtra("android.intent.extra.SUBJECT", "");
                intent.putExtra("android.intent.extra.TEXT", "");
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        });

        rlShare.setOnClickListener(v -> {
            Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.banner);
            String imgBitmapPath = MediaStore.Images.Media.insertImage(context.getContentResolver(),imgBitmap,"WhatsDirect - Direct Chat",null);
            Uri imgBitmapUri = Uri.parse(imgBitmapPath);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
            shareIntent.setType("image/png");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Enjoy direct chat without saving numbers with WhatsDirect - Direct Chat.\n\n" + "Download Link\n" + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\nWebsite :- https://whatscan.co/");
            startActivity(Intent.createChooser(shareIntent, "Share this"));
        });

        rlPrivacy.setOnClickListener(v -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorTools));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage("com.android.chrome");
            customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            customTabsIntent.launchUrl(context, Uri.parse("https://whatscan.co/privacy-policy/"));
        });

        rlTerm.setOnClickListener(v -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorTools));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage("com.android.chrome");
            customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            customTabsIntent.launchUrl(context, Uri.parse("https://whatscan.co/terms-conditions/"));
        });

        rlPrePlan.setOnClickListener(v -> startActivity(new Intent(context, ActivityPremium.class), ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle()));


        rlDeveloper.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context, R.style.ios_sheet_style);
            dialog.setContentView(R.layout.dialog_developer);

            CardView cardDevloper = dialog.findViewById(R.id.cardDevloper);
            ImageView bt_close = dialog.findViewById(R.id.bt_close);
            TextView txtOne = dialog.findViewById(R.id.txtOne);
            TextView txtTwo = dialog.findViewById(R.id.txtTwo);
            TextView txtThree = dialog.findViewById(R.id.txtThree);
            TextView txtversion = dialog.findViewById(R.id.txtversion);
            TextView txtWebsite = dialog.findViewById(R.id.txtWebsite);
            if (Preference.getBooleanTheme(false)) {
                cardDevloper.setCardBackgroundColor(ContextCompat.getColor(context, R.color.darkBlack));
                bt_close.setImageResource(R.drawable.ic_close2);
                txtOne.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                txtTwo.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                txtThree.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                txtversion.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            }

            txtWebsite.setOnClickListener(view -> {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorTools));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.intent.setPackage("com.android.chrome");
                customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                customTabsIntent.launchUrl(context, Uri.parse("https://whatscan.co/"));
            });

            txtversion.setText(Html.fromHtml("Version: " + BuildConfig.VERSION_NAME));

            bt_close.setOnClickListener(v1 -> dialog.dismiss());

            dialog.findViewById(R.id.imgWapp).setOnClickListener(v1 -> {
                try {
                    if (Constant.whatsappInstalledOrNot((Activity) context)) {
                        Intent sendIntent = new Intent("android.intent.action.MAIN");
                        sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91" + "7203927842") + "@s.whatsapp.net");
                        startActivity(sendIntent);
                    } else {
                        Uri uri = Uri.parse("market://details?id=com.whatsapp");
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                        startActivity(goToMarket);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            });

            dialog.findViewById(R.id.imgTwitter).setOnClickListener(v1 -> {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://twitter.com/whatstool"));
                startActivity(i);
                dialog.dismiss();
            });

            dialog.findViewById(R.id.imgFb).setOnClickListener(v1 -> {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.facebook.com/Whatstool3141"));
                startActivity(i);
                dialog.dismiss();
            });

            dialog.findViewById(R.id.imgInsta).setOnClickListener(v1 -> {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://instagram.com/whats_tool"));
                startActivity(i);
                dialog.dismiss();
            });
            dialog.findViewById(R.id.imgLinkdin).setOnClickListener(v1 -> {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://instagram.com/whats_tool"));
                startActivity(i);
                dialog.dismiss();
            });

            dialog.findViewById(R.id.imgYoutube).setOnClickListener(v1 -> {
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=pqd6DNp2HYc")));
                } catch (ActivityNotFoundException unused) {
                    unused.printStackTrace();
                }
                dialog.dismiss();
            });

            dialog.show();
        });
    }

    public void showRateApp() {
        Constant.BottomSheetDialogRateApp((Activity) context);
    }


}