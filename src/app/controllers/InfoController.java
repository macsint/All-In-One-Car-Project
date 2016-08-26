package app.controllers;
import app.VistaNavigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.pepperonas.fxiconics.FxIconics;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.math.RoundingMode;

public class InfoController implements Initializable {

    private static MainController mainController;

    @FXML
    private TopBarController topBarController;

    @FXML
    private Label phoneHistory;

    @FXML
    private Label drivingHistory;

    @FXML
    private Label radioHistory;

    @FXML
    private Label avgSpeed;

    @FXML
    private Label maxSpeed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainController = VistaNavigator.getMainController();
        topBarController.setBackButton(VistaNavigator.DASHBOARD, InfoController.this);

        Font font = FxIconics.getAwesomeFont(114);
        phoneHistory.setFont(font);
        phoneHistory.setText(FxFontAwesome.Icons.faw_phone.toString());

        drivingHistory.setFont(font);
        drivingHistory.setText(FxFontAwesome.Icons.faw_car.toString());

        radioHistory.setFont(font);
        radioHistory.setText(FxFontAwesome.Icons.faw_music.toString());

        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);

        maxSpeed.setText("Maximum Speed: " + String.valueOf(df.format(mainController.getSession().getDriver().getMaxSpeed())) + " mph");
        avgSpeed.setText("Average Speed: " + String.valueOf(df.format(mainController.getSession().getDriver().getAverageSpeed())) + " mph");

    }

    @FXML public void driveHistClick(MouseEvent arg0) {
        VistaNavigator.loadVista(VistaNavigator.DRIVEHIST);
    }

    @FXML public void phoneHistClick(MouseEvent arg0) {
        VistaNavigator.loadVista(VistaNavigator.PHONEHIST);
    }

    @FXML public void radioHistClick(MouseEvent arg0) {
        VistaNavigator.loadVista(VistaNavigator.RADIOHIST);
    }

    public static MainController getMainController() {
        return mainController;
    }

    public static void setMainController(MainController mainController) {
        InfoController.mainController = mainController;
    }

    public Label getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Label avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Label getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Label maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

}