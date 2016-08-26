package app.models;

/**
 * Created by drewdavis on 10/31/15.
 */
public class RadioHistory {

    private int driverID;
    private String name;
    private String station;
    private String date;
    private String time;
    private double duration;

    public RadioHistory(int driverID, String name, String station, String date, String time, double duration) {
        this.name = name;
        this.station = station;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.driverID = driverID;
    }

    public int getdriverID() {
        return driverID;
    }

    public void setdriverID(int driverID) {
        this.driverID = driverID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

}