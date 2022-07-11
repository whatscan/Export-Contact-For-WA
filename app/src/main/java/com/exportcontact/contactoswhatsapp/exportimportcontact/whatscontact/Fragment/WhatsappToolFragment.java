package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity.ChatWithBlankMessage;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity.ChatWithMsg;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity.ChatWithNumber;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Advertisement;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.adpter.CommonAdapter;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.RecyclerItemClickListener;

public class WhatsappToolFragment extends Fragment {
    public Context context;
    public String DeviceModel;
    public String DeviceName;
    public CommonAdapter moAdapter;
    public RecyclerView moRecyclerView;
    public String[] moTitle;
    public RelativeLayout rl_main_wa;
    public int[] moTitleImage = {R.drawable.chat_new, R.drawable.message, R.drawable.black_massage};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_layout, container, false);

        context = getContext();

        DeviceModel = Build.MODEL;
        DeviceName = Build.MANUFACTURER;

        moTitle = getResources().getStringArray(R.array.open_in_whatsapp);
        moRecyclerView = view.findViewById(R.id.recycleView);
        rl_main_wa = view.findViewById(R.id.rl_main_wa);
        setAdapter();
        onItemClickEvent();

        if (Preference.getBooleanTheme(false)) {
            setStatusBarTheme();
            rl_main_wa.setBackgroundColor(ContextCompat.getColor(context, R.color.darkBlack));
        }

        return view;
    }

    private void setAdapter() {
        moRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        moAdapter = new CommonAdapter(context, moTitle, moTitleImage);
        moRecyclerView.setAdapter(moAdapter);
    }

    private void onItemClickEvent() {
        moRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, moRecyclerView, new RecyclerItemClickListener.ClickListener() {
            public void onLongClick(View view, int i) {
            }

            public void onClick(View view, int i) {
                if (i == 0) {
                    Advertisement.getInstance((Activity) context).showFull((Activity) context, () -> {
                        Intent moIntent = new Intent(context, ChatWithNumber.class);
                        startActivity(moIntent);
                    });

                } else if (i == 1) {
                    Advertisement.getInstance((Activity) context).showFull((Activity) context, () -> {
                        Intent moIntent = new Intent(context, ChatWithMsg.class);
                        startActivity(moIntent);
                    });
                } else if (i == 2) {
                    Advertisement.getInstance((Activity) context).showFull((Activity) context, () -> {
                        startActivity(new Intent(context, ChatWithBlankMessage.class));
                    });
                }
            }
        }));
    }
    public void setStatusBarTheme() {
        View view =getActivity().getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.darkBlack));
    }
}
