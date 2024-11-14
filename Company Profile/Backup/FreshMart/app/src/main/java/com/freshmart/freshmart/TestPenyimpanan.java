package com.freshmart.freshmart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TestPenyimpanan {

    private final SharedPreferences pref;
    SharedPreferences.Editor editor;
    private final String PREF_NAME = "uji";

    private final String LOGIN = PREF_NAME + " LOGIN";
    private final String ID = PREF_NAME + " ID";

    @SuppressLint("CommitPrefEdits")
    public TestPenyimpanan(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
    }

    public void setLogin(Boolean login, String id) {
        editor.putBoolean(LOGIN, login).apply();
        editor.putString(ID, id).apply();
    }

    public Boolean getLogin() {
        return pref.getBoolean(LOGIN, false);
    }

    public String getId() {
        return pref.getString(ID, "");
    }

}