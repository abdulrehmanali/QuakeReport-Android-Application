package com.example.android.quakereport;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private View itemView;

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
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        viewGroup = parent;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EarthquakeAdapter.MyViewHolder viewHolder, int position) {
        final Earthquake earthQuake = earthQuakeList.get(position);
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
        viewHolder.magnitudeView.setText(earthQuake.getFormatMagnitude());
        magnitudeCircle.setColor(earthQuake.getMagnitudeColorUsingViewGroup(viewGroup));
        viewHolder.primaryLocationView.setText(primaryLocation);
        viewHolder.locationOffsetView.setText(locationOffset);
        viewHolder.dateView.setText(earthQuake.getFormatDate());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openQuakeDetail = new Intent(viewGroup.getContext(),QuakeDetailActivity.class);
                openQuakeDetail.putExtra("id",earthQuake.getId());
                viewGroup.getContext().startActivity(openQuakeDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return earthQuakeList.size();
    }

}