package com.example.myese.fitlyfe;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends ArrayAdapter<Location> {
    private Context mContext;
    private List<Location> locationList = new ArrayList<Location>();

    public LocationAdapter( Context context, ArrayList<Location> list)
    {
        super( context, 0, list);
        mContext = context;
        locationList = list;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        // Associates the list with the XML Layout file "location_view"
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.location_view,parent,false);

        // Individually handles each Location in the contactList
        Location currentLocation = locationList.get(position);

        // Extracts the name of the Location
        TextView name = (TextView) listItem.findViewById(R.id.textView_Name);
        name.setText(currentLocation.getName());

        // Extracts the city name of the Location
        TextView city = (TextView) listItem.findViewById(R.id.textView_City);
        city.setText(currentLocation.getCity());

        // Extracts the state name of the Location
        TextView state = (TextView) listItem.findViewById(R.id.textView_State);
        state.setText(currentLocation.getState());

        return listItem;
    }
}

