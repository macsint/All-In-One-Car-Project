package app.models;

//import java.util.Date;

import app.SqlDriver;

/**
 * Created by drewdavis on 10/24/15.
 */
public class DriverHistory {
    private String name;
    private String date;
    private int driverID;
    private double duration;
    private double maxSpeed;
    private double avgSpeed;

    public DriverHistory(int driverID, String name, String date, double duration, double avgSpeed, double maxSpeed) {
        this.driverID = driverID;
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.maxSpeed = maxSpeed;
        this.avgSpeed = avgSpeed;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }
}

