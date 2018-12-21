package com.example.android.quakereport;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.graphics.drawable.GradientDrawable;



import static com.example.android.quakereport.R.id.date;
import static com.example.android.quakereport.R.id.location_offset;
import static com.example.android.quakereport.R.id.magnitude;
import static com.example.android.quakereport.R.id.primary_location;
import static com.example.android.quakereport.R.id.time;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.MyViewHolder> {
    private static final String LOCATION_SEPARATOR = " of ";
    private List<Earthquake> earthQuakeList;
    private ViewGroup viewGroup;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView magnitudeView,primaryLocationView,locationOffsetView,dateView,timeView;
        public MyViewHolder(View itemView) {
            super(itemView);
            magnitudeView = itemView.findViewById(magnitude);
            primaryLocationView =  itemView.findViewById(primary_location);
            locationOffsetView =  itemView.findViewById(location_offset);
            dateView =  itemView.findViewById(date);
            timeView =  itemView.findViewById(time);
        }
    }
    protected EarthquakeAdapter(List<Earthquake> earthQuakeList){this.earthQuakeList = earthQuakeList;}
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        viewGroup = parent;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openQuakeDetail = new Intent(viewGroup.getContext(),QuakeDetailActivity.class);
                openQuakeDetail.putExtra("quakeObject","s");
                viewGroup.getContext().startActivity(openQuakeDetail);
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EarthquakeAdapter.MyViewHolder viewHolder, int position) {
        Earthquake earthQuake = earthQuakeList.get(position);
        Date dateObject = new Date(earthQuake.getDate());
        GradientDrawable magnitudeCircle = (GradientDrawable)  viewHolder.magnitudeView.getBackground();
        String originalLocation = earthQuake.getPlace();
        String primaryLocation,locationOffset;

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = viewGroup.getResources().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }
        viewHolder.magnitudeView.setText(formatMagnitude(earthQuake.getMagnitude()));
        viewHolder.magnitudeView.setText(formatMagnitude(earthQuake.getMagnitude()));
        magnitudeCircle.setColor(getMagnitudeColor(earthQuake.getMagnitude()));
        viewHolder.primaryLocationView.setText(primaryLocation);
        viewHolder.locationOffsetView.setText(locationOffset);
        viewHolder.dateView.setText(formatDate(dateObject));
        viewHolder.timeView.setText(formatTime(dateObject));
    }

    @Override
    public int getItemCount() {
        return earthQuakeList.size();
    }
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return viewGroup.getResources().getColor(magnitudeColorResourceId);
    }
}