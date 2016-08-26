package app.tests;

import app.Main;
import app.controllers.MainController;
import app.controllers.RouteController;
import app.models.Driver;
import app.models.Session;
import javafx.scene.control.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.utils.FXTestUtils;

import static org.junit.Assert.*;

/**
 * Created by arinhouck on 11/21/15.
 */
public class RouteControllerTest {

    private static RouteController controller;
    private Driver driver;
    private Session session;

    @BeforeClass
    public static void setUpClass() {
        Main.testing = true; // Stops SQL Queries from executing during tests
        controller = new RouteController();
        controller.setMainController(new MainController());
        controller.setSlider(new Slider());
    }

    @Before
    public void setup() {
        driver = new Driver(1, "Example", "User", "example", "password", "FM", 0, 0, 0, 220, 0, 0);
        session = new Session();

        controller.getMainController().setSession(session);
        controller.getMainController().getSession().setDriver(driver);
    }


    @Test
    public void canInitSlider() {
        controller.setMilesRemaining(driver.getMilesRemaining());
        // Expected 80 because 300 - 220 for travel distance
        assertEquals(80.0, controller.initSlider(0, Main.MAX_MILEAGE, controller.getMilesRemaining()), 0);
    }


}
