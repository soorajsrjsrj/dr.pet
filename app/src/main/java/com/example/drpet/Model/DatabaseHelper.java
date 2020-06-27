package com.example.drpet.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    //table_name
    public static final String TABLE_NAME = "profile";

    // Table columns
    public static final String id = "id";
    public static final String fName = "fName";
    public static final String lName = "lName";
    public static final String email = "email";
    public static final String password = "password";
    public static final String phone = "phone";
    public static final String profile_img = "profile_img";

    // Database Information
    static final String DB_NAME = "DRPET.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME +
            "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + fName + " TEXT, " + lName + " TEXT, " + email + " TEXT, " +
            password + " TEXT, " + phone + " INTEGER, " + profile_img + " BLOB);" ;

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
