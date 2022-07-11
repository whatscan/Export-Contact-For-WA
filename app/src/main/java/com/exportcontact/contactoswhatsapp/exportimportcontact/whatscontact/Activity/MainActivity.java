package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Advertisement;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet.GetSetContact;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.adpter.ContactAdapter;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.export.Conversion;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.export.JSON;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.CommonStuffs;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.SharedPreferenceClass;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public String Wval;
    public String allContact = "";
    public int totalContact;
    public ArrayList<GetSetContact> contactArray = new ArrayList<>();
    public Cursor cursor9;
    public ListView lvContact;
    public CommonStuffs mCommonFunction = new CommonStuffs();
    public TextView rel_int_error;

    public static ArrayList<Conversion> getConvertedFiles(Context context) {
        ArrayList<Conversion> arrayList = new ArrayList<>();
        String stringPrefrence = SharedPreferenceClass.getStringPrefrence(context, Conversion.TAG_CONVERSIONS, (String) null);
        int integerPrefrence = SharedPreferenceClass.getIntegerPrefrence(context, Conversion.TAG_CONVERSIONS_SIZE, 0);
        if (stringPrefrence != null) {
            JSON json = new JSON(stringPrefrence);
            for (int i = 0; i < integerPrefrence; i++) {
                Conversion conversion = new Conversion();
                conversion.CONVERTED_FILE_PATH = json.key(i + "_" + Conversion.TAG_CONVERTED_FILE_PATH).stringValue();
                conversion.CONVERTED_FILE_NAME = json.key(i + "_" + Conversion.TAG_CONVERTED_FILE_NAME).stringValue();
                conversion.CONVERTED_FILE_DURATION = json.key(i + "_" + Conversion.TAG_CONVERTED_FILE_DURATION).stringValue();
                conversion.CONVERTED_FILE_SIZE = json.key(i + "_" + Conversion.TAG_CONVERTED_FILE_SIZE).stringValue();
                if (new File(conversion.CONVERTED_FILE_PATH).exists()) {
                    arrayList.add(conversion);
                }
            }
        }
        return arrayList;
    }

    public static void saveConvertedFiles(Context context, ArrayList<Conversion> arrayList) {
        SharedPreferenceClass.saveIntegerPrefrence(context, Conversion.TAG_CONVERSIONS_SIZE, arrayList.size());
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(i + "_" + Conversion.TAG_CONVERTED_FILE_PATH);
            arrayList2.add(arrayList.get(i).CONVERTED_FILE_PATH);
            arrayList2.add(i + "_" + Conversion.TAG_CONVERTED_FILE_NAME);
            arrayList2.add(arrayList.get(i).CONVERTED_FILE_NAME);
            arrayList2.add(i + "_" + Conversion.TAG_CONVERTED_FILE_DURATION);
            arrayList2.add(arrayList.get(i).CONVERTED_FILE_DURATION);
            arrayList2.add(i + "_" + Conversion.TAG_CONVERTED_FILE_SIZE);
            arrayList2.add(arrayList.get(i).CONVERTED_FILE_SIZE);
        }
        SharedPreferenceClass.saveStringPrefrence(context, Conversion.TAG_CONVERSIONS, JSON.create(JSON.dic((Object[]) arrayList2.toArray(new String[arrayList2.size()]))).toString());
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        SetStatusBar();

        RelativeLayout rel_viewcontact = findViewById(R.id.rel_viewcontact);
        RelativeLayout ic_include = findViewById(R.id.ic_include);
        LinearLayout ll_banner = findViewById(R.id.ll_banner);
        Advertisement.showBanner(MainActivity.this, ll_banner);

        ImageView la_back = findViewById(R.id.la_back);
        la_back.setOnClickListener(v -> onBackPressed());

        lvContact = findViewById(R.id.lvContact);
        rel_int_error = findViewById(R.id.rel_int_error);

        if (appInstalledOrNot("com.whatsapp")) {
            rel_int_error.setText(getResources().getString(R.string.no_nwhatsapp_contact_navailable));
        } else if (appInstalledOrNot("com.whatsapp.w4b")) {
            rel_int_error.setText(getResources().getString(R.string.no_nwhatsapp_contact_navailable));
        } else {
            rel_int_error.setText(getResources().getString(R.string.no_whatsapp));
        }

        if (Preference.getBooleanTheme(false)) {
            setStatusBarTheme();
            rel_viewcontact.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.darkBlack));
            ic_include.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.darkBlack));
        }

        new ViewContactClass().execute();
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
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.darkBlack));
    }
    private boolean appInstalledOrNot(String str) {
        try {
            getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public void LoadData() {

        Wval = SharedPreferenceClass.getString(this, "whatsvalue", "null");
        if (Wval.equals("whatsapp")) {
            cursor9 = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, null, "account_type='com.whatsapp' and Deleted='0'", null, "display_name ASC");
        } else if (Wval.equals("whatsappB")) {
            cursor9 = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, null, "account_type='com.whatsapp.w4b' and Deleted='0'", null, "display_name ASC");
        }

        if (cursor9 != null) {
            totalContact = cursor9.getCount();
            cursor9.moveToFirst();
            if (cursor9.getCount() != 0) {
                do {
                    String string2 = cursor9.getString(cursor9.getColumnIndex("display_name"));
                    String string3 = cursor9.getString(cursor9.getColumnIndex("sync1"));
                    String string4 = cursor9.getString(cursor9.getColumnIndex("account_type"));
                    String replace = string3.replace("@s.whatsapp.net", "");
                    allContact += IOUtils.LINE_SEPARATOR_UNIX + (string2 + "," + replace);
                    contactArray.add(new GetSetContact(string2, replace, string4));
                } while (cursor9.moveToNext());
            }
            cursor9.close();
            return;
        }
        totalContact = 0;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void snacbar() {
        mCommonFunction.showSnackBar(findViewById(R.id.rel_viewcontact), "No Contact Found");
    }

    private class ViewContactClass extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;

        private ViewContactClass() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Analyzing contacts ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        public Void doInBackground(Void... voidArr) {
            LoadData();
            return null;
        }


        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            runOnUiThread(() -> {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (totalContact == 0) {
                    rel_int_error.setVisibility(0);
                    lvContact.setVisibility(8);
                    return;
                }
                rel_int_error.setVisibility(8);
                lvContact.setVisibility(0);
                ContactAdapter contactAdapter = new ContactAdapter(contactArray, MainActivity.this);
                lvContact.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();
            });
        }
    }
}
