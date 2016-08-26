package app.controllers;

import app.VistaNavigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by arinhouck on 10/9/15.
 */
public class TopBarController implements Initializable {

    @FXML
    private Label time;

    @FXML
    private Label left;

    @FXML
    private Label right;

    private String location;

    private Object controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set time eg. 12:00 PM
        DateFormat currentTime = new SimpleDateFormat("hh:mm a");
        Date date = new Date();
        time.setText(currentTime.format(date).toString());
    }

    public void setBackButton(String location, Object controller) {
        left.setText("Back");
        this.location = location;
        this.controller = controller;
    }

    @FXML
    public void handleLeftClick(MouseEvent arg0) {
        if (location == null) {
            System.out.println("No location been set.");
            return;
        }
        if (controller instanceof RadioController) {
            ((RadioController) controller).setClosing(true);
        }
        VistaNavigator.loadVista(location);
    }
}
