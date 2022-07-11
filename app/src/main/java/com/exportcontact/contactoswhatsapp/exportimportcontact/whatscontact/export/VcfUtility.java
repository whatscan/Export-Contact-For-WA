package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.export;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity.MainActivity;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet.ContactGS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.StructuredName;

public class VcfUtility {
    public static String FILE_EXTENSION = ".vcf";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void exportContacts(Context context, List<ContactGS> list, String str, boolean z, HttpServletResponse httpServletResponse) {
        new ExportDataToFile(context, list, str, z, httpServletResponse).execute(new String[0]);
    }

    public static VCard getVCard(Context context, ContactGS contactGS) throws IOException {
        VCard vCard = new VCard();
        String str = "";
        String name = contactGS.getName() == null ? str : contactGS.getName();
        if (contactGS.getNumber() != null) {
            str = contactGS.getNumber();
        }
        StructuredName structuredName = new StructuredName();
        structuredName.setGiven(name);
        vCard.setStructuredName(structuredName);
        vCard.addTelephoneNumber(str, TelephoneType.CELL);
        return vCard;
    }

    public static class ExportDataToFile extends AsyncTask<String, Integer, String> {
        List<ContactGS> contacts = null;
        Context context = null;
        boolean file_created = false;
        String file_name = null;
        String file_path = null;
        boolean fromSvt;
        HttpServletResponse response;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public ExportDataToFile(Context context2, List<ContactGS> list, String str, boolean z, HttpServletResponse httpServletResponse) {
            this.context = context2;
            this.file_name = str;
            this.contacts = list;
            this.response = httpServletResponse;
            this.fromSvt = z;
            String str2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator + "WhatsApp Contact Export";
            if (!new File(str2).exists()) {
                new File(str2).mkdirs();
            }
            this.file_path = str2 + "/" + this.file_name + VcfUtility.FILE_EXTENSION;
        }

        public void onPreExecute() {
        }

        public String doInBackground(String... strArr) {
            try {
                ArrayList arrayList = new ArrayList();
                if (this.contacts.size() <= 0) {
                    return null;
                }
                int size = this.contacts.size();
                for (int i = 0; i < size; i++) {
                    if (this.contacts.get(i) != null) {
                        arrayList.add(VcfUtility.getVCard(this.context, this.contacts.get(i)));
                    }
                }
                Ezvcard.write((Collection<VCard>) arrayList).go(new File(this.file_path));
                this.file_created = true;
                return null;
            } catch (Exception unused) {
                this.file_created = false;
                return null;
            }
        }

        public void onPostExecute(String str) {
            super.onPostExecute(str);
            if (this.file_created) {
                Conversion conversion = new Conversion();
                conversion.CONVERTED_FILE_PATH = this.file_path;
                conversion.CONVERTED_FILE_NAME = this.file_name;
                ArrayList<Conversion> convertedFiles = MainActivity.getConvertedFiles(this.context);
                convertedFiles.add(conversion);
                MainActivity.saveConvertedFiles(this.context, convertedFiles);
            }
        }
    }
}
