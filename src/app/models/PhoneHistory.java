package app.models;

/**
 * Created by drewdavis on 10/31/15.
 */
public class PhoneHistory {
    private int driverID;
    private String name;
    private String phoneNumber;
    private String date;
    private String time;
    private Double duration;

    public PhoneHistory(int driverID, String name, String phoneNumber, String date, String time, Double duration) {
        this.driverID = driverID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.time = time;
        this.duration = duration;
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

    public String getNumber() {
        return phoneNumber;
    }

    public void setNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber; }

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

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

}
