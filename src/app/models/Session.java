package app.models;


import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.sql.*;

/**
 * Created by anthonymace on 10/14/15.
 */
public class Session {
    private LocalTime startTime;
    private LocalTime endTime;
    private long duration;
    private Driver driver;

    public Session() {
        startTime = LocalTime.now();
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public boolean validateLogin(String username, String password) {
        if (driver.getUsername().equals(username) && driver.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public void endSession() {
        endTime = LocalTime.now();
        duration = ChronoUnit.SECONDS.between(startTime, endTime);
    }

    public void printDuration() {
        System.out.println("The duration of your session: " + duration);
    }
}
