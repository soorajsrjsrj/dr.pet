package com.example.drpet.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database,d2;

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

    public void insert(String fName, String lName, String phone, String email) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.fName, fName);
        contentValue.put(DatabaseHelper.lName, lName);
        contentValue.put(DatabaseHelper.phone, phone);
        contentValue.put(DatabaseHelper.email, email);

        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public void insertIntoPayment(String cardName, String cardNumber, String expiry, String cvv) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.cardName, cardName);
        contentValue.put(DatabaseHelper.cardNumber, cardNumber);
        contentValue.put(DatabaseHelper.expiry, expiry);
        contentValue.put(DatabaseHelper.cvv, cvv);

        database.insert(DatabaseHelper.TABLE_PAYMENT, null, contentValue);
    }


    public Cursor fetch() {
        String[] columns = new String[] {DatabaseHelper.fName, DatabaseHelper.lName, DatabaseHelper.email, DatabaseHelper.phone };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchFromPayment() {
        String[] columns = new String[] {DatabaseHelper.cardName, DatabaseHelper.cardNumber, DatabaseHelper.expiry, DatabaseHelper.cvv };
        Cursor cursor = database.query(DatabaseHelper.TABLE_PAYMENT, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void update(String fName, String lName, String phone, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.fName, fName);
        contentValues.put(DatabaseHelper.lName, lName);
        contentValues.put(DatabaseHelper.email, email);
        contentValues.put(DatabaseHelper.phone, phone);
        database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.email + " = '" + email + "'" , null);
    }

    public void delete(String email) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.email + "=" + email, null);
    }

}

