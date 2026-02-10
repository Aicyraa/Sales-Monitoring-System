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

    private void loadSalesData() {
        try {
            this.salesList = DataManager.getSales();
        } catch (Exception e) {
            this.salesList = new ArrayList<>();
            System.err.println("Error loading sales data: " + e.getMessage());
        }
    }

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

    private void updateBestPerformers() {
        // Find best day
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

        // Find best month
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

        // Find best season
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

    private void styleCharts() {
        // Style pie chart
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

        // Style all charts
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

        // Apply custom colors to bar charts
        applyChartColors(bestDayChart, "#3B82F6");
        applyChartColors(bestMonthChart, "#4ADE80");
    }

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
