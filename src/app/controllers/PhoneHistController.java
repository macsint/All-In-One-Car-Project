package app.controllers;
import app.SqlDriver;
import app.VistaNavigator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import app.models.PhoneHistory;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PhoneHistController  implements Initializable {
    private static MainController mainController;


    @FXML
    private TopBarController topBarController;

    @FXML
    private TableView<PhoneHistory> phoneTableView;

    @FXML
    private TableColumn<PhoneHistory, String> name;

    @FXML
    private TableColumn<PhoneHistory, String> number;

    @FXML
    private TableColumn<PhoneHistory, String> date;

    @FXML
    private TableColumn<PhoneHistory, String> time;

    @FXML
    private TableColumn<PhoneHistory, Double> duration;


    private ObservableList<PhoneHistory> phoneHistory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topBarController.setBackButton(VistaNavigator.INFORMATION, PhoneHistController.this);

        mainController = VistaNavigator.getMainController();

        List<String> results = SqlDriver.findBy("PHONE_HISTORIES", "DRIVER_ID", mainController.getSession().getDriver().getID());
        phoneHistory = phoneTableView.getItems();

        for (int i = 0; i < results.size(); i++) {
            String[] array = results.get(i).split("  ");

            phoneHistory.add(
                    new PhoneHistory(
                            Integer.parseInt(array[1]),
                            array[2],
                            array[3],
                            array[4],
                            array[5],
                            Double.parseDouble(array[6])
                    )
            );
        }
    }
}