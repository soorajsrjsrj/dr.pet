package com.example.drpet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.drpet.Model.DBManager;

public class Payment extends Fragment {

    private DBManager dbManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbManager = new DBManager(getActivity().getApplicationContext());
        dbManager.open();
//        Cursor cursor = dbManager.();

        final TextView c_Name = (TextView) getView().findViewById(R.id.card_name);
        final TextView c_Number = (TextView) getView().findViewById(R.id.card_number);
        final TextView c_expiryDate = (TextView) getView().findViewById(R.id.expiry_date);
        final TextView c_cvvNumb = (TextView) getView().findViewById(R.id.cvc);
        Button payButton = (Button) getView().findViewById(R.id.btn_pay);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("id_pref", Context.MODE_PRIVATE);
        final int user_id = pref.getInt("key_id", 0);

//        if(cursor.getCount() > 0){
//            cursor.moveToFirst();
//            cardName.setText(cursor.getString(0));
//            carNumber.setText(cursor.getString(1));
//            expiryDate.setText(cursor.getString(2));
//            cvvNumb.setText(cursor.getString(3));
//        }
//
//

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cardName = c_Name.getText().toString();
                String cardNumber = c_Number.getText().toString();
                String expiry = c_expiryDate.getText().toString();
                String cvv = c_cvvNumb.getText().toString();

                dbManager.insertintopayment(cardName, cardNumber, expiry, cvv, user_id);
                Toast.makeText(Payment.this.getActivity(), "Succesfully Added" + cardName + ":" + cardNumber +
                        " : " + user_id , Toast.LENGTH_LONG).show();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MenuFragment()).commit();
            }
        });
    }

}
