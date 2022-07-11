package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.export;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity.MainActivity;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet.ContactGS;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class HtmlUtility {
    public static String FILE_EXTENSION = ".html";
    public static String FILE_TYPE = "HTML";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void exportContacts(Context context, List<ContactGS> list, String str, boolean z, HttpServletResponse httpServletResponse) {
        new ExportDataToFile(context, list, str, z, httpServletResponse).execute(new String[0]);
    }

    public static class ExportDataToFile extends AsyncTask<String, Integer, String> {
        public String filename;
        List<ContactGS> contacts = null;
        Context context = null;
        boolean file_created = false;
        String file_name = null;
        String file_path = null;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public ExportDataToFile(Context context2, List<ContactGS> list, String str, boolean z, HttpServletResponse httpServletResponse) {
            this.context = context2;
            this.contacts = list;
            this.file_name = str;
            String str2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator + "WhatsApp Contact Export";
            if (!new File(str2).exists()) {
                new File(str2).mkdirs();
            }
            this.file_path = str2 + "/" + this.file_name + HtmlUtility.FILE_EXTENSION;
        }

        public void onPreExecute() {
        }

        public String doInBackground(String... strArr) {
            try {
                String str = "<table border=1 width='100%25' style=border-spacing:0px;>";
                for (int i = 0; i < this.contacts.size(); i++) {
                    str = (str + "<tr>") + ("" + "<td style='font-size:14; padding: 15px; text-align: center;'>" + this.contacts.get(i).getName() + " </td><td style='font-size:14; padding: 15px; text-align: center;'>" + this.contacts.get(i).getNumber() + " </td>") + "</tr>";
                }
                FileUtils.writeStringToFile(new File(this.file_path), (str + "</table>").toString(), "utf-8");
                this.file_created = true;
                return null;
            } catch (Exception e) {
                e.printStackTrace();
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

        public String getFileName(String str, String str2, String str3) {
            String str4 = str2 + str3;
            String str5 = str + "/" + str2 + str3;
            boolean z = true;
            int i = 1;
            while (z) {
                if (!new File(str5).exists()) {
                    z = false;
                } else {
                    String str6 = str + "/" + str2 + "(" + i + ")" + str3;
                    i++;
                    str5 = str6;
                    str4 = str2 + "(" + i + ")" + str3;
                }
            }
            return str4;
        }
    }
}
