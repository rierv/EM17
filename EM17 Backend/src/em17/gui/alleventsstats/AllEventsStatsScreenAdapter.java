/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em17.gui.alleventsstats;

import em17.logic.AllEventsStatsController;
import em17.logic.ChartCreator;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Raffolox
 */
public class AllEventsStatsScreenAdapter implements Initializable {
    
    /**
     * The controller of the "view all events' stats" function
     */
    private AllEventsStatsController aesc = new AllEventsStatsController();
    
    /**
     * The chart creator object, used to create charts
     */
    private ChartCreator chartCreator = new ChartCreator();
    
    // <editor-fold defaultstate="collapsed" desc=" Customers by age chart ">

    /**
     * The customers by age bar chart
     */
    @FXML
    private BarChart customersByAgeBarChart;
    
    /**
     * The horizontal axis of the customers by age bar chart
     */
    @FXML
    private CategoryAxis customerByAgeCategoryAxis;
    
    /**
     * The vertical axis of the customers by age bar chart
     */
    @FXML
    private NumberAxis customersByAgeNumberAxis;
    
    /**
     * The chart data
     */
    private Map<String, Number> customersByAgeData;
    
    /**
     * Fills the customers by age chart
     */
    private void setupCustomersByAgeChart() {
        customerByAgeCategoryAxis.setLabel("Age Ranges");
        customersByAgeNumberAxis.setLabel("Customers");
        chartCreator.createBarChart(customersByAgeBarChart, customersByAgeData);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Sold tickets by price range chart ">
    
    /**
     * The sold tickets by price range chart
     */
    @FXML
    private BarChart soldTicketsByPriceRangeChart;
    
    /**
     * The horizontal axis of the sold tickets by price range bar chart
     */
    @FXML
    private CategoryAxis soldTicketsByPriceRangeCategoryAxis;
    
    /**
     * The vertical axis of the sold tickets by price range bar chart
     */
    @FXML
    private NumberAxis soldTicketsByPriceRangeNumberAxis;
    
    /**
     * The chart data
     */
    private Map<String, Number> soldTicketsByPriceRangeData;
    
    /**
     * Fills the sold tickets by price range chart
     */
    private void setupSoldTicketsByPriceRangeChart() {
        soldTicketsByPriceRangeCategoryAxis.setLabel("Price ranges");
        soldTicketsByPriceRangeNumberAxis.setLabel("Sold tickets");
        chartCreator.createBarChart(soldTicketsByPriceRangeChart, soldTicketsByPriceRangeData);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Sold tickets by event type chart ">
    
    /**
     * The sold tickets by event type chart
     */
    @FXML
    private PieChart soldTicketsByEventTypeChart;
    
    /**
     * The data
     */
    private Map<String, Number> soldTicketsByEventTypeData;
    
    /**
     * Fills the sold tickets by event type chart
     */
    private void setupSoldTicketsByEventTypeChart() {
        chartCreator.createPieChart(soldTicketsByEventTypeChart, soldTicketsByEventTypeData);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Total sold tickets label ">
    
    /**
     * The total sold tickets label
     */
    @FXML
    private Label totalSoldTicketsLabel;
    
    /**
     * The data
     */
    private int totalSoldTickets;
  
    /**
     * Fills the total sold tickets label
     */
    private void setupTotalSoldTicketsLabel() {
        totalSoldTicketsLabel.setText("" + totalSoldTickets);
    }
    
    // </editor-fold>
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                customersByAgeData = aesc.getCustomersByAgeData();
                soldTicketsByPriceRangeData = aesc.getSoldTicketsByPriceData();
                soldTicketsByEventTypeData = aesc.getSoldTicketsByEventTypeData();
                totalSoldTickets = aesc.getTotalSoldTickets();
                setupCustomersByAgeChart();
                setupSoldTicketsByPriceRangeChart();
                setupSoldTicketsByEventTypeChart();
                setupTotalSoldTicketsLabel();
                aesc.sendIsReadySignal();
            }
        });
    }
}
