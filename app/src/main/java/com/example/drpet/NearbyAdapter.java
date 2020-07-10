package com.example.drpet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

public class NearbyAdapter extends BaseAdapter {


    private List<NearbyHospitals> nw;
    private Context context;


    public NearbyAdapter(Context context, List<NearbyHospitals> earthquakes) {
//        super();
        this.nw = earthquakes;
        this.context = context;
//    super(context, 0, earthquakes);
    }
    public void addFeeds(List<NearbyHospitals> feedArrayList) {
        this.nw = feedArrayList;
    }




    public void clear() {
        nw.clear();
    }
    @Override
    public int getCount() {
        return nw.size();
    }

    @Override
    public NearbyHospitals getItem(int position) {
        return nw.get(position);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                    R.layout.nearbyhospitallist, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        NearbyHospitals currentHospital = getItem(position);

        // Find the TextView with view ID magnitude
        TextView hospitalnamelist = (TextView) listItemView.findViewById(R.id.hospitalnme);
        // Format the magnitude to show 1 decimal place
        String formatname = currentHospital.getmName();
        // Display the magnitude of the current earthquake in that TextView
        hospitalnamelist.setText(formatname);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.


        // Get the original location string from the Earthquake object,
        // which can be in the format of "5km N of Cairo, Egypt" or "Pacific-Antarctic Ridge".
        String distancefromlist = currentHospital.getmDistance();

        // If the original location string (i.e. "5km N of Cairo, Egypt") contains
        // a primary location (Cairo, Egypt) and a location offset (5km N of that city)
        // then store the primary location separately from the location offset in 2 Strings,
        // so they can be displayed in 2 TextViews.
//        String primaryLocation;
//        String locationOffset;


        // Find the TextView with view ID location

        // Create a new Date object from the time in milliseconds of the earthquake


        // Find the TextView with view ID date
        TextView ratintlist = (TextView) listItemView.findViewById(R.id.distance);
        // Format the date string (i.e. "Mar 3, 1984")
        String formatrating = currentHospital.getmRating();
        // Display the date of the current earthquake in that TextView
        ratintlist.setText(formatrating);
        TextView address = (TextView) listItemView.findViewById(R.id.rating);
        // Format the date string (i.e. "Mar 3, 1984")
        String formataddress = currentHospital.getmDistance();
        // Display the date of the current earthquake in that TextView
        address.setText(formataddress);

        // Find the TextView with view ID time

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
    }

