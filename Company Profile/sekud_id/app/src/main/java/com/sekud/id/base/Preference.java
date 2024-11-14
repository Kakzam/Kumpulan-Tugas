package com.sekud.id.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {

    private final SharedPreferences pref;
    SharedPreferences.Editor editor;
    private final String PREF_NAME = "chandra-pref";

    private final String NAME = PREF_NAME + " NAME";
    private final String USERNAME = PREF_NAME + " USERNAME";
    private final String PASSWORD = PREF_NAME + " PASSWORD";
    private final String STATUS = PREF_NAME + " STATUS";
    private final String ID = PREF_NAME + " ID";

    private final String UPDATE = PREF_NAME + " UPDATE";
    private final String LOGIN = PREF_NAME + " LOGIN";
    private final String LOADING = PREF_NAME + " LOADING";

    /* UPDATE_USER */
    private final String UPDATE_USERNAME = PREF_NAME + " UPDATE_USERNAME";
    private final String UPDATE_PASSWORD = PREF_NAME + " UPDATE_PASSWORD";
    private final String UPDATE_NAME = PREF_NAME + " UPDATE_NAME";
    private final String UPDATE_STATUS = PREF_NAME + " UPDATE_STATUS";
    private final String UPDATE_ID = PREF_NAME + " UPDATE_ID";

    /* LOCATION */
    private final String LOCATION_TITLE = PREF_NAME + " LOCATION_TITLE";
    private final String LOCATION_LINK = PREF_NAME + " LOCATION_LINK";
    private final String LOCATION_ID = PREF_NAME + " LOCATION_ID";

    @SuppressLint("CommitPrefEdits")
    public Preference(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
    }

    /* USER ------------------------------------------------------------------------------------- */

    public void setLogin(Boolean login) {
        editor.putBoolean(LOGIN, login).apply();
    }

    public Boolean getLogin() {
        return pref.getBoolean(LOGIN, false);
    }

    public void setUsername(String username) {
        editor.putString(USERNAME, username).apply();
    }

    public String getUsername() {
        return pref.getString(USERNAME, "");
    }

    public void setPassword(String password) {
        editor.putString(PASSWORD, password).apply();
    }

    public String getPassword() {
        return pref.getString(PASSWORD, "");
    }

    public void setName(String name) {
        editor.putString(NAME, name).apply();
    }

    public String getName() {
        return pref.getString(NAME, "");
    }

    public void setStatus(Boolean status) {
        editor.putBoolean(STATUS, status).apply();
    }

    public Boolean getStatus() {
        return pref.getBoolean(STATUS, false);
    }

    public void setId(String id) {
        editor.putString(ID, id).apply();
    }

    public String getId() {
        return pref.getString(ID, "");
    }

    public void setUpdateUser(Boolean update) {
        editor.putBoolean(UPDATE, update).apply();
    }

    public Boolean getUpdateUser() {
        return pref.getBoolean(UPDATE, false);
    }

    /* Update User ----------------------------------------------------------------- */

    public void setUpdateUsername(String username) {
        editor.putString(UPDATE_USERNAME, username).apply();
    }

    public String getUpdateUsername() {
        return pref.getString(UPDATE_USERNAME, "");
    }

    public void setUpdatePassword(String password) {
        editor.putString(UPDATE_PASSWORD, password).apply();
    }

    public String getUpdatePassword() {
        return pref.getString(UPDATE_PASSWORD, "");
    }

    public void setUpdateName(String name) {
        editor.putString(UPDATE_NAME, name).apply();
    }

    public String getUpdateName() {
        return pref.getString(UPDATE_NAME, "");
    }

    public void setUpdateStatus(Boolean status) {
        editor.putBoolean(UPDATE_STATUS, status).apply();
    }

    public Boolean getUpdateStatus() {
        return pref.getBoolean(UPDATE_STATUS, false);
    }

    public void setUpdateId(String id) {
        editor.putString(UPDATE_ID, id).apply();
    }

    public String getUpdateId() {
        return pref.getString(UPDATE_ID, "");
    }

    /* Dialog Location Fragment ----------------------------------------------------------------- */

    public void setLoading(String loading) {
        editor.putString(LOADING, loading).apply();
    }

    public String getLoading() {
        return pref.getString(LOADING, "");
    }

    /* Dialog Location Fragment ----------------------------------------------------------------- */

    public void setLocationTitle(String title) {
        editor.putString(LOCATION_TITLE, title).apply();
    }

    public String getLocationTitle() {
        return pref.getString(LOCATION_TITLE, "");
    }

    public void setLocationLink(String link) {
        editor.putString(LOCATION_LINK, link).apply();
    }

    public String getLocationLink() {
        return pref.getString(LOCATION_LINK, "");
    }

    public void setLocationId(String id) {
        editor.putString(LOCATION_ID, id).apply();
    }

    public String getLocationId() {
        return pref.getString(LOCATION_ID, "");
    }

}