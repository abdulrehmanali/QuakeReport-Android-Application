package com.example.android.quakereport;

/**
 * Created by Abdul on 4/15/2017.
 */

public class Earthquake {

    double mMagnitude;

    private String mPlace;

    private long mDate;

    private String mUrl;

    public Earthquake(double vMagnitude, String vPlace, Long vdate,String vUrl)
    {
        mMagnitude = vMagnitude;
        mPlace = vPlace;
        mDate = vdate;
        mUrl = vUrl;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getPlace() {
        return mPlace;
    }

    public Long getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }


}
