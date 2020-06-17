package com.example.drpet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.drpet.Model.Profiles;

import io.realm.Realm;

public class Profile extends  Fragment {

public class profile extends AppCompatActivity
{
    Realm realm;
    TextView first;
    TextView last;
    TextView email;
    TextView phone;
    Button update;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
       realm = Realm.getDefaultInstance();
        first = findViewById(R.id.first);
        last = findViewById(R.id.last);
        email = findViewById(R.id.mail);
        phone = findViewById(R.id.phone);
        update = findViewById(R.id.btn_update);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              saveData();
            }
        });
    }
    private void saveData()
    {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Number maxId = bgRealm.where(Profiles.class).max("Profile_id");
                int newKey = (maxId == null) ? 1 : maxId.intValue()+1;
                Profiles profiles = bgRealm.createObject(Profiles.class,newKey);
                profiles.setProfile_email(email.getText().toString());
                profiles.setProfile_fname(first.getText().toString());
                profiles.setProfile_lname(last.getText().toString());
                //profiles.setProfile_phone(phone.getText().);




            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(profile.this,"Success",Toast.LENGTH_LONG).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
               Toast.makeText(profile.this, "Fail",Toast.LENGTH_LONG).show();
            }
        });
    }


}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
