package com.catering.dessert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Storage {

    private final SharedPreferences pref;
    SharedPreferences.Editor editor;
    private final String PREF_NAME = "chandra-pref";

    private final String LOGIN = PREF_NAME + " LOGIN";
    private final String ID = PREF_NAME + " ID";

    @SuppressLint("CommitPrefEdits")
    public Storage(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
    }

    public void setLogin(Boolean login) {
        editor.putBoolean(LOGIN, login).apply();
    }

    public Boolean getLogin() {
        return pref.getBoolean(LOGIN, false);
    }

    public void setId(String login) {
        editor.putString(ID, login).apply();
    }

    public String getId() {
        return pref.getString(ID, "");
    }

}