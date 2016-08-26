package app.controllers;
import app.SqlDriver;
import app.VistaNavigator;
import app.models.Contact;
import app.models.PhoneHistory;
import com.pepperonas.fxiconics.FxIconics;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;

/**
 * Created by Marius on 10/17/2015.
 */


public class PhoneController implements Initializable {
    private MainController mainController;

    @FXML
    private Label call;

    @FXML
    private Label clear;

    @FXML
    private Label add;

    @FXML
    private Label volumeLabel;
    private int volume;

    @FXML
    private Label phoneNumberField;

    private String phoneNumber;

    private LocalDate date;
    private LocalTime start;

    @FXML
    private ObservableList<String> contacts;


    @FXML
    private ListView contactsList;

    @FXML
    private TopBarController topBarController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        phoneNumber = phoneNumberField.getText();
        phoneNumberField.setText(phoneNumber);

        mainController = VistaNavigator.getMainController();

        volume = mainController.getSession().getDriver().getPhoneVolume();
        volumeLabel.setText(Integer.toString(volume));

        Font font = FxIconics.getAwesomeFont(52);
        call.setFont(font);
        call.setText(FxFontAwesome.Icons.faw_phone.toString());

        clear.setFont(font);
        clear.setText(FxFontAwesome.Icons.faw_times_circle_o.toString());

        add.setFont(font);
        add.setText(FxFontAwesome.Icons.faw_plus_square_o.toString());

        List<String> results = SqlDriver.findBy("CONTACTS", "DRIVER_ID", mainController.getSession().getDriver().getID());
        contacts = contactsList.getItems();
        contacts.add(0, "");

        for (int i = 0; i < results.size(); i++) {
            String[] array = results.get(i).split("  ");
            contacts.add(i, array[2]);
        }
        date = LocalDate.now();
        start = LocalTime.now();
        topBarController.setBackButton(VistaNavigator.DASHBOARD, PhoneController.this);
    }

    @FXML
    public void clearNumber() {
        phoneNumber = "";
        phoneNumberField.setText(phoneNumber);
    }

    @FXML
    public void addNumber() {
        for (int i = 0; i < 10; i++) {
            if (phoneNumber.length() == 14) {
                if (contacts.get(0) == "") {
                    contacts.add(0, phoneNumber);
                    contacts.remove(1);
                    SqlDriver.insertRecord(new Contact(mainController.getSession().getDriver().getID(), phoneNumber));
                } else {
                    contacts.add(i, phoneNumber);
                    SqlDriver.insertRecord(new Contact(mainController.getSession().getDriver().getID(), phoneNumber));
                }
                clearNumber();
            }
        }
    }

    @FXML
    public void callNumber() {
        if(phoneNumber.length() == 14) {
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3600)));
            timeline.play();
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setTitle("Phone Call");
            a.setHeaderText(phoneNumber);
            a.setContentText("Calling...");
            a.setResizable(true);
            ButtonType end = new ButtonType("End");
            a.getButtonTypes().setAll(end);

            Optional<ButtonType> result = a.showAndWait();

            if (result.get() == end) {
                long duration = ChronoUnit.SECONDS.between(start, LocalTime.now());
                DateTimeFormatter outputFormat = new DateTimeFormatterBuilder().appendPattern("hh:mm a").toFormatter();
                SqlDriver.insertRecord(new PhoneHistory(
                                mainController.getSession().getDriver().getID(),
                                mainController.getSession().getDriver().getFirstName(),
                                phoneNumber,
                                date.toString(),
                                start.format(outputFormat).toString(),
                                (double) duration)
                );
                timeline.stop();
                clearNumber();
                VistaNavigator.loadVista(VistaNavigator.PHONE);
            }
        }
        else
        {
            VistaNavigator.loadVista(VistaNavigator.PHONE);
        }
    }


    public void addDigit(String digit) {
        // Size of a US phone number is 14 when written as a string
        if (phoneNumber.length() < 14) {
            if (phoneNumber.isEmpty() == true) {
                phoneNumber += "(" + digit;
            } else if (phoneNumber.length() == 3) {
                phoneNumber += digit + ")" + "-";
            } else if (phoneNumber.length() == 8) {
                phoneNumber += digit + "-";
            } else {
                phoneNumber += digit;
            }
            phoneNumberField.setText(phoneNumber);
        }
    }

    @FXML
    public void addDigit0() {
        // Size of a US phone number is 14 when written as a string
        addDigit("0");
    }

    @FXML
    public void addDigit1() {
        // Size of a US phone number is 14 when written as a string
        addDigit("1");
    }

    @FXML
    public void addDigit2() {
        // Size of a US phone number is 14 when written as a string
        addDigit("2");
    }

    @FXML
    public void addDigit3() {
        // Size of a US phone number is 14 when written as a string
        addDigit("3");
    }

    @FXML
    public void addDigit4() {
        // Size of a US phone number is 14 when written as a string
        addDigit("4");
    }

    @FXML
    public void addDigit5() {
        // Size of a US phone number is 14 when written as a string
        addDigit("5");
    }

    @FXML
    public void addDigit6() {
        // Size of a US phone number is 14 when written as a string
        addDigit("6");
    }

    @FXML
    public void addDigit7() {
        // Size of a US phone number is 14 when written as a string
        addDigit("7");
    }

    @FXML
    public void addDigit8() {
        // Size of a US phone number is 14 when written as a string
        addDigit("8");
    }

    @FXML
    public void addDigit9() {
        // Size of a US phone number is 14 when written as a string
        addDigit("9");
    }

    @FXML
    public void addDigitStar() {
        // Size of a US phone number is 14 when written as a string
        addDigit("*");
    }

    @FXML
    public void addDigitHashtag() {
        // Size of a US phone number is 14 when written as a string
        addDigit("#");
    }

    @FXML
    public int volumeUp() {
        if (volume != 10) {
            volumeLabel.setText(Integer.toString(++volume));
            SqlDriver.updateRecord("DRIVERS", "PHONE_VOLUME", mainController.getSession().getDriver().getID(), volume);
            mainController.getSession().getDriver().setPhoneVolume(volume);
        }
        return volume;
    }

    @FXML
    public int volumeDown() {
        if (volume != 0) {
            volumeLabel.setText(Integer.toString(--volume));
            SqlDriver.updateRecord("DRIVERS", "PHONE_VOLUME", mainController.getSession().getDriver().getID(), volume);
            mainController.getSession().getDriver().setPhoneVolume(volume);
        }
        return volume;
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Label getVolumeLabel() {
        return volumeLabel;
    }

    public void setVolumeLabel(Label volumeLabel) {
        this.volumeLabel = volumeLabel;
    }

    public ListView getContactsList() {
        return contactsList;
    }

    public void setContactsList(ListView contactsList) {
        this.contactsList = contactsList;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Label getPhoneNumberField() {
        return phoneNumberField;
    }

    public void setPhoneNumberField(Label phoneNumberField) {
        this.phoneNumberField = phoneNumberField;
    }

    public ObservableList<String> getContacts() {
        return contacts;
    }

    public void setContacts(ObservableList<String> contacts) {
        this.contacts = contacts;
    }

}