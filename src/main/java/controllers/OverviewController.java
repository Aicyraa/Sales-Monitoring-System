package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.util.StringConverter;
import models.Products;
import models.Sales;
import utilities.DataManager;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;

public class OverviewController implements Initializable {

    @FXML
    private ComboBox<Products> productComboBox;
    @FXML
    private TextField qtyField;
    @FXML
    private TextField discountField;
    @FXML
    private Button btnAddSale;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private VBox recentSalesContainer;
    @FXML
    private Label statusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupProductComboBox();
        setupValidation();
        refreshSalesList();
    }

    private void setupProductComboBox() {
        ObservableList<Products> products = FXCollections.observableArrayList(DataManager.getProducts());
        productComboBox.setItems(products);

        // Custom converter to show product name
        productComboBox.setConverter(new StringConverter<Products>() {
            @Override
            public String toString(Products object) {
                return object == null ? "" : object.getName() + " (Stock: " + object.getCurrentStock() + ")";
            }

            @Override
            public Products fromString(String string) {
                return productComboBox.getItems().stream()
                        .filter(p -> p.getName().equals(string))
                        .findFirst().orElse(null);
            }
        });

        // Update total when product changes
        productComboBox.setOnAction(e -> calculateTotal());
    }

    private void setupValidation() {
        qtyField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                qtyField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            calculateTotal();
        });

        discountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                discountField.setText(newValue.replaceAll("[^\\d.]", ""));
            }
            calculateTotal();
        });
    }

    private void calculateTotal() {
        try {
            Products selected = productComboBox.getValue();
            if (selected == null) {
                totalAmountLabel.setText("Total: ₱0.00");
                return;
            }

            int qty = qtyField.getText().isEmpty() ? 0 : Integer.parseInt(qtyField.getText());
            double discount = discountField.getText().isEmpty() ? 0 : Double.parseDouble(discountField.getText());
            double price = selected.getPrice();

            double gross = price * qty;
            double discountAmount = gross * (discount / 100);
            double net = gross - discountAmount;

            totalAmountLabel.setText(String.format("Total: ₱%.2f", net));

        } catch (NumberFormatException e) {
            totalAmountLabel.setText("Total: ₱0.00");
        }
    }

    @FXML
    private void handleAddSale() {
        statusLabel.setText("");
        statusLabel.setStyle("-fx-text-fill: #94A3B8;"); // Reset color

        Products selected = productComboBox.getValue();
        if (selected == null) {
            showMessage("Please select a product.", true);
            return;
        }

        String qtyText = qtyField.getText();
        if (qtyText.isEmpty()) {
            showMessage("Please enter quantity.", true);
            return;
        }

        int qty = Integer.parseInt(qtyText);
        if (qty <= 0) {
            showMessage("Quantity must be greater than 0.", true);
            return;
        }

        if (qty > selected.getCurrentStock()) {
            showMessage("Insufficient stock! Available: " + selected.getCurrentStock(), true);
            return;
        }

        double discount = 0;
        if (!discountField.getText().isEmpty()) {
            discount = Double.parseDouble(discountField.getText());
        }

        // Deduct stock
        selected.deductStock(qty);

        // Create Sale Record
        Sales newSale = new Sales(
                selected.getId(),
                selected.getName(),
                qty,
                selected.getPrice(),
                discount,
                "Regular");

        DataManager.addSale(newSale);

        showMessage("Sale added successfully!", false);

        // Reset fields
        productComboBox.getSelectionModel().clearSelection();
        qtyField.clear();
        discountField.clear();
        calculateTotal(); // Reset total

        // Refresh UI
        refreshSalesList();

        // Re-setup combo box to update stock display
        setupProductComboBox();
    }

    private void showMessage(String msg, boolean isError) {
        statusLabel.setText(msg);
        if (isError) {
            statusLabel.setStyle("-fx-text-fill: #ef4444;"); // Red
        } else {
            statusLabel.setStyle("-fx-text-fill: #22c55e;"); // Green
        }
    }

    private void refreshSalesList() {
        recentSalesContainer.getChildren().clear();

        // Sort sales by date descending (newest first)
        ObservableList<Sales> sortedSales = FXCollections.observableArrayList(DataManager.getSales());
        sortedSales.sort(Comparator.comparing(Sales::getDateTime).reversed());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

        for (Sales sale : sortedSales) {
            HBox row = new HBox();
            row.setSpacing(20);
            row.setPadding(new Insets(10));
            row.setStyle("-fx-background-color: #1E293B; -fx-background-radius: 8;");
            row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            Label name = new Label(sale.getProductName());
            name.setPrefWidth(200);
            name.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

            Label qty = new Label("Qty: " + sale.getQuantitySold());
            qty.setPrefWidth(100);
            qty.setStyle("-fx-text-fill: #94A3B8;");

            Label total = new Label(String.format("₱%.2f", sale.getTotalAmount()));
            total.setPrefWidth(120);
            total.setStyle("-fx-text-fill: #4ADE80; -fx-font-weight: bold;");

            Label date = new Label(sale.getDateTime().format(dtf));
            date.setStyle("-fx-text-fill: #64748b;");

            row.getChildren().addAll(name, qty, total, date);
            recentSalesContainer.getChildren().add(row);
        }
    }
}
