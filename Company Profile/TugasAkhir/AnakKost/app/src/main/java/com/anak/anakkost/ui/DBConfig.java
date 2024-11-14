package com.anak.anakkost.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String ANAK_KOST = "ANAK_KOST";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, ANAK_KOST, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tbl_pengguna = "CREATE TABLE 'tbl_pengguna' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'nama_depan' TEXT(100) NOT NULL," +
                "'nama_belakang' TEXT(100) NOT NULL," +
                "'no_hp' TEXT(100) NOT NULL," +
                "'password' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_pengguna);

        String tbl_kost = "CREATE TABLE 'tbl_kost' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'judul' TEXT(100) NOT NULL," +
                "'harga' TEXT(100) NOT NULL," +
                "'deskripsi' TEXT(100) NOT NULL," +
                "'lokasi' TEXT(100) NOT NULL," +
                "'image' TEXT NOT NULL," +
                "'menu' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_kost);

        String tbl_password = "CREATE TABLE 'tbl_password' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'password' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_password);

        sqLiteDatabase.execSQL("INSERT INTO tbl_password (id,password) VALUES(" + "'0'," + "'admin')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
