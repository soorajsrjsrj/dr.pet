package com.example.drpet.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    public void insert(String fName, String lName, String phone, String email, String password, byte[] image) throws SQLException {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.fName, fName);
        contentValue.put(DatabaseHelper.lName, lName);
        contentValue.put(DatabaseHelper.phone, phone);
        contentValue.put(DatabaseHelper.email, email);
        contentValue.put(DatabaseHelper.password, password);
        contentValue.put(DatabaseHelper.profile_img, image);


        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }
// insert payment method
    public void insertintopayment(String cName, String cNumber, String Expiry, String Cvv) throws SQLException {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.cardName, cName);
        contentValue.put(DatabaseHelper.cardNumber, cNumber);
        contentValue.put(DatabaseHelper.expiry, Expiry);
        contentValue.put(DatabaseHelper.cvv, Cvv);
        database.insert(DatabaseHelper.TABLE_PAYMENT, null, contentValue);
    }



    public Cursor fetchId(String u_email) {
        String query = "SELECT id FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.email + " = '" + u_email + "'";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public  Cursor fetchUserData(int user_id){


        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.id + " = " + user_id;
        Cursor cursor = database.rawQuery(query, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void update(int id, String fName, String lName, String phone, byte[] image) throws SQLException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.fName, fName);
        contentValues.put(DatabaseHelper.lName, lName);
        contentValues.put(DatabaseHelper.phone, phone);
        contentValues.put(DatabaseHelper.profile_img, image);
        database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.id + " = '" + id + "'" , null);
        System.out.println(image);
    }

    public void delete(String email) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.email + "=" + email, null);
    }

    public boolean checkUserExist(String u_email){

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE email = '" + u_email + "'";
        Cursor cursor = database.rawQuery(query, null);

        if(cursor.getCount() > 0){
            return true;
        }


        return false;
    }

    public boolean checkLogin(String u_email,  String u_pwd){
        String query = "SELECT id FROM " + DatabaseHelper.TABLE_NAME + " WHERE email = '" + u_email + "' AND password = '" + u_pwd + "'";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() > 0 ){
            return true;
        }

        return false;
    }

}

