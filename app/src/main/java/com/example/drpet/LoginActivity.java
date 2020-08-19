package com.example.drpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drpet.Model.DBManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginActivity extends AppCompatActivity {

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbManager = new DBManager(this);
        dbManager.open();

        final EditText l_email = findViewById(R.id.login_email);
        final EditText l_pwd = findViewById(R.id.login_pwd);
        final Button login_btn = findViewById(R.id.btn_login);
//        final Button login_btn = findViewById(R.id.btn_login);
        final TextView l_reg = findViewById(R.id.register_link);



        l_email.requestFocus();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(l_email.getText().toString())){
                    l_email.setError("Email field can't be Empty !");
                    l_email.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(l_email.getText().toString()).matches()){
                    l_email.setError("Provide a valid email address !");
                    l_email.requestFocus();
                }
                else if (TextUtils.isEmpty(l_pwd.getText().toString())){
                    l_pwd.setError("Password is Required");
                    l_pwd.requestFocus();
                }
                else if(!dbManager.checkUserExist(l_email.getText().toString())){
                    l_email.setError("This Email is not registered yet");
                }
                else{
                    final String login_email = l_email.getText().toString();
                    final String login_pwd = l_pwd.getText().toString();

                    if (dbManager.checkLogin(login_email, login_pwd)){

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("email_key", login_email);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        System.out.println("Email:" + login_email);
                        System.out.println("Password:" + login_pwd);

                        Toast.makeText(LoginActivity.this, "Incorrect Email or Password !!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        l_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

    }
}