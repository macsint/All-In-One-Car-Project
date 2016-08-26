package app.controllers;

import app.SqlDriver;
import app.models.Contact;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.*;
import app.models.Session;

/**
 * Main controller class for the entire layout.
 */
public class MainController {
    private static Session session;
    private static SqlDriver sql;

    /** Holder of a switchable vista. */
    @FXML
    private StackPane vistaHolder;

    /**
     * Replaces the vista displayed in the vista holder with a new vista.
     *
     * @param node the vista node to be swapped in.
     */
    public void setVista(Node node) {
        vistaHolder.getChildren().setAll(node);
    }

    public Session getSession()
    {
        return session;
    }

    public void setSession(Session session)
    {
        this.session = session;
    }
}