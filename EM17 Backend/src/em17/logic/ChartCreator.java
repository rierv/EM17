package em17.logic;


import java.util.*;
import java.util.function.Consumer;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;

/**
 * Creates a chart
 */
public class ChartCreator {

    /**
     * Default constructor
     */
    public ChartCreator() {
    }

    /**
     * Puts the given set of data inside the given BarChart 
     * @param chart
     * @param data
     */
    public void createBarChart(BarChart chart, Map<String, Number> data) {
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        data.keySet().forEach((label) -> {
            dataSeries.getData().add(new XYChart.Data<>(label, data.get(label)));
        });
        chart.getData().add(dataSeries);
    }

    /**
     * Puts the given set of data inside the given PieChart 
     * @param chart
     * @param data
     */
    public void createPieChart(PieChart chart, Map<String, Number> data) {
        data.keySet().forEach((label) -> {
            chart.getData().add(new Data(label, data.get(label).doubleValue()));
        });
    }
    
    /**
     * Puts the given set of data inside the given LineChart 
     * @param chart
     * @param data
     */
    public void createLineChart(LineChart chart, Map<String, Number> data) {
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        Collection<String> unsorted = data.keySet();
        List<String> sorted = new ArrayList<String>(unsorted);
        Collections.sort(sorted);
        sorted.forEach(new Consumer<String>() {
            @Override
            public void accept(String label) {
                dataSeries.getData().add(new XYChart.Data<>(label, data.get(label)));
            }
        });
        chart.getData().add(dataSeries);
    }

}