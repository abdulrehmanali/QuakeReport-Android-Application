package com.example.android.quakereport;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=4&limit=15";

    private EarthquakeAdapter mAdapter;
    private ProgressDialog progress;
    private LinearLayout noInternetLayout;
    private ListView earthquakeListView;

    public static boolean isNetworkStatusAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null)
                if (netInfos.isConnected())
                    return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        noInternetLayout = (LinearLayout) findViewById(R.id.noInternetLayout);
        earthquakeListView = (ListView) findViewById(R.id.list);
        progress=new ProgressDialog(this);
        progress.setMessage("Loading Latest Stats...");
        progress.setIndeterminate(true);
        progress.setProgress(0);
        load();

    }

    private void load() {
        earthquakeListView.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.GONE);
        progress.show();

        if (isNetworkStatusAvailable(getApplicationContext())) {
            mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
            earthquakeListView.setAdapter(mAdapter);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Earthquake currentEarthquake = mAdapter.getItem(position);
                    Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                    startActivity(websiteIntent);
                }
            });
            EarthquakeAsyncTask task = new EarthquakeAsyncTask();
            task.execute(USGS_REQUEST_URL);
        } else {
            earthquakeListView.setVisibility(View.GONE);
            progress.cancel();
            noInternetLayout.setVisibility(View.VISIBLE);
            Snackbar.make(findViewById(R.id.earthquake_activity), "Connection Error",
                    Snackbar.LENGTH_LONG)
                    .setAction("Try Again", new snackbarClick())
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reload_data:
                load();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public class snackbarClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            load();
        }
    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Earthquake> data) {

            mAdapter.clear();
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
            noInternetLayout.setVisibility(View.GONE);
            progress.cancel();
            earthquakeListView.setVisibility(View.VISIBLE);
        }

    }
}
