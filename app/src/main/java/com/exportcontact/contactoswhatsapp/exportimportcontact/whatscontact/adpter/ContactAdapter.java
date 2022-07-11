package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.adpter;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity.MainActivity;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet.GetSetContact;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    ArrayList<GetSetContact> contactValue;
    Context context;
    LayoutInflater inflater;
    Typeface f445tf;

    @SuppressLint("WrongConstant")
    public ContactAdapter(ArrayList<GetSetContact> arrayList, Context context2) {
        this.contactValue = arrayList;
        this.context = context2;
        this.inflater = (LayoutInflater) context2.getSystemService("layout_inflater");
        this.f445tf = Typeface.createFromAsset(context2.getAssets(), "fonts/roboto-regular.ttf");

    }

    public long getItemId(int i) {
        return 0;
    }

    public int getCount() {
        return this.contactValue.size();
    }

    public Object getItem(int i) {
        return this.contactValue.get(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = this.inflater.inflate(R.layout.layout_row, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.txtName);
            viewHolder.linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
            viewHolder.tvNumber = (TextView) view.findViewById(R.id.txtNumber);
            ImageView unused = viewHolder.imageView1 = (ImageView) view.findViewById(R.id.imageView1);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvName.setText(this.contactValue.get(i).getName());
        viewHolder.tvNumber.setText(this.contactValue.get(i).getNumber());

        viewHolder.tvName.setTypeface(this.f445tf);
        viewHolder.tvNumber.setTypeface(this.f445tf);
        viewHolder.tvNumber.setEnabled(false);
        viewHolder.tvNumber.setClickable(false);
        viewHolder.tvName.setEnabled(false);
        viewHolder.tvName.setClickable(false);


        if (Preference.getBooleanTheme(false)){
            viewHolder.tvName.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        }


        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ContactAdapter.this.appInstalledOrNot("com.whatsapp") && ContactAdapter.this.appInstalledOrNot("com.whatsapp.w4b")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ContactAdapter.this.context);
                    builder.setMessage((CharSequence) ContactAdapter.this.context.getResources().getString(R.string.wa_wb_dialouge_msg));
                    builder.setPositiveButton((CharSequence) "Go to Whatsapp", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                Intent intent = new Intent("android.intent.action.MAIN");
                                intent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                                intent.setAction("android.intent.action.SEND");
                                intent.setType("text/plain");
                                intent.putExtra("android.intent.extra.TEXT", "");
                                intent.putExtra("jid", viewHolder.tvNumber.getText() + "@s.whatsapp.net");
                                intent.setPackage("com.whatsapp");
                                ContactAdapter.this.context.startActivity(intent);
                            } catch (Exception unused) {
                                ((MainActivity) ContactAdapter.this.context).snacbar();
                            }
                        }
                    });
                    builder.setNegativeButton((CharSequence) "Go to Whatsapp Business", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                Intent intent = new Intent("android.intent.action.MAIN");
                                intent.setComponent(new ComponentName("com.whatsapp.w4b", "com.whatsapp.Conversation"));
                                intent.setAction("android.intent.action.SEND");
                                intent.setType("text/plain");
                                intent.putExtra("android.intent.extra.TEXT", "");
                                intent.putExtra("jid", viewHolder.tvNumber.getText() + "@s.whatsapp.net");
                                intent.setPackage("com.whatsapp.w4b");
                                ContactAdapter.this.context.startActivity(intent);
                            } catch (Exception unused) {
                                ((MainActivity) ContactAdapter.this.context).snacbar();
                            }
                        }
                    });
                    builder.setNeutralButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null);
                    builder.create().show();
                } else if (ContactAdapter.this.contactValue.get(i).getAcType().equals("com.whatsapp")) {
                    try {
                        Intent intent = new Intent("android.intent.action.MAIN");
                        intent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                        intent.setAction("android.intent.action.SEND");
                        intent.setType("text/plain");
                        intent.putExtra("android.intent.extra.TEXT", "");
                        intent.putExtra("jid", viewHolder.tvNumber.getText() + "@s.whatsapp.net");
                        intent.setPackage("com.whatsapp");
                        ContactAdapter.this.context.startActivity(intent);
                    } catch (Exception unused) {
                        ((MainActivity) ContactAdapter.this.context).snacbar();
                    }
                } else if (ContactAdapter.this.contactValue.get(i).getAcType().equals("com.whatsapp.w4b")) {
                    try {
                        Intent intent2 = new Intent("android.intent.action.MAIN");
                        intent2.setComponent(new ComponentName("com.whatsapp.w4b", "com.whatsapp.Conversation"));
                        intent2.setAction("android.intent.action.SEND");
                        intent2.setType("text/plain");
                        intent2.putExtra("android.intent.extra.TEXT", "");
                        intent2.putExtra("jid", viewHolder.tvNumber.getText() + "@s.whatsapp.net");
                        intent2.setPackage("com.whatsapp.w4b");
                        ContactAdapter.this.context.startActivity(intent2);
                    } catch (Exception unused2) {
                        ((MainActivity) ContactAdapter.this.context).snacbar();
                    }
                }
            }
        });
        return view;
    }

    public boolean appInstalledOrNot(String str) {
        try {
            this.context.getPackageManager().getPackageInfo(str, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    class ViewHolder {
        public ImageView imageView1;
        LinearLayout linearLayout;
        TextView tvName;
        TextView tvNumber;

        ViewHolder() {
        }
    }
}
