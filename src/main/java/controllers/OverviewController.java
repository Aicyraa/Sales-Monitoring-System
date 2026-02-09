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

        // Custom converter to show product name in the button area
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

        // Cell Factory for the dropdown list to handle color coding
        productComboBox.setCellFactory(lv -> new ListCell<Products>() {
            @Override
            protected void updateItem(Products item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle(null);
                } else {
                    setText(item.getName() + " (Stock: " + item.getCurrentStock() + ")");

                    // Check logic for low stock
                    if (item.getCurrentStock() <= item.getReOrderStock()) {
                        setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;"); // Red for low stock
                    } else {
                        setStyle("-fx-text-fill: white;");
                    }
                }
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

        DateTimeFormatter timeFit = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFit = DateTimeFormatter.ofPattern("MMM dd");

        for (Sales sale : sortedSales) {

            // Container for the sale card
            HBox card = new HBox();
            card.setSpacing(15);
            card.setPadding(new Insets(15));
            card.setStyle(
                    "-fx-background-color: #1E293B; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");
            card.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            // Left: Icon or Initials
            Label icon = new Label("S");
            icon.setStyle(
                    "-fx-background-color: #3b82f6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 50; -fx-min-width: 40; -fx-min-height: 40; -fx-alignment: center; -fx-font-size: 16;");

            // Middle: Product Name and Time
            VBox details = new VBox(5);
            Label name = new Label(sale.getProductName());
            name.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");

            Label time = new Label(sale.getDateTime().format(dateFit) + " at " + sale.getDateTime().format(timeFit));
            time.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12;");
            details.getChildren().addAll(name, time);

            // Spacer
            javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

            // Right: Qty and Total
            VBox numbers = new VBox(5);
            numbers.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

            Label total = new Label(String.format("₱%.2f", sale.getTotalAmount()));
            total.setStyle("-fx-text-fill: #4ADE80; -fx-font-weight: bold; -fx-font-size: 15;");

            Label qty = new Label(sale.getQuantitySold() + " pcs");
            qty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 12;");

            numbers.getChildren().addAll(total, qty);

            card.getChildren().addAll(icon, details, spacer, numbers);
            recentSalesContainer.getChildren().add(card);
        }
    }
}
