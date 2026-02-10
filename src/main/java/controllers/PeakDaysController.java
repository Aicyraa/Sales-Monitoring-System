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
        series.setName("Revenue by Day of Week");

        Map<DayOfWeek, Double> revenueByDay = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            revenueByDay.put(day, 0.0);
        }

        for (Sales sale : salesList) {
            DayOfWeek day = sale.getDate().getDayOfWeek();
            revenueByDay.put(day, revenueByDay.get(day) + sale.getTotalAmount());
        }

        for (DayOfWeek day : DayOfWeek.values()) {
            series.getData().add(new XYChart.Data<>(
                    day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    revenueByDay.get(day)));
        }

        bestDayChart.getData().add(series);
    }

    private void loadBestMonthChart() {
        if (bestMonthChart == null)
            return;

        bestMonthChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenue by Month");

        int currentYear = 2026;
        for (Month month : Month.values()) {
            Double revenue = SaleServices.getRevenuePerMonth(salesList, month, currentYear);
            if (revenue > 0) {
                series.getData().add(new XYChart.Data<>(
                        month.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                        revenue));
            }
        }

        bestMonthChart.getData().add(series);
    }

    private void loadBestSeasonChart() {
        if (bestSeasonChart == null)
            return;

        bestSeasonChart.getData().clear();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        Set<String> seasons = new HashSet<>();
        for (Sales sale : salesList) {
            if (sale.getSeasonTag() != null && !sale.getSeasonTag().isEmpty()) {
                seasons.add(sale.getSeasonTag());
            }
        }

        double total = SaleServices.getTotalRevenue(salesList);

        for (String season : seasons) {
            Double revenue = SaleServices.getRevenueBySeason(salesList, season);
            if (revenue > 0) {
                PieChart.Data data = new PieChart.Data(season, revenue);
                data.nameProperty().bind(javafx.beans.binding.Bindings.concat(
                        season, " (", String.format("%.1f", (revenue / total * 100)), "%)"));
                pieChartData.add(data);
            }
        }

        bestSeasonChart.setData(pieChartData);
    }

    private void updateBestPerformers() {
        // Find best day
        Map<DayOfWeek, Double> revenueByDay = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            revenueByDay.put(day, 0.0);
        }
        for (Sales sale : salesList) {
            DayOfWeek day = sale.getDate().getDayOfWeek();
            revenueByDay.put(day, revenueByDay.get(day) + sale.getTotalAmount());
        }
        DayOfWeek bestDay = Collections.max(revenueByDay.entrySet(), Map.Entry.comparingByValue()).getKey();
        double bestDayRevenue = revenueByDay.get(bestDay);

        if (bestDayLabel != null) {
            bestDayLabel.setText(bestDay.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }
        if (bestDayRevenueLabel != null) {
            bestDayRevenueLabel.setText(String.format("₱%,.2f", bestDayRevenue));
        }

        // Find best month
        int currentYear = 2026;
        Map<Month, Double> revenueByMonth = new HashMap<>();
        for (Month month : Month.values()) {
            revenueByMonth.put(month, SaleServices.getRevenuePerMonth(salesList, month, currentYear));
        }
        Month bestMonth = Collections.max(revenueByMonth.entrySet(), Map.Entry.comparingByValue()).getKey();
        double bestMonthRevenue = revenueByMonth.get(bestMonth);

        if (bestMonthLabel != null) {
            bestMonthLabel.setText(bestMonth.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }
        if (bestMonthRevenueLabel != null) {
            bestMonthRevenueLabel.setText(String.format("₱%,.2f", bestMonthRevenue));
        }

        // Find best season
        Map<String, Double> revenueBySeason = new HashMap<>();
        Set<String> seasons = new HashSet<>();
        for (Sales sale : salesList) {
            if (sale.getSeasonTag() != null && !sale.getSeasonTag().isEmpty()) {
                seasons.add(sale.getSeasonTag());
            }
        }
        for (String season : seasons) {
            revenueBySeason.put(season, SaleServices.getRevenueBySeason(salesList, season));
        }

        if (!revenueBySeason.isEmpty()) {
            String bestSeason = Collections.max(revenueBySeason.entrySet(), Map.Entry.comparingByValue()).getKey();
            double bestSeasonRevenue = revenueBySeason.get(bestSeason);

            if (bestSeasonLabel != null) {
                bestSeasonLabel.setText(bestSeason);
            }
            if (bestSeasonRevenueLabel != null) {
                bestSeasonRevenueLabel.setText(String.format("₱%,.2f", bestSeasonRevenue));
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
