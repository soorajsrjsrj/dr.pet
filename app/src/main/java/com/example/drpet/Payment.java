package com.example.drpet;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

        final TextView cardName = (TextView) getView().findViewById(R.id.card_name);
        final TextView carNumber = (TextView) getView().findViewById(R.id.card_number);
        final TextView expiryDate = (TextView) getView().findViewById(R.id.expiry_date);
        final TextView cvvNumb = (TextView) getView().findViewById(R.id.cvc);
        Button payButton = (Button) getView().findViewById(R.id.btn_pay);

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
                String p_name = cardName.getText().toString();
                String p_number = carNumber.getText().toString();
                String p_expiry = expiryDate.getText().toString();
                String p_cvv = cvvNumb.getText().toString();


                dbManager.insertintopayment(p_name, p_number, p_expiry, p_cvv);



            }
        });

    }


}
