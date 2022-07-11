package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
    Context context;

    public ConnectionDetector(Context context2) {
        this.context = context2;
    }

    public boolean isConnectingToInternet() {
        Context context2 = this.context;
        if (context2 == null) {
            return false;
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context2.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.getType() == 1 || activeNetworkInfo.getType() == 0;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }
}
