package app.controllers;
import app.SqlDriver;
import app.VistaNavigator;
import app.models.DriverHistory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


//Helpful links
//http://docs.oracle.com/javafx/2/fxml_get_started/fxml_tutorial_intermediate.htm

// http://stackoverflow.com/questions/11180884/how-to-populate-a-tableview-that-is-defined-in-an-fxml-file-that-is-designed-in

public class DriverHistController implements Initializable {

    @FXML
    private TopBarController topBarController;

    @FXML
    private TableView<DriverHistory> driverTableView;

    @FXML
    private TableColumn<DriverHistory, String> name;

    @FXML
    private TableColumn<DriverHistory, String> date;

    @FXML
    private TableColumn<DriverHistory, Double> duration;

    @FXML
    private  TableColumn<DriverHistory, Double> avgSpeed;

    @FXML
    private TableColumn<DriverHistory, Double> maxSpeed;


    private ObservableList<DriverHistory> driverHistory;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topBarController.setBackButton(VistaNavigator.INFORMATION, DriverHistController.this);


        driverHistory = driverTableView.getItems();
        List<String> driverHistoryRecords = SqlDriver.getRecords("DRIVER_HISTORIES");
        for (String record : driverHistoryRecords) {
            String[] driverHistoryStr = record.split(" ");
            DriverHistory display =
                    new DriverHistory(
                            Integer.parseInt(driverHistoryStr[1]),
                            driverHistoryStr[2] + " " + driverHistoryStr[3],
                            driverHistoryStr[4],
                            Double.parseDouble(driverHistoryStr[5]),
                            Double.parseDouble(driverHistoryStr[6]),
                            Double.parseDouble(driverHistoryStr[7])
                    );
            driverHistory.add(display);
        }

    }

}