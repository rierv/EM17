/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em17.gui;

import em17.logic.SessionManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Raffolox
 */
public class OuterViewAdapter implements Initializable {

    public OuterViewAdapter() {
        instance = this;
    }
    
    public static void receiveAllStatsReadySignal() {
        if (instance != null) {
            instance.tabPane.getTabs().add(instance.allEventsStatsScreenTab);
        }
    }

    /**
     * The user's name's label
     */
    @FXML
    private Label userNameLabel;
    
    /**
     * The multiplier used to set the logoutButtonImage dimensions
     * Their dimensions are gridPane's dimensions * this constant
     */
    private static final double LOGOUT_BUTTON_IMAGE_DIMENSIONS_MULTIPLIER = 0.04;
    
    /**
     * The last created instance of OuterViewAdapter, so the one that can manipulate the screen
     */
    private static OuterViewAdapter instance;
    
    /**
     * The logout button's image
     */
    @FXML
    private ImageView logoutButtonImage;
    
    /**
     * The entire pane
     */
    @FXML
    private GridPane gridPane;
    
    /**
     * The entire tab pane
     */
    @FXML
    private TabPane tabPane;
    
    /**
     * The all events stats screen tab
     */
    @FXML
    private Tab allEventsStatsScreenTab;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userNameLabel.textProperty().set("Welcome " + SessionManager.getInstance().getActiveUserName());
        logoutButtonImage.fitWidthProperty().bind(gridPane.widthProperty().multiply(LOGOUT_BUTTON_IMAGE_DIMENSIONS_MULTIPLIER));
        logoutButtonImage.fitWidthProperty().bind(gridPane.heightProperty().multiply(LOGOUT_BUTTON_IMAGE_DIMENSIONS_MULTIPLIER));
        tabPane.getTabs().remove(allEventsStatsScreenTab);
    }
    
    /**
     * Handle the logout button pressed event
     * @param event
     */
    @FXML
    protected void handleOnLogoutButtonPressed(ActionEvent event) {
        SessionManager.getInstance().logout(event);
    }
    
}
