package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Advertisement;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.BuildConfig;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet.ContactGS;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.export.HtmlUtility;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.export.JsonUtility;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.export.PdfUtility;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.export.VcfUtility;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.export.XmlUtility;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.CommonStuffs;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.io.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExportContactActivity extends AppCompatActivity implements View.OnClickListener {
    public int TotalExportedContact;
    public AlertDialog alertExitDialog = null;
    public AlertDialog alertSimpleDialog;
    public String allContact = "";
    public boolean backround = false;
    public TextView buttonExit;
    public Button button_save_file;
    public List<ContactGS> contacts = new ArrayList<>();
    public Cursor cursorWhatsApp;
    public Cursor cursorWhatsAppB;
    public File file;
    public String fileName;
    public CommonStuffs mCommonFunction = new CommonStuffs();
    public EditText mEditTextExportFileName;
    public TextView mTextViewStoragepath;
    public ProgressBar progress_bar;
    public Snackbar snackbar;
    public int totalWhatsAppContactB;
    public int whatsappContactCount = 0;
    public RelativeLayout rlContactExp;
    public View ic_include;
    public CardView cardOne;
    TextView textview_contact_support_title;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_export_contact);
        SetStatusBar();


        LinearLayout ll_banner = findViewById(R.id.ll_banner);
        Advertisement.showBanner(ExportContactActivity.this, ll_banner);

        mEditTextExportFileName = findViewById(R.id.edittext_export_contact);
        button_save_file = findViewById(R.id.button_save_file);

        rlContactExp = findViewById(R.id.rlContactExp);
        ic_include = findViewById(R.id.ic_include);
        cardOne = findViewById(R.id.cardOne);
        textview_contact_support_title = findViewById(R.id.textview_contact_support_title);

        ImageView la_back = findViewById(R.id.la_back);
        la_back.setOnClickListener(v -> onBackPressed());


        if (Preference.getBooleanTheme(false)) {
            setStatusBarTheme();
            rlContactExp.setBackgroundColor(ContextCompat.getColor(this, R.color.darkBlack));
            ic_include.setBackgroundColor(ContextCompat.getColor(this, R.color.darkBlack));
            cardOne.setCardBackgroundColor(ContextCompat.getColor(this, R.color.colorShape));
            mEditTextExportFileName.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            mEditTextExportFileName.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_black_dark));

            textview_contact_support_title.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        }

        button_save_file.setOnClickListener(this);
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
        View view =getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(ExportContactActivity.this, R.color.darkBlack));
    }

    public void onClick(View view) {
        if (view.getId() == R.id.button_save_file) {
            button_save_file.setEnabled(false);
            if (!validateString(mEditTextExportFileName.getText().toString())) {
                button_save_file.setEnabled(true);
                mCommonFunction.showSnackBar(mEditTextExportFileName, getResources().getString(R.string.file_name_error));
            } else if (chekPermission()) {
                hideKeybord();
                startprocess();
            } else {
                permissionOnly(101);
            }
        }
    }

    public boolean chekPermission() {
        boolean z = ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") == 0;
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            z = false;
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        return z;
    }


    public void permissionOnly(int i) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("android.permission.READ_CONTACTS");
        arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
        arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        requestPermissions((String[]) arrayList.toArray(new String[0]), i);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr[0] == -1 || iArr[1] == -1) {
            showSimpleDialog();
            button_save_file.setEnabled(true);
        } else if (iArr[0] == 0 && iArr[1] == 0 && i == 101) {
            startprocess();
        }
    }

    private void startprocess() {
        try {
            fileName = mEditTextExportFileName.getText().toString().trim();

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ExportContactActivity.this);
            View inflate = LayoutInflater.from(ExportContactActivity.this).inflate(R.layout.dialog_export_choose_type, null);
            bottomSheetDialog.setContentView(inflate);

            LinearLayout llExport = inflate.findViewById(R.id.llExport);

            TextView txtOne = inflate.findViewById(R.id.txtOne);
            TextView txtCSV = inflate.findViewById(R.id.txtCSV);
            TextView txtHTML = inflate.findViewById(R.id.txtHTML);
            TextView txtPDF = inflate.findViewById(R.id.txtPDF);
            TextView txtVCF = inflate.findViewById(R.id.txtVCF);
            TextView txtJSON = inflate.findViewById(R.id.txtJSON);
            TextView txtXML = inflate.findViewById(R.id.txtXML);
            TextView txtTXT = inflate.findViewById(R.id.txtTXT);
            TextView txtCancel = inflate.findViewById(R.id.txtCancel);

            if (Preference.getBooleanTheme(false)) {
                llExport.setBackgroundColor(ContextCompat.getColor(this, R.color.darkBlack));
                txtOne.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                txtCSV.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                txtHTML.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                txtPDF.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                txtVCF.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                txtJSON.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                txtXML.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                txtTXT.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            }

            txtCSV.setOnClickListener(v -> {
                new LoadContactClass(".csv").execute();
                bottomSheetDialog.dismiss();
            });

            txtHTML.setOnClickListener(v -> {
                new LoadContactTypes(1).execute();
                bottomSheetDialog.dismiss();
            });

            txtPDF.setOnClickListener(v -> {
                new LoadContactTypes(2).execute();
                bottomSheetDialog.dismiss();
            });

            txtVCF.setOnClickListener(v -> {
                new LoadContactTypes(3).execute();
                bottomSheetDialog.dismiss();
            });

            txtJSON.setOnClickListener(v -> {
                new LoadContactTypes(4).execute();
                bottomSheetDialog.dismiss();
            });

            txtXML.setOnClickListener(v -> {
                new LoadContactTypes(5).execute();
                bottomSheetDialog.dismiss();
            });

            txtTXT.setOnClickListener(v -> {
                new LoadContactClass(".txt").execute();
                bottomSheetDialog.dismiss();
            });

            txtCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

            bottomSheetDialog.show();
        } catch (Exception ignored) {
        }
    }

    @SuppressLint("WrongConstant")
    private void hideKeybord() {
        ((InputMethodManager) getApplicationContext().getSystemService("input_method")).hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 2);
    }

    public void showSimpleDialog() {
        try {
            if (alertSimpleDialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.app_name));
                builder.setMessage(getResources().getString(R.string.allow_for_smooth));
                builder.setPositiveButton(getResources().getString(R.string.action_settings), (dialogInterface, i) -> {
                    if (alertSimpleDialog != null) {
                        alertSimpleDialog.dismiss();
                    }
                    Intent intent = new Intent();
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, (String) null));
                    startActivity(intent);
                });
                alertSimpleDialog = builder.create();
                alertSimpleDialog.show();
            } else if (!alertSimpleDialog.isShowing()) {
                alertSimpleDialog.show();
            }
        } catch (Exception unused) {
        }
    }

    public boolean validateString(String str) {
        return str != null && !str.isEmpty() && str.length() > 0 && !str.equals("null");
    }

    public void createFile(String str, String str2) {
        File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator + "WhatsApp Contact Export");
        if (!file2.exists()) {
            try {
                file2.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator + "WhatsApp Contact Export" + File.separator + str + str2);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        if (file.exists()) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                bufferedWriter.write(allContact);
                bufferedWriter.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (backround) {
                    progress_bar.setVisibility(View.GONE);
                    mTextViewStoragepath.setText("Export File Location:\n" + file.getAbsolutePath());
                    return;
                }

                snackbar = Snackbar.make(mEditTextExportFileName, "" + file.getAbsolutePath(), -2).setAction("OK", view -> snackbar.dismiss());
                snackbar.show();
                backround = false;
            }
        }, 2000);
    }

    public void showProgressDialog() {
        try {
            View inflate = View.inflate(this, R.layout.partial_popup_ads, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(inflate);

            LinearLayout llContactE = inflate.findViewById(R.id.llContactE);

            mTextViewStoragepath = inflate.findViewById(R.id.path_textview);
            progress_bar = inflate.findViewById(R.id.progress_bar);
            buttonExit = inflate.findViewById(R.id.button_yes);

            if (Preference.getBooleanTheme(false)){
                llContactE.setBackgroundColor(ContextCompat.getColor(this, R.color.darkBlack));
                mTextViewStoragepath.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            }

            buttonExit.setOnClickListener(view -> {
                alertExitDialog.dismiss();
                finish();
            });
            alertExitDialog = builder.create();
            alertExitDialog.setCancelable(false);
            alertExitDialog.show();
            backround = true;
        } catch (Exception e) {
            Log.e("AutoStamperActivity", "showSimpleDialog: " + e.getMessage());
        }
    }

    public void onBackPressed() {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(mEditTextExportFileName.getWindowToken(), 0);
        finish();
    }

    private class LoadContactClass extends AsyncTask<Void, Integer, Void> {
        String fileExt;
        int f462i = 0;
        ProgressDialog pDialog;
        int percentage = 0;

        public LoadContactClass(String str) {
            fileExt = str;
        }


        public void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ExportContactActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

            cursorWhatsApp = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, null, "account_type='com.whatsapp'and Deleted='0'", null, "display_name ASC");
            cursorWhatsAppB = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, null, "account_type='com.whatsapp.w4b' and Deleted='0'", null, "display_name ASC");

            whatsappContactCount = 0;
            TotalExportedContact = 0;
            if (cursorWhatsApp == null) {
                whatsappContactCount = 0;
            } else {
                whatsappContactCount = cursorWhatsApp.getCount();
            }

            if (cursorWhatsAppB == null) {
                totalWhatsAppContactB = 0;
            } else {
                totalWhatsAppContactB = cursorWhatsAppB.getCount();
            }

            if (cursorWhatsApp != null && whatsappContactCount > totalWhatsAppContactB) {
                whatsappContactCount = cursorWhatsApp.getCount();
            } else if (cursorWhatsAppB != null && whatsappContactCount < totalWhatsAppContactB) {
                whatsappContactCount = cursorWhatsAppB.getCount();
                cursorWhatsApp = cursorWhatsAppB;
            } else if (whatsappContactCount > 0) {
                whatsappContactCount = cursorWhatsApp.getCount();
            } else if (totalWhatsAppContactB > 0) {
                whatsappContactCount = cursorWhatsAppB.getCount();
            } else {
                whatsappContactCount = 0;
            }
        }


        public Void doInBackground(Void... voidArr) {
            try {
                f462i = 0;
                String allContact = "";
                if (cursorWhatsApp != null) {
                    cursorWhatsApp.moveToFirst();
                    if (whatsappContactCount != 0) {
                        while (true) {
                            String str = cursorWhatsApp.getString(cursorWhatsApp.getColumnIndex("display_name")) + "," + cursorWhatsApp.getString(cursorWhatsApp.getColumnIndex("sync1")).replace("@s.whatsapp.net", "");
                            allContact = allContact + IOUtils.LINE_SEPARATOR_UNIX + str;
                            f462i = f462i + 1;
                            if (whatsappContactCount > 0) {
                                percentage = (f462i * 100) / whatsappContactCount;
                            }
                            if (!cursorWhatsApp.moveToNext()) {
                                break;
                            }
                        }
                    }
                }
            } catch (Exception unused6) {
            }
            return null;
        }


        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (whatsappContactCount == 0) {
                mCommonFunction.showSnackBarLong(mEditTextExportFileName, getResources().getString(R.string.no_whatsapp_contact_msg));
                return;
            }
            if (CommonStuffs.isInternetPresent(getApplicationContext())) {
                showProgressDialog();
            }

            createFile(fileName, fileExt);
        }
    }

    private class LoadContactTypes extends AsyncTask<Void, Integer, Void> {
        int f463i = 0;
        ProgressDialog pDialog;
        int percentage = 0;
        int type;

        public LoadContactTypes(int i) {
            type = i;
        }

        public void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ExportContactActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


            cursorWhatsApp = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, (String[]) null, "account_type='com.whatsapp'and Deleted='0'", (String[]) null, "display_name ASC");
            cursorWhatsAppB = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, (String[]) null, "account_type='com.whatsapp.w4b' and Deleted='0'", (String[]) null, "display_name ASC");
            whatsappContactCount = 0;
            TotalExportedContact = 0;

            if (cursorWhatsApp == null) {
                whatsappContactCount = 0;
            } else {
                whatsappContactCount = cursorWhatsApp.getCount();
            }
            if (cursorWhatsAppB == null) {
                totalWhatsAppContactB = 0;
            } else {
                totalWhatsAppContactB = cursorWhatsAppB.getCount();
            }

            if (cursorWhatsApp != null && whatsappContactCount > totalWhatsAppContactB) {
                whatsappContactCount = cursorWhatsApp.getCount();
            } else if (cursorWhatsAppB != null && whatsappContactCount < totalWhatsAppContactB) {
                whatsappContactCount = cursorWhatsAppB.getCount();
                cursorWhatsApp = cursorWhatsAppB;
            } else if (whatsappContactCount > 0) {
                whatsappContactCount = cursorWhatsApp.getCount();
            } else if (totalWhatsAppContactB > 0) {
                whatsappContactCount = cursorWhatsAppB.getCount();
            } else {
                whatsappContactCount = 0;
            }
            Log.d("KP******************** ", "First whatsappContactCount" + whatsappContactCount);
        }


        public Void doInBackground(Void... voidArr) {
            try {
                f463i = 0;
                String allContact = "";
                if (cursorWhatsApp != null) {
                    cursorWhatsApp.moveToFirst();
                    if (whatsappContactCount != 0) {
                        do {
                            String string = cursorWhatsApp.getString(cursorWhatsApp.getColumnIndex("display_name"));
                            String replace = cursorWhatsApp.getString(cursorWhatsApp.getColumnIndex("sync1")).replace("@s.whatsapp.net", "");

                            allContact = allContact + IOUtils.LINE_SEPARATOR_UNIX + (string + "," + replace);
                            contacts.add(new ContactGS(string, replace));
                            f463i = f463i + 1;
                            if (whatsappContactCount > 0) {
                                percentage = (f463i * 100) / whatsappContactCount;
                            }
                        } while (cursorWhatsApp.moveToNext());
                    }
                }
            } catch (Exception unused3) {
            }
            return null;
        }


        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            ProgressDialog progressDialog = pDialog;
            if (progressDialog != null && progressDialog.isShowing()) {
                pDialog.dismiss();
            }
            Log.d("contacts_sz_", ":: " + contacts.size());
            int i = type;
            if (i == 1) {
                ExportContactActivity exportContactActivity = ExportContactActivity.this;
                HtmlUtility.exportContacts(exportContactActivity, exportContactActivity.contacts, fileName, false, null);
            } else if (i == 2) {
                ExportContactActivity exportContactActivity2 = ExportContactActivity.this;
                PdfUtility.exportContacts(exportContactActivity2, exportContactActivity2.contacts, fileName, false, null);
            } else if (i == 3) {
                ExportContactActivity exportContactActivity3 = ExportContactActivity.this;
                VcfUtility.exportContacts(exportContactActivity3, exportContactActivity3.contacts, fileName, false, null);
            } else if (i == 4) {
                ExportContactActivity exportContactActivity4 = ExportContactActivity.this;
                JsonUtility.exportContacts(exportContactActivity4, exportContactActivity4.contacts, fileName, false, null);
            } else if (i == 5) {
                ExportContactActivity exportContactActivity5 = ExportContactActivity.this;
                XmlUtility.exportContacts(exportContactActivity5, exportContactActivity5.contacts, fileName, false, null);
            }
            if (whatsappContactCount == 0) {
                mCommonFunction.showSnackBarLong(mEditTextExportFileName, getResources().getString(R.string.no_whatsapp_contact_msg));
                return;
            }
            if (CommonStuffs.isInternetPresent(getApplicationContext())) {
                showProgressDialog();
            }
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    int i = type;
                    String str = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator + "WhatsApp Contact Export/" + fileName + (i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? "" : ".xml" : ".json" : ".vcf" : ".pdf" : ".html");
                    if (backround) {
                        progress_bar.setVisibility(8);
                        mTextViewStoragepath.setText("Export File Location:\n" + str);
                        return;
                    }
                    snackbar = Snackbar.make((View) mEditTextExportFileName, (CharSequence) "" + str, -2).setAction((CharSequence) "OK", (View.OnClickListener) new View.OnClickListener() {
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                    backround = false;
                }
            }, 3000);
        }
    }
}
