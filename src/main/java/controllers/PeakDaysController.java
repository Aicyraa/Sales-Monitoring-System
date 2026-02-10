package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import models.Sales;
import utilities.DataManager;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

/**
 * Controller for the Peak Days analytics view.
 * Displays charts and statistics showing best performing days, months, and
 * seasons based on product sales.
 */
public class PeakDaysController implements Initializable {

    @FXML
    private BarChart<String, Number> bestDayChart;
    @FXML
    private CategoryAxis dayChartXAxis;
    @FXML
    private NumberAxis dayChartYAxis;

    @FXML
    private BarChart<String, Number> bestMonthChart;
    @FXML
    private CategoryAxis monthChartXAxis;
    @FXML
    private NumberAxis monthChartYAxis;

    @FXML
    private PieChart bestSeasonChart;

    @FXML
    private Label bestDayLabel;
    @FXML
    private Label bestDayRevenueLabel;
    @FXML
    private Label bestMonthLabel;
    @FXML
    private Label bestMonthRevenueLabel;
    @FXML
    private Label bestSeasonLabel;
    @FXML
    private Label bestSeasonRevenueLabel;

    private ArrayList<Sales> salesList = new ArrayList<>();

    /**
     * Initializes the controller after FXML loading.
     * Loads sales data and populates all charts and statistics.
     * 
     * @param location  URL location used to resolve relative paths
     * @param resources ResourceBundle for localization
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadSalesData();
            loadBestDayChart();
            loadBestMonthChart();
            loadBestSeasonChart();
            updateBestPerformers();

            Platform.runLater(this::styleCharts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads sales data from the DataManager.
     * Initializes an empty list if loading fails.
     */
    private void loadSalesData() {
        try {
            this.salesList = DataManager.getSales();
        } catch (Exception e) {
            this.salesList = new ArrayList<>();
            System.err.println("Error loading sales data: " + e.getMessage());
        }
    }

    /**
     * Loads and populates the bar chart showing products sold by day of week.
     * Aggregates all sales data by day of week and displays total product counts.
     */
    private void loadBestDayChart() {
        if (bestDayChart == null)
            return;

        bestDayChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Products Sold by Day of Week");

        Map<DayOfWeek, Integer> productsByDay = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            productsByDay.put(day, 0);
        }

        for (Sales sale : salesList) {
            DayOfWeek day = sale.getDate().getDayOfWeek();
            productsByDay.put(day, productsByDay.get(day) + sale.getQuantitySold());
        }

        for (DayOfWeek day : DayOfWeek.values()) {
            series.getData().add(new XYChart.Data<>(
                    day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    productsByDay.get(day)));
        }

