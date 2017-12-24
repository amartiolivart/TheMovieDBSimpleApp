package com.android.amartiolivart.themoviedbsimpleapp.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.android.amartiolivart.themoviedbsimpleapp.tasks.data.TvShow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amartiolivart on 23/12/17.
 */

public class LoadPopularTvShowsAsyncTask extends AsyncTask<String, Void, List<?>> {

    private static final String TAG = LoadPopularTvShowsAsyncTask.class.getSimpleName();
    private TaskCallback callback;

    public LoadPopularTvShowsAsyncTask(TaskCallback mainActivity) {
        this.callback = mainActivity;
    }

    @Override
    protected List<?> doInBackground(String... urls) {

        List<Object> data = new ArrayList<>();
        for (String url : urls) {
            if (url != null) {
                loadXMLFromURI(url, data);
            } else {
                Log.e(TAG, getClass().getSimpleName() + " error loading null url");
            }
        }
        return data;
    }

    @Override
    protected void onPostExecute(List<?> result) {
        super.onPostExecute(result);
        if (result != null && result.size() > 0) {
            callback.setTvShows(result);
        } else {
            callback.setTvShows(new ArrayList());
        }
    }

    protected void loadXMLFromURI(String url, List<Object> data) {
        InputStream inStream = null;
        try {
            HttpURLConnection conn = createConnection(url);
            if (verifyConnectionResponse(conn)) {
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK || conn.getResponseCode() == 400) {
                    inStream = conn.getInputStream();
                } else {
                    inStream = conn.getErrorStream();
                }
                processResponse(inStream, url, data);
            }
        } catch (Exception e) {
            Log.e(TAG, getClass().getSimpleName() + " error loading " + url, e);
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (Exception ee) {
                Log.e(TAG, "error closing inputStream" + ee);
            }
        }
    }

    protected HttpURLConnection createConnection(String URL) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(URL).openConnection();
        return conn;
    }

    protected boolean verifyConnectionResponse(HttpURLConnection conn) throws IOException {
        return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    protected void processResponse(InputStream is, String url, List<Object> data) {
        StringBuilder result = new StringBuilder();
        try {
            try {
                //Receive the response from the server
                InputStream in = new BufferedInputStream(is);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } catch (IOException e) {
                Log.e("JSON Parser", "Error parsing data for IOException ", e);
            }
            // JSONObject parsing
            try {
                String res = result.toString();

                // Remove special blank characters JiC
                //res = res.replaceAll("[^\\x20-\\x7e]", "");

                // Get just the TvShows
                JSONObject jObjAll = new JSONObject(res);
                String results = jObjAll.getString("results");

                if (results.startsWith("{")) {
                    JSONObject jObj = new JSONObject(results);
                    parseJson(jObj, data);
                } else if (results.startsWith("[")) {
                    // Maybe it's an array
                    JSONArray jArr = new JSONArray(results);
                    parseJson(jArr, data);
                }

            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString(), e);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing JSON Parser " + url, e);
        }
    }

    protected void parseJson(JSONObject tvS, List<Object> data) throws JSONException {
        TvShow tvShow = new TvShow(
                tvS.getString("backdrop_path")
                // TODO what should we print? Name or original Name?
                , tvS.getString("name")
                , tvS.getString("vote_average")
                , tvS.getString("id")
                , tvS.getString("overview")
                , tvS.getString("poster_path")
        );
        data.add(tvShow);
    }

    protected void parseJson(JSONArray jarr, List<Object> data) throws JSONException {
        for (int i = 0; i < jarr.length(); ++i) {
            parseJson(jarr.getJSONObject(i), data);
        }
    }
}
