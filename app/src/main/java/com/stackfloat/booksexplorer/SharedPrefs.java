package com.stackfloat.booksexplorer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SharedPrefs {

    public static final String BOOKS_SHARED_PREFS = "com.stackfloat.booksexplorer.BooksSharedPrefs";
    private static final String DEFAULT_PREFS_STRING = "";
    private static final int DEFAULT_PREFS_INT = 0;
    public static final String QUERY = "Query";
    public static final String POSITION = "position";

    private  SharedPrefs(){}

    public static SharedPreferences getSharedPrefs(Context context){
        return context.getSharedPreferences(BOOKS_SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public static String getStringPref(Context context,String key){
        return getSharedPrefs(context).getString(key, DEFAULT_PREFS_STRING);
    }

    public static int getIntPref(Context context,String key){
        return getSharedPrefs(context).getInt(key, DEFAULT_PREFS_INT);
    }

    public static void setIntPref(Context context,String key, int value){
        SharedPreferences.Editor editor=getSharedPrefs(context).edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public static void setStringPref(Context context,String key, String value){
        SharedPreferences.Editor editor=getSharedPrefs(context).edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static ArrayList<String> getRecentAdvancedSearchLIst(Context context){
        ArrayList<String> recentAdvancedSearchLIst = new ArrayList<>();
        for (int i=1;i<=5;i++){
            String key = QUERY + i;
            String recentAdvancedSearch= getStringPref(context, key);
            if (!recentAdvancedSearch.isEmpty()) {
                recentAdvancedSearch= recentAdvancedSearch.replace(",", " ");
                recentAdvancedSearchLIst.add(recentAdvancedSearch.trim());
            }
        }
        return  recentAdvancedSearchLIst;
    }
}
