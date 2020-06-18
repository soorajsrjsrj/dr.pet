package com.example.drpet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.craftman.cardform.CardForm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Payment extends Fragment  {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);

//   java code     CardForm cardForm = (CardForm) findViewById(R.id.cardform);
        //cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
          //  @Override
        //    public void onClick(Card card) {
                //Your code here!! use card.getXXX() for get any card property
                //for instance card.getName();
      //      }
    //    });
  //  }
}
