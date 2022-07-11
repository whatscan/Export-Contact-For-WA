package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.adpter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.fileViewHolder> {
    public Context context;
    public List<File> files;
    TextView no_file;
    Typeface f446tf;

    public FileAdapter(Context context2, List<File> list, TextView textView) {
        this.context = context2;
        this.files = list;
        this.no_file = textView;
        this.f446tf = Typeface.createFromAsset(context2.getAssets(), "fonts/roboto-regular.ttf");
    }

    public fileViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new fileViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list, viewGroup, false), i);
    }

    public void onBindViewHolder(final fileViewHolder fileviewholder, final int position) {

        if (Preference.getBooleanTheme(false)) {
            fileviewholder.relativeLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_black));
            fileviewholder.tvName.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        }

        fileviewholder.tvName.setTypeface(this.f446tf);
        fileviewholder.tvName.setText(this.files.get(position).getName());
        fileviewholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FileAdapter.this.openFile(position);
            }
        });

        fileviewholder.imgShare.setOnClickListener(v -> share(position));

        fileviewholder.imgDelete.setOnClickListener(v -> {
             BottomSheetDialog bottomSheetDialog;

            bottomSheetDialog = new BottomSheetDialog(context);
            View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null);
            bottomSheetDialog.setContentView(inflate);

            LinearLayout ll_delete_dialog = inflate.findViewById(R.id.ll_delete_dialog);
            TextView txtYes = inflate.findViewById(R.id.txtYes);
            TextView txtNo = inflate.findViewById(R.id.txtNo);
            TextView txtDelete = inflate.findViewById(R.id.txtDelete);


            if (Preference.getBooleanTheme(false)) {
                ll_delete_dialog.setBackgroundColor(ContextCompat.getColor(context, R.color.darkBlack));
                txtDelete.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            }

            txtYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(position);
                    bottomSheetDialog.dismiss();
                }
            });

            txtNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                }
            });
            bottomSheetDialog.setCancelable(false);
            bottomSheetDialog.show();


//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setCancelable(true);
//            builder.setMessage(context.getResources().getString(R.string.delete_chat));
//            builder.setPositiveButton(context.getString(R.string.yes), (dialogInterface, i) -> delete(position));
//            builder.setNegativeButton(context.getString(R.string.no), (dialogInterface, i) -> {
//            });
//            final AlertDialog create = builder.create();
//            create.setOnShowListener(dialogInterface -> {
//                create.getButton(-1).setTextColor(context.getResources().getColor(R.color.colorTools));
//                create.getButton(-2).setTextColor(context.getResources().getColor(R.color.colorBlack));
//            });
//            create.show();
        });


    }


    public void delete(int i) {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(externalStoragePublicDirectory, "WhatsApp Contact Export/" + this.files.get(i).getName());
        Context context2 = this.context;
        this.context.getContentResolver().delete(FileProvider.getUriForFile(context2, this.context.getApplicationContext().getPackageName() + ".provider", file), (String) null, (String[]) null);
        this.files.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, this.files.size());
        if (this.files.size() <= 0) {
            this.no_file.setVisibility(0);
        } else {
            this.no_file.setVisibility(8);
        }
    }


    public void share(int i) {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        Intent intent = new Intent("android.intent.action.SEND");
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (new File(externalStoragePublicDirectory, "WhatsApp Contact Export/" + this.files.get(i).getName()).exists()) {
            intent.setType("text/*");
            intent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/WhatsApp Contact Export/" + this.files.get(i).getName()));
            intent.putExtra("android.intent.extra.SUBJECT", "Share File...");
            this.context.startActivity(Intent.createChooser(intent, "Share File"));
        }
    }

    private void openFile(int i) {
        try {
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(externalStoragePublicDirectory, "WhatsApp Contact Export/" + this.files.get(i).getName());
            Log.d("FILE___", "" + file);
            Intent intent = new Intent("android.intent.action.VIEW");
            Context context2 = this.context;
            intent.setDataAndType(FileProvider.getUriForFile(context2, this.context.getPackageName() + ".provider", file), "*/*");
            intent.setFlags(1);
            intent.addFlags(268435456);
            this.context.startActivity(intent);
        } catch (Exception unused) {
            Context context3 = this.context;
            Toast.makeText(context3, context3.getResources().getString(R.string.no_file_found), 0).show();
        }
    }

    public int getItemCount() {
        return this.files.size();
    }

    public class fileViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgShare, imgDelete;
        RelativeLayout relativeLayout;
        TextView tvName;

        public fileViewHolder(View view, int i) {
            super(view);
            this.tvName = (TextView) view.findViewById(R.id.filename);
            this.relativeLayout = (RelativeLayout) view.findViewById(R.id.lay_file);
            imgShare = view.findViewById(R.id.imgShare);
            imgDelete = view.findViewById(R.id.imgDelete);
        }
    }
}
