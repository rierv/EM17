/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em17.gui.eventmanagement.addevent;

import em17.entities.Sector;
import em17.entities.Turnstile;
import em17.logic.EventManagementController;
import em17.logic.SessionManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 */
public class EventLocationDetailsInserterScreenAdapter implements Initializable {
 
    // <editor-fold defaultstate="collapsed" desc=" Event Listeners ">
        
        // <editor-fold defaultstate="collapsed" desc=" The sector capacity's slider's selected value changes ">

        /**
         * This listens for changes on the sector capacity's slider's selected value and updates the label accordingly
         */
        private final ChangeListener onSelectedValueChangedListener = new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                sectorCapacityLabel.textProperty().set("Capacity: " + newValue.intValue());
            }
        };
    
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" The selected sector on the sectors table changes ">

        private final ChangeListener onSectorSelectedListener = new ChangeListener<Sector>() {
            @Override
            public void changed(ObservableValue<? extends Sector> observable, Sector oldValue, Sector newValue) {
                System.out.println("changed sector");
                showAddTurnstileForm();
                showModifySectorForm();
                turnstilesTable.setItems(FXCollections.observableList(emc.getTurnstilesOfSectorOfActiveLocation(newValue.getSectorName())));
                turnstileNameColumn.setCellValueFactory(new PropertyValueFactory<Turnstile, String>("turnstileName"));
                sectorNameTextField.setText(newValue.getSectorName());
                sectorCapacitySlider.setValue(newValue.getCapacity());
            }
        };
    
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" The selected turnstile on the turnstiles table changes ">

        private final ChangeListener onTurnstileSelectedListener = (ChangeListener<Turnstile>) new ChangeListener<Turnstile>() {
            @Override
            public void changed(ObservableValue<? extends Turnstile> observable, Turnstile oldValue, Turnstile newValue) {
                System.out.println("changed turnstile");
                if (newValue != null) {
                    showModifyTurnstileForm();
                    turnstileNameTextField.setText(newValue.getTurnstileName());
                }
            }
        };
    
        // </editor-fold>
    
    // </editor-fold>
 
    // <editor-fold defaultstate="collapsed" desc=" Variables ">
    
    /**
     * The event management controller object
     */
    private final EventManagementController emc = EventManagementController.getInstance();
    
    /**
     * The label for the user's name
     */
    @FXML
    private Label userNameText;
    
        // <editor-fold defaultstate="collapsed" desc=" Sector panel ">
    
        // <editor-fold defaultstate="collapsed" desc=" Sectors table ">

        /**
         * The table that contains all of the sectors
         */
        @FXML
        private TableView sectorsTable;

        /**
         * The sector name column of the sectors table
         */
        @FXML
        private TableColumn sectorNameColumn;


        /**
         * The sector capacity column of the sectors table
         */
        @FXML
        private TableColumn capacityColumn;

        // </editor-fold>
    
        // <editor-fold defaultstate="collapsed" desc=" Sector form ">
        /**
         * The sector name's text field
         */
        @FXML
        private TextField sectorNameTextField;

        /**
         * The sector's capacity
         */
        @FXML
        private Slider sectorCapacitySlider;

        /**
         * The sector's capacity label that shows the slider's selected value
         */
        @FXML
        private Label sectorCapacityLabel;

        /**
         * The add sector form label
         */
        @FXML
        private Label addSectorLabel;

        /**
         * The modify sector form label
         */
        @FXML
        private Label modifySectorLabel;
        
        /**
         * The add sector button
         */
        @FXML
        private Button addSectorButton;
        
        /**
         * The remove sector button
         */
        @FXML
        private Button deleteSectorButton;
        
        /**
         * The modify sector button
         */
        @FXML
        private Button modifySectorButton;

        // </editor-fold>

    // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc=" Turnstile panel ">
        
            // <editor-fold defaultstate="collapsed" desc=" Turnstiles table ">

            /**
             * The table that contains all of the turnstiles
             */
            @FXML
            private TableView turnstilesTable;

            /**
             * The turnstile name column of the turnstiles table
             */
            @FXML
            private TableColumn turnstileNameColumn;
            
            // </editor-fold>
    
            // <editor-fold defaultstate="collapsed" desc=" Turnstile form ">

            /**
             * The turnstile name's text field
             */
            @FXML
            private TextField turnstileNameTextField;

            /**
             * The add turnstile form label
             */
            @FXML
            private Label addTurnstileLabel;

            /**
             * The modify turnstile form label
             */
            @FXML
            private Label modifyTurnstileLabel;

            /**
             * The add turnstile button
             */
            @FXML
            private Button addTurnstileButton;

            /**
             * The remove turnstile button
             */
            @FXML
            private Button deleteTurnstileButton;

            /**
             * The modify turnstile button
             */
            @FXML
            private Button modifyTurnstileButton;
            
            // </editor-fold>
            
        // </editor-fold>
            
    // </editor-fold>

    /**
     * The width in percentage points of each column of the sectors table
     */
    private static final double SECTORS_TABLE_COLUMNS_WIDTH[] = {0.7, 0.2, 0.1};
    
    // <editor-fold defaultstate="collapsed" desc=" UI Manipulations methods ">

        // <editor-fold defaultstate="collapsed" desc=" Turnstile form ">

        /**
         * Hides the turnstiles panel (table plus form and buttons)
         */
        @FXML
        private void hideEntireTurnstilesPanel() {
            turnstilesTable.visibleProperty().set(false);
            turnstileNameTextField.visibleProperty().set(false);
            hideModifyAndRemoveTurnstileButtonsAndLabel();
            hideAddTurnstileButtonAndLabel();
        }

        /**
         * Shows the turnstile panel (table plus form and add button)
         */
        @FXML
        private void showAddTurnstileForm() {
            turnstilesTable.visibleProperty().set(true);
            turnstileNameTextField.visibleProperty().set(true);
            hideModifyAndRemoveTurnstileButtonsAndLabel();
            showAddTurnstileButtonAndLabel();
        }

        /**
         * Shows the modify turnstile form instead of the add turnstile form
         */
        @FXML
        private void showModifyTurnstileForm() {
            turnstilesTable.visibleProperty().set(true);
            turnstileNameTextField.visibleProperty().set(true);
            hideAddTurnstileButtonAndLabel();
            showModifyAndRemoveTurnstileButtonsAndLabel();
        }


            // <editor-fold defaultstate="collapsed" desc=" Sub-methods ">

        /**
         * Hides the modify and remove sector buttons
         */
        @FXML
        private void hideModifyAndRemoveTurnstileButtonsAndLabel() {
            deleteTurnstileButton.visibleProperty().set(false);
            modifyTurnstileButton.visibleProperty().set(false);
            modifyTurnstileLabel.visibleProperty().set(false);
        }

        /**
         * Hides the add turnstile button
         */
        @FXML
        private void hideAddTurnstileButtonAndLabel() {
            addTurnstileButton.visibleProperty().set(false);
            addTurnstileLabel.visibleProperty().set(false);
        }

        /**
         * Shows the modify and remove sector buttons
         */
        @FXML
        private void showModifyAndRemoveTurnstileButtonsAndLabel() {
            deleteTurnstileButton.visibleProperty().set(true);
            modifyTurnstileButton.visibleProperty().set(true);
            modifyTurnstileLabel.visibleProperty().set(true);
        }

        /**
         * Shows the add turnstile button
         */
        @FXML
        private void showAddTurnstileButtonAndLabel() {
            addTurnstileButton.visibleProperty().set(true);
            addTurnstileLabel.visibleProperty().set(true);
        }

        // </editor-fold>

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc=" Sector form ">

            // <editor-fold defaultstate="collapsed" desc=" Sub-methods ">

            /**
             * Shows the add sector form
             */
            @FXML
            private void showAddSectorButtonAndLabel() {
                addSectorLabel.visibleProperty().set(true);
                addSectorButton.visibleProperty().set(true);
            }

            /**
             * Hides the add sector form
             */
            @FXML
            private void hideAddSectorButtonAndLabel() {
                addSectorLabel.visibleProperty().set(false);
                addSectorButton.visibleProperty().set(false);
            }

            /**
             * Shows the modify sector form
             */
            @FXML
            private void showModifyAndRemoveSectorButtonsAndLabel() {
                modifySectorLabel.visibleProperty().set(true);
                deleteSectorButton.visibleProperty().set(true);
                modifySectorButton.visibleProperty().set(true);
            }

            /**
             * Hides the modify sector form
             */
            @FXML
            private void hideModifyAndRemoveSectorButtonsAndLabel() {
                modifySectorLabel.visibleProperty().set(false);
                deleteSectorButton.visibleProperty().set(false);
                modifySectorButton.visibleProperty().set(false);
            }

            // </editor-fold>

            /**
             * Hides the add sector form and shows the modify sector form
             */
            @FXML
            private void showAddSectorForm() {
                hideModifyAndRemoveSectorButtonsAndLabel();
                showAddSectorButtonAndLabel();
            }

            /**
             * Hides the add sector form and shows the modify sector form
             */
            @FXML
            private void showModifySectorForm() {
                hideAddSectorButtonAndLabel();
                showModifyAndRemoveSectorButtonsAndLabel();
            }

        // </editor-fold>
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" UI Reading methods ">

    /**
     * @return the selected turnstile's name
     */
    private String getSelectedTurnstileName() {
        return ((Turnstile)(turnstilesTable.getSelectionModel().getSelectedItem())).getTurnstileName();
    }
    
    /**
     * @return the selected sector's name
     */
    private String getSelectedSectorName() {
        return ((Sector)(sectorsTable.getSelectionModel().getSelectedItem())).getSectorName();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Initialization methods ">
    
        // <editor-fold defaultstate="collapsed" desc=" Sub-methods ">

        /**
         * This method gets called on the adapter initialization
         * It is used to set each column's width according to the percentages specified in EVENT_TABLE_COLUMNS_WIDTH
         */
        @FXML
        private void setColumnsWidth() {
            ObservableList columns = sectorsTable.getColumns();
            for (int i=0; i<columns.size(); i++) {
                TableColumn column = ((TableColumn)(columns.get(i)));
                column.prefWidthProperty().bind(sectorsTable.widthProperty().multiply(SECTORS_TABLE_COLUMNS_WIDTH[i]));
            }
            ((TableColumn)(turnstilesTable.getColumns().get(0))).prefWidthProperty().bind(turnstilesTable.widthProperty());
        }

        /**
         * This method gets called on the adapter initialization
         * It is used to show the sectors data on the sectors table
         */
        @FXML
        private void showSectorsTableData() {
            sectorsTable.setItems(FXCollections.observableList(emc.getSectorsOfActiveLocation()));
            sectorNameColumn.setCellValueFactory(new PropertyValueFactory<Sector, String>("sectorName"));
            capacityColumn.setCellValueFactory(new PropertyValueFactory<Sector, String>("capacity"));
        }

        /**
         * This method gets called on the adapter initialization
         * It is used to setup the sector capacity slider
         */
        @FXML
        private void setupSectorCapacitySlider() {
            sectorCapacitySlider.valueProperty().addListener(onSelectedValueChangedListener);
        }

        // </editor-fold>
           
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setColumnsWidth();
        showSectorsTableData();
        showAddSectorForm();
        hideEntireTurnstilesPanel();
        setupSectorCapacitySlider();
        sectorsTable.getSelectionModel().selectedItemProperty().addListener(onSectorSelectedListener);
        turnstilesTable.getSelectionModel().selectedItemProperty().addListener(onTurnstileSelectedListener);
        userNameText.setText("Welcome " + SessionManager.getInstance().getActiveUserName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" FXML Event Handlers ">
    
    /**
     * Handle the cancel button pressed event by returning to the previous screen
     * @param event 
     */
    @FXML
    protected void handleBackButtonPressed(ActionEvent event) {
        emc.showEventInformationInserterScreenForNewEvent(event);
    }
    
    /**
     * Handle the next button pressed event by going to the next screen
     * @param event 
     */
    @FXML
    protected void handleNextButtonPressed(ActionEvent event) {
        emc.showPriceChooserScreen(event);
    }
    
    /**
     * Handle the next button pressed event by going to the next screen
     * @param event 
     */
    @FXML
    protected void handleAddSectorButtonPressed(ActionEvent event) {
        emc.addSector(sectorNameTextField.getText(), ((int)(sectorCapacitySlider.getValue())));
        sectorsTable.getItems().add(new Sector(sectorNameTextField.getText(), ((int)(sectorCapacitySlider.getValue()))));
    }
    
    /**
     * Handle the next button pressed event by going to the next screen
     * @param event 
     */
    @FXML
    protected void handleDeleteSectorButtonPressed(ActionEvent event) {
        emc.removeSector(getSelectedSectorName());
        Sector selected = ((Sector)(sectorsTable.getSelectionModel().getSelectedItem()));
        sectorsTable.getItems().remove(new Sector(selected.getSectorName(), selected.getCapacity()));
    }
    
    /**
     * Handle the next button pressed event by going to the next screen
     * @param event 
     */
    @FXML
    protected void handleModifySectorButtonPressed(ActionEvent event) {
        emc.modifySector(getSelectedSectorName(), sectorNameTextField.getText(), ((int)(sectorCapacitySlider.getValue())));
        Sector selected = ((Sector)(sectorsTable.getSelectionModel().getSelectedItem()));
        sectorsTable.getItems().add(new Sector(sectorNameTextField.getText(), ((int)(sectorCapacitySlider.getValue()))));
        sectorsTable.getItems().remove(new Sector(selected.getSectorName(), selected.getCapacity()));
    }
    
    /**
     * Handle the next button pressed event by going to the next screen
     * @param event 
     */
    @FXML
    protected void handleModifyTurnstileButtonPressed(ActionEvent event) {
        emc.renameTurnstile(getSelectedSectorName(), getSelectedTurnstileName(), turnstileNameTextField.getText());
        turnstilesTable.getItems().add(new Turnstile(turnstileNameTextField.getText()));
        turnstilesTable.getItems().remove(new Turnstile(getSelectedTurnstileName()));
    }
    
    /**
     * Handle the next button pressed event by going to the next screen
     * @param event 
     */
    @FXML
    protected void handleDeleteTurnstileButtonPressed(ActionEvent event) {
        emc.removeTurnstile(getSelectedSectorName(), getSelectedTurnstileName());
        turnstilesTable.getItems().remove(new Turnstile(getSelectedTurnstileName()));
    }
    
    /**
     * Handle the next button pressed event by going to the next screen
     * @param event 
     */
    @FXML
    protected void handleAddTurnstileButtonPressed(ActionEvent event) {
        emc.addTurnstile(getSelectedSectorName(), turnstileNameTextField.getText());
        turnstilesTable.getItems().add(new Turnstile(turnstileNameTextField.getText()));
    }
    
    // </editor-fold>

}
