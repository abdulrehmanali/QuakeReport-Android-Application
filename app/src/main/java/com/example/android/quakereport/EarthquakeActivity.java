package com.example.android.quakereport;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=4&limit=1500";

    private EarthquakeAdapter mAdapter;
    private ProgressDialog progress;
    private LinearLayout noInternetLayout;
    private RecyclerView earthquakeRecyclerView;
    private List<Earthquake> earthquakes = new ArrayList<>();
    ;

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
        noInternetLayout = findViewById(R.id.noInternetLayout);
        earthquakeRecyclerView = findViewById(R.id.list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        earthquakeRecyclerView.setLayoutManager(mLayoutManager);
        earthquakeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new EarthquakeAdapter(earthquakes);
        earthquakeRecyclerView.setAdapter(mAdapter);
        progress = new ProgressDialog(this);
        progress.setMessage("Loading Latest Stats...");
        progress.setIndeterminate(true);
        progress.setProgress(0);
        load();

    }

    private void load() {
        earthquakeRecyclerView.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.GONE);
        progress.show();

        if (isNetworkStatusAvailable(getApplicationContext())) {
            EarthquakeAsyncTask task = new EarthquakeAsyncTask();
            task.execute(USGS_REQUEST_URL);
        } else {
            earthquakeRecyclerView.setVisibility(View.GONE);
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
            if (data != null && !data.isEmpty()) {
                for (int i=0; i<data.size(); i++) {
                    earthquakes.add(data.get(i));
                }
                mAdapter.notifyDataSetChanged();
            }
            noInternetLayout.setVisibility(View.GONE);
            progress.cancel();
            earthquakeRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
