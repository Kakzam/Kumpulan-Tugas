package com.uber.trip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Admin {

    private final SharedPreferences pref;
    SharedPreferences.Editor editor;
    private final String PREF_NAME = "PREF_NAME";

    private final String ADMIN = PREF_NAME + " ADMIN";

    @SuppressLint("CommitPrefEdits")
    public Admin(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
    }

    public void setAdmin(Boolean login) {
        editor.putBoolean(ADMIN, login).apply();
    }

    public Boolean getAdmin() {
        return pref.getBoolean(ADMIN, false);
    }

}