package com.example.drpet;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.drpet.Model.DBManager;
import com.example.drpet.Model.DatabaseHelper;

public class Profile extends Fragment {

//public class profile extends AppCompatActivity {


    /*ImageView profile_image;
    TextView first;
    TextView last;//413 dimple
    TextView email;
    TextView phone;
    Button update;*/

    private DBManager dbManager;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper.email,
            DatabaseHelper.fName, DatabaseHelper.lName, DatabaseHelper.phone };

    final int[] to = new int[] { R.id.mail, R.id.last, R.id.first, R.id.phone};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);



            /*update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });*/
       /* profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Update Image", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbManager = new DBManager(getActivity().getApplicationContext());
        dbManager.open();
        Cursor cursor = dbManager.fetch();



        ImageView profile_image = (ImageView) getView().findViewById(R.id.profile_image);
        final TextView first = (TextView) getView().findViewById(R.id.first);
        final TextView last = (TextView) getView().findViewById(R.id.last);
        final TextView email = (TextView) getView().findViewById(R.id.mail);
        final TextView phone = (TextView) getView().findViewById(R.id.phone);
        Button update = (Button) getView().findViewById(R.id.btn_update);
        Button insert = (Button) getView().findViewById(R.id.btn_insert);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            first.setText(cursor.getString(0));
            last.setText(cursor.getString(1));
            email.setText(cursor.getString(2));
            phone.setText(cursor.getString(3));
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String upd_first = first.getText().toString();
                String upd_last = last.getText().toString();
                String upd_phone = phone.getText().toString();
                String upd_email = email.getText().toString();
                dbManager.update(upd_first, upd_last, upd_phone, upd_email);
            }
        });



        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity().getApplicationContext(), "Insert Button", Toast.LENGTH_SHORT).show();
                Context context = getActivity().getApplicationContext();
//                LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.linear);
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View v = layoutInflater.inflate(R.layout.user_input_form, null);


                final EditText s_email = (EditText) v.findViewById(R.id.email);
                final EditText fName = (EditText) v.findViewById(R.id.first_name);
                final EditText lName = (EditText) v.findViewById(R.id.last_name);
                final EditText s_phone = (EditText) v.findViewById(R.id.s_phone);



                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(false);
                ad.setTitle("Insert");
                ad.setView(v);
                ad.setButton(DialogInterface.BUTTON_POSITIVE, "Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String u_email = s_email.getText().toString();
                        String f_name = fName.getText().toString();
                        String l_name = lName.getText().toString();
                        String u_phone = s_phone.getText().toString();


                        dbManager.insert(f_name, l_name, u_phone, u_email);

                        dialogInterface.dismiss();
                    }
                });
                ad.show();


//                linearLayout.addView(v);
            }

        });


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Update Image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*@Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_profile);
           realm = Realm.getDefaultInstance();

           profile_image = findViewById(R.id.profile_image);
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
            profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                            Toast.makeText(profile.this, "Update Image",Toast.LENGTH_SHORT).show();
                }
            });
        }
        */
    /*private void saveData() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Number maxId = bgRealm.where(Profiles.class).max("Profile_id");
                int newKey = (maxId == null) ? 1 : maxId.intValue() + 1;
                Profiles profiles = bgRealm.createObject(Profiles.class, newKey);
                profiles.setProfile_email(email.getText().toString());
                profiles.setProfile_fname(first.getText().toString());
                profiles.setProfile_lname(last.getText().toString());
                //profiles.setProfile_phone(phone.getText().);


            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(profile.this, "Success", Toast.LENGTH_LONG).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(profile.this, "Fail", Toast.LENGTH_LONG).show();
            }
        });
    }*/
}


//    public void updateImage(View view){
//        Toast.makeText(profile.this, "Update Image",Toast.LENGTH_SHORT).show();
//    }

//    }
   /* @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}*/
