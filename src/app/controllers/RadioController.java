package app.controllers;
import app.SqlDriver;
import app.VistaNavigator;
import app.models.RadioHistory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/**
 * Created by arinhouck on 10/17/15.
 */
public class RadioController implements Initializable {
    private MainController mainController;

    @FXML
    private TopBarController topBarController;

    @FXML
    private BottomBarController bottomBarController;

    @FXML
    private Button AMButton;

    @FXML
    private Button FMButton;

    @FXML
    private ListView stationList;

    @FXML
    private Label volumeLabel;

    private int volume;

    private LocalDate date;
    private LocalTime start;

    private ObservableList<String> stations;

    private BooleanProperty closing;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AMButton.getStyleClass().add("active");
        topBarController.setBackButton(VistaNavigator.DASHBOARD, RadioController.this);

        setMainController(VistaNavigator.getMainController());
        setVolume(mainController.getSession().getDriver().getRadioVolume());
        volumeLabel.setText(Integer.toString(volume));

        stations = stationList.getItems();

        setStationType(mainController.getSession().getDriver().getChannel());
        stationList.getSelectionModel().select(mainController.getSession().getDriver().getStation());
        date = LocalDate.now();
        start = LocalTime.now();
        closing = new SimpleBooleanProperty(false);

        closing.addListener((observable, oldValue, newValue) -> {
            saveHistory();
        });

        stationList.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                saveStation(stationList.getSelectionModel().getSelectedIndex());
            }
        });

    }

    private void saveHistory() {
        long duration = ChronoUnit.SECONDS.between(start, LocalTime.now());
        DateTimeFormatter outputFormat = new DateTimeFormatterBuilder().appendPattern("hh:mm a").toFormatter();
        SqlDriver.insertRecord(new RadioHistory(
                        mainController.getSession().getDriver().getID(),
                        mainController.getSession().getDriver().getFirstName(),
                        stationList.getSelectionModel().getSelectedItem().toString(),
                        date.toString(),
                        start.format(outputFormat).toString(),
                        (double) duration
                )
        );
    }

    public void setStationType(String type) {
        stations.clear();

        double milesRemaining = mainController.getSession().getDriver().getMilesRemaining();

        switch (type) {
            case "FM":
                AMButton.getStyleClass().removeAll("active");
                FMButton.getStyleClass().add("active");
                SqlDriver.updateRecord("DRIVERS", "CHANNEL", mainController.getSession().getDriver().getID(), "FM");
                mainController.getSession().getDriver().setChannel("FM");

                if (milesRemaining < 100) {
                    stationsFMByLocation(1);
                } else if (milesRemaining >= 100 && milesRemaining < 200) {
                    stationsFMByLocation(2);
                } else {
                    stationsFMByLocation(3);
                }
                break;
            case "AM":
                FMButton.getStyleClass().removeAll("active");
                AMButton.getStyleClass().add("active");
                SqlDriver.updateRecord("DRIVERS", "CHANNEL", mainController.getSession().getDriver().getID(), "AM");
                mainController.getSession().getDriver().setChannel("AM");

                if (milesRemaining < 100) {
                    stationsAMByLocation(1);
                } else if (milesRemaining >= 100 && milesRemaining < 200) {
                    stationsAMByLocation(2);
                } else {
                    stationsAMByLocation(3);
                }
                break;
        }
    }

    @FXML
    public int volumeUp() {
        if (volume != 10) {
            volumeLabel.setText(Integer.toString(++volume));
            SqlDriver.updateRecord("DRIVERS", "RADIO_VOLUME", mainController.getSession().getDriver().getID(), volume);
            mainController.getSession().getDriver().setRadioVolume(volume);
        }
        return volume;
    }

    @FXML
    public int volumeDown() {
        if (volume != 0) {
            volumeLabel.setText(Integer.toString(--volume));
            SqlDriver.updateRecord("DRIVERS", "RADIO_VOLUME", mainController.getSession().getDriver().getID(), volume);
            mainController.getSession().getDriver().setRadioVolume(volume);
        }

        return volume;
    }

    @FXML
    public void setAM() {
        setStationType("AM");
        saveStation(0);
        stationList.getSelectionModel().select(mainController.getSession().getDriver().getStation());
    }

    @FXML
    public void setFM() {
        setStationType("FM");
        saveStation(0);
        stationList.getSelectionModel().select(mainController.getSession().getDriver().getStation());
    }

    private void stationsAMByLocation(int location) {
        switch(location) {
            case 1:
                stations.addAll("550", "580", "620", "710", "740");
                break;
            case 2:
                stations.addAll("780", "830", "860", "910", "960");
                break;
            case 3:
                stations.addAll("990", "1010", "1060", "1100", "1150");
                break;
        }
    }

    private void stationsFMByLocation(int location) {
        switch(location) {
            case 1:
                stations.addAll("88.3", "88.7", "88.9", "89.1", "89.5");
                break;
            case 2:
                stations.addAll("89.7", "89.9", "90.3", "90.9", "91.1");
                break;
            case 3:
                stations.addAll("96.9", "97.5", "97.9", "98.3", "98.7");
                break;
        }
    }

    private void saveStation(int stationIndex) {
        mainController.getSession().getDriver().setStation(stationIndex);
        SqlDriver.updateRecord("DRIVERS", "STATION", mainController.getSession().getDriver().getID(), stationIndex);
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Button getAMButton() {
        return AMButton;
    }

    public void setAMButton(Button AMButton) {
        this.AMButton = AMButton;
    }

    public Button getFMButton() {
        return FMButton;
    }

    public void setFMButton(Button FMButton) {
        this.FMButton = FMButton;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Label getVolumeLabel() {
        return volumeLabel;
    }

    public void setVolumeLabel(Label volumeLabel) {
        this.volumeLabel = volumeLabel;
    }

    public ObservableList<String> getStations() {
        return stations;
    }

    public void setStations(ObservableList<String> stations) {
        this.stations = stations;
    }

    public ListView getStationList() {
        return stationList;
    }

    public void setStationList(ListView stationList) {
        this.stationList = stationList;
    }

    public boolean getClosing() {
        return closing.get();
    }

    public BooleanProperty closingProperty() {
        return closing;
    }

    public void setClosing(boolean closing) {
        this.closing.set(closing);
    }

    public BottomBarController getBottomBarController() {
        return bottomBarController;
    }

    public void setBottomBarController(BottomBarController bottomBarController) {
        this.bottomBarController = bottomBarController;
    }



}
