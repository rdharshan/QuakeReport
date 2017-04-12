package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by darshan on 15-02-2017.
 */

public final class QueryUtils {

    private QueryUtils() {

    }

    public static ArrayList<Earthquake> extractEarthquakes(String requestUrl) {
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            String place, url;
            double magnitude;
            long longTime;
            JSONObject jsonRoot = new JSONObject(getJSONResponse(requestUrl));
            JSONArray features = jsonRoot.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {
                magnitude = features.getJSONObject(i).getJSONObject
                        ("properties").getDouble("mag");
                place = features.getJSONObject(i).getJSONObject
                        ("properties").getString("place");
                longTime = features.getJSONObject(i).getJSONObject
                        ("properties").getLong("time");
                url = features.getJSONObject(i).getJSONObject
                        ("properties").getString("url");
                earthquakes.add(new Earthquake(magnitude, place, longTime, url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes

        return earthquakes;
    }

    private static String getJSONResponse(String requestUrl) {
        String JSONResponse = "";
        URL url = createURL(requestUrl);
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(20000);
            urlConnection.setConnectTimeout(30000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int status = urlConnection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                JSONResponse = readFromInputStream(inputStream);
            } else if (status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection
                    .HTTP_MOVED_TEMP) {
                return getJSONResponse(urlConnection.getHeaderField("Location"));
            } else {
                Log.e("QueryUtils", "Error response code: " + urlConnection.getResponseCode() +
                        urlConnection.getResponseMessage());
            }
        } catch (IOException e) {
            Log.e("QueryUtils", "Connection failed: " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("QueryUtils", "Error" + e);
                }
            }
        }
        return JSONResponse;
    }

    private static URL createURL(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e("QueryUtils", "Cannot create url");
        }
        return url;
    }

    private static String readFromInputStream(InputStream inputStream) {
        StringBuilder JSONResponse = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset
                    .forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            try {
                String line = reader.readLine();
                while (line != null) {
                    JSONResponse.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                Log.e("QueryUtils", "Cannot read JSON string " + e);
            }
        }
        return JSONResponse.toString();
    }

}