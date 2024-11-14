package com.companyprofile.alzigugun.another;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    private final SharedPreferences pref;
    SharedPreferences.Editor editor;
    private final String PREF_NAME = "earth-pref";
    private final String LOGIN = PREF_NAME + "_LOGIN";

    public Preference(Context context){
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void removeAllData(){
        pref.edit().clear().apply();
    }

    public void setLogin(Boolean login){
        editor.putBoolean(LOGIN, login).apply();
    }

    public Boolean getLogin(){
        return pref.getBoolean(LOGIN, false);
    }

}
