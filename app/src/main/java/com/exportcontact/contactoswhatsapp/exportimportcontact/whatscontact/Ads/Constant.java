package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.content.ContextCompat;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {
    public static boolean whatsappInstalledOrNot(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return app_installed;
    }

    public static void BottomSheetDialogRateApp(Activity activity) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_add_review, null);
        bottomSheetDialog.setContentView(inflate);

        LinearLayout rlReview = inflate.findViewById(R.id.rlReview);
        TextView txtOne = inflate.findViewById(R.id.txtOne);
        TextView txtTwo = inflate.findViewById(R.id.txtTwo);
        TextInputLayout mtf_name = inflate.findViewById(R.id.mtf_name);
        EditText et_post = inflate.findViewById(R.id.et_post);
        AppCompatRatingBar rating_bar = inflate.findViewById(R.id.rating_bar);


        if (Preference.getBooleanTheme(false)) {
            rlReview.setBackgroundColor(ContextCompat.getColor(activity, R.color.darkBlack));
            txtOne.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
            txtTwo.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
            et_post.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
            mtf_name.setDefaultHintTextColor(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.colorWhite)));
        }
        inflate.findViewById(R.id.bt_cancel).setOnClickListener(v -> bottomSheetDialog.dismiss());

        inflate.findViewById(R.id.bt_submit).setOnClickListener(v -> {
            String review = et_post.getText().toString().trim();
            try {
                Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
                Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(goMarket);
            } catch (ActivityNotFoundException e) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName());
                Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(goMarket);
            }
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }


}