        bestDayChart.getData().add(series);
    }

    /**
     * Loads and populates the bar chart showing products sold by month.
     * Aggregates sales data for the current year (2026) by month.
     */
    private void loadBestMonthChart() {
        if (bestMonthChart == null)
            return;

        bestMonthChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Products Sold by Month");

        int currentYear = 2026;
        Map<Month, Integer> productsByMonth = new HashMap<>();

        for (Month month : Month.values()) {
            productsByMonth.put(month, 0);
        }

        for (Sales sale : salesList) {
            if (sale.getDate().getYear() == currentYear) {
                Month month = sale.getDate().getMonth();
                productsByMonth.put(month, productsByMonth.get(month) + sale.getQuantitySold());
            }
        }

        for (Month month : Month.values()) {
            int count = productsByMonth.get(month);
            if (count > 0) {
                series.getData().add(new XYChart.Data<>(
                        month.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                        count));
            }
        }

        bestMonthChart.getData().add(series);
    }

    /**
     * Loads and populates the pie chart showing products sold by season.
     * Displays percentage breakdown of products sold in each season.
     */
    private void loadBestSeasonChart() {
        if (bestSeasonChart == null)
            return;

        bestSeasonChart.getData().clear();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        Map<String, Integer> productsBySeason = new HashMap<>();

        for (Sales sale : salesList) {
            if (sale.getSeasonTag() != null && !sale.getSeasonTag().isEmpty()) {
                String season = sale.getSeasonTag();
                productsBySeason.put(season, productsBySeason.getOrDefault(season, 0) + sale.getQuantitySold());
            }
        }

        int totalProducts = productsBySeason.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : productsBySeason.entrySet()) {
            String season = entry.getKey();
            int count = entry.getValue();
            if (count > 0) {
                PieChart.Data data = new PieChart.Data(season, count);
                double percentage = totalProducts > 0 ? (count * 100.0 / totalProducts) : 0;
                data.nameProperty().bind(javafx.beans.binding.Bindings.concat(
                        season, " (", String.format("%.1f", percentage), "%)"));
                pieChartData.add(data);
            }
        }

        bestSeasonChart.setData(pieChartData);
    }

    /**
     * Updates the summary labels showing best performing day, month, and season.
     * Calculates which time periods had the highest product sales.
     */
    private void updateBestPerformers() {
        Map<DayOfWeek, Integer> productsByDay = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            productsByDay.put(day, 0);
        }
        for (Sales sale : salesList) {
            DayOfWeek day = sale.getDate().getDayOfWeek();
            productsByDay.put(day, productsByDay.get(day) + sale.getQuantitySold());
        }
        DayOfWeek bestDay = Collections.max(productsByDay.entrySet(), Map.Entry.comparingByValue()).getKey();
        int bestDayCount = productsByDay.get(bestDay);

        if (bestDayLabel != null) {
            bestDayLabel.setText(bestDay.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }
        if (bestDayRevenueLabel != null) {
            bestDayRevenueLabel.setText(String.format("%,d products", bestDayCount));
        }

        int currentYear = 2026;
        Map<Month, Integer> productsByMonth = new HashMap<>();
        for (Month month : Month.values()) {
            productsByMonth.put(month, 0);
        }
        for (Sales sale : salesList) {
            if (sale.getDate().getYear() == currentYear) {
                Month month = sale.getDate().getMonth();
                productsByMonth.put(month, productsByMonth.get(month) + sale.getQuantitySold());
            }
        }
        Month bestMonth = Collections.max(productsByMonth.entrySet(), Map.Entry.comparingByValue()).getKey();
        int bestMonthCount = productsByMonth.get(bestMonth);

        if (bestMonthLabel != null) {
            bestMonthLabel.setText(bestMonth.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }
        if (bestMonthRevenueLabel != null) {
            bestMonthRevenueLabel.setText(String.format("%,d products", bestMonthCount));
        }

        Map<String, Integer> productsBySeason = new HashMap<>();
        for (Sales sale : salesList) {
            if (sale.getSeasonTag() != null && !sale.getSeasonTag().isEmpty()) {
                String season = sale.getSeasonTag();
                productsBySeason.put(season, productsBySeason.getOrDefault(season, 0) + sale.getQuantitySold());
            }
        }

        if (!productsBySeason.isEmpty()) {
            String bestSeason = Collections.max(productsBySeason.entrySet(), Map.Entry.comparingByValue()).getKey();
            int bestSeasonCount = productsBySeason.get(bestSeason);

            if (bestSeasonLabel != null) {
                bestSeasonLabel.setText(bestSeason);
            }
            if (bestSeasonRevenueLabel != null) {
                bestSeasonRevenueLabel.setText(String.format("%,d products", bestSeasonCount));
            }
        }
    }

    /**
     * Applies custom styling to all charts.
     * Sets text colors, legend styles, and axis label colors to match the system
     * theme.
     */
    private void styleCharts() {
        if (bestSeasonChart != null) {
            for (Node node : bestSeasonChart.lookupAll(".chart-pie-label")) {
                if (node instanceof Text) {
                    node.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 11px;");
                }
            }
            for (Node node : bestSeasonChart.lookupAll(".chart-pie-label-line")) {
                node.setStyle("-fx-stroke: #ffffff;");
            }
        }

        Chart[] charts = { bestDayChart, bestMonthChart, bestSeasonChart };
        for (Chart chart : charts) {
            if (chart == null)
                continue;

            for (Node node : chart.lookupAll(".chart-legend-item"))
                node.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
            for (Node node : chart.lookupAll(".axis-label"))
                node.setStyle("-fx-text-fill: #cbd5e1;");
            for (Node node : chart.lookupAll(".axis-tick-label"))
                node.setStyle("-fx-text-fill: #94a3b8;");
        }

        applyChartColors(bestDayChart, "#3B82F6");
        applyChartColors(bestMonthChart, "#4ADE80");
    }

    /**
     * Applies custom color to bar chart bars.
     * Uses Platform.runLater to ensure chart is fully rendered before styling.
     * 
     * @param chart The bar chart to style
     * @param color Hex color code for the bars
     */
    private void applyChartColors(BarChart<String, Number> chart, String color) {
        if (chart == null)
            return;

        Platform.runLater(() -> {
            for (Node node : chart.lookupAll(".chart-bar")) {
                node.setStyle("-fx-bar-fill: " + color + ";");
            }
        });
    }
}
