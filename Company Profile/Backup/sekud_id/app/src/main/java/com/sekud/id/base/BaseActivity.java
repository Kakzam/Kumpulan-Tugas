package com.sekud.id.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sekud.id.R;
import com.sekud.id.feature.alert.LoadingFragment;

import java.io.ByteArrayOutputStream;

public class BaseActivity extends AppCompatActivity {

    public String INTENT_1 = "INTENT_1";
    public String INTENT_2 = "INTENT_2";
    public String EMPTY = "EMPTY";

    LoadingFragment loadingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadingFragment = new LoadingFragment();
    }

    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap decodeTobase64(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public void setAlerttDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(Html.fromHtml(title))
                .setMessage(Html.fromHtml(message))
                .setNeutralButton("Ya", (dialog, id) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    public void setAlerttDialogForce(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(Html.fromHtml(title))
                .setMessage(Html.fromHtml(message))
                .setCancelable(false)
//                .setNeutralButton("Ya", (dialog, id) -> {
//                    dialog.dismiss();
//                })
                .create()
                .show();
    }

    public void setAlerttDialogExit() {
        new AlertDialog.Builder(this)
                .setTitle(Html.fromHtml(StringUtil.WARNING))
                .setMessage(Html.fromHtml(StringUtil.PUBLIC_MESSAGE_1))
                .setCancelable(false)
                .setNeutralButton(StringUtil.PUBLIC_MESSAGE_1_YES, (dialog, id) -> {
                    finishAffinity();
                })
                .setNegativeButton(StringUtil.NO, ((dialog, id) -> {
                    dialog.dismiss();
                }))
                .create()
                .show();
    }

    public void setLog(String log) {
        Log.v("ZAM", log);
    }

    public void setLoading(String loading) {
        new Preference(this).setLoading(loading);
        loadingFragment.show(getSupportFragmentManager(), "LoadingFragment");
    }

    public void dismissLoading() {
        new Preference(this).setLoading(EMPTY);
        loadingFragment.dismiss();
    }

    public void setActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        finish();
    }

    public void setActivityNoFinish(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void setActivity(Class<?> activity1, String value1, String value2) {
        Intent intent = new Intent(this, activity1);
        intent.putExtra(INTENT_1, value1);
        intent.putExtra(INTENT_2, value2);
        startActivity(intent);
        finish();
    }

}