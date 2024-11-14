package com.square.hijab.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String HIJAB_SQUARE = "HIJAB_SQUARE";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, HIJAB_SQUARE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tbl_hijab = "CREATE TABLE 'tbl_hijab' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'nama' TEXT(100) NOT NULL," +
                "'harga' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_hijab);

        String tbl_gambar_hijab = "CREATE TABLE 'tbl_gambar_hijab' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'id_hijab' TEXT(100) NOT NULL," +
                "'gambar' TEXT NOT NULL );";
        sqLiteDatabase.execSQL(tbl_gambar_hijab);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
