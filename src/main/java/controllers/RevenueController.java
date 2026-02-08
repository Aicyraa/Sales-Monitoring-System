package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import models.Sales;
import utilities.DataManager;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class RevenueController implements Initializable {

    @FXML private LineChart<String, Number> revenueLineChart;
    @FXML private CategoryAxis lineChartXAxis;
    @FXML private NumberAxis lineChartYAxis;

    @FXML private BarChart<String, Number> monthlyBarChart;
    @FXML private CategoryAxis barChartXAxis;
    @FXML private NumberAxis barChartYAxis;

    @FXML private AreaChart<String, Number> cumulativeAreaChart;
    @FXML private CategoryAxis areaChartXAxis;
    @FXML private NumberAxis areaChartYAxis;

    @FXML private PieChart seasonalPieChart;

    @FXML private ComboBox<String> periodComboBox;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Button refreshButton;

    @FXML private Label totalRevenueLabel;
    @FXML private Label weeklyRevenueLabel;
    @FXML private Label todayRevenueLabel;
    @FXML private Label avgDailyLabel;

    private ArrayList<Sales> salesList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setupPeriodFilter();
            setupDatePickers();
            setupEventHandlers();

            loadSalesData();
            loadAllCharts();
            updateSummaryStatistics();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupPeriodFilter() {
        if (periodComboBox == null) return;
        ObservableList<String> periods = FXCollections.observableArrayList(
                "Daily", "Weekly", "Monthly", "Quarterly", "Yearly"
        );
        periodComboBox.setItems(periods);
        periodComboBox.setValue("Monthly");
    }

    private void setupDatePickers() {
        // Set default date to Feb 2, 2026 to match your dummy data
        if (endDatePicker != null) endDatePicker.setValue(LocalDate.of(2026, 2, 2));
        if (startDatePicker != null) startDatePicker.setValue(LocalDate.of(2026, 1, 1));
    }

    private void setupEventHandlers() {
        if (refreshButton != null) refreshButton.setOnAction(e -> handleRefresh());
        if (periodComboBox != null) periodComboBox.setOnAction(e -> loadAllCharts());
        if (startDatePicker != null) startDatePicker.setOnAction(e -> loadAllCharts());
        if (endDatePicker != null) endDatePicker.setOnAction(e -> loadAllCharts());
    }

    private void loadSalesData() {
        try {
            DataManager.generateSales();
            this.salesList = DataManager.getSaleList();
        } catch (Exception e) {
            this.salesList = new ArrayList<>();
            System.err.println("Error loading sales data: " + e.getMessage());
        }
    }

    private void loadAllCharts() {
        if (salesList == null || salesList.isEmpty()) return;

        if (revenueLineChart != null) loadRevenueLineChart();
        if (monthlyBarChart != null) loadMonthlyBarChart();
        if (cumulativeAreaChart != null) loadCumulativeAreaChart();
        if (seasonalPieChart != null) loadSeasonalPieChart();

        // Run this slightly later to ensure the chart is fully drawn before coloring
        Platform.runLater(this::styleCharts);
    }

    private void loadRevenueLineChart() {
        revenueLineChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenue Trend");

        String period = periodComboBox.getValue();
        if (period == null) period = "Monthly";

        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();
        ArrayList<Sales> filtered = filterSalesByDateRange(salesList, start, end);

        if (period.equals("Monthly")) loadMonthlyRevenue(series, filtered, start, end);
        else loadDailyRevenue(series, filtered, start, end);

        revenueLineChart.getData().add(series);
    }

    private void loadMonthlyBarChart() {
        monthlyBarChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Revenue");
        int currentYear = 2026;

        for (Month month : Month.values()) {
            Double revenue = controllers.SaleServices.getRevenuePerMonth(salesList, month, currentYear);
            if (revenue > 0) {
                series.getData().add(new XYChart.Data<>(month.getDisplayName(TextStyle.SHORT, Locale.getDefault()), revenue));
            }
        }
        monthlyBarChart.getData().add(series);
    }

    private void loadCumulativeAreaChart() {
        cumulativeAreaChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Cumulative Revenue");

        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        double total = controllers.SaleServices.getTotalRevenue(salesList);
        series.getData().add(new XYChart.Data<>(start.toString(), 0));
        series.getData().add(new XYChart.Data<>(end.toString(), total));

        cumulativeAreaChart.getData().add(series);
    }

    private void loadSeasonalPieChart() {
        seasonalPieChart.getData().clear();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        Set<String> seasons = new HashSet<>();
        for (Sales sale : salesList) {
            if (sale.getSeasonTag() != null && !sale.getSeasonTag().isEmpty()) {
                seasons.add(sale.getSeasonTag());
            }
        }

        double total = controllers.SaleServices.getTotalRevenue(salesList);

        for (String season : seasons) {
            Double revenue = controllers.SaleServices.getRevenueBySeason(salesList, season);
            if (revenue > 0) {
                PieChart.Data data = new PieChart.Data(season, revenue);
                data.nameProperty().bind(javafx.beans.binding.Bindings.concat(
                        season, " (", String.format("%.1f", (revenue / total * 100)), "%)"
                ));
                pieChartData.add(data);
            }
        }
        seasonalPieChart.setData(pieChartData);
    }

    private void updateSummaryStatistics() {
        if (salesList.isEmpty()) return;

        // *** FIX: Simulate "Today" as Feb 2, 2026 to match your dummy data ***
        LocalDate simulationDate = LocalDate.of(2026, 2, 2);

        double totalRevenue = 0;
        for (Sales s : salesList) totalRevenue += s.getTotalAmount();
        if (totalRevenueLabel != null) totalRevenueLabel.setText(String.format("₱%,.2f", totalRevenue));

        double todayRevenue = 0;
        for (Sales s : salesList) {
            if (s.getDate().isEqual(simulationDate)) todayRevenue += s.getTotalAmount();
        }
        if (todayRevenueLabel != null) todayRevenueLabel.setText(String.format("₱%,.2f", todayRevenue));

        double weeklyRevenue = 0;
        LocalDate weekStart = simulationDate.minusDays(6);
        for (Sales s : salesList) {
            LocalDate d = s.getDate();
            if ((d.isEqual(weekStart) || d.isAfter(weekStart)) &&
                    (d.isEqual(simulationDate) || d.isBefore(simulationDate))) {
                weeklyRevenue += s.getTotalAmount();
            }
        }
        if (weeklyRevenueLabel != null) weeklyRevenueLabel.setText(String.format("₱%,.2f", weeklyRevenue));

        if (startDatePicker != null && endDatePicker != null) {
            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();
            long days = java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1;
            double rangeRevenue = 0;
            for (Sales s : salesList) {
                LocalDate d = s.getDate();
                if ((d.isEqual(start) || d.isAfter(start)) && (d.isEqual(end) || d.isBefore(end))) {
                    rangeRevenue += s.getTotalAmount();
                }
            }
            double avg = (days > 0) ? rangeRevenue / days : 0;
            if (avgDailyLabel != null) avgDailyLabel.setText(String.format("₱%,.2f", avg));
        }
    }

    // *** THIS IS THE WHITE TEXT FIX ***
    private void styleCharts() {
        // Pie Chart Text
        for (Node node : seasonalPieChart.lookupAll(".chart-pie-label")) {
            if (node instanceof Text) {
                node.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 11px;");
            }
        }
        // Connecting Lines
        for (Node node : seasonalPieChart.lookupAll(".chart-pie-label-line")) {
            node.setStyle("-fx-stroke: #ffffff;");
        }

        // Legends and Axes
        Chart[] charts = {revenueLineChart, monthlyBarChart, cumulativeAreaChart, seasonalPieChart};
        for (Chart chart : charts) {
            if (chart == null) continue;
            for (Node node : chart.lookupAll(".chart-legend-item")) node.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
            for (Node node : chart.lookupAll(".axis-label")) node.setStyle("-fx-text-fill: #cbd5e1;");
            for (Node node : chart.lookupAll(".axis-tick-label")) node.setStyle("-fx-text-fill: #94a3b8;");
        }
    }

    private void loadDailyRevenue(XYChart.Series<String, Number> series, ArrayList<Sales> sales, LocalDate start, LocalDate end) {
        LocalDate current = start;
        while (!current.isAfter(end)) {
            Double revenue = controllers.SaleServices.getRevenuePerDay(sales, current);
            series.getData().add(new XYChart.Data<>(current.toString(), revenue));
            current = current.plusDays(1);
        }
    }

    private void loadMonthlyRevenue(XYChart.Series<String, Number> series, ArrayList<Sales> sales, LocalDate start, LocalDate end) {
        LocalDate current = start.withDayOfMonth(1);
        while (!current.isAfter(end)) {
            Double revenue = controllers.SaleServices.getRevenuePerMonth(sales, current.getMonth(), current.getYear());
            series.getData().add(new XYChart.Data<>(current.getMonth().toString(), revenue));
            current = current.plusMonths(1);
        }
    }

    private ArrayList<Sales> filterSalesByDateRange(ArrayList<Sales> sales, LocalDate start, LocalDate end) {
        ArrayList<Sales> filtered = new ArrayList<>();
        if (sales == null) return filtered;
        for (Sales sale : sales) {
            LocalDate d = sale.getDate();
            if ((d.isEqual(start) || d.isAfter(start)) && (d.isEqual(end) || d.isBefore(end))) {
                filtered.add(sale);
            }
        }
        return filtered;
    }

    @FXML public void handleRefresh() {
        loadSalesData();
        loadAllCharts();
        updateSummaryStatistics();
    }
}