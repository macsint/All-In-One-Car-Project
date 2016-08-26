package app.controllers;
import app.SqlDriver;
import app.VistaNavigator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import app.models.RadioHistory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RadioHistController  implements Initializable {
    private static MainController mainController;

    @FXML
    private TopBarController topBarController;

    @FXML
    private TableView<RadioHistory> radioTableView;

    @FXML
    private TableColumn<RadioHistory, String> name;

    @FXML
    private  TableColumn<RadioHistory, String> station;

    @FXML
    private TableColumn<RadioHistory, String> date;

    @FXML
    private TableColumn<RadioHistory, String> time;

    @FXML
    private TableColumn<RadioHistory, Double> duration;


    private ObservableList<RadioHistory> radioHistory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topBarController.setBackButton(VistaNavigator.INFORMATION, RadioHistController.this);

        mainController = VistaNavigator.getMainController();

        List<String> results = SqlDriver.findBy("RADIO_HISTORIES", "DRIVER_ID", mainController.getSession().getDriver().getID());
        radioHistory = radioTableView.getItems();

        for(int i = 0; i < results.size(); i++) {
            String[] array = results.get(i).split("  ");

            radioHistory.add(
                    new RadioHistory(
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