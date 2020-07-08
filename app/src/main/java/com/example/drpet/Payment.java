package com.example.drpet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.drpet.Model.DBManager;

public class Payment extends Fragment {

    private DBManager dbManager;

    private TextWatcher text = null;

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
        final EditText c_Number = (EditText) getView().findViewById(R.id.card_number);
        final TextView c_expiryDate = (TextView) getView().findViewById(R.id.expiry_date);
        final TextView c_cvvNumb = (TextView) getView().findViewById(R.id.cvc);
        final TextView txt_input = (TextView) getView().findViewById(R.id.card_preview_number);
        final TextView txt_name = (TextView) getView().findViewById(R.id.card_preview_name);
        final TextView txt_date = (TextView) getView().findViewById(R.id.card_preview_expiry);
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

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cardName = c_Name.getText().toString();
                String cardNumber = c_Number.getText().toString();
                String expiry = c_expiryDate.getText().toString();
                String cvv = c_cvvNumb.getText().toString();

                dbManager.insertintopayment(cardName, cardNumber, expiry, cvv, user_id);
                Toast.makeText(Payment.this.getActivity(), cardNumber + " Successfully Added", Toast.LENGTH_LONG).show();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MenuFragment()).commit();
            }
        });

        text = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               txt_input.setText(c_Number.getText().toString());
               txt_name.setText(c_Name.getText().toString());
               txt_date.setText(c_expiryDate.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        c_Number.addTextChangedListener(text);
        c_Name.addTextChangedListener(text);
        c_expiryDate.addTextChangedListener(text);
    }

}
