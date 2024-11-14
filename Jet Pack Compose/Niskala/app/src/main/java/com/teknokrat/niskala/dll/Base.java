package com.teknokrat.niskala.dll;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class Base extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public FirebaseFirestore db;

    private static final String PREF_NAME = "MyPreference";
    private static final String PERTAMA = "PERTAMA";
    private static final String MASUK = "MASUK";
    private static final String NAME = "NAME";
    private static final String ID = "ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        db = FirebaseFirestore.getInstance();
    }

    public void setLog(String log){
        Log.v("Niskala", log);
    }

    public void setToast(String toast){
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
    }

    public void setIntent(Class<?> activity){
        Intent intent = new Intent(getApplicationContext(), activity);
        startActivity(intent);
        finish();
    }

    public void setIntent(Class<?> activity, String data1, Boolean data2){
        Intent intent = new Intent(getApplicationContext(), activity);
        intent.putExtra("DATA_1", data1);
        intent.putExtra("DATA_2", data2);
        startActivity(intent);
        finish();
    }

    public Bitmap imageBase64(String base){
        byte[] decodedBytes = Base64.decode(base, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public void setPertama(Boolean pertama) {
        editor.putBoolean(PERTAMA, pertama);
        editor.apply();
    }

    public Boolean getPertama() {
        return sharedPreferences.getBoolean(PERTAMA, false);
    }

    public void setL(Boolean l) {
        editor.putBoolean(MASUK, l);
        editor.apply();
    }

    public Boolean getL() {
        return sharedPreferences.getBoolean(MASUK, false);
    }

    public void setN(String n) {
        editor.putString(NAME, n);
        editor.apply();
    }

    public String getN() {
        return sharedPreferences.getString(NAME, "EMPTY");
    }

    public void setId(String id) {
        editor.putString(ID, id);
        editor.apply();
    }

    public String getId() {
        return sharedPreferences.getString(ID, "EMPTY");
    }

    public void clearO() {
        editor.clear();
        editor.apply();
    }
}
