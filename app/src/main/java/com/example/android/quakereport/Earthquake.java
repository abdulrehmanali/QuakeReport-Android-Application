package com.example.android.quakereport;

/**
 * Created by Abdul on 4/15/2017.
 */

public class Earthquake {

    private double mMagnitude;
    private String mPlace,mUrl;
    private long mDate;

    public Earthquake(double vMagnitude, String vPlace, Long vdate,String vUrl)
    {
        mMagnitude = vMagnitude;
        mPlace = vPlace;
        mDate = vdate;
        mUrl = vUrl;
    }
    public void setMagnitude(double mMagnitude) {this.mMagnitude =  mMagnitude;}
    public void setPlace(String mPlace) {this.mPlace = mPlace;}
    public void setDate(Long mDate) {this.mDate = mDate;}
    public void setUrl(String mUrl) {this.mUrl = mUrl;}
    public double getMagnitude() {return mMagnitude;}
    public String getPlace() {return mPlace;}
    public Long getDate() {return mDate;}
    public String getUrl() {return mUrl;}
}
