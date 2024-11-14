package com.uber.trip;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String UBER_TRIP = "UBER_TRIP.db";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, UBER_TRIP, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbl_pengguna = "CREATE TABLE 'tbl_pengguna' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," + //0
                "'pilih' TEXT(100) NOT NULL," + //1
                "'username' TEXT(100) NOT NULL," + //2
                "'password' TEXT(100) NOT NULL," + //3
                "'nama' TEXT(100) NOT NULL );"; //4

        sqLiteDatabase.execSQL(tbl_pengguna);

        String tbl_trip = "CREATE TABLE 'tbl_trip' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'trip' TEXT(100) NOT NULL," +
                "'jpeg' TEXT(100) NOT NULL," +
                "'penjelasan' TEXT(100) NOT NULL," +
                "'biaya' TEXT(100) NOT NULL," +
                "'contact' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_trip);

        String tbl_gambar = "CREATE TABLE 'tbl_gambar' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'gambar' TEXT NOT NULL );";

        sqLiteDatabase.execSQL(tbl_gambar);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
