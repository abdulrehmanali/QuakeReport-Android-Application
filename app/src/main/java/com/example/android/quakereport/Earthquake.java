package com.example.android.quakereport;

import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Abdul on 4/15/2017.
 */

public class Earthquake {

    private double mMagnitude;
    private String mPlace,mUrl,mid;
    private long mDate;
    private String COUNTRY_SERTATOR = ",";

    public Earthquake()
    {
    }


    public void setId(String id) {this.mid = id;}
    public void setMagnitude(double mMagnitude) {this.mMagnitude =  mMagnitude;}
    public void setPlace(String mPlace) {this.mPlace = mPlace;}
    public void setDate(Long mDate) {this.mDate = mDate;}
    public void setUrl(String mUrl) {this.mUrl = mUrl;}
    public String getId() {return mid;}
    public double getMagnitude() {return mMagnitude;}
    public String getPlace() {return mPlace;}
    public Long getDate() {return mDate;}
    public String getUrl() {return mUrl;}

    public String getFormatMagnitude() {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(this.mMagnitude);
    }

    public String getFormatDate() {
        Date dateObject = new Date(this.mDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    public String getFormatTime() {
        Date dateObject = new Date(this.mDate);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
    public String getCountry(){
        if (this.getPlace().contains(COUNTRY_SERTATOR)) {
            String[] parts = this.getPlace().split(COUNTRY_SERTATOR);
            return parts[1].trim();
        }
        return "N/A";
    }
    public int getMagnitudeColorUsingViewGroup(ViewGroup viewGroup){
        return viewGroup.getResources().getColor(getMagnitudeColor());
    }
    public int getMagnitudeColorUsingView(View view){
        return view.getResources().getColor(getMagnitudeColor());
    }
    public int getMagnitudeColor() {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(this.mMagnitude);
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
return magnitudeColorResourceId;
    }
}
