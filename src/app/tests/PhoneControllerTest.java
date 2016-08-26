    package app.tests;

/**
 * Created by Marius on 11/29/2015.
 */
    import app.Main;
    import app.controllers.MainController;
    import app.controllers.PhoneController;
    import app.controllers.RadioController;
    import app.controllers.RouteController;
    import app.models.Driver;
    import app.models.Session;
    import javafx.scene.control.*;
    import org.junit.Before;
    import org.junit.BeforeClass;
    import org.junit.Test;
    import org.loadui.testfx.utils.FXTestUtils;
    import static org.junit.Assert.assertEquals;

    import static org.junit.Assert.*;

    public class PhoneControllerTest {

        private static PhoneController controller;
        private Driver driver;
        private Session session;


        @BeforeClass
        public static void setUpClass() {
            Main.testing = true; // Stops SQL Queries from executing during tests
            controller = new PhoneController();
            controller.setMainController(new MainController());
        }

        @Before
        public void setup() {
            driver = new Driver(1, "Example", "User", "example", "password", "FM", 0, 0, 0, 300, 0, 0);
            session = new Session();
            controller.getMainController().setSession(session);
            controller.getMainController().getSession().setDriver(driver);

            controller.setPhoneNumber(new String());
            controller.setPhoneNumberField(new Label());

            controller.setContactsList(new ListView());
            controller.setContacts(controller.getContactsList().getItems());
            controller.getContacts().add(0,"");

        }

        @Test
        public void addInPhoneNumberField(){
           controller.addDigit6();
           controller.addDigit2();
           controller.addDigit2();
           controller.addDigit3();
           controller.addDigit0();
           controller.addDigit5();
           controller.addDigit6();
           controller.addDigit8();
           controller.addDigit9();
           controller.addDigit2();
           assertEquals("(622)-305-6892", controller.getPhoneNumberField().getText());
        }

        @Test
        public void clearPhoneNumberField(){
            controller.addDigit6();
            controller.addDigit2();
            controller.addDigit2();
            controller.addDigit3();
            controller.addDigit0();
            controller.addDigit5();
            controller.addDigit6();
            controller.addDigit8();
            controller.addDigit9();
            controller.addDigit2();
            controller.clearNumber();
            assertEquals("", controller.getPhoneNumberField().getText());
        }

        @Test
        public void addPhoneNumber() {
            controller.addDigit6();
            controller.addDigit2();
            controller.addDigit2();
            controller.addDigit3();
            controller.addDigit0();
            controller.addDigit5();
            controller.addDigit6();
            controller.addDigit8();
            controller.addDigit9();
            controller.addDigit2();
            controller.addNumber();
            assertEquals("(622)-305-6892", controller.getContacts().get(0));
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
        public void validatePhoneNumberLength(){
            controller.addDigit6();
            controller.addDigit2();
            controller.addDigit2();
            controller.addDigit3();
            controller.addDigit0();
            controller.addDigit5();
            controller.addDigit6();
            controller.addDigit8();
            controller.addDigit9();
            assertEquals("(622)-305-689", controller.getPhoneNumberField().getText());
            controller.addNumber();
            assertEquals("", controller.getContacts().get(0));
        }

    }

