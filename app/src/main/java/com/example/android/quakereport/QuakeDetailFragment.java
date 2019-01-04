package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class QuakeDetailFragment extends Fragment {
    View view;
    Earthquake earthquake;
    public QuakeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quake_detail, container, false);
        GridView gridView = view.findViewById(R.id.detail_page_grid);
        TextView detail_page_magnitude = view.findViewById(R.id.detail_page_magnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable)  detail_page_magnitude.getBackground();

        earthquake = new Earthquake();
        ArrayList<HashMap<String,String>> quakeDetail = new ArrayList<>();
        HashMap<String,String> quakeDetailColumn = new HashMap<>();
        String[] from = { "label","value"};
        int[] to = { R.id.detail_page_grid_item_label,R.id.detail_page_grid_item_value};
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(),quakeDetail,R.layout.detail_page_grid_item, from, to);

        try {
            JSONObject properties = new JSONObject(getArguments().getString("properties"));
Log.d("Properties",properties.toString());

            earthquake.setMagnitude(properties.getInt("mag"));
            earthquake.setPlace(properties.getString("place"));
            earthquake.setDate(properties.getLong("time"));
            earthquake.setUrl(properties.getString("url"));

            detail_page_magnitude.setText(earthquake.getFormatMagnitude());
            magnitudeCircle.setColor(earthquake.getMagnitudeColorUsingView(view));


            quakeDetailColumn.put("label","Type");
            quakeDetailColumn.put("value",properties.getString("type"));
            quakeDetail.add(quakeDetailColumn);

            quakeDetailColumn = new HashMap<>();
            quakeDetailColumn.put("label","Status");
            quakeDetailColumn.put("value",properties.getString("status"));
            quakeDetail.add(quakeDetailColumn);

            quakeDetailColumn = new HashMap<>();
            quakeDetailColumn.put("label","Date");
            quakeDetailColumn.put("value",earthquake.getFormatDate());
            quakeDetail.add(quakeDetailColumn);

            quakeDetailColumn = new HashMap<>();
            quakeDetailColumn.put("label","Time");
            quakeDetailColumn.put("value",earthquake.getFormatTime());
            quakeDetail.add(quakeDetailColumn);

            quakeDetailColumn = new HashMap<>();
            quakeDetailColumn.put("label","Country");
            quakeDetailColumn.put("value",earthquake.getCountry());
            quakeDetail.add(quakeDetailColumn);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        gridView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


}
