package com.example.android.quakereport;

/**
 * Created by darshan on 14-02-2017.
 */

public class Earthquake {
    private double mMagnitude;
    private String mPlace;
    private long mTime;
    private String mUrl;

    public Earthquake(double magnitude, String place, long time, String url) {
        mMagnitude = magnitude;
        mTime = time;
        mPlace = place;
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public long getTimeInMilliseconds() {
        return mTime;
    }

    public String getPlace() {
        return mPlace;
    }
}
