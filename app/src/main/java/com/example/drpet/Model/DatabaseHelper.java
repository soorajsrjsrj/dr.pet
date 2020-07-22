package com.example.drpet.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    //table_name
    public static final String TABLE_NAME = "profile";
    public static final String TABLE_PAYMENT = "payment";
    public static final String TABLE_LOCATION = "location";
    // Table columns
    public static final String id = "id";
    public static final String fName = "fName";
    public static final String lName = "lName";
    public static final String email = "email";
    public static final String password = "password";
    public static final String phone = "phone";
    public static final String profile_img = "profile_img";
    public static final String card_Id = "card_id";
    public static final String cardName = "cardName";
    public static final String cardNumber = "cardNumber";
    public static final String expiry = "expiry";
    public static final String cvv = "cvv";
    public static final String user_id = "user_id";
    public static final String location_Id = "card_id";
    public static final String latitude="latitude";
    public static final String logitude="logitude";

    // Database Information
    static final String DB_NAME = "DRPET.DB";
    // database version
    static final int DB_VERSION = 1;
    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME +
            "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + fName + " TEXT, " + lName + " TEXT, " + email + " TEXT, " +
            password + " TEXT, " + phone + " INTEGER, " + profile_img + " BLOB);" ;
    private static final String CREATE_PAYMENT = "create table " + TABLE_PAYMENT +
            "(" + card_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + cardName + " TEXT, " +
            cardNumber + " INTEGER, " + expiry + " INTEGER, " + cvv + " INTEGER, " +
            user_id + " INTEGER, PRIMARY KEY(" + user_id + ") REFERENCES  " + TABLE_NAME + " ( " + id + " ));" ;
    private static final String CREATE_LOCATION = "create table " + TABLE_LOCATION +
            "( " + location_Id + " INTEGER PRIMARY KEY AUTOINCREMENT," + latitude + " DOUBLE, " +
         logitude + " DOUBLE, "+ user_id + " INTEGER, FOREIGN KEY(" + user_id + ") REFERENCES  " + TABLE_NAME + " ( " + id + " ));" ;

//    " + location_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,
//            +
//            user_id + " INTEGER, FOREIGN KEY(" + user_id + ") REFERENCES  " + TABLE_NAME + " ( " + id + " ));" ;
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_PAYMENT);
        db.execSQL(CREATE_LOCATION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        onCreate(db);
    }
    /**
     * This method to check user exist or not
     *
     *
     */
    public boolean checkUser(String u_email, String u_password) {
        // array of columns to fetch
        String[] columns = {
                id
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = email + " = ?" + " AND " + password + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}