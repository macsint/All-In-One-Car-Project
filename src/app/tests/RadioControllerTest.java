package app.tests;

import app.Main;
import app.controllers.BottomBarController;
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
 * Created by arinhouck on 11/21/15.
 */
public class RadioControllerTest {

    private static RadioController controller;
    private Driver driver;
    private Session session;

    @BeforeClass
    public static void setUpClass() {
        Main.testing = true; // Stops SQL Queries from executing during tests
        controller = new RadioController();
        controller.setMainController(new MainController());
        controller.setBottomBarController(new BottomBarController());
    }

    @Before
    public void setup() {
        driver = new Driver(1, "Example", "User", "example", "password", "FM", 0, 0, 0, 300, 0, 0);
        session = new Session();

        controller.getMainController().setSession(session);
        controller.getMainController().getSession().setDriver(driver);

        controller.getBottomBarController().setMainController(controller.getMainController());

        // Create Buttons
        controller.setAMButton(new Button("AM"));
        controller.setFMButton(new Button("FM"));

        // Stub stations
        controller.setStationList(new ListView());
        controller.setStations(controller.getStationList().getItems());

        // Set Driver's Current station
        String channel = controller.getMainController().getSession().getDriver().getChannel();
        controller.setStationType(channel);

        // Select station index
        controller.getStationList().getSelectionModel().select(controller.getMainController().getSession().getDriver().getStation());

    }

    @Test
    public void changeVolumeUp() {
        controller.setVolume(0);
        controller.setVolumeLabel(new Label(Integer.toString(controller.getVolume())));
        assertEquals(1, controller.volumeUp());
    }

    @Test
    public void changeVolumeDown() {
        controller.setVolume(5);
        controller.setVolumeLabel(new Label(Integer.toString(controller.getVolume())));
        assertEquals(4, controller.volumeDown());
    }

    @Test
    public void setAMStations() {
        controller.setAM();

        assertArrayEquals(new String[]{"990", "1010", "1060", "1100", "1150"}, controller.getStations().toArray());
        assertEquals(5, controller.getStations().size());
        assertEquals(0, controller.getStationList().getSelectionModel().getSelectedIndex());
    }

    @Test
    public void setFMStations() {
        controller.setFM();

        assertArrayEquals(new String[]{"96.9", "97.5", "97.9", "98.3", "98.7"}, controller.getStations().toArray());
        assertEquals(5, controller.getStations().size());
        assertEquals(0, controller.getStationList().getSelectionModel().getSelectedIndex());
    }

    @Test
    public void stationsChangeOnLocation() {
        // Change miles remaining
        controller.getMainController().getSession().getDriver().setMilesRemaining(150);

        // reload stations
        String channel = controller.getMainController().getSession().getDriver().getChannel();
        controller.setStationType(channel);

        // Expect Location 2 on FM
        assertArrayEquals(new String[]{"89.7", "89.9", "90.3", "90.9", "91.1"}, controller.getStations().toArray());
        assertEquals(5, controller.getStations().size());
    }

    @Test
    public void validateMaxVolume() {
        controller.setVolume(10);
        assertEquals(10, controller.volumeUp());
    }

    @Test
    public void validateMinVolume() {
        controller.setVolume(0);
        assertEquals(0, controller.volumeDown());
    }

    @Test
    public void integrateLogOutTest() {
        controller.getBottomBarController().turnOff();
        assertNull(controller.getMainController().getSession());
    }

}
