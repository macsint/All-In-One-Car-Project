package app.controllers;

import app.SqlDriver;
import app.VistaNavigator;
import com.pepperonas.fxiconics.FxIconics;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by arinhouck on 10/12/15.
 */
public class DashboardController implements Initializable {

    private static MainController mainController;

    @FXML
    private Label radio;

    @FXML
    private Label phone;

    @FXML
    private Label routes;

    @FXML
    private Label info;

    @FXML
    private BottomBarController bottomBarController;

    @FXML
    private Label speedLabel;

    @FXML
    private Label milesLeftLabel;

    @FXML
    private VBox phoneBox;

    @FXML
    private VBox radioBox;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label lowFuel;

    private Double speed;

    private Double milesLeft;

    private boolean timerRunning = false;

    private List<Double> speedList = new ArrayList<Double>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainController = VistaNavigator.getMainController();

        Font font = FxIconics.getAwesomeFont(114);
        borderPane.setFocusTraversable(true);

        setSpeed(0.0);
        speedLabel.setText(speed.toString());

        milesLeft = mainController.getSession().getDriver().getMilesRemaining();
        milesLeftLabel.setText(milesLeft.toString());

        radio.setFont(font);
        radio.setText(FxFontAwesome.Icons.faw_music.toString());

        phone.setFont(font);
        phone.setText(FxFontAwesome.Icons.faw_phone.toString());

        routes.setFont(font);
        routes.setText(FxFontAwesome.Icons.faw_location_arrow.toString());

        info.setFont(font);
        info.setText(FxFontAwesome.Icons.faw_info_circle.toString());
    }

    @FXML
    private void keyPressed(final KeyEvent event)
    {
        if (speed >= 0 && milesLeft > 0) {

            changeSpeed(event.getCode());
        }
    }

    public void changeSpeed(final KeyCode code) {
        if (code == KeyCode.UP) {
            if (speed < 100) {
                speedLabel.setText((++speed).toString());
                speedList.add(speed);
            }
        } else if (code == KeyCode.DOWN) {
            if (speed > 0) {
                speedLabel.setText((--speed).toString());
                speedList.add(speed);
            }
        }
    }

    @FXML
    private void keyReleased(final KeyEvent event)
    {
        if (speed > 0) {
            if (!timerRunning) {
                timerRunning = true;
                decrementSpeed();
            }
        } else {
            speed = 0.0;
            speedLabel.setText((speed).toString());
        }

        if (milesLeft < 0) {
            milesLeft = 0.0;
            milesLeftLabel.setText((milesLeft).toString());
        }
        if (milesLeft < 25) {
            lowFuel.setStyle("-fx-text-fill:yellow;");
            lowFuel.setText("Low Fuel");
        }
        if (milesLeft == 0) {
            lowFuel.setStyle("-fx-text-fill:red;");
            lowFuel.setText("No fuel");
        }
    }

    private void decrementSpeed() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        speedLabel.setText((--speed).toString());
                        speedList.add(speed);
                        if (milesLeft >= 0) {
                            milesLeft -= .5;
                            milesLeftLabel.setText((milesLeft).toString());
                        }
                        if (speed <= 0) {
                            timer.cancel();
                            timerRunning = false;
                            milesLeftLabel.setText((--milesLeft).toString());
                        }
                    }
                });
            }
        }, 500, 500); //Every half second
    }

    @FXML
    public void handlePhoneClick(MouseEvent arg0) {
        updateSession();
        VistaNavigator.loadVista(VistaNavigator.PHONE);
    }

    @FXML
    public void handleRadioClick(MouseEvent arg0) {
        updateSession();
        VistaNavigator.loadVista(VistaNavigator.RADIO);
    }

    @FXML
    public void handleRouteClick(MouseEvent arg0) {
        updateSession();
        VistaNavigator.loadVista(VistaNavigator.ROUTE);
    }

    @FXML
    public void handleInfoClick(MouseEvent arg0) {
        updateSession();
        VistaNavigator.loadVista(VistaNavigator.INFORMATION);
    }

    public double[] calculateAverageAndMax() {
        double sum = 0.0;
        double avgSpeed = mainController.getSession().getDriver().getAverageSpeed();
        double maxSpeed = mainController.getSession().getDriver().getMaxSpeed();

        for (int i = 0; i < speedList.size(); i++) {
            if(speedList.get(i) > maxSpeed)
                maxSpeed = speedList.get(i);

            sum += speedList.get(i);
        }

        double temp = avgSpeed;
        //check that the user drove the car, otherwise don't do anything
        if(Double.isNaN(speedList.size()) == false && speedList.size() != 0)
        {
            //if the user has previous avg speed, then take the average of the new average and the old average
            if (temp != 0)
                avgSpeed = ((sum / speedList.size()) + temp) / 2;
            else
                avgSpeed = sum / speedList.size();
        }

        return new double[] {avgSpeed, maxSpeed};
    }

    private void updateSession() {
        double avgSpeed, maxSpeed;
        double[] result = calculateAverageAndMax();
        avgSpeed = result[0];
        maxSpeed = result[1];

        if (Double.isNaN(avgSpeed))
            avgSpeed = 0.0;
        if (Double.isNaN(maxSpeed))
            maxSpeed = 0.0;
        if (Double.isNaN(milesLeft))
            milesLeft = 0.0;


        SqlDriver.updateRecord("DRIVERS", "MAX_SPEED", mainController.getSession().getDriver().getID(), maxSpeed);
        mainController.getSession().getDriver().setMaxSpeed(maxSpeed);

        SqlDriver.updateRecord("DRIVERS", "AVERAGE_SPEED", mainController.getSession().getDriver().getID(), avgSpeed);
        mainController.getSession().getDriver().setAverageSpeed(avgSpeed);

        SqlDriver.updateAllRecords("DRIVERS", "MILES_REMAINING", milesLeft);
        mainController.getSession().getDriver().setMilesRemaining(milesLeft);

    }

    public List<Double> getSpeedList() {
        return speedList;
    }

    public void setSpeedList(List<Double> speedList) {
        this.speedList = speedList;
    }

    public Label getSpeedLabel() {
        return speedLabel;
    }

    public void setSpeedLabel(Label speedLabel) {
        this.speedLabel = speedLabel;
    }

    public static MainController getMainController() {
        return mainController;
    }

    public static void setMainController(MainController mainController) {
        DashboardController.mainController = mainController;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }


}
