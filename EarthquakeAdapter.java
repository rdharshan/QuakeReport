package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by darshan on 14-02-2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(Context context, int resource, ArrayList<Earthquake> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View earthquakeListView = convertView;
        String offset = "Near the", primaryLocation;
        if (earthquakeListView == null) {
            earthquakeListView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_item, parent, false);
        }
        Earthquake currentEarthquake = getItem(position);
        TextView magnitudeText = (TextView) earthquakeListView.findViewById(R.id
                .magnitude_text_view);
        double magnitude = currentEarthquake.getMagnitude();
        String formattedMagnitude = formatMagnitude(magnitude);
        magnitudeText.setText(formattedMagnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeText.getBackground();
        magnitudeCircle.setColor(getMagnitudeColor(magnitude));
        TextView offsetText = (TextView) earthquakeListView.findViewById(R.id.offset_text_view);
        TextView primaryLocationText = (TextView) earthquakeListView.findViewById(R.id
                .primary_location_text_view);
        primaryLocation = currentEarthquake.getPlace();
        if (primaryLocation.contains("km ")) {
            String[] placeArray = primaryLocation.split("of ", 2);
            offset = placeArray[0] + "of";
            primaryLocation = placeArray[1];
        }
        offsetText.setText(offset);
        primaryLocationText.setText(primaryLocation);
        TextView dateText = (TextView) earthquakeListView.findViewById(R.id.date_text_view);
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());
        dateText.setText(formatDate(dateObject));
        TextView timeText = (TextView) earthquakeListView.findViewById(R.id.time_text_view);
        timeText.setText(formatTime(dateObject));

        return earthquakeListView;
    }

    private int getMagnitudeColor(double magnitude) {
        switch ((int) magnitude) {
            case 0:
            case 1:
                return ContextCompat.getColor(getContext(), R.color.magnitude1);
            case 2:
                return ContextCompat.getColor(getContext(), R.color.magnitude2);
            case 3:
                return ContextCompat.getColor(getContext(), R.color.magnitude3);
            case 4:
                return ContextCompat.getColor(getContext(), R.color.magnitude4);
            case 5:
                return ContextCompat.getColor(getContext(), R.color.magnitude5);
            case 6:
                return ContextCompat.getColor(getContext(), R.color.magnitude6);
            case 7:
                return ContextCompat.getColor(getContext(), R.color.magnitude7);
            case 8:
                return ContextCompat.getColor(getContext(), R.color.magnitude8);
            case 9:
                return ContextCompat.getColor(getContext(), R.color.magnitude9);
            default:
                return ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat formattedDate = new SimpleDateFormat("LLL dd, yyyy");
        return formattedDate.format(date);
    }

    private String formatTime(Date date) {
        SimpleDateFormat formattedTime = new SimpleDateFormat("h:mm a");
        return formattedTime.format(date);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(magnitude);
    }
}