package com.example.yasin.projeversion1_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UsersAdapter extends ArrayAdapter<Ihbar> {
    public UsersAdapter(Context context, ArrayList<Ihbar> ihbar) {
        super(context, 0, ihbar);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Ihbar ihbar = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.textView_name);
        TextView tvHome = (TextView) convertView.findViewById(R.id.textView_release);
        // Populate the data into the template view using the data object
        tvName.setText(ihbar.sucTuru);
        tvHome.setText(ihbar.kulmail);
        // Return the completed view to render on screen
        return convertView;
    }
}