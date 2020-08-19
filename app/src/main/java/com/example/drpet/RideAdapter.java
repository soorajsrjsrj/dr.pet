package com.example.drpet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RideAdapter extends BaseAdapter {
    private ArrayList<RideDetail> ridedt;
    private Context context;


    public RideAdapter(ArrayList<RideDetail> list, Context cont){
        this.ridedt = list;
        this.context = cont;
    }

    @Override
    public int getCount() {
        return this.ridedt.size();
    }

    @Override
    public Object getItem(int position) {
        return this.ridedt.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.ridedetaillist, null);

            holder = new ViewHolder();
            holder.hospname = (TextView)convertView.findViewById(R.id.hospitalname);
            holder.hospaddress = (TextView)convertView.findViewById(R.id.hospaddress);
            holder.hospdistance = (TextView)convertView.findViewById(R.id.hospdistance);
            holder.hospprice = (TextView)convertView.findViewById(R.id.hospprice);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        RideDetail stu = ridedt.get(position);
        holder.hospname.setText(stu.getHospName());
        holder.hospaddress.setText(stu.getHospAddress());
        holder.hospdistance.setText(stu.getDistance());
        holder.hospprice.setText(stu.getPrice()+"$ Price");


        return convertView;
    }

    private static class ViewHolder{
        public TextView hospname;
        public TextView hospaddress;
        public TextView hospdistance;
        public TextView hospprice;

    }
}