/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em17.gui.eventmanagement;

import em17.entities.Event;
import em17.entities.EventTypeEnum;
import em17.logic.EventManagementController;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Raffolox
 */
public class EventManagerScreenAdapter implements Initializable {
    
 
    // <editor-fold defaultstate="collapsed" desc=" Event Listeners ">
        
        // <editor-fold defaultstate="collapsed" desc=" The selected event on the events table changes ">

        /**
         * This listens for changes on the sector capacity's slider's selected value and updates the label accordingly
         */
        private final ChangeListener onSelectedEventChangedListener = new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> observable, Event oldValue, Event newValue) {
                modifyEventButton.setDisable(false);
                deleteEventButton.setDisable(false);
                viewEventStatsButton.setDisable(false);
            }
        };
    
        // </editor-fold>
            
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc=" Variables ">
        
        // <editor-fold defaultstate="collapsed" desc=" Event table ">
        
        /**
         * The table that contains all of the events
         */
        @FXML
        private TableView eventTable;

        /**
         * The event id column of the event table
         */
        @FXML
        private TableColumn eventIdColumn; 

        /**
         * The event name column of the event table
         */
        @FXML
        private TableColumn eventNameColumn; 

        /**
         * The event location column of the event table
         */
        @FXML
        private TableColumn eventLocationColumn; 

        /**
         * The event start date column of the event table
         */
        @FXML
        private TableColumn eventStartDateColumn; 

        /**
         * The event start time column of the event table
         */
        @FXML
        private TableColumn eventStartTimeColumn; 

        /**
         * The event end date column of the event table
         */
        @FXML
        private TableColumn eventEndDateColumn; 

        /**
         * The event end time column of the event table
         */
        @FXML
        private TableColumn eventEndTimeColumn; 

        /**
         * The event sold tickets column of the event table
         */
        @FXML
        private TableColumn eventSoldTicketsColumn; 

        // </editor-fold>
    
        /**
         * The modify event button
         */
        @FXML
        private Button modifyEventButton;
        
        /**
         * The delete event button
         */
        @FXML
        private Button deleteEventButton;
        
        /**
         * The view statistics of event button
         */
        @FXML
        private Button viewEventStatsButton;
        
    // </editor-fold>
        
    /**
     * The event management controller object
     */
    private final EventManagementController emc = EventManagementController.getInstance();
    
    /**
     * The width in percentage points of each column
     */
    private static final double EVENT_TABLE_COLUMNS_WIDTH[] = {0.075, 0.275, 0.275, 0.075, 0.075, 0.075, 0.075, 0.075};
    
    
    // <editor-fold defaultstate="collapsed" desc=" Initialization methods ">
        
        // <editor-fold defaultstate="collapsed" desc=" Sub-methods ">
    
        /**
         * This method gets called on the adapter initialization
         * It is used to set the event table's on row selected listener
         */
        @FXML
        private void setTableSelectionListener() {
            eventTable.getSelectionModel().selectedItemProperty().addListener(onSelectedEventChangedListener);
        }

        /**
         * This method gets called on the adapter initialization
         * It is used to set the data displayed in the event table
         */
        @FXML
        private void setDisplayedData() {
            eventTable.setItems(FXCollections.observableArrayList(emc.getAllEvents()));
            eventIdColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("id"));
            eventNameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
            eventLocationColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("locationDescription"));
            eventStartDateColumn.setCellValueFactory(new PropertyValueFactory<Event, LocalDate>("startDate"));
            eventStartTimeColumn.setCellValueFactory(new PropertyValueFactory<Event, LocalTime>("startTime"));
            eventEndDateColumn.setCellValueFactory(new PropertyValueFactory<Event, LocalDate>("endDate"));
            eventEndTimeColumn.setCellValueFactory(new PropertyValueFactory<Event, LocalTime>("endTime"));
            eventSoldTicketsColumn.setCellValueFactory(new PropertyValueFactory<Event, EventTypeEnum.EventType>("eventType"));
            
        }

        /**
         * This method gets called on the adapter initialization
         * It is used to set each column's width according to the percentages specified in EVENT_TABLE_COLUMNS_WIDTH
         */
        @FXML
        private void setColumnsWidth() {
            ObservableList columns = eventTable.getColumns();
            for (int i=0; i<columns.size(); i++) {
                TableColumn column = ((TableColumn)(columns.get(i)));
                column.prefWidthProperty().bind(eventTable.widthProperty().multiply(EVENT_TABLE_COLUMNS_WIDTH[i]));
            }
        }

        /**
         * This method gets called on the adapter initialization
         * Setup the event table, by setting its columns's width, its displayed data and its selection listener
         */
        @FXML
        private void setupEventTable() {
            setColumnsWidth();
            setDisplayedData();
            setTableSelectionListener();
        }
    
        /**
         * Hides the modify event, delete event and view stats of event button
         */
        @FXML
        private void hideModifyDeleteStatsButtons() {
            modifyEventButton.setDisable(true);
            deleteEventButton.setDisable(true);
            viewEventStatsButton.setDisable(true);
        }
        
        // </editor-fold>
    
    // </editor-fold>
    
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hideModifyDeleteStatsButtons();
        setupEventTable();
        emc.reset();
    }
    
    /**
     * Handle the click on the "Add event" button
     * @param event 
     */
    @FXML
    protected void handleAddEventButtonAction(ActionEvent event) {
        emc.showEventInformationInserterScreenForNewEvent(event);
    }
    
    /**
     * Handle the click on the "Modify selected" button
     * @param event 
     */
    @FXML
    protected void handleModifyEventButtonAction(ActionEvent event) {
        emc.showEventInformationInserterScreenForExistingEvent(getSelectedEventId(), event);
    }
    
    /**
     * Handle the click on the "Delete selected" button
     * @param event 
     */
    @FXML
    protected void handleDeleteEventButtonAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.NONE, "Are you sure you want to delete the event " + ((Event)(eventTable.getSelectionModel().getSelectedItem())).getName() + "? This action is irreversible!", ButtonType.YES, ButtonType.CANCEL);
        alert.setTitle("Confirmation request");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            String eventID = getSelectedEventId();
            if (eventID != null && !eventID.isEmpty()) {
                emc.deleteEventWithId(getSelectedEventId());
                eventTable.getItems().remove(eventTable.getSelectionModel().getSelectedItem());
            }
        }
    }
    
    /**
     * Handle the click on the "View stats for selected" button
     * @param event 
     */
    @FXML
    protected void handleViewStatsForEventButtonAction(ActionEvent event) {
        emc.showEventStatistics(getSelectedEventId(), event);
    }
    
    /**
     * @return the selected event id
     */
    @FXML
    private String getSelectedEventId() {
        if (((Event)(eventTable.getSelectionModel().getSelectedItem())) != null) {
            return ((Event)(eventTable.getSelectionModel().getSelectedItem())).getId();
        }
        else {
            return null;
        }
    }
}
