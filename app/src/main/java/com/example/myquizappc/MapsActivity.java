package com.example.myquizappc;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap gmap;
    FrameLayout map;
    int score;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent=getIntent();
        score=intent.getIntExtra("score",0) ;

        map=findViewById(R.id.map);
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady( GoogleMap googleMap) {
        this.gmap=googleMap;
        //coordonnée emsi
        LatLng mapMaroc=new LatLng(33.5415, -7.6735);

        this.gmap.addMarker(new MarkerOptions().position(mapMaroc).title("Votre score :"+(100*score/7)+" %"));
        this.gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapMaroc,15));
    }
}