package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Products;
import utilities.DataManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductServices implements Initializable {

    @FXML
    private VBox cardContainer;
    @FXML
    private Label totalProductsLabel;
    @FXML
    private Label totalValueLabel;
    @FXML
    private Label totalStockLabel;
    @FXML
    private Button btnAddProduct;
    @FXML
    private ComboBox<String> filterComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Only load products if cardContainer exists (i.e., we are in products.fxml)
        if (cardContainer != null) {
            // Setup Filter
            if (filterComboBox != null) {
                ArrayList<String> categories = DataManager.getProducts().stream()
                        .map(Products::getCategory)
                        .distinct()
                        .sorted()
                        .collect(Collectors.toCollection(ArrayList::new));

                filterComboBox.getItems().clear();
                filterComboBox.getItems().add("All");
                filterComboBox.getItems().addAll(categories);
                filterComboBox.setValue("All"); // Default

                filterComboBox.setOnAction(e -> applyFilter());
            }

            displayProducts(DataManager.getProducts());
            calculateSummaryStats();
        }
    }

    private void applyFilter() {
        String selected = filterComboBox.getValue();
        if (selected == null || "All".equals(selected)) {
            displayProducts(DataManager.getProducts());
        } else {
            ArrayList<Products> filtered = getCategory(DataManager.getProducts(), selected);
            displayProducts(filtered);
        }
    }

    private void calculateSummaryStats() {
        ArrayList<Products> productList = DataManager.getProducts();
        if (productList == null)
            return;

        int totalProducts = productList.size();
        double totalInventoryValue = 0;
        int totalStock = 0;

        for (Products p : productList) {
            totalInventoryValue += (p.getPrice() * p.getStock());
            totalStock += p.getStock();
        }

        if (totalProductsLabel != null)
            totalProductsLabel.setText(String.valueOf(totalProducts));
        if (totalValueLabel != null)
            totalValueLabel.setText(String.format("$%.2f", totalInventoryValue));
        if (totalStockLabel != null)
            totalStockLabel.setText(String.valueOf(totalStock));
    }

    @FXML
    private void handleAddProduct() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/products/addProduct.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            if (btnAddProduct.getScene() != null) {
                dialogStage.initOwner(btnAddProduct.getScene().getWindow());
            }
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddProductController controller = loader.getController();

            dialogStage.showAndWait();

            if (controller.isSaveClicked()) {
                Products newProduct = controller.getNewProduct();
                if (newProduct != null) {
                    System.out.println("Adding new product: " + newProduct.getName());
                    DataManager.addProduct(newProduct);
                    System.out.println("Current product list size: " + DataManager.getProducts().size());

                    // Update filter if new category
                    if (filterComboBox != null && !filterComboBox.getItems().contains(newProduct.getCategory())) {
                        filterComboBox.getItems().add(newProduct.getCategory());
                    }
                    if (filterComboBox != null)
                        filterComboBox.setValue("All");

                    displayProducts(DataManager.getProducts());
                    calculateSummaryStats();
                } else {
                    System.out.println("New product is null!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayProducts(ArrayList<Products> productsToDisplay) {
        System.out.println("Displaying products...");
        cardContainer.getChildren().clear();
        try {
            for (Products product : productsToDisplay) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/products/card.fxml"));
                Node card = loader.load();

                // Populate card data
                Label nameLabel = (Label) card.lookup("#nameLabel");
                Label categoryLabel = (Label) card.lookup("#categoryLabel");
                Label priceLabel = (Label) card.lookup("#priceLabel");
                Label stockLabel = (Label) card.lookup("#stockLabel");
                Label statusLabel = (Label) card.lookup("#statusLabel");
                Label soldLabel = (Label) card.lookup("#soldLabel");
                Label profitLabel = (Label) card.lookup("#profitLabel");
                ImageView productImage = (ImageView) card.lookup("#productImage");

                // Round Image
                if (productImage != null) {
                    Rectangle clip = new Rectangle(
                            productImage.getFitWidth(), productImage.getFitHeight());
                    clip.setArcWidth(40);
                    clip.setArcHeight(40);
                    productImage.setClip(clip);
                }

                if (nameLabel != null)
                    nameLabel.setText(product.getName());
                if (categoryLabel != null)
                    categoryLabel.setText(product.getCategory() + " | " + product.getSupplier());
                if (priceLabel != null) {
                    priceLabel.setText(String.format("$%.2f", product.getPrice()));
                    priceLabel.setStyle("-fx-text-fill: #fbbf24;"); // Gold
                }
                if (stockLabel != null) {
                    stockLabel.setText(String.valueOf(product.getStock()));
                    stockLabel.setStyle("-fx-text-fill: #4ade80;"); // Green
                }

                if (soldLabel != null) {
                    soldLabel.setText(product.getQtySold() + " items");
                }

                if (profitLabel != null) {
                    Double[] profitData = product.getProfitMargin();
                    // profitData[1] is percentage
                    profitLabel.setText(String.format("%.1f%%", profitData[1]));
                }

                if (statusLabel != null) {
                    String status = product.getStockStatus();
                    statusLabel.setText(status);

                    // Color coding
                    if ("Low".equals(status) || "Out of stock".equals(status)) {
                        statusLabel.setStyle("-fx-text-fill: #ef4444;"); // Red
                    } else {
                        statusLabel.setStyle("-fx-text-fill: #22c55e;"); // Green
                    }
                }

                cardContainer.getChildren().add(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Products> getCategory(ArrayList<Products> products, String category) {
        return new ArrayList<>(products.stream().filter(p -> p.getCategory().equalsIgnoreCase(category)).toList());
    }

    public static ArrayList<Products> getBestSelling(ArrayList<Products> products) {
        return new ArrayList<>(
                products.stream().sorted(Comparator.comparingInt(Products::getQtySold).reversed()).toList());
    }

    public static ArrayList<Products> add(ArrayList<Products> products, Products p) {
        products.add(p);
        return products;
    }

}
