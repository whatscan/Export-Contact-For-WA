package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity.ExportContactActivity;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity.MainActivity;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Advertisement;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.SharedPreferenceClass;

public class ExportContactFragment extends Fragment {
    public Context context;
    public ProgressBar mSeekArc;
    public LinearLayout llExport, llView;
    public Cursor cursorLocal, cursorWhatsApp, cursorWhatsAppB;
    public TextView txtWhatsAppContact, txtTotalContact, mSeekArcProgress, txtExportContact;
    public int totalLocalContact, totalWhatsAppContact, totalWhatsAppContactB, totalContact, totalWhatsAppC;
    public CardView card_export;
    public RelativeLayout rlExp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contact_export, container, false);

        context = getContext();
        FrameLayout fl_native = view.findViewById(R.id.fl_native);
        Advertisement.shoNativeAds((Activity) context, fl_native);


        mSeekArc = view.findViewById(R.id.seekArc);
        llExport = view.findViewById(R.id.llExport);
        llView = view.findViewById(R.id.llView);
        txtWhatsAppContact = view.findViewById(R.id.txtWhatsAppContact);
        txtTotalContact = view.findViewById(R.id.txtTotalContact);
        txtExportContact = view.findViewById(R.id.txtExportContact);
        mSeekArcProgress = view.findViewById(R.id.seekArcProgress);
        card_export = view.findViewById(R.id.card_export);
        rlExp = view.findViewById(R.id.rlExp);


        llExport.setOnClickListener(v -> {
            Advertisement.getInstance((Activity) context).showFull((Activity) context, () -> {
                Intent i = new Intent(context, ExportContactActivity.class);
                startActivity(i);
            });
        });

        llView.setOnClickListener(v -> {
            Advertisement.getInstance((Activity) context).showFull((Activity) context, () -> {
                Intent i = new Intent(context, MainActivity.class);
                startActivity(i);
            });
        });

        if (Preference.getBooleanTheme(false)) {
            setStatusBarTheme();
            rlExp.setBackgroundColor(ContextCompat.getColor(context, R.color.darkBlack));
            card_export.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorShape));
            txtWhatsAppContact.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            txtTotalContact.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            txtExportContact.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        }

        new LoadContact().execute();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setStatusBarTheme() {
        View view = getActivity().getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.darkBlack));
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadContact extends AsyncTask<Void, Integer, Void> {
        ProgressDialog pDialog;

        private LoadContact() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            mSeekArc.setProgress(0);
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Analyzing contacts ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        public Void doInBackground(Void... voidArr) {
            try {
                Cursor query = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, "display_name ASC");
                String str = "";
                int i = 0;
                while (query.moveToNext()) {
                    @SuppressLint("Range") String string = query.getString(query.getColumnIndex("data1"));
                    if (!string.equals(str)) {
                        i++;
                        str = string;
                    }
                }
                query.close();
                cursorLocal = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                totalLocalContact = cursorLocal.getCount() + -1;
                cursorWhatsApp = context.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, null, "account_type='com.whatsapp'and Deleted='0'", null, "display_name ASC");
                cursorWhatsAppB = context.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, null, "account_type='com.whatsapp.w4b'and Deleted='0'", null, "display_name ASC");
                totalWhatsAppContact = cursorWhatsApp.getCount();
                totalWhatsAppContactB = cursorWhatsAppB.getCount();
                totalContact = cursorLocal.getCount();
                return null;
            } catch (Exception e) {
                return null;
            }
        }

        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            SharedPreferenceClass.setBoolean(context, "firstLoad", false);
            if (cursorWhatsApp == null && cursorWhatsAppB == null) {
                totalWhatsAppC = 0;
            } else if (cursorWhatsApp == null) {
                totalWhatsAppContact = 0;
            } else if (cursorWhatsAppB == null) {
                totalWhatsAppContactB = 0;
            }

            if (totalWhatsAppContact > totalWhatsAppContactB) {
                totalWhatsAppC = totalWhatsAppContact;
                SharedPreferenceClass.setString(context, "whatsvalue", "whatsapp");
            } else if (totalWhatsAppContact < totalWhatsAppContactB) {
                totalWhatsAppC = totalWhatsAppContactB;
                SharedPreferenceClass.setString(context, "whatsvalue", "whatsappB");
            } else {
                if (totalWhatsAppContact > 0) {
                    totalWhatsAppC = totalWhatsAppContact;
                    SharedPreferenceClass.setString(context, "whatsvalue", "whatsapp");
                } else if (totalWhatsAppContactB > 0) {
                    totalWhatsAppC = totalWhatsAppContactB;
                    SharedPreferenceClass.setString(context, "whatsvalue", "whatsappB");
                }
            }
            txtWhatsAppContact.setVisibility(View.VISIBLE);
            txtWhatsAppContact.setText(Html.fromHtml(getString(R.string.whatsapp_contact) + " : " + "<strong>" + totalWhatsAppC + "</strong>"));
            float f = 0.0f;
            if (totalWhatsAppC == 0) {
                mSeekArc.setProgress((int) 0.0f);
                txtTotalContact.setText("0");
            } else if (totalContact != 0) {
                txtTotalContact.setText(Html.fromHtml(getString(R.string.total_contact) + " : " + "<strong>" + totalContact + "</strong>"));
                cursorLocal.close();
                f = (float) ((totalWhatsAppC * 100) / totalContact);
                mSeekArc.setProgress((int) f);
            } else {
                txtTotalContact.setText("0");
                cursorLocal.close();
                mSeekArc.setProgress((int) 0.0f);
            }

            mSeekArcProgress.setText(Html.fromHtml((int) f + "%"));
        }
    }


}
