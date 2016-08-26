package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import app.controllers.MainController;
import java.io.IOException;

public class Main extends Application {
    public static boolean testing = false;
    public static int MAX_MILEAGE = 300;
    private static Scene scene;



    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("All-In-One Car System");

        stage.setScene(
                createScene(
                        loadMainPane()
                )
        );

        stage.show();
    }


    /**
     * Loads the main fxml layout.
     * Sets up the vista switching VistaNavigator.
     * Loads the first vista into the fxml layout.
     *
     * @return the loaded pane.
     * @throws IOException if the pane could not be loaded.
     */
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Pane mainPane = (Pane) loader.load(
                getClass().getResourceAsStream(
                        VistaNavigator.MAIN
                )
        );

        MainController mainController = loader.getController();

        VistaNavigator.setMainController(mainController);
        VistaNavigator.loadVista(VistaNavigator.LOGIN);

        return mainPane;
    }

    /**
     * Creates the main application scene.
     *
     * @param mainPane the main application layout.
     *
     * @return the created scene.
     */
    private Scene createScene(Pane mainPane) {
        scene = new Scene(
                mainPane, 850, 550
        );

        scene.getStylesheets().setAll(
                getClass().getResource("views/styles/main.css").toExternalForm()
        );

        return scene;
    }

    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
