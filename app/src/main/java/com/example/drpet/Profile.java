package com.example.drpet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import androidx.fragment.app.FragmentTransaction;

import com.example.drpet.Model.DBManager;
import com.example.drpet.Model.DatabaseHelper;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.net.URI;

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

    ImageView profile_image;

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

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("id_pref", Context.MODE_PRIVATE);
        final int user_id = pref.getInt("key_id", 0);

        dbManager = new DBManager(getActivity().getApplicationContext());
        dbManager.open();
        Cursor cursor = dbManager.fetchUserData(user_id);



        profile_image = (ImageView) getView().findViewById(R.id.profile_image);
        final TextView first = (TextView) getView().findViewById(R.id.first);
        final TextView last = (TextView) getView().findViewById(R.id.last);
        final TextView email = (TextView) getView().findViewById(R.id.mail);
        final TextView phone = (TextView) getView().findViewById(R.id.phone);
        Button update = (Button) getView().findViewById(R.id.btn_update);
//        Button insert = (Button) getView().findViewById(R.id.btn_insert);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            first.setText(cursor.getString(1));
            last.setText(cursor.getString(2));
            email.setText(cursor.getString(3));
            phone.setText(cursor.getString(5));
            byte[] image = cursor.getBlob(6);

            /*System.out.println(cursor.getString(1));
            System.out.println(cursor.getString(2));
            System.out.println(cursor.getString(3));
            System.out.println(cursor.getString(5));
            System.out.println(image);*/

            Bitmap profile = getImage(image);
            profile_image.setImageBitmap(profile);

//            insert.setVisibility(View.INVISIBLE);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String upd_first = first.getText().toString();
                String upd_last = last.getText().toString();
                String upd_phone = phone.getText().toString();
                String upd_email = email.getText().toString();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) profile_image.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                byte[] profile_img;

                if (bitmap != null){
                    profile_img = getBytes(bitmap);
                }else{
                    profile_img = null;
                }
                int id = user_id;


                dbManager.update(id, upd_first, upd_last, upd_phone,  profile_img);

                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileDetailFragment()).commit();

                /*Fragment frg = null;
                frg = getFragmentManager().findFragmentById(R.id.fragment_container);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();*/

                Toast.makeText(getActivity().getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();

            }
        });

        /*profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*ImagePicker.Companion.with(Profile.this)
                        .cropSquare()
                        .start();*//*
            }
        });*/



        /*insert.setOnClickListener(new View.OnClickListener() {
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
                final ImageView prof_pick = (ImageView) v.findViewById(R.id.prof_pick);



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

                        BitmapDrawable Drawable = (BitmapDrawable) prof_pick.getDrawable();
                        Bitmap bt = Drawable.getBitmap();

                        byte[] pro_pick = getBytes(bt);
                        String pwd = "";
                        dbManager.insert(f_name, l_name, u_phone, u_email, pwd, pro_pick);
                        dialogInterface.dismiss();
                    }
                });
                ad.show();


//                linearLayout.addView(v);
            }

        });*/


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Update Image", Toast.LENGTH_SHORT).show();
                ImagePicker.Companion.with(Profile.this)
                        .cropSquare()
                        .compress(1024)
                        .start();
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

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        System.out.println("get back bitmap");
        System.out.println(image);
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    /*public static Bitmap getImage(byte[] image){

        return
    }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            Uri fileUri = data.getData();
            profile_image.setImageURI(fileUri);
        }
    }


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
