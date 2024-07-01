package br.ucsal.trabalho_n2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class TrailShowcase extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private DatabaseHelper dbHelper;
    private LatLng[] trailCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trail_showcase);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("trailCoordinates")) {
            ArrayList<LatLng> trailCoordinatesList = intent.getParcelableArrayListExtra("trailCoordinates");
            if (trailCoordinatesList != null && !trailCoordinatesList.isEmpty()) {
                trailCoordinates = trailCoordinatesList.toArray(new LatLng[0]);
            }
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        findViewById(R.id.showcase_return).setOnClickListener(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (trailCoordinates != null && trailCoordinates.length > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            PolylineOptions polylineOptions = new PolylineOptions();
            for (LatLng coord : trailCoordinates) {
                googleMap.addMarker(new MarkerOptions().position(coord));
                builder.include(coord);
                polylineOptions.add(coord);
            }

            googleMap.addPolyline(polylineOptions);

            LatLngBounds bounds = builder.build();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.showcase_return) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
