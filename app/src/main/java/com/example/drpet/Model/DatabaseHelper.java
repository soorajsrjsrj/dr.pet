package com.example.drpet.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    //table_name
    public static final String TABLE_NAME = "profile";

    // Table columns
    public static final String fName = "fName";
    public static final String lName = "lName";
    public static final String email = "email";
    public static final String phone = "phone";

    // Database Information
    static final String DB_NAME = "DRPET.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + fName + " TEXT, " + lName + " TEXT, " + email + " TEXT, " + phone + " INTEGER);" ;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
