package app.tests;

import app.Main;
import app.controllers.LoginController;
import app.controllers.MainController;
import app.controllers.RadioController;
import app.models.Driver;
import app.models.Session;
import javafx.scene.control.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by anthonymace on 11/29/15.
 */


public class SessionTest {

    private static MainController controller;
    private Driver driver;
    private Session session;

    @BeforeClass
    public static void setUpClass() {
        Main.testing = true; // Stops SQL Queries from executing during tests
        controller = new MainController();
    }

    @Before
    public void setup() {
        driver = new Driver(1, "Example", "User", "example", "password", "FM", 0, 0, 0, 300, 0, 0);
        session = new Session();

        controller.setSession(session);
        controller.getSession().setDriver(driver);
    }

    @Test
    public void validLogin() {
        boolean valid = controller.getSession().validateLogin(driver.getUsername(), driver.getPassword());
        assertEquals(true, valid);
    }

    @Test
    public void invalidLogin() {
        boolean invalid = controller.getSession().validateLogin("fail", "fail");
        assertEquals(false, invalid);
    }
}
