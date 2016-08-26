package app.models;

/**
 * Created by Marius on 11/11/2015.
 */
public class Contact {

    private int driverID;
    private String PhoneNumber;

    public Contact(int driverID, String PhoneNumber) {
        this.driverID = driverID;
        this.PhoneNumber = PhoneNumber;
    }


    public void setdriverID(int driverID) {
        this.driverID = driverID;
    }

    public int getdriverID() {
        return driverID;
    }

    public void setphoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }
}
