package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.adpter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity.ChatWithNumber;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet.Contect;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.DatabaseHandler;

import java.util.List;

public class MyContactListAdpter extends RecyclerView.Adapter<MyContactListAdpter.ViewHolder> {

    public List<Contect> mArrContect;
    public DatabaseHandler mDatabaseHandler;
    private Context mContext;

    public MyContactListAdpter(List<Contect> list, Context context) {
        this.mArrContect = list;
        this.mContext = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, (ViewGroup) null, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        if (Preference.getBooleanTheme(false)) {
            viewHolder.lst_crd.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_black));
            viewHolder.list_text.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));

        }

        this.mDatabaseHandler = new DatabaseHandler((Activity) this.mContext);
        viewHolder.list_text.setText(this.mArrContect.get(i).getContect());
        viewHolder.list_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MyContactListAdpter.this.AskOption(i).show();
            }
        });
        viewHolder.lst_crd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Contect contact = MyContactListAdpter.this.mDatabaseHandler.getContact(Integer.parseInt(((Contect) MyContactListAdpter.this.mArrContect.get(i)).getId()));
                ChatWithNumber.moEdMobNum.setText("");
                ChatWithNumber.moEdMobNum.setText(contact.getContect());
                ChatWithNumber.moEdMobNum.setSelection(ChatWithNumber.moEdMobNum.getText().length());
            }
        });
    }

    public int getItemCount() {
        return this.mArrContect.size();
    }


    public AlertDialog AskOption(final int i) {
        return new AlertDialog.Builder(this.mContext).setMessage("Are you sure want to Delete this number?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                MyContactListAdpter.this.mDatabaseHandler.deleteContactById(MyContactListAdpter.this.mDatabaseHandler.getContact(Integer.parseInt(((Contect) MyContactListAdpter.this.mArrContect.get(i)).getId())).getId());
                MyContactListAdpter.this.mArrContect.remove(i);
                MyContactListAdpter.this.notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout list_delete;
        public TextView list_text;
        CardView lst_crd;

        public ViewHolder(View view) {
            super(view);
            this.list_delete = (LinearLayout) view.findViewById(R.id.list_delete);
            this.list_text = (TextView) view.findViewById(R.id.list_text);
            this.lst_crd = (CardView) view.findViewById(R.id.lst_crd);
        }
    }
}
