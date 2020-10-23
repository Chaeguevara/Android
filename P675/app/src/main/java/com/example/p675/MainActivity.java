package com.example.p675;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    GoogleMap gmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
                LatLng latLng = new LatLng(37.456587, 126.952319);
                gmap.addMarker(
                        new MarkerOptions().position(latLng).title("대학교")
                );
                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
            }
        });
    }

    public void ck1(View v){
                LatLng latLng = new LatLng(37.517100, 126.971562);
                gmap.addMarker(
                        new MarkerOptions().position(latLng).title("공원").snippet("xxx").icon(BitmapDescriptorFactory.fromResource(R.drawable.d1))
                );
                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
    }

    public void ck2(View v){

                LatLng latLng = new LatLng(37.544780, 127.037565);
                gmap.addMarker(
                        new MarkerOptions().position(latLng).title("서울숲").snippet("GD")
                );
                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));

    }
}