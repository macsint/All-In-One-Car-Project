package app.controllers;

import app.Main;
import app.SqlDriver;
import app.VistaNavigator;
import app.models.DriverHistory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


/**
 * Created by arinhouck on 10/9/15.
 */
public class BottomBarController implements Initializable {
    private MainController mainController;

    @FXML
    private Label left;

    @FXML
    private Label center;

    @FXML
    private Label right;

    @FXML
    private Button logoutButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // View did load
        mainController = VistaNavigator.getMainController();
    }

    public void turnOff() {
        mainController.getSession().endSession();
        mainController.getSession().printDuration();
        createAndInsertDriverHistoryRecord();
        if(!Main.testing)
            VistaNavigator.loadVista(VistaNavigator.LOGIN);
        mainController.setSession(null);
    }

    public void createAndInsertDriverHistoryRecord() {
        int driverID = mainController.getSession().getDriver().getID();
        String name = mainController.getSession().getDriver().getFirstName() + " " + mainController.getSession().getDriver().getLastName();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        double duration = mainController.getSession().getDuration();
        double avgSpeed = mainController.getSession().getDriver().getAverageSpeed();
        double maxSpeed = mainController.getSession().getDriver().getMaxSpeed();
        DriverHistory driverHistory = new DriverHistory(driverID, name, dateString, duration, avgSpeed, maxSpeed);
        SqlDriver.insertRecord(driverHistory);
    }

    public Label getRight() {
        return right;
    }

    public Label getLeft() {
        return left;
    }

    public Label getCenter() {
        return center;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}

