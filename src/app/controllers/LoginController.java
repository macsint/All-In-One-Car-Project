package app.controllers;

import app.VistaNavigator;
import app.models.Driver;
import app.models.Session;
import com.pepperonas.fxiconics.*;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import app.SqlDriver;


public class LoginController implements Initializable {
    private static MainController mainController;

    @FXML
    private Label user;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font font = FxIconics.getAwesomeFont(196);
        user.setFont(font);
        user.setText(FxFontAwesome.Icons.faw_user.toString());

        mainController = VistaNavigator.getMainController();
    }

    @FXML
    protected void handleLoginAction(ActionEvent actionEvent) {
        List<String> results = SqlDriver.findBy("DRIVERS", "USERNAME", usernameTextField.getText());
        if(results.size() > 0) {
            String[] array = results.get(0).split("  ");
            Driver driver = new Driver(
                    Integer.parseInt(array[0]), // ID
                    array[1], // First Name
                    array[2], // Last Name
                    array[3], // Username
                    array[4], // Password
                    array[5], // Channel
                    Integer.parseInt(array[6]), // Radio Volume
                    Integer.parseInt(array[7]), // Station
                    Integer.parseInt(array[8]), // Phone Volume
                    Double.parseDouble(array[9]), // Miles remaining
                    Double.parseDouble(array[10]), // Average Speed
                    Double.parseDouble(array[11]) // Max Speed
            );
            Session session = new Session();
            mainController.setSession(session);
            mainController.getSession().setDriver(driver);
        } else {
            invalidLogin();
            return;
        }

        if (mainController.getSession().validateLogin(usernameTextField.getText(), passwordTextField.getText())) {
            VistaNavigator.loadVista(VistaNavigator.DASHBOARD);
        } else {
            invalidLogin();
        }

    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public void setUsernameTextField(TextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public PasswordField getPasswordTextField() {
        return passwordTextField;
    }

    public void setPasswordTextField(PasswordField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }

    public MainController getMainController() {
        return mainController;
    }

    public static void setMainController(MainController mainController) {
        LoginController.mainController = mainController;
    }

    public void invalidLogin() {
        Alert invalidLogin = new Alert(Alert.AlertType.WARNING);
        invalidLogin.setTitle("Invalid login");
        invalidLogin.setHeaderText("Invalid username and/or password");
        invalidLogin.setContentText("The username and/or password you entered are not valid! Please enter a valid login or register.");
        invalidLogin.showAndWait();

        mainController.setSession(null);
    }

}
