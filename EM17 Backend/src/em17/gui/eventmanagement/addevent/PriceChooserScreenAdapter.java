/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em17.gui.eventmanagement.addevent;

import common.MathUtils;
import em17.entities.SectorPrices;
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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Raffolox
 */
public class PriceChooserScreenAdapter implements Initializable {
    
    // <editor-fold defaultstate="collapsed" desc=" Event Listeners ">
        
        // <editor-fold defaultstate="collapsed" desc=" A SectorPrices element is selected from the pricesTable ">

        /**
         * This listens for a full price to be changed and notifies EventManagementController of the change
         */
        private final ChangeListener onPricesItemSelected = (ChangeListener<SectorPrices>) (ObservableValue<? extends SectorPrices> observable, SectorPrices oldValue, SectorPrices newValue) -> {
            if (oldValue != null) { //If no row was selected before newValue
                savePricesChanges(oldValue, getFullPriceSliderSelectedValue(), getReducedPriceSliderSelectedValue());
            }
            showFullAndReducedPriceSlidersAndLabels(newValue.getFullPrice(), newValue.getReducedPrice());
        };
    
        // </editor-fold>        
        
        // <editor-fold defaultstate="collapsed" desc=" The full price of a sector is changed ">

        /**
         * This listens for a full price to be changed and notifies EventManagementController of the change
         */
        private final ChangeListener onFullPriceSliderValueChanged = new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                fullPriceLabel.setText("New full price: " + MathUtils.toRoundedString(newValue.floatValue()) + "€");
                if (reducedPriceSlider.valueProperty().get() > newValue) {
                    reducedPriceSlider.valueProperty().set(newValue);
                }
            }
        };
    
        // </editor-fold>        
        
        // <editor-fold defaultstate="collapsed" desc=" The reduced price of a sector is changed ">

        /**
         * This listens for a reduced price to be changed and notifies EventManagementController of the change
         */
        private final ChangeListener onReducedPriceSliderValueChanged = new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                reducedPriceLabel.setText("New reduced price: " + MathUtils.toRoundedString(newValue.floatValue()) + "€");
                if (fullPriceSlider.valueProperty().get() < newValue) {
                    fullPriceSlider.valueProperty().set(newValue);
                }
            }
        };
    
        // </editor-fold>
    
    // </editor-fold>
        
    /**
     * The event management controller object
     */
    private final EventManagementController emc = EventManagementController.getInstance();
    
    /**
     * The table that contains all of the prices
     */
    @FXML
    private TableView pricesTable;
    
    /**
     * The column of pricesTable containing the sector names
     */
    @FXML
    private TableColumn sectorNamesColumn;
    
    /**
     * The column of pricesTable containing the full prices
     */
    @FXML
    private TableColumn fullPricesColumn;
    
    /**
     * The column of pricesTable containing the reduced prices
     */
    @FXML
    private TableColumn reducedPricesColumn;
    
    /**
     * The full price slider's label
     */
    @FXML
    private Label fullPriceLabel;
    
    /**
     * The reduced price slider's label
     */
    @FXML
    private Label reducedPriceLabel;
    
    /**
     * The full price slider
     */
    @FXML
    private Slider fullPriceSlider;
    
    /**
     * The reduced price slider
     */
    @FXML
    private Slider reducedPriceSlider;
    
    /**
     * The label for the user's name
     */
    @FXML
    private Label userNameText;
    
    /**
     * The width in percentage points of each column
     */
    private static final double PRICES_TABLE_COLUMNS_WIDTH[] = {0.5, 0.25, 0.25};
    
    /**
     * This method gets called on the adapter initialization
     * It is used to set each column's width according to the percentages specified in EVENT_TABLE_COLUMNS_WIDTH
     */
    @FXML
    private void setColumnsWidth() {
        ObservableList columns = pricesTable.getColumns();
        for (int i=0; i<columns.size(); i++) {
            TableColumn column = ((TableColumn)(columns.get(i)));
            column.prefWidthProperty().bind(pricesTable.widthProperty().multiply(PRICES_TABLE_COLUMNS_WIDTH[i]));
        }
    }
    
    /**
     * Shows the full price label, the full price slider, the reduced price label and the reduced price slider
     */
    @FXML
    private void showFullAndReducedPriceSlidersAndLabels(float fullPrice, float reducedPrice) {
        fullPriceLabel.visibleProperty().set(true);
        fullPriceLabel.setText("New full price: " + MathUtils.toRoundedString(fullPrice) + "€");
        reducedPriceLabel.visibleProperty().set(true);
        reducedPriceLabel.setText("New reduced price: " + MathUtils.toRoundedString(reducedPrice) + "€");
        fullPriceSlider.visibleProperty().set(true);
        fullPriceSlider.valueProperty().set(fullPrice);
        reducedPriceSlider.visibleProperty().set(true);
        reducedPriceSlider.valueProperty().set(reducedPrice);
    }
    
    /**
     * Hides the full price and reduced price sliders and labels
     */
    @FXML
    private void hideFullAndReducedPriceLabelsAndSliders() {
        fullPriceLabel.visibleProperty().set(false);
        reducedPriceLabel.visibleProperty().set(false);
        fullPriceSlider.visibleProperty().set(false);
        reducedPriceSlider.visibleProperty().set(false);
    }

    /**
     * Shows the sectors names in the table
     */
    @FXML
    private void setupPricesTable() {
        setColumnsWidth();
        pricesTable.setItems(FXCollections.observableArrayList(emc.getPricesOfActiveEvent()));
        pricesTable.getSelectionModel().selectedItemProperty().addListener(onPricesItemSelected);
        sectorNamesColumn.setCellValueFactory(new PropertyValueFactory<SectorPrices, String>("sectorName"));
        fullPricesColumn.setCellValueFactory(new PropertyValueFactory<SectorPrices, String>("fullPrice"));
        reducedPricesColumn.setCellValueFactory(new PropertyValueFactory<SectorPrices, String>("reducedPrice"));
    }
    
    /**
     * Hides the full price and reduced price sliders and labels, and sets the full price and reduced price sliders's on value changing listeners
     */
    @FXML
    private void setupFullAndReducedPriceLabelsAndSliders() {
        hideFullAndReducedPriceLabelsAndSliders();
        fullPriceSlider.valueProperty().addListener(onFullPriceSliderValueChanged);
        reducedPriceSlider.valueProperty().addListener(onReducedPriceSliderValueChanged);
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupPricesTable();
        setupFullAndReducedPriceLabelsAndSliders();
        userNameText.setText("Welcome " + SessionManager.getInstance().getActiveUserName());
    }
    
    /**
     * Handle the click on the back button
     * @param event 
     */
    @FXML
    protected void handleBackButtonPressed(ActionEvent event) {
        emc.showEventInformationInserterScreenForActiveEvent(event);
    }
    
    /**
     * Handle the click on the "Done" button
     * @param event 
     */
    @FXML
    protected void handleFinishButtonPressed(ActionEvent event) {
        if (pricesTable.getSelectionModel().getSelectedItem() != null) {
            savePricesChanges(((SectorPrices)(pricesTable.getSelectionModel().getSelectedItem())), getFullPriceSliderSelectedValue(), getReducedPriceSliderSelectedValue());
        }
        emc.finalizeEvent(event);
    }
    
    /**
     * @return the selected full price
     */
    @FXML
    private float getFullPriceSliderSelectedValue() {
        return Double.valueOf(fullPriceSlider.getValue()).floatValue();
    }
    
    /**
     * @return the selected reduced price
     */
    @FXML
    private float getReducedPriceSliderSelectedValue() {
        return Double.valueOf(reducedPriceSlider.getValue()).floatValue();
    }
    
    /**
     * Saved the last price changes for the specified SectorPrice object
     * @param sectorPrices
     * @param fullPriceSliderSelectedValue
     * @param reducedPriceSliderSelectedValue 
     */
    private void savePricesChanges(SectorPrices sectorPrices, float fullPriceSliderSelectedValue, float reducedPriceSliderSelectedValue) {
        emc.setTicketFullPriceForSector(sectorPrices.getSectorName(), MathUtils.toRoundedFloat(fullPriceSliderSelectedValue));
        emc.setTicketReducedPriceForSector(sectorPrices.getSectorName(), MathUtils.toRoundedFloat(reducedPriceSliderSelectedValue));
        sectorPrices.setFullPrice(MathUtils.toRoundedFloat(fullPriceSliderSelectedValue));
        sectorPrices.setReducedPrice(MathUtils.toRoundedFloat(reducedPriceSliderSelectedValue));
    }
}