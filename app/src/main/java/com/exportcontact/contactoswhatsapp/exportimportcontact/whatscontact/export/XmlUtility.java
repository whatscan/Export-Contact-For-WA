package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.export;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet.ContactGS;
import com.itextpdf.text.html.HtmlTags;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlUtility {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void exportContacts(Context context, List<ContactGS> list, String str, boolean z, HttpServletResponse httpServletResponse) {
        new ExportDataToFile(context, list, str, z, httpServletResponse).execute(new String[0]);
    }

    public static class ExportDataToFile extends AsyncTask<String, Integer, String> {
        List<ContactGS> contacts = null;
        Context context = null;
        Document doc;
        boolean file_created = false;
        String file_name = null;
        String file_path = null;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public ExportDataToFile(Context context2, List<ContactGS> list, String str, boolean z, HttpServletResponse httpServletResponse) {
            this.context = context2;
            this.file_name = str;
            this.contacts = list;
            String str2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator + "WhatsApp Contact Export";
            if (!new File(str2).exists()) {
                new File(str2).mkdirs();
            }
            this.file_path = str2 + "/" + str;
        }

        public void onPreExecute() {
        }

        public String doInBackground(String... strArr) {
            try {
                Document newDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                this.doc = newDocument;
                Element createElement = newDocument.createElement("List");
                this.doc.appendChild(createElement);
                for (int i = 0; i < this.contacts.size(); i++) {
                    Element createElement2 = this.doc.createElement("Contacts");
                    createElement.appendChild(createElement2);
                    Element createElement3 = this.doc.createElement("Name");
                    createElement3.appendChild(this.doc.createTextNode(String.valueOf(this.contacts.get(i).getName())));
                    createElement2.appendChild(createElement3);
                    Element createElement4 = this.doc.createElement("Number");
                    createElement4.appendChild(this.doc.createTextNode(String.valueOf(this.contacts.get(i).getNumber())));
                    createElement2.appendChild(createElement4);
                }
                this.file_created = true;
                return null;
            } catch (Exception unused) {
                this.file_created = false;
                return null;
            }
        }

        public void onPostExecute(String str) {
            super.onPostExecute(str);
            try {
                Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
                newTransformer.setOutputProperty(HtmlTags.ENCODING, "ISO-8859-1");
                newTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                newTransformer.setOutputProperty(HtmlTags.INDENT, "yes");
                DOMSource dOMSource = new DOMSource(this.doc);
                newTransformer.transform(dOMSource, new StreamResult(new FileWriter(this.file_path + ".xml")));
            } catch (IOException | TransformerException unused) {
            }
        }
    }
}
