package br.com.trikwcc.Trabalho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TrailControl extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {
    private SharedPreferences save, walk;
    GoogleMap myMap;
    FrameLayout map;
    Button btn_settings, btn_register, btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.trail_control);
        save = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        walk = getSharedPreferences("trail_info", MODE_PRIVATE);
        activateButtons();
        map = findViewById(R.id.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        myMap.getUiSettings().setMapToolbarEnabled(true);
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setMyLocationButtonEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);
        myMap.getUiSettings().setRotateGesturesEnabled(true);
        myMap.getUiSettings().setScrollGesturesEnabled(true);
        myMap.getUiSettings().setTiltGesturesEnabled(true);

        double latitude = Double.parseDouble(walk.getString("latitude", "0"));
        double longitude = Double.parseDouble(walk.getString("longitude", "0"));

        LatLng location = new LatLng(latitude, longitude);
        myMap.addMarker(new MarkerOptions().position(location));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(location));

    }

    private void mapControl(){
        int speedOption = save.getInt("speed_option", 1);
        int coordinatesOption = save.getInt("coordinates_option", 1);
        int mapOrientation = save.getInt("map_orientation_option",1);
        int mapView = save.getInt("map_view_option",1);

        if (speedOption == 1) {
            myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (speedOption == 2) {
            myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

        if (coordinatesOption == 1) {

        }
        if (coordinatesOption == 2) {

        }
        if (coordinatesOption == 3) {

        }

        if (mapOrientation == 1){
            myMap.setMapOrientation(GoogleMap.ORIENTATION_PORTRAIT);
        }
        if (mapOrientation == 2){
            myMap.setMapOrientation(GoogleMap.ORIENTATION_LANDSCAPE);
        }

        if(mapView == 1){
            myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if(mapView == 2){
            myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }

    }

    private void activateButtons() {
        btn_register = findViewById(R.id.trail_register);
        btn_settings = findViewById(R.id.trail_settings);
        btn_return = findViewById(R.id.trail_return);
        btn_register.setOnClickListener(this);
        btn_settings.setOnClickListener(this);
        btn_return.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.trail_register) {
            startActivity(new Intent(this, RegisterScreen.class));
        }
        if (id == R.id.trail_settings) {
            startActivity(new Intent(this, ConfigScreen.class));
        }
        if (id == R.id.trail_return) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            finish();
        }
    }
}
