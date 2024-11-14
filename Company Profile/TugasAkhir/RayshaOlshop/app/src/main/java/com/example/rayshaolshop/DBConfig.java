package com.example.rayshaolshop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConfig extends SQLiteOpenHelper {

    public static final String RAYSHA_OLSHOP = "RAYSHA_OLSHOP.db";
    public static final int DB_VERSION = 1;

    public DBConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, RAYSHA_OLSHOP, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tbl_customer = "CREATE TABLE 'tbl_customer' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'nama' TEXT(100) NOT NULL," +
                "'email' TEXT(100) NOT NULL," +
                "'password' TEXT(100) NOT NULL );";
        sqLiteDatabase.execSQL(tbl_customer);

        String tbl_category = "CREATE TABLE 'tbl_category' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'img_url' TEXT(100) NOT NULL," +
                "'name' TEXT(100) NOT NULL," +
                "'type' TEXT NOT NULL );";
        sqLiteDatabase.execSQL(tbl_category);
        sqLiteDatabase.execSQL("INSERT INTO tbl_category (img_url,name,type) VALUES('https://firebasestorage.googleapis.com/v0/b/rayshaolshop.appspot.com/o/icon_tas.jpg?alt=media&token=8b4096df-6aa9-4dcf-99e2-099d8403e816','Sepatu Sneaker Original','bag')");

        String tbl_new_products = "CREATE TABLE 'tbl_new_products' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'description' TEXT(100) NOT NULL," +
                "'name' TEXT(100) NOT NULL," +
                "'rating' TEXT(100) NOT NULL," +
                "'price' INT(100) NOT NULL," +
                "'img_url' TEXT NOT NULL );";
        sqLiteDatabase.execSQL(tbl_new_products);
        sqLiteDatabase.execSQL("INSERT INTO tbl_new_products (description,name,rating,price,img_url) VALUES('Jam Alexander Christie Original','watch','4.5','200000','https://firebasestorage.googleapis.com/v0/b/rayshaolshop.appspot.com/o/watch1.jpg?alt=media&token=2be14dfa-dafe-4b06-ab80-a3db68d121e7')");

        String tbl_popular_product = "CREATE TABLE 'tbl_popular_product' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'description' TEXT(100) NOT NULL," +
                "'name' TEXT(100) NOT NULL," +
                "'rating' TEXT(100) NOT NULL," +
                "'price' INT(100) NOT NULL," +
                "'img_url' TEXT NOT NULL );";
        sqLiteDatabase.execSQL(tbl_popular_product);
        sqLiteDatabase.execSQL("INSERT INTO tbl_popular_product (description,name,rating,price,img_url) VALUES('Sepatu Sneaker Original','sneaker','4.8','450000','https://firebasestorage.googleapis.com/v0/b/rayshaolshop.appspot.com/o/shoes3.jpg?alt=media&token=988a5b73-2049-411f-91f4-01e7068ba030')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
