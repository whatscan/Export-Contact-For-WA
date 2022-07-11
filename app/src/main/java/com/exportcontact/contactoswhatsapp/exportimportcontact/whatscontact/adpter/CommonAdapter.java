package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.MyViewHolder> {
    private Context moContext;
    private String[] moTitle;
    private int[] moTitleImage;

    public CommonAdapter(Context context, String[] strArr, int[] iArr) {
        this.moContext = context;
        this.moTitle = strArr;
        this.moTitleImage = iArr;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return i;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.moContext).inflate(R.layout.cell_common_layout, (ViewGroup) null, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        if (Preference.getBooleanTheme(false)) {
            myViewHolder.sub_lay.setBackgroundColor(ContextCompat.getColor(moContext, R.color.colorShape));
            myViewHolder.imgNextArrow.setImageResource(R.drawable.ic_baseline_navigate_next_24_white);
            myViewHolder.imgNextArrow.setBackground(ContextCompat.getDrawable(moContext, R.drawable.setting_next_bg_black));
            myViewHolder.textView.setTextColor(ContextCompat.getColor(moContext, R.color.colorWhite));


        }


        myViewHolder.textView.setText(this.moTitle[i]);
        myViewHolder.imageView.setImageResource(this.moTitleImage[i]);
    }

    public int getItemCount() {
        return this.moTitle.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,imgNextArrow;
        TextView textView;
        RelativeLayout sub_lay;

        public MyViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.TvTitle);
            this.imageView = (ImageView) view.findViewById(R.id.imgIcon);
            this.imgNextArrow = (ImageView) view.findViewById(R.id.imgNextArrow);
            this.sub_lay = (RelativeLayout) view.findViewById(R.id.sub_lay);
        }
    }
}
