package com.example.drpet;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.drpet.Model.DBManager;
import com.example.drpet.Model.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

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
        Cursor cursor = dbManager.fetch();

        final EditText s_email = findViewById(R.id.signup_email);
        final EditText s_fname = findViewById(R.id.signup_fname);
        final EditText s_lname = findViewById(R.id.signup_lname);
        final EditText s_phone = findViewById(R.id.signup_phone);
        final EditText s_pwd = findViewById(R.id.signup_pwd);
        Button signup = findViewById(R.id.btn_signup);

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
                else if (s_pwd.getText().toString().equals("")){
                    s_pwd.setError("Fname is Required");
                    s_pwd.requestFocus();
                }
                else if (isValidEmail(s_email.getText())){
                    s_email.setError("Email not Valid");
                    s_email.requestFocus();
                    Toast.makeText(SignupActivity.this, "Provide a valid email address", Toast.LENGTH_SHORT).show();
                }
                else if (isValidPassword(s_pwd.getText().toString().trim())){
                    Toast.makeText(SignupActivity.this, "You need to have one upper case, one number and one specail character",
                            Toast.LENGTH_LONG).show();
                    s_pwd.requestFocus();
                }else if (dbHelper.checkUser(s_email.getText().toString())){
                    Toast.makeText(SignupActivity.this, "This Email address is already registered", Toast.LENGTH_SHORT).show();
                }else {
                    String email = s_email.getText().toString();
                    String fname = s_fname.getText().toString();
                    String lname = s_lname.getText().toString();
                    String phone = s_phone.getText().toString();
                    String pwd = s_pwd.getText().toString();

                    dbManager.insert(fname,lname, phone, email, pwd)    ;

                }
            }
        });

    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}