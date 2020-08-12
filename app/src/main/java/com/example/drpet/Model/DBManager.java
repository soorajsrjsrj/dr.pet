package com.example.drpet.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.drpet.RideDetail;

import java.util.ArrayList;

import static com.example.drpet.Model.DatabaseHelper.TABLE_RETAILS;
import static com.example.drpet.Model.DatabaseHelper.card_Id;
import static com.example.drpet.Model.DatabaseHelper.id;
import static com.example.drpet.Model.DatabaseHelper.location_Id;

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
    public void insertintopayment(String cName, String cNumber, String Expiry, String Cvv, int user_id) throws SQLException {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.cardName, cName);
        contentValue.put(DatabaseHelper.cardNumber, cNumber);
        contentValue.put(DatabaseHelper.expiry, Expiry);
        contentValue.put(DatabaseHelper.cvv, Cvv);
        contentValue.put(DatabaseHelper.user_id, user_id);
        database.insert(DatabaseHelper.TABLE_PAYMENT, null, contentValue);
    }
    //insert into location method
    public void insertintolocation(Double lat, Double log,int user_id) throws SQLException {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.latitude, lat);
        contentValue.put(DatabaseHelper.logitude, log);
       contentValue.put(DatabaseHelper.user_id, user_id);
        database.insert(DatabaseHelper.TABLE_LOCATION, null, contentValue);
    }
    public void insertintorideDetail(String hospName,String hospAddress,Double distance, Double price,int user_id) throws SQLException {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.hospName, hospName);
        contentValue.put(DatabaseHelper.hospAddres, hospAddress);
        contentValue.put(DatabaseHelper.hospDistnace, distance);
        contentValue.put(DatabaseHelper.hospPrice, price);
        contentValue.put(DatabaseHelper.user_id, user_id);
        database.insert(DatabaseHelper.TABLE_RETAILS, null, contentValue);
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
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + id + " = " + user_id;
        Cursor cursor = database.rawQuery(query, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchlocationData(int location_id){
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_LOCATION + " WHERE " + location_Id + " = " + location_id;
        Cursor cursor = database.rawQuery(query, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;

    }
    public Cursor fetchpaymentData(int user_id){
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_PAYMENT + " WHERE " + card_Id + " = " + user_id;
        Cursor cursor = database.rawQuery(query, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;

    }
    //////
    //ride methods
    public ArrayList<RideDetail> getAllData() {
        ArrayList<RideDetail> doglist = new ArrayList<>();

        Cursor res = database.rawQuery("select * from "+TABLE_RETAILS,null);

        while(res.moveToNext()) {
            String name = res.getString(0);   //0 is the number of id column in your database table
            String address = res.getString(1);
            String distance = res.getString(2);
            String price = res.getString(3);


            RideDetail newDog = new RideDetail(name,address,distance,price);
            doglist.add(newDog);
        }
        return doglist;
    }


    public Cursor fetchrideData(int user_id){
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_RETAILS + " WHERE " + user_id + " = " + user_id;
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

    public void updateintolocation(int user_id,Double lat, Double log) throws SQLException {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.latitude, lat);
        contentValue.put(DatabaseHelper.logitude, log);
//        contentValue.put(DatabaseHelper.user_id, user_id);

        database.update(DatabaseHelper.TABLE_LOCATION, contentValue, DatabaseHelper.user_id + " = '" + user_id + "'" , null);
    }
//update the location
//    public void updateintolocation(Double lat, Double log) throws SQLException {
//        ContentValues contentValue = new ContentValues();
//        contentValue.put(DatabaseHelper.latitude, lat);
//        contentValue.put(DatabaseHelper.logitude, log);
//
//        database.update(DatabaseHelper.TABLE_LOCATION, null);
//    }
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