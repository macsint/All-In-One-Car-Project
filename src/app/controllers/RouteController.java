package app.controllers;

import app.Main;
import app.VistaNavigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by arinhouck on 10/31/15.
 */
public class RouteController implements Initializable {
    private static MainController mainController;

    @FXML
    private TopBarController topBarController;

    @FXML
    private Slider slider;

    private double milesRemaining;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topBarController.setBackButton(VistaNavigator.DASHBOARD, RouteController.this);
        mainController = VistaNavigator.getMainController();

        setMilesRemaining(mainController.getSession().getDriver().getMilesRemaining());
        initSlider(0, Main.MAX_MILEAGE, milesRemaining);
    }

    public double initSlider(int min, int max, double milesRemaining) {
        double value = max - milesRemaining;
        slider.setValue(value);
        slider.setMin(min);
        slider.setMax(max);
        slider.setDisable(true);

        return value;
    }

    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    public static MainController getMainController() {
        return mainController;
    }

    public static void setMainController(MainController mainController) {
        RouteController.mainController = mainController;
    }

    public double getMilesRemaining() {
        return milesRemaining;
    }

    public void setMilesRemaining(double milesRemaining) {
        this.milesRemaining = milesRemaining;
    }
}
