package controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Products;
import models.Sales;
import utilities.DataManager;

import java.util.*;
import java.util.stream.Collectors;

public class ProfitMarginController {

    @FXML private Label totalRevenueLabel;
    @FXML private Label totalProfitLabel;
    @FXML private Label avgMarginLabel;

    @FXML private ComboBox<String> categoryFilter;
    @FXML private ComboBox<String> sortByCombo;

    @FXML private TableView<ProfitItem> profitTable;
    @FXML private TableColumn<ProfitItem, Integer> idColumn;
    @FXML private TableColumn<ProfitItem, String> nameColumn;
    @FXML private TableColumn<ProfitItem, String> categoryColumn;
    @FXML private TableColumn<ProfitItem, Double> costColumn;
    @FXML private TableColumn<ProfitItem, Double> priceColumn;
    @FXML private TableColumn<ProfitItem, Double> unitProfitColumn;
    @FXML private TableColumn<ProfitItem, Double> marginColumn;
    @FXML private TableColumn<ProfitItem, Integer> quantitySoldColumn;
    @FXML private TableColumn<ProfitItem, Double> totalRevenueColumn;
    @FXML private TableColumn<ProfitItem, Double> totalProfitColumn;

    private ObservableList<ProfitItem> profitItems;
    private ObservableList<ProfitItem> allProfitItems;

    @FXML
    public void initialize() {
        setupTableColumns();
        setupFilters();
        loadProfitData();
    }

    // ========== CALCULATION METHODS ==========

    private double calculateProfitMargin(double sellingPrice, double cost) {
        if (sellingPrice == 0) return 0.0;
        return ((sellingPrice - cost) / sellingPrice) * 100;
    }

    private double calculateProfit(int productId, ArrayList<Sales> sales, double cost) {
        double totalProfit = 0.0;

        for (Sales sale : sales) {
            if (sale.getProductId() == productId) {
                double unitPrice = sale.getUnitPrice();
                int quantity = sale.getQuantitySold();
                double discount = sale.getDiscountPercentage();

                double effectivePrice = unitPrice * (1 - discount / 100);
                double profitPerUnit = effectivePrice - cost;
                totalProfit += profitPerUnit * quantity;
            }
        }

        return totalProfit;
    }

    private double calculateRevenue(int productId, ArrayList<Sales> sales) {
        double totalRevenue = 0.0;

        for (Sales sale : sales) {
            if (sale.getProductId() == productId) {
                double unitPrice = sale.getUnitPrice();
                int quantity = sale.getQuantitySold();
                double discount = sale.getDiscountPercentage();

                double effectivePrice = unitPrice * (1 - discount / 100);
                totalRevenue += effectivePrice * quantity;
            }
        }

        return totalRevenue;
    }

    private int getTotalQuantitySold(int productId, ArrayList<Sales> sales) {
        int totalQuantity = 0;

        for (Sales sale : sales) {
            if (sale.getProductId() == productId) {
                totalQuantity += sale.getQuantitySold();
            }
        }

        return totalQuantity;
    }

    // ========== UI SETUP METHODS ==========

