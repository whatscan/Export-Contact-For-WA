package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.adpter.FileAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExportedFilesFragment extends Fragment {
    public Context context;
    public FileAdapter adapter;
    public TextView no_file;
    public RecyclerView recyclerView;
    public RelativeLayout rl_files_main;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.file_list, container, false);

        context = getContext();

        rl_files_main = view.findViewById(R.id.rl_files_main);
        no_file = view.findViewById(R.id.no_files);
        recyclerView = view.findViewById(R.id.recyclerView);

        if (getFiles("") == null || getFiles("").size() <= 0) {
            no_file.setVisibility(View.VISIBLE);
        } else {
            no_file.setVisibility(View.GONE);
            adapter = new FileAdapter(context, getFiles(""), no_file);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        }

        if (Preference.getBooleanTheme(false)) {
            setStatusBarTheme();

            rl_files_main.setBackgroundColor(ContextCompat.getColor(context, R.color.darkBlack));
        }

        return view;
    }

    public List<File> getFiles(String str) {
        File[] listFiles = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/" + "WhatsApp Contact Export").listFiles();
        ArrayList<File> arrayList = new ArrayList<>();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].getName().contains(str)) {
                    arrayList.add(listFiles[i]);
                }
            }
        }
        return arrayList;
    }

    public void setStatusBarTheme() {
        View view =getActivity().getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.darkBlack));
    }

}
