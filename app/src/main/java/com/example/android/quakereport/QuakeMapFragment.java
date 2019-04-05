package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class QuakeMapFragment extends Fragment implements OnMapReadyCallback, LocationSource.OnLocationChangedListener,ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String ARG_RADIUS = "radius", ARG_LATITUDE = "latitude", ARG_LONGITUDE = "longitude";
    private static final int MY_ACCESS_FINE_LOCATION = 10001;
    private static final int MY_ACCESS_COARSE_LOCATION = 10002;
    private double radius, latitude, longitude;
    private GoogleMap mMap;
    private MapView mapView;
    private Context context;

    public QuakeMapFragment() {
    }

    public static QuakeMapFragment newInstance(double radius, double latitude, double longitude) {
        QuakeMapFragment fragment = new QuakeMapFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_RADIUS, radius);
        args.putDouble(ARG_LATITUDE, latitude);
        args.putDouble(ARG_LONGITUDE, longitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            radius = Double.parseDouble(getArguments().getString(ARG_RADIUS));
            latitude = Double.parseDouble(getArguments().getString(ARG_LATITUDE));
            longitude = Double.parseDouble(getArguments().getString(ARG_LONGITUDE));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        Log.e("QuakeMapFragment",requestCode+" == requestCode, "+permissions+" == permissions, grantResults === "+grantResults);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quake_map, container, false);
        mapView = view.findViewById(R.id.quakeMapView);
        mapView.onCreate(savedInstanceState);
        context = getContext();
        Log.e("Map Fragment",">>>"+radius+" "+latitude+" "+longitude);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mapView.getMapAsync(this);
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_ACCESS_FINE_LOCATION);
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_ACCESS_COARSE_LOCATION);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        setPointer();
    }
    public void setPointer(){
          mMap.addCircle(new CircleOptions()
                .center(new LatLng(latitude,longitude))
                .radius(radius)
                .strokeColor(Color.BLUE)
                .fillColor(Color.BLUE));
        Log.e("QuakeMapFragment","Map Ready ");
        LatLng yourLocation = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(yourLocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(yourLocation));
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
