package com.example.android.quakereport;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


public class QuakeDetailPagerAdapter extends FragmentPagerAdapter {
    public QuakeDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Log.e("QuakeDetailPagerAdapter",">>>>>>>>>> "+i);
        switch(i) {
            case 0: return QuakeDetailFragment.newInstance("FirstFragment","dasdsa");
            case 1: return QuakeMapFragment.newInstance(000.000,000.00,00.00);
            default: return QuakeDetailFragment.newInstance("FirstFragment","sada");
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0: return "Detail View";
            case 1: return "Map View";
            default: return "Detail View";
        }
    }
}
