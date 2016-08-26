package app.tests;

import app.Main;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.utils.FXTestUtils;
import static org.junit.Assert.*;


/**
 * Created by arinhouck on 11/29/15.
 */
public class A_SetupTest {

    @BeforeClass
    public static void setUpClass() {
        FXTestUtils.launchApp(Main.class); // Launch app for all tests
        Main.testing = true; // Stops SQL Queries from executing during tests
    }

    @Test
    public void setMainTesting() {
        assertTrue(Main.testing);
    }
}
