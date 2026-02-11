package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import models.Sales;
import utilities.DataManager;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class RevenueController {

    @FXML
    private ComboBox<String> periodCombo;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private Button refreshButton;

    @FXML
    private Label totalRevenueLabel;
    @FXML
    private Label weekRevenueLabel;
    @FXML
    private Label todayRevenueLabel;
    @FXML
    private Label avgDailyLabel;

    @FXML
    private LineChart<String, Number> revenueTrendChart;
    @FXML
    private BarChart<String, Number> monthlyComparisonChart;
    @FXML
    private LineChart<String, Number> cumulativeGrowthChart;
    @FXML
    private PieChart seasonalDistributionChart;

    private ArrayList<Sales> allSales;
    private LocalDate startDate;
    private LocalDate endDate;

    @FXML
    public void initialize() {
        allSales = DataManager.getSales();

        setupDateRange();
        setupPeriodCombo();
        loadRevenueData();
    }

    private void setupDateRange() {
        // Default range
        LocalDate earliest = LocalDate.of(2025, 3, 1);
        LocalDate latest = LocalDate.of(2026, 2, 10);

        if (!allSales.isEmpty()) {
            earliest = allSales.get(0).getDate();
            latest = allSales.get(0).getDate();

            for (Sales sale : allSales) {
                LocalDate saleDate = sale.getDate();
                if (saleDate.isBefore(earliest)) {
                    earliest = saleDate;
                }
                if (saleDate.isAfter(latest)) {
                    latest = saleDate;
                }
            }
        }

        startDate = earliest;
        endDate = latest;

        fromDatePicker.setValue(startDate);
        toDatePicker.setValue(endDate);
    }

    private void setupPeriodCombo() {
        periodCombo.getItems().addAll("Daily", "Weekly", "Monthly", "Yearly");
        periodCombo.setValue("Monthly");
    }

    @FXML
    private void handleRefresh() {
        startDate = fromDatePicker.getValue();
        endDate = toDatePicker.getValue();
        loadRevenueData();
    }

    private void loadRevenueData() {
        // Filter sales by date range
        List<Sales> filteredSales = allSales.stream()
                .filter(s -> !s.getDate().isBefore(startDate) && !s.getDate().isAfter(endDate))
                .collect(Collectors.toList());

        updateSummaryCards(filteredSales);
        updateRevenueTrendChart(filteredSales);
        updateMonthlyComparisonChart(filteredSales);
        updateCumulativeGrowthChart(filteredSales);
        updateSeasonalDistribution(filteredSales);

        // Apply styling after data is loaded
        javafx.application.Platform.runLater(this::styleCharts);
    }

    private void styleCharts() {
        // Pie Chart Text - Adjusts text color for better visibility
        for (javafx.scene.Node node : seasonalDistributionChart.lookupAll(".chart-pie-label")) {
            if (node instanceof javafx.scene.text.Text) {
                node.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 11px;");
            }
        }
        // Connecting Lines
        for (javafx.scene.Node node : seasonalDistributionChart.lookupAll(".chart-pie-label-line")) {
            node.setStyle("-fx-stroke: #ffffff;");
        }

        // Legends and Axes
        Chart[] charts = { revenueTrendChart, monthlyComparisonChart, cumulativeGrowthChart,
                seasonalDistributionChart };
        for (Chart chart : charts) {
            if (chart == null)
                continue;
            for (javafx.scene.Node node : chart.lookupAll(".chart-legend-item"))
                node.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
            for (javafx.scene.Node node : chart.lookupAll(".axis-label"))
                node.setStyle("-fx-text-fill: #cbd5e1;");
            for (javafx.scene.Node node : chart.lookupAll(".axis-tick-label"))
                node.setStyle("-fx-text-fill: #94a3b8;");
        }
    }

    private void updateSummaryCards(List<Sales> sales) {
        // Calculate total revenue
        double totalRevenue = sales.stream()
                .mapToDouble(Sales::getRevenue)
                .sum();
        totalRevenueLabel.setText(String.format("₱%,.2f", totalRevenue));

        // Calculate this week revenue
        LocalDate weekStart = LocalDate.now().minusDays(7);
        double weekRevenue = sales.stream()
                .filter(s -> !s.getDate().isBefore(weekStart))
                .mapToDouble(Sales::getRevenue)
                .sum();
        weekRevenueLabel.setText(String.format("₱%,.2f", weekRevenue));

        // Calculate today revenue
        LocalDate today = LocalDate.now();
        double todayRevenue = sales.stream()
                .filter(s -> s.getDate().equals(today))
                .mapToDouble(Sales::getRevenue)
                .sum();
        todayRevenueLabel.setText(String.format("₱%,.2f", todayRevenue));

        // Calculate average daily revenue
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        double avgDaily = daysBetween > 0 ? totalRevenue / daysBetween : 0;
        avgDailyLabel.setText(String.format("₱%,.2f", avgDaily));
    }

    private void updateRevenueTrendChart(List<Sales> sales) {
        revenueTrendChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenue");

        // Group by month
        Map<YearMonth, Double> monthlyRevenue = new TreeMap<>();

        for (Sales sale : sales) {
            YearMonth month = YearMonth.from(sale.getDateTime());
            monthlyRevenue.merge(month, sale.getRevenue(), Double::sum);
        }

        // Add data points
        for (Map.Entry<YearMonth, Double> entry : monthlyRevenue.entrySet()) {
            String monthLabel = entry.getKey().format(DateTimeFormatter.ofPattern("MMMM")).toUpperCase();
            series.getData().add(new XYChart.Data<>(monthLabel, entry.getValue()));
        }

        revenueTrendChart.getData().add(series);
    }

    private void updateMonthlyComparisonChart(List<Sales> sales) {
        monthlyComparisonChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Revenue");

        // Group by month
        Map<YearMonth, Double> monthlyRevenue = new TreeMap<>();

        for (Sales sale : sales) {
            YearMonth month = YearMonth.from(sale.getDateTime());
            monthlyRevenue.merge(month, sale.getRevenue(), Double::sum);
        }

        // Add all months
        for (Map.Entry<YearMonth, Double> entry : monthlyRevenue.entrySet()) {
            String monthLabel = entry.getKey().format(DateTimeFormatter.ofPattern("MMM"));
            series.getData().add(new XYChart.Data<>(monthLabel, entry.getValue()));
        }

        monthlyComparisonChart.getData().add(series);
    }

    private void updateCumulativeGrowthChart(List<Sales> sales) {
        cumulativeGrowthChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Cumulative Revenue");

        // Group by month
        Map<YearMonth, Double> monthlyRevenue = new TreeMap<>();

        for (Sales sale : sales) {
            YearMonth month = YearMonth.from(sale.getDateTime());
            monthlyRevenue.merge(month, sale.getRevenue(), Double::sum);
        }

        // Calculate cumulative
        double cumulative = 0;
        for (Map.Entry<YearMonth, Double> entry : monthlyRevenue.entrySet()) {
            cumulative += entry.getValue();
            String monthLabel = entry.getKey().format(DateTimeFormatter.ofPattern("MMM"));
            series.getData().add(new XYChart.Data<>(monthLabel, cumulative));
        }

        cumulativeGrowthChart.getData().add(series);
    }

    private void updateSeasonalDistribution(List<Sales> sales) {
        seasonalDistributionChart.getData().clear();

        // Group by season tag
        Map<String, Double> seasonRevenue = new HashMap<>();

        for (Sales sale : sales) {
            String season = sale.getSeasonTag();
            seasonRevenue.merge(season, sale.getRevenue(), Double::sum);
        }

        // Calculate total for percentages
        double total = seasonRevenue.values().stream().mapToDouble(Double::doubleValue).sum();

        // Sort by revenue (descending)
        List<Map.Entry<String, Double>> sortedSeasons = new ArrayList<>(seasonRevenue.entrySet());
        sortedSeasons.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        // Add pie chart data with percentages
        for (Map.Entry<String, Double> entry : sortedSeasons) {
            double percentage = (entry.getValue() / total) * 100;
            PieChart.Data data = new PieChart.Data(
                    String.format("%s (%.1f%%)", entry.getKey(), percentage),
                    entry.getValue());
            seasonalDistributionChart.getData().add(data);
        }
    }
}