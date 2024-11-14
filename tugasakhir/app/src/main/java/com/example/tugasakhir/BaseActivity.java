package com.example.tugasakhir;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public static String nama;
    public static String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setLog(String message){
        Log.v("TugasAkhir", message);
    }

    public void setToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void setIntent(Class<?> activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void setNama(String data){
        nama = data;
    }

    public String getNama(){
        return nama;
    }

    public void setId(String data){
        id = data;
    }

    public String getId(){
        return id;
    }
}