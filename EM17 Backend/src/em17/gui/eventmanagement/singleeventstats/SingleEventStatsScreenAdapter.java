/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package em17.gui.eventmanagement.singleeventstats;

import em17.logic.ChartCreator;
import em17.logic.EventManagementController;
import em17.logic.SessionManager;
import em17.logic.SingleEventStatsController;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Raffolox
 */
public class SingleEventStatsScreenAdapter implements Initializable {


    /**
     * The controller of the "view all events' stats" function
     */
    private SingleEventStatsController sesc = new SingleEventStatsController(EventManagementController.getInstance().getActiveEventId());
    
    /**
     * The chart creator object, used to create charts
     */
    private ChartCreator chartCreator = new ChartCreator();
    
    /**
     * The label for the user's name
     */
    @FXML
    private Label userNameText;
    
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
     * Fills the customers by age chart
     */
    private void setupCustomersByAgeChart() {
        customerByAgeCategoryAxis.setLabel("Age Ranges");
        customersByAgeNumberAxis.setLabel("Customers");
        chartCreator.createBarChart(customersByAgeBarChart, sesc.getCustomersByAgeData());
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Sold tickets by sector chart ">
    
    /**
     * The sold tickets by sector pie chart
     */
    @FXML
    private PieChart soldTicketsBySectorPieChart;
    
    /**
     * Fills the sold tickets by sector chart
     */
    private void setupSoldTicketsBySectorChart() {
        chartCreator.createPieChart(soldTicketsBySectorPieChart, sesc.getSoldTicketsBySectorData());
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Full and reduced tickets pie chart ">
    
    /**
     * The full and reduced tickets pie chart
     */
    @FXML
    private PieChart fullAndReducedTicketsPieChart;
    
    /**
     * Fills the sold tickets by event type chart
     */
    private void setupFullAndReducedTicketsChart() {
        chartCreator.createPieChart(fullAndReducedTicketsPieChart, sesc.getFullAndReducedTicketsPriceData());
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Sold tickets over time line chart ">
    
    /**
     * The sold tickets over time line chart
     */
    @FXML
    private LineChart soldTicketsOverTimeLineChart;
  
    /**
     * Fills the total sold tickets over time line chart
     */
    private void setupSoldTicketsOverTimeChart() {
        Map<String, Number> map = sesc.getSoldTicketsOverTimeData();
        for (String s: map.keySet()) {
            System.out.println(map.get(s) + " were sold before " + s);
        }
        chartCreator.createLineChart(soldTicketsOverTimeLineChart, map);
    }
    
    // </editor-fold>
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupCustomersByAgeChart();
        setupSoldTicketsBySectorChart();
        setupFullAndReducedTicketsChart();
        setupSoldTicketsOverTimeChart();
        userNameText.setText("Welcome " + SessionManager.getInstance().getActiveUserName());
    }    
    
    /**
     * Handle the cancel button pressed event by returning to the previous screen
     * @param event 
     */
    @FXML
    protected void handleBackButtonPressed(ActionEvent event) {
        sesc.showMainScreen(event);
    }
}
