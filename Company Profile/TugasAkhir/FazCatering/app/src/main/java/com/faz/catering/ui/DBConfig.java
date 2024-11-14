package com.faz.catering.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String FAZ_CATERING = "FAZ_CATERING.db";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, FAZ_CATERING, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tbl_masuk = "CREATE TABLE 'tbl_masuk' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'username' TEXT(100) NOT NULL," +
                "'password' TEXT(100) NOT NULL );";

        sqLiteDatabase.execSQL(tbl_masuk);

        String tbl_menu_catering = "CREATE TABLE 'tbl_menu_catering' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'nama' TEXT(100) NOT NULL," +
                "'deskripsi' TEXT(100) NOT NULL," +
                "'harga' TEXT(100) NOT NULL," +
                "'gambar' TEXT(100) NOT NULL," +
                "'handphone' TEXT NOT NULL," +
                "'lokasi' TEXT NOT NULL," +
                "'menu' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_menu_catering);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
