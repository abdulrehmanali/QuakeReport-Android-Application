package com.example.android.quakereport;


import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class QuakeDetailActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String eventId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake_detail);

        viewPager = findViewById(R.id.quakeViewPager);
        tabLayout = findViewById(R.id.tabs);


        final QuakeDetailPagerAdapter pagerAdapter = new QuakeDetailPagerAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        eventId = getIntent().getStringExtra("id");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventid=" + eventId;
        final QuakeDetailFragment quakeDetailFragment = new QuakeDetailFragment();
        final QuakeMapFragment quakeMapFragment = new QuakeMapFragment();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject earthquake = new JSONObject(response);
                            String properties = earthquake.getJSONObject("properties").toString();
                            JSONArray geometry = earthquake.getJSONObject("geometry").getJSONArray("coordinates");
                            Bundle propertiesBundle = new Bundle();
                            Bundle geometryBundle = new Bundle();

                            propertiesBundle.putString("properties",properties );
                            geometryBundle.putString("longitude",geometry.get(0).toString() );
                            geometryBundle.putString("latitude",geometry.get(1).toString() );
                            geometryBundle.putString("radius",geometry.get(2).toString() );

                            Log.e("Map Fragment",">>>"+geometry.get(0).toString()+" "+geometry.get(1).toString()+" "+geometry.get(2).toString());
                            quakeDetailFragment.setArguments(propertiesBundle);
                            quakeMapFragment.setArguments(geometryBundle);
                            pagerAdapter.addFragment(quakeDetailFragment,"Detail");
                            pagerAdapter.addFragment(quakeMapFragment,"Map");

                            viewPager.setAdapter(pagerAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("QuakeDetailActivity", "Ops Volley Didn't Work!!!!!"+error.networkResponse);
            }
        });
        queue.add(stringRequest);


    }
}
