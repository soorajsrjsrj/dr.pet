package com.example.drpet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drpet.Model.DBManager;
import com.example.drpet.Model.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private DBManager dbManager;
    private DatabaseHelper dbHelper;

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbManager = new DBManager(this);
        dbManager.open();



        final EditText s_email = findViewById(R.id.signup_email);
        final EditText s_fname = findViewById(R.id.signup_fname);
        final EditText s_lname = findViewById(R.id.signup_lname);
        final EditText s_phone = findViewById(R.id.signup_phone);
        final EditText s_pwd = findViewById(R.id.signup_pwd);
        TextView signup = findViewById(R.id.btn_signup);
        s_email.requestFocus();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s_email.getText().toString().equals("")){
                    s_email.setError("Email is Required");
                    s_email.requestFocus();
                }
                else if (s_fname.getText().toString().equals("")){
                    s_fname.setError("First Name is Required");
                    s_fname.requestFocus();
                }
                else if (s_lname.getText().toString().equals("")){
                    s_lname.setError("Last Name is Required");
                    s_lname.requestFocus();
                }
                else if (s_phone.getText().toString().equals("")){
                    s_phone.setError("Contact No. is Required");
                    s_phone.requestFocus();
                }
                else if (s_phone.getText().toString().length() < 10){
                    s_phone.setError("Contact No. must be 10 digits");
                    s_phone.requestFocus();
                }
                else if (s_pwd.getText().toString().equals("")){
                    s_pwd.setError("Fname is Required");
                    s_pwd.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(s_email.getText().toString()).matches()){
                    s_email.setError("Email not Valid");
                    s_email.requestFocus();
                    Toast.makeText(SignupActivity.this, "Provide a valid email address", Toast.LENGTH_SHORT).show();
                }
                else if (s_pwd.getText().toString().length() < 6){
//                    Toast.makeText(SignupActivity.this, "Password Should be minimum six Characters" Toast.LENGTH_LONG).show();
                    s_pwd.setError("Password Should be minimum six Characters");
                    s_pwd.requestFocus();
                }
                else if (dbManager.checkUserExist(s_email.getText().toString())){
                    s_email.setError("This Email address is already registered");
                    s_email.requestFocus();
//                    Toast.makeText(SignupActivity.this, "This Email address is already registered", Toast.LENGTH_SHORT).show();
                }
                else {
                    String email = s_email.getText().toString();
                    String fname = s_fname.getText().toString();
                    String lname = s_lname.getText().toString();
                    String phone = s_phone.getText().toString();
                    String pwd = s_pwd.getText().toString();

                    System.out.println(pwd);

                    Drawable drawable = getResources().getDrawable(R.drawable.add_image);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
                    Bitmap bt = bitmapDrawable.getBitmap();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bt.compress(Bitmap.CompressFormat.PNG, 50, stream);
                    byte[] bitmapData = stream.toByteArray();


//                    byte[] image = getBytes(bt);
                    System.out.println("Blob:" + bitmapData);

                    /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.add_image);
                    byte[] image = getBytes(bitmap);*/

                    dbManager.insert(fname,lname, phone, email, pwd, bitmapData);
                    Toast.makeText(SignupActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    intent.putExtra("email_key", email);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        System.out.println("bitmap to byte array");
        System.out.println(stream.toByteArray());
        return stream.toByteArray();

    }

}