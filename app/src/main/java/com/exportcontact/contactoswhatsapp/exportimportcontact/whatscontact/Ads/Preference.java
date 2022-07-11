package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads;

import android.content.Context;
import android.content.SharedPreferences;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util.MyApplication;


public class Preference {
    private static final String active_Weekly = "weekly_key";
    private static final String active_Monthly = "monthly_key";
    private static final String active_Yearly = "yearly_key";
    private static final String weekly_price = "weekly_price";
    private static final String monthly_price = "monthly_price";
    private static final String yearly_price = "yearly_price";

    public static final String SWITCH2 = "globalSwitch";


    private static SharedPreferences get() {
        return MyApplication.getApp().getSharedPreferences("AppController", Context.MODE_PRIVATE);
    }
    
    public static void setBooleanTheme(Boolean bool) {
        get().edit().putBoolean(SWITCH2, bool).apply();
    }

    public static Boolean getBooleanTheme(Boolean bool) {
        return get().getBoolean(SWITCH2, bool);
    }



    public static String getActive_Weekly() {
        return get().getString(active_Weekly, "");
    }

    public static void setActive_Weekly(String value) {
        get().edit().putString(active_Weekly, value).apply();
    }

    public static String getActive_Monthly() {
        return get().getString(active_Monthly, "");
    }

    public static void setActive_Monthly(String value) {
        get().edit().putString(active_Monthly, value).apply();
    }

    public static String getActive_Yearly() {
        return get().getString(active_Yearly, "");
    }

    public static void setActive_Yearly(String value) {
        get().edit().putString(active_Yearly, value).apply();
    }


    public static String getWeekly_price() {
        return get().getString(weekly_price, "");
    }

    public static void setWeekly_price(String value) {
        get().edit().putString(weekly_price, value).apply();
    }


    public static String getMonthly_price() {
        return get().getString(monthly_price, "");
    }

    public static void setMonthly_price(String value) {
        get().edit().putString(monthly_price, value).apply();
    }

    public static String getYearly_price() {
        return get().getString(yearly_price, "");
    }

    public static void setYearly_price(String value) {
        get().edit().putString(yearly_price, value).apply();
    }

}
