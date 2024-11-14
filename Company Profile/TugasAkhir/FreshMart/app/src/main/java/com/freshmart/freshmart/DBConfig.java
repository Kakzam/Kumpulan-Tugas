package com.freshmart.freshmart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String FRESH_MART = "FRESH_MART.db";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, FRESH_MART, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tbl_pemakai = "CREATE TABLE 'tbl_pemakai' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'nama' TEXT(100) NOT NULL," +
                "'handphone' TEXT(100) NOT NULL," +
                "'username' TEXT(100) NOT NULL," +
                "'password' TEXT(100) NOT NULL," +
                "'type' TEXT(100) NOT NULL );";
        sqLiteDatabase.execSQL(tbl_pemakai);

        sqLiteDatabase.execSQL(
                "INSERT INTO tbl_pemakai (nama,handphone,username,password,type) VALUES(" +
                        "'Ristia Ningsih'," +
                        "'080808'," +
                        "'feb'," +
                        "'feb'," +
                        "'1')");


        String tbl_sayur = "CREATE TABLE 'tbl_sayur' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'nama_sayuran' TEXT(100) NOT NULL," +
                "'harga' TEXT(100) NOT NULL," +
                "'gambar' TEXT NOT NULL );";
        sqLiteDatabase.execSQL(tbl_sayur);

        String tbl_image_sayur = "CREATE TABLE 'tbl_image_sayur' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'image' TEXT(100) NOT NULL," +
                "'id_sayur' TEXT NOT NULL );";
        sqLiteDatabase.execSQL(tbl_image_sayur);


        String tbl_sayur_beli = "CREATE TABLE 'tbl_sayur_beli' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'nama_sayuran' TEXT(100) NOT NULL," +
                "'harga' TEXT(100) NOT NULL," +
                "'qty' TEXT(100) NOT NULL," +
                "'total' TEXT(100) NOT NULL," +
                "'id_pemakai' TEXT(100) NOT NULL," +
                "'gambar' TEXT NOT NULL );";
        sqLiteDatabase.execSQL(tbl_sayur_beli);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
