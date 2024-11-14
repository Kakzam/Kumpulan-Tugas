package com.ayamgeprek.agh;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String AGH = "AGH.db";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, AGH, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tbl_pengguna_geprek = "CREATE TABLE 'tbl_pengguna_geprek' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," + //0
                "'all_name' TEXT(100) NOT NULL," + //1
                "'number' TEXT(100) NOT NULL," + //2
                "'user' TEXT(100) NOT NULL," + //3
                "'sandi' TEXT(100) NOT NULL," + //4
                "'very' TEXT(100) NOT NULL );"; //5

        sqLiteDatabase.execSQL(tbl_pengguna_geprek);

        String tbl_menu_geprek = "CREATE TABLE 'tbl_menu_geprek' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'east' TEXT(100) NOT NULL," +
                "'price' TEXT(100) NOT NULL," +
                "'image' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_menu_geprek);

        String tbl_pesan_geprek = "CREATE TABLE 'tbl_pesan_geprek' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'id_geprek' TEXT(100) NOT NULL," +
                "'east' TEXT(100) NOT NULL," +
                "'price' TEXT(100) NOT NULL," +
                "'image' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_pesan_geprek);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