    private void setupTableColumns() {
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));
        costColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getCost()).asObject());
        priceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        unitProfitColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getUnitProfit()).asObject());
        marginColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMargin()).asObject());
        quantitySoldColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantitySold()).asObject());
        totalRevenueColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotalRevenue()).asObject());
        totalProfitColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotalProfit()).asObject());

        costColumn.setCellFactory(col -> new CurrencyCell<>());
        priceColumn.setCellFactory(col -> new CurrencyCell<>());
        unitProfitColumn.setCellFactory(col -> new CurrencyCell<>());
        totalRevenueColumn.setCellFactory(col -> new CurrencyCell<>());
        totalProfitColumn.setCellFactory(col -> new CurrencyCell<>());
        marginColumn.setCellFactory(col -> new PercentageCell<>());
    }

    private void setupFilters() {
        Set<String> categories = DataManager.getProducts().stream()
                .map(Products::getCategory)
                .collect(Collectors.toSet());

        ObservableList<String> categoryList = FXCollections.observableArrayList("All Categories");
        categoryList.addAll(categories);
        categoryFilter.setItems(categoryList);
        categoryFilter.setValue("All Categories");

        ObservableList<String> sortOptions = FXCollections.observableArrayList(
                "Profit Margin (High to Low)",
                "Profit Margin (Low to High)",
                "Total Profit (High to Low)",
                "Total Revenue (High to Low)",
                "Product Name (A-Z)"
        );
        sortByCombo.setItems(sortOptions);
        sortByCombo.setValue("Profit Margin (High to Low)");
    }

    private void loadProfitData() {
        allProfitItems = FXCollections.observableArrayList();

        ArrayList<Products> products = DataManager.getProducts();
        ArrayList<Sales> sales = DataManager.getSales();

        for (Products product : products) {
            int quantitySold = getTotalQuantitySold(product.getId(), sales);
            double totalRevenue = calculateRevenue(product.getId(), sales);
            double totalProfit = calculateProfit(product.getId(), sales, product.getCost());
            double unitProfit = product.getPrice() - product.getCost();
            double margin = calculateProfitMargin(product.getPrice(), product.getCost());

            ProfitItem item = new ProfitItem(
                    product.getId(),
                    product.getName(),
                    product.getCategory(),
                    product.getCost(),
                    product.getPrice(),
                    unitProfit,
                    margin,
                    quantitySold,
                    totalRevenue,
                    totalProfit
            );

            allProfitItems.add(item);
        }

        profitItems = FXCollections.observableArrayList(allProfitItems);
        profitTable.setItems(profitItems);

        updateSummary();
        sortTable();
    }

    private void updateSummary() {
        double totalRevenue = profitItems.stream()
                .mapToDouble(ProfitItem::getTotalRevenue)
                .sum();

        double totalProfit = profitItems.stream()
                .mapToDouble(ProfitItem::getTotalProfit)
                .sum();

        double avgMargin = profitItems.stream()
                .mapToDouble(ProfitItem::getMargin)
                .average()
                .orElse(0.0);

        totalRevenueLabel.setText(String.format("₱%.2f", totalRevenue));
        totalProfitLabel.setText(String.format("₱%.2f", totalProfit));
        avgMarginLabel.setText(String.format("%.2f%%", avgMargin));
    }

    @FXML
    private void filterByCategory() {
        String selectedCategory = categoryFilter.getValue();

        if ("All Categories".equals(selectedCategory)) {
            profitItems.setAll(allProfitItems);
        } else {
            List<ProfitItem> filtered = allProfitItems.stream()
                    .filter(item -> item.getCategory().equals(selectedCategory))
                    .collect(Collectors.toList());
            profitItems.setAll(filtered);
        }

        updateSummary();
        sortTable();
    }

    @FXML
    private void sortTable() {
        String sortBy = sortByCombo.getValue();

        if (sortBy == null) return;

        Comparator<ProfitItem> comparator;

        switch (sortBy) {
            case "Profit Margin (High to Low)":
                comparator = Comparator.comparingDouble(ProfitItem::getMargin).reversed();
                break;
            case "Profit Margin (Low to High)":
                comparator = Comparator.comparingDouble(ProfitItem::getMargin);
                break;
            case "Total Profit (High to Low)":
                comparator = Comparator.comparingDouble(ProfitItem::getTotalProfit).reversed();
                break;
            case "Total Revenue (High to Low)":
                comparator = Comparator.comparingDouble(ProfitItem::getTotalRevenue).reversed();
                break;
            case "Product Name (A-Z)":
                comparator = Comparator.comparing(ProfitItem::getName);
                break;
            default:
                return;
        }

        FXCollections.sort(profitItems, comparator);
    }

    @FXML
    private void refreshData() {
        loadProfitData();
        categoryFilter.setValue("All Categories");
        sortByCombo.setValue("Profit Margin (High to Low)");
    }

    // ========== INNER CLASSES ==========

    private static class CurrencyCell<T> extends TableCell<T, Double> {
        @Override
        protected void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(String.format("₱%.2f", item));
            }
        }
    }

    private static class PercentageCell<T> extends TableCell<T, Double> {
        @Override
        protected void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(String.format("%.2f%%", item));

                if (item >= 40) {
                    setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                } else if (item >= 20) {
                    setStyle("-fx-text-fill: #f39c12;");
                } else {
                    setStyle("-fx-text-fill: #e74c3c;");
                }
            }
        }
    }

    public static class ProfitItem {
        private final int id;
        private final String name;
        private final String category;
        private final double cost;
        private final double price;
        private final double unitProfit;
        private final double margin;
        private final int quantitySold;
        private final double totalRevenue;
        private final double totalProfit;

        public ProfitItem(int id, String name, String category, double cost, double price,
                          double unitProfit, double margin, int quantitySold,
                          double totalRevenue, double totalProfit) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.cost = cost;
            this.price = price;
            this.unitProfit = unitProfit;
            this.margin = margin;
            this.quantitySold = quantitySold;
            this.totalRevenue = totalRevenue;
            this.totalProfit = totalProfit;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public double getCost() { return cost; }
        public double getPrice() { return price; }
        public double getUnitProfit() { return unitProfit; }
        public double getMargin() { return margin; }
        public int getQuantitySold() { return quantitySold; }
        public double getTotalRevenue() { return totalRevenue; }
        public double getTotalProfit() { return totalProfit; }
    }
}