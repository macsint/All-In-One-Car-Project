package app.tests;

import app.Main;
import app.controllers.DashboardController;
import app.controllers.InfoController;
import app.controllers.MainController;
import app.models.Driver;
import app.models.Session;
import javafx.scene.input.KeyCode;
import javafx.scene.control.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by arinhouck on 11/29/15.
 */
public class DashboardAndInfoControllerTest {


    private static DashboardController dashboardController;
    private static InfoController infoController;
    private static MainController mainController;
    private Driver driver;
    private Session session;

    @BeforeClass
    public static void setUpClass() {
        Main.testing = true; // Stops SQL Queries from executing during tests
        dashboardController = new DashboardController();
        infoController = new InfoController();

        mainController = new MainController();
        dashboardController.setMainController(mainController);
        infoController.setMainController(mainController);
    }

    @Before
    public void setup() {
        driver = new Driver(1, "Example", "User", "example", "password", "FM", 0, 0, 0, 300, 0, 0);
        session = new Session();

        mainController.setSession(session);
        mainController.getSession().setDriver(driver);

        dashboardController.setSpeed(0.0);
        dashboardController.setSpeedLabel(new Label());

        infoController.setMaxSpeed(new Label());
        infoController.setAvgSpeed(new Label());
    }


    // Integration Test

    @Test
    public void getMaxAndAverageSpeedFromDashboard() {

        // Speed up to 20
        for(int i = 0; i < 20; i++)
            dashboardController.changeSpeed(KeyCode.UP);

        assertEquals(20.0 , dashboardController.getSpeed(), 0);

        double[] result = dashboardController.calculateAverageAndMax();

        infoController.getAvgSpeed().setText(String.valueOf(result[0]));
        infoController.getMaxSpeed().setText(String.valueOf(result[1]));

        assertEquals("10.5", infoController.getAvgSpeed().getText());
        assertEquals("20.0", infoController.getMaxSpeed().getText());

    }

}
