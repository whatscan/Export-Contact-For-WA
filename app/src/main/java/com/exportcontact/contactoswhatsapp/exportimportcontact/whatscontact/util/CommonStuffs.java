package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class CommonStuffs {
    static ConnectionDetector f501cd;

    public static boolean isInternetPresent(Context context) {
        f501cd = new ConnectionDetector(context);
        return f501cd.isConnectingToInternet();
    }

    public static void SnackBar(View view, String str, int i) {
        Snackbar.make(view, str, i).show();
    }

    @SuppressLint("WrongConstant")
    public static void hideKeyboardFrom(Context context, View view) {
        if (view != null) {
            ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @SuppressLint("WrongConstant")
    public static void setClipboard(Context context, String str) {
        ((android.content.ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Copied Text", str));
        Toast.makeText(context, "Copied To Clipboard", Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(View view, String str) {
        if (view != null) {
            try {
                Snackbar.make(view, (CharSequence) "" + str, -1).show();
            } catch (IllegalArgumentException | IllegalStateException | NullPointerException unused) {
            }
        }
    }

    public void showSnackBarLong(View view, String str) {
        if (view != null) {
            try {
                Snackbar.make(view, (CharSequence) "" + str, 0).show();
            } catch (IllegalArgumentException | IllegalStateException | NullPointerException unused) {
            }
        }
    }
}