package com.uninorte.penkartus;

/**
 * Created by karolaya on 31/10/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


class MyAdapter extends ArrayAdapter<String> {
    public MyAdapter(Context context, String[] values) {
        super(context, R.layout.row_layout2, values);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.row_layout2, parent, false);
        String holo = getItem(position);
        TextView theTextView = (TextView) theView.findViewById(R.id.txView);
        theTextView.setText(holo);
        return theView;
    }
}
