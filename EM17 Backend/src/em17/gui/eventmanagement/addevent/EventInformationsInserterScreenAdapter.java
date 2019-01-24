/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em17.gui.eventmanagement.addevent;

import em17.entities.Event;
import em17.entities.EventTypeEnum;
import em17.entities.EventTypeEnum.EventType;
import em17.gui.ToastMessage;
import em17.logic.EventManagementController;
import static em17.logic.EventManagementController.EVENT_CREATION_STATUS_LOCATION_NOT_AVAILABLE;
import static em17.logic.EventManagementController.EVENT_CREATION_STATUS_LOCATION_NOT_FOUND;
import em17.logic.SessionManager;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalDateTimeTextField;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 */
public class EventInformationsInserterScreenAdapter implements Initializable {

    /**
     * The event management controller object
     */
    private final EventManagementController emc = EventManagementController.getInstance();
    
    /**
     * The thread that updates the autocompletion panel
     */
    private Thread updateAutocompletionPanelThread;
    
    /**
     * This listens for text changes on the location text field
     */
    private final ChangeListener onLocationTextChangedListener = (ChangeListener<String>) (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
        EventInformationsInserterScreenAdapter.this.locationTextChanged(newValue);
    };
    
    /**
     * The image next to the event's starting time selector
     */
    @FXML
    private ImageView startingTimeImage;
    
    /**
     * The image next to the event's ending time selector
     */
    @FXML
    private ImageView endingTimeImage;
    
    /**
     * The entire panel
     */
    @FXML
    private GridPane gridPane;
    
    /**
     * The event name text field
     */
    @FXML
    private TextField nameTextField;
    
    /**
     * The location text field
     */
    @FXML
    private TextField locationTextField;
    
    /**
     * The event description text area
     */
    @FXML
    private TextArea descriptionTextField;
    
    /**
     * The event description text area
     */
    @FXML
    private LocalDateTimeTextField startDateTime;
    
    /**
     * The event description text area
     */
    @FXML
    private LocalDateTimeTextField endDateTime;
    
    /**
     * The event type combobox
     */
    @FXML
    private ComboBox eventTypeComboBox;
    
    /**
     * The label on top of the page, default = "Add an event", but "Edit the event" if an event is being modified
     */
    @FXML
    private Label addOrEditEventLabel;
    
    /**
     * The label for the user's name
     */
    @FXML
    private Label userNameText;
    
    /**
     * The multiplier used to set the startingTimeImage and endingTimeImage dimensions
     * Their dimensions are gridPane's dimensions * this constant
     */
    private static final double TIME_IMAGE_DIMENSIONS_MULTIPLIER = 0.065;
    
    /**
     * Set the dimensions of startingEventImage and endingEventImage
     */
    private void setTimeImagesToFitTheirCell() {
        startingTimeImage.fitWidthProperty().bind(gridPane.widthProperty().multiply(TIME_IMAGE_DIMENSIONS_MULTIPLIER));
        startingTimeImage.fitWidthProperty().bind(gridPane.heightProperty().multiply(TIME_IMAGE_DIMENSIONS_MULTIPLIER));
        endingTimeImage.fitWidthProperty().bind(gridPane.widthProperty().multiply(TIME_IMAGE_DIMENSIONS_MULTIPLIER));
        endingTimeImage.fitWidthProperty().bind(gridPane.heightProperty().multiply(TIME_IMAGE_DIMENSIONS_MULTIPLIER));
    }
    
