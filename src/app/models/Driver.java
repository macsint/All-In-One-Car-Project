package app.models;

import javafx.beans.property.SimpleStringProperty;
/**
 * Created by arinhouck on 10/9/15.
 */
public class Driver {

    private int ID;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String channel;
    private int radioVolume;
    private int station;
    private int phoneVolume;
    private double milesRemaining;
    private double averageSpeed;
    private double maxSpeed;

    public Driver(int ID, String firstName, String lastName, String username, String password,
                  String channel, int radioVolume, int station, int phoneVolume,
                  double milesRemaining, double averageSpeed, double maxSpeed) {

        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.channel = channel;
        this.radioVolume = radioVolume;
        this.station = station;
        this.phoneVolume = phoneVolume;
        this.milesRemaining = milesRemaining;
        this.averageSpeed = averageSpeed;
        this.maxSpeed = maxSpeed;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getRadioVolume() {
        return radioVolume;
    }

    public void setRadioVolume(int radioVolume) {
        this.radioVolume = radioVolume;
    }

   public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public int getPhoneVolume() {
        return phoneVolume;
    }

    public void setPhoneVolume(int phoneVolume) {
        this.phoneVolume = phoneVolume;
    }

    public double getMilesRemaining() {
        return milesRemaining;
    }

    public void setMilesRemaining(double milesRemaining) {
        this.milesRemaining = milesRemaining;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

}
