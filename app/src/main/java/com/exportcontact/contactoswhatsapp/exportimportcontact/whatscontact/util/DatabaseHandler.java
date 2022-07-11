package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet.Contect;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contactsManager";
    private static final String KEY_PH_NO = "phone_number";
    private static final String TABLE_CONTACTS = "contacts";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE contacts(id INTEGER PRIMARY KEY AUTOINCREMENT,phone_number TEXT NOT NULL)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(sQLiteDatabase);
    }

    public void addContact(Contect contect) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PH_NO, contect.getContect());
        writableDatabase.insert(TABLE_CONTACTS, null, contentValues);
        writableDatabase.close();
    }

    public Contect getContact(int i) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor query = readableDatabase.query(TABLE_CONTACTS, new String[]{"id", KEY_PH_NO}, "id=?", new String[]{String.valueOf(i)}, null, null, null, null);
        if (query != null) {
            query.moveToFirst();
        }
        Contect contect = new Contect(query.getString(0), query.getString(1));
        if (query != null) {
            query.close();
        }
        readableDatabase.close();
        return contect;
    }

    public List<Contect> getAllContacts() {
        ArrayList<Contect> arrayList = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("SELECT  DISTINCT * FROM contacts", (String[]) null);
        if (rawQuery.moveToFirst()) {
            do {
                Contect contect = new Contect();
                contect.setId(rawQuery.getString(0));
                contect.setContect(rawQuery.getString(1));
                arrayList.add(contect);
            } while (rawQuery.moveToNext());
        }
        if (rawQuery != null) {
            rawQuery.close();
        }
        writableDatabase.close();
        return arrayList;
    }

    public void deleteContactById(String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(TABLE_CONTACTS, "id = ?", new String[]{str});
        writableDatabase.close();
    }
}
