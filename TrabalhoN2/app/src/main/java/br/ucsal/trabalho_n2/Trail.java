package br.ucsal.trabalho_n2;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Trail {
    private long id;
    private String startDate;
    private double distance;
    private double duration;
    private double avgSpeed;
    private double latitude;
    private double longitude;
    private List<LatLng> coordinates;
    public Trail() {}
    public Trail(String startDate, double distance, double duration, double avgSpeed, List<LatLng> coordinates) {
        this.startDate = startDate;
        this.distance = distance;
        this.duration = duration;
        this.avgSpeed = avgSpeed;
        this.coordinates = coordinates;
    }
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public String getStartDate() {return startDate;}
    public void setStartDate(String startDate) {this.startDate = startDate;}
    public double getDistance() {return distance;}
    public void setDistance(double distance) {this.distance = distance;}
    public double getDuration() {return duration;}
    public void setDuration(double duration) {this.duration = duration;}
    public double getAvgSpeed() {return avgSpeed;}
    public void setAvgSpeed(double avgSpeed) {this.avgSpeed = avgSpeed;}
    public double getLatitude() {return latitude;}
    public void setLatitude(double latitude) {this.latitude = latitude;}
    public double getLongitude() {return longitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}
    public List<LatLng> getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(List<LatLng> coordinates) {
        this.coordinates = coordinates;
    }
}