    /**
     * If the event is being modified, fill the fields with the existing event's data
     */
    private void changeInterfaceIfModifyingEvent() {
        if (emc.isModifyingEvent()) {
            Event activeEvent = emc.getActiveEvent();
            addOrEditEventLabel.textProperty().set("Edit the event");
            nameTextField.textProperty().set(activeEvent.getName());
            nameTextField.editableProperty().set(false);
            locationTextField.textProperty().set(activeEvent.getLocationDescription());
            locationTextField.editableProperty().set(false);
            descriptionTextField.textProperty().set(activeEvent.getDescription());
            descriptionTextField.editableProperty().set(false);
            startDateTime.localDateTimeProperty().set(activeEvent.getStartDateTime());
            endDateTime.localDateTimeProperty().set(activeEvent.getEndDateTime());
            eventTypeComboBox.getSelectionModel().select(activeEvent.getEventType().toString());
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTimeImagesToFitTheirCell();
        locationTextField.textProperty().addListener(onLocationTextChangedListener);
        eventTypeComboBox.getItems().setAll(EventTypeEnum.values());
        eventTypeComboBox.getSelectionModel().select(EventTypeEnum.NON_CATEGORIZZATO.toString());
        changeInterfaceIfModifyingEvent();
        userNameText.setText("Welcome " + SessionManager.getInstance().getActiveUserName());
    }
    
    /**
     * Handle the cancel button pressed event by returning to the previous screen
     * @param event 
     */
    @FXML
    protected void handleCancelButtonPressed(ActionEvent event) {
        emc.showMainScreen(event);
    }
    
    /**
     * Handle the next button pressed event by going to the next screen
     * @param event 
     */
    @FXML
    protected void handleNextButtonPressed(ActionEvent event) {
        String eventTypeName = ((String)(eventTypeComboBox.getSelectionModel().getSelectedItem()));
        EventType eventType = EventTypeEnum.getEventTypeFromString(eventTypeName);
        if (nameTextField.getText() == null || nameTextField.getText().isEmpty() ||
            locationTextField.getText() == null || locationTextField.getText().isEmpty() ||
            descriptionTextField.getText() == null || descriptionTextField.getText().isEmpty() ||
            startDateTime.getText() == null || startDateTime.getText().isEmpty() ||
            endDateTime.getText() == null || endDateTime.getText().isEmpty() ||
            eventTypeName == null || eventTypeName.isEmpty()) {
            ToastMessage.makeErrorToast(((Stage)(gridPane.getScene().getWindow())), "Error: all fields need to be filled!");
        }
        else if (startDateTime.getLocalDateTime().isBefore(LocalDateTime.now())) {
            if (emc.isModifyingEvent()) {
                ToastMessage.makeErrorToast(((Stage)(gridPane.getScene().getWindow())), "Error: you can't modify an already started event!");
            }
            else {
                ToastMessage.makeErrorToast(((Stage)(gridPane.getScene().getWindow())), "Error: you can't create an event in the past!");
            }
        }
        else if (endDateTime.getLocalDateTime().isBefore(startDateTime.getLocalDateTime())) {
            ToastMessage.makeErrorToast(((Stage)(gridPane.getScene().getWindow())), "Error: your end date is earlier than your start date!");
        }
        else {
            if (!emc.isModifyingEvent()) {
                int eventStatus = emc.createEvent(nameTextField.getText(), descriptionTextField.getText(), locationTextField.getText(), startDateTime.getLocalDateTime(), endDateTime.getLocalDateTime(), eventType, event);
                if (eventStatus == EVENT_CREATION_STATUS_LOCATION_NOT_FOUND) {
                    ToastMessage.makeErrorToast(((Stage)(gridPane.getScene().getWindow())), "Error: the location you entered has not been found neither on the database nor on Google Maps!");
                }
                else if (eventStatus == EVENT_CREATION_STATUS_LOCATION_NOT_AVAILABLE) {
                    ToastMessage.makeErrorToast(((Stage)(gridPane.getScene().getWindow())), "Error: the location you entered is already occupied for the specified time frame!");
                }
            }
            else {
                emc.modifyEvent(startDateTime.getLocalDateTime(), endDateTime.getLocalDateTime(), eventType, event);
            }
        }
    }
    
    /**
     * Handle the location text field text changed, from the previous value to the newValue parameter
     * @param newValue
     */
    @FXML
    private void locationTextChanged(String newValue) {
        if (updateAutocompletionPanelThread != null) {
            updateAutocompletionPanelThread.interrupt();
        }
        updateAutocompletionPanelThread = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        List<String> locationsNames = emc.getNamesOfLocationsStartingWith(newValue);
                        updateLocationAutocompletionPanel(locationsNames);
                    }
                    catch (InterruptedException ignored) {                        
                    }
                }    
            }
        );
        updateAutocompletionPanelThread.start();        
    }
    
    /**
     * Uses the given location names to create an autocompletion panel for the location text field
     * @param locationNames
     */
    @FXML
    private void updateLocationAutocompletionPanel(List<String> locationsNames) {
        if (locationTextField != null && locationsNames != null) {
            TextFields.bindAutoCompletion(locationTextField, locationsNames);
        }
    }
    
}
