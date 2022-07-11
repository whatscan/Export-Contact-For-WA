package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.export;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity.MainActivity;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet.ContactGS;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class PdfUtility {
    public static String FILE_EXTENSION = ".pdf";
    public static String FILE_TYPE = "PDF";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void exportContacts(Context context, List<ContactGS> list, String str, boolean z, HttpServletResponse httpServletResponse) {
        new ExportDataToFile(context, list, str, z, httpServletResponse).execute(new String[0]);
    }

    public static String writeContact(Context context, ContactGS contactGS) {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder();
        String name = contactGS.getName() == null ? "" : contactGS.getName();
        if (contactGS.getNumber() == null) {
            str = "";
        } else {
            str = contactGS.getNumber();
        }
        if (name.isEmpty()) {
            str2 = "";
        } else {
            str2 = "<b>" + name + "</b>";
        }
        if (str2.endsWith("<br/><br/>")) {
            str2 = str2.substring(0, str2.lastIndexOf("<br/><br/>"));
        }
        if (!str2.isEmpty()) {
            sb.append("<br/><font size='5'><b>" + context.getString(R.string.name) + "</b></font><br/><font size='6'>" + str2 + "</font><br/>");
        }
        String str3 = "" + "<b>" + str + "</b><br/>";
        if (str3.endsWith("<br/><br/>")) {
            str3 = str3.substring(0, str3.lastIndexOf("<br/><br/>"));
        }
        if (!str3.isEmpty()) {
            String str4 = "<font size='5'><b>" + context.getString(R.string.number) + "</b></font><br/><font size='6'>" + str3 + "</font>";
            if (!sb.toString().isEmpty()) {
                str4 = "<br/>" + str4;
            }
            sb.append(str4);
        }
        return sb.toString();
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
            this.file_path = str2 + "/" + this.file_name + PdfUtility.FILE_EXTENSION;
        }

        public void onPreExecute() {
        }

        public String doInBackground(String... strArr) {
            try {
                if (this.contacts.size() <= 0) {
                    return null;
                }
                Document document = new Document(PageSize.A4);
                PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(this.file_path));
                document.open();
                int size = this.contacts.size();
                StringBuilder sb = new StringBuilder();
                sb.append(new String("<html><body>"));
                for (int i = 0; i < size; i++) {
                    if (this.contacts.get(i) != null) {
                        sb.append("<font color='#FF0000' size='7'><b>" + (i + 1) + "</b></font><br/><br/>" + PdfUtility.writeContact(this.context, this.contacts.get(i)) + "<br/><br/><br/><br/>");
                    }
                }
                sb.append(new String("</body></html>"));
                XMLWorkerHelper.getInstance().parseXHtml(instance, document, (InputStream) new ByteArrayInputStream(sb.toString().getBytes()), Charset.forName("UTF-8"));
                document.close();
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
