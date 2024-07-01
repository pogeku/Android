package br.ucsal.trabalho_n2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TrailControl extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {
    private GoogleMap myMap;
    private Marker marker;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private DatabaseHelper dbHelper;
    private LatLng previousLocation, lastLocation;
    private LatLng[] fullLocation = {};
    private TextView tvSpeed, tvTimer, tvDistance;
    private long startTime;
    private double totalDistance;
    private boolean isRegistered = false, isRouteSaved = false;
    private final ArrayList<Double> speedList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.trail_control);
        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        dbHelper = new DatabaseHelper(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        buttons();
        if (checkLocationPermission()) {
            initMap();
        } else {
            requestLocationPermission();
        }
        resetTimer();
    }

    private void buttons() {
        tvSpeed = findViewById(R.id.tv_speed);
        tvTimer = findViewById(R.id.tv_timer);
        tvDistance = findViewById(R.id.tv_distance);
        findViewById(R.id.trail_register).setOnClickListener(this);
        findViewById(R.id.trail_settings).setOnClickListener(this);
        findViewById(R.id.trail_return).setOnClickListener(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        myMap.getUiSettings().setAllGesturesEnabled(true);
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setMyLocationButtonEnabled(true);
        mapControl();
    }

    private void mapControl() {
        int mapView = sharedPreferences.getInt("map_view_option", 1);
        myMap.setMapType(mapView == 1 ? GoogleMap.MAP_TYPE_NORMAL : GoogleMap.MAP_TYPE_HYBRID);
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initMap();
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "Please enable location permissions in settings to use the app.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Location permission is required to use the app.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    updateUI(location);
                    updateMarkerLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                }
            }
        };
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000).setFastestInterval(1000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void updateMarkerLocation(LatLng latLng) {
        if (myMap != null) {
            if (marker == null) {
                marker = myMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
            } else {
                if (previousLocation == null || isSignificantlyDifferent(latLng, previousLocation)) {
                    marker.setPosition(latLng);
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    previousLocation = latLng;
                }
            }
        }
    }

    private boolean isSignificantlyDifferent(LatLng newLocation, LatLng oldLocation) {
        float[] results = new float[1];
        Location.distanceBetween(newLocation.latitude, newLocation.longitude, oldLocation.latitude, oldLocation.longitude, results);
        return results[0] > 10;
    }

    private void updateUI(Location location) {
        if (isRegistered && location != null) {
            int speedOption = sharedPreferences.getInt("speed_option", 1);
            double currentSpeed = location.getSpeed();
            if (speedOption == 2) {
                currentSpeed *= 3.6;
            }
            speedList.add(currentSpeed);
            tvSpeed.setText(String.format(Locale.getDefault(), "Speed: %.2f %s", currentSpeed, speedOption == 1 ? "km/h" : "m/s"));

            long elapsedTime = SystemClock.elapsedRealtime() - startTime;
            int hours = (int) (elapsedTime / 3600000);
            int minutes = (int) (elapsedTime % 3600000) / 60000;
            int seconds = (int) (elapsedTime % 60000) / 1000;
            tvTimer.setText(String.format(Locale.getDefault(), "Time: %02d:%02d:%02d", hours, minutes, seconds));

            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            if (lastLocation != null) {
                float[] results = new float[1];
                Location.distanceBetween(lastLocation.latitude, lastLocation.longitude, currentLocation.latitude, currentLocation.longitude, results);
                totalDistance += results[0];
            }
            lastLocation = currentLocation;
            tvDistance.setText(String.format(Locale.getDefault(), "Distance: %.2f m", totalDistance));

            updateMarkerLocation(currentLocation);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.trail_register) {
            if (!isRegistered) {
                isRegistered = true;
                isRouteSaved = false;
                startTime = SystemClock.elapsedRealtime();
                totalDistance = 0.0;
                speedList.clear();
                startLocationUpdates();
            } else {
                if (!isRouteSaved) {
                    isRouteSaved = true;
                    saveRoute();
                }
                isRegistered = false;
                stopLocationUpdates();
                clearTrailData();
                resetTimer();
            }
        } else if (id == R.id.trail_settings) {
            if (isRegistered) {
                isRegistered = false;
                isRouteSaved = true;
                saveRoute();
                stopLocationUpdates();
                resetTimer();
            }
            clearTrailData();
        } else if (id == R.id.trail_return) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            finish();
        }
    }

    private void resetTimer() {
        tvTimer.setText(String.format(Locale.getDefault(), "Time: %02d:%02d:%02d", 0, 0, 0));
    }

    private void saveRoute() {
        String startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        double distance = totalDistance;
        long elapsedTime = SystemClock.elapsedRealtime() - startTime;
        double averageSpeed = 0.0;
        if (!speedList.isEmpty()) {
            for (double speed : speedList) {
                averageSpeed += speed;
            }
            averageSpeed /= speedList.size();
        }
        long duration = elapsedTime / 1000;
        if (lastLocation != null) {
            dbHelper.addTrail(startDate, distance, duration, averageSpeed, lastLocation.latitude, lastLocation.longitude);
        }
        startActivity(new Intent(this, ManageScreen.class));
    }

    private void clearTrailData() {
        tvSpeed.setText(String.format(Locale.getDefault(), "Speed: %.2f %s", 0.0, "km/h"));
        tvTimer.setText(String.format(Locale.getDefault(), "Time: %02d:%02d:%02d", 0, 0, 0));
        tvDistance.setText(String.format(Locale.getDefault(), "Distance: %.2f m", 0.0));
        totalDistance = 0.0;
        lastLocation = null;
        speedList.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
        if (fusedLocationClient != null) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkLocationPermission()) {
            initMap();
        } else {
            requestLocationPermission();
        }
    }

}

