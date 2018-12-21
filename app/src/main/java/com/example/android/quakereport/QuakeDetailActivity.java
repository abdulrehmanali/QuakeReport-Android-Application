package com.example.android.quakereport;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toolbar;

public class QuakeDetailActivity extends FragmentActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake_detail);

        viewPager = findViewById(R.id.quakeViewPager);
        tabLayout =  findViewById(R.id.tabs);
        viewPager.setAdapter(new QuakeDetailPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }
}
