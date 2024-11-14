package com.kost.ku;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String KOST_KU = "KOST_KU";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, KOST_KU, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tbl_pengguna = "CREATE TABLE 'tbl_pengguna' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'username' TEXT(100) NOT NULL," +
                "'password' TEXT(100) NOT NULL," +
                "'nama' TEXT(100) NOT NULL," +
                "'pilih' TEXT(100) NOT NULL );";
        sqLiteDatabase.execSQL(tbl_pengguna);

        sqLiteDatabase.execSQL("INSERT INTO tbl_pengguna (username,password,nama,pilih) VALUES(" +
                "'a'," +
                "'a'," +
                "'Administrator'," +
                "'" + "admin" + "')");


        String tbl_kost = "CREATE TABLE 'tbl_kost' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," + //0
                "'kost' TEXT(100) NOT NULL," + //1
                "'alamat' TEXT(100) NOT NULL," + //2
                "'jenis' TEXT(100) NOT NULL," + //3
                "'fasilitas' TEXT(100) NOT NULL," + //4
                "'phone' TEXT NOT NULL," + //5
                "'gambar' TEXT NOT NULL );"; //6

        sqLiteDatabase.execSQL(tbl_kost);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
