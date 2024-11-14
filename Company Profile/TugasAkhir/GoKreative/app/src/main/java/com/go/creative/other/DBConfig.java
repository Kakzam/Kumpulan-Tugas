package com.go.creative.other;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String GO_CREATIVE = "GO_CREATIVE";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, GO_CREATIVE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tbl_desain = "CREATE TABLE 'tbl_desain' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'nama' TEXT(100) NOT NULL," +
                "'harga' TEXT(100) NOT NULL," +
                "'terjual' TEXT(100) NOT NULL," +
                "'gambar' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_desain);


        String tbl_sosmed = "CREATE TABLE 'tbl_sosmed' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'sosmed' TEXT(100) NOT NULL," +
                "'nama' TEXT(100) NOT NULL," +
                "'link' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_sosmed);

        String tbl_pin = "CREATE TABLE 'tbl_pin' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'pin' TEXT NOT NULL );";
        sqLiteDatabase.execSQL(tbl_pin);
        sqLiteDatabase.execSQL("INSERT INTO tbl_pin (pin) VALUES(" + "'masuk')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
