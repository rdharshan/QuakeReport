package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by darshan on 11-03-2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     * @param mUrl to load data from
     */
    public EarthquakeLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Earthquake> earthquakes = QueryUtils.extractEarthquakes(mUrl);
        return earthquakes;
    }
}
