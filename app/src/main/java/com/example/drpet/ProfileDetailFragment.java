package com.example.drpet;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drpet.Model.DBManager;


public class ProfileDetailFragment extends Fragment {

    private DBManager dbManager;

    ImageView profile_image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_detail, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("id_pref", Context.MODE_PRIVATE);
        int user_id = pref.getInt("key_id", 0);

        dbManager = new DBManager(getActivity().getApplicationContext());
        dbManager.open();
        Cursor cursor = dbManager.fetchUserData(user_id);

        profile_image = (ImageView) getView().findViewById(R.id.pro_picture);
        final TextView pro_email = (TextView) getActivity().findViewById(R.id.p_email);
        final TextView pro_fname = (TextView) getActivity().findViewById(R.id.p_fname);
        final TextView pro_lname = (TextView) getActivity().findViewById(R.id.p_lname);
        final TextView pro_phone = (TextView) getActivity().findViewById(R.id.p_phone);
        final TextView email_pd = (TextView) getActivity().findViewById(R.id.email_in_pd);
        final TextView btn_edit = (TextView) getActivity().findViewById(R.id.btn_edit);

        if (cursor.getCount() > 0 ){
            pro_email.setText(cursor.getString(3));
            pro_fname.setText(cursor.getString(1));
            pro_lname.setText(cursor.getString(2));
            pro_phone.setText(cursor.getString(5));

            email_pd.setText(cursor.getString(3));

            byte[] profile_picture = cursor.getBlob(6);

            Bitmap pro_pic = getImage(profile_picture);
            profile_image.setImageBitmap(pro_pic);
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Profile()).commit();
            }
        });


    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        System.out.println("get back bitmap");
        System.out.println(image);
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}