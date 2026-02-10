package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
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

/**
 * Controller class for managing product-related views and operations.
 * Handles displaying the product catalog, filtering, and summary statistics.
 */
public class ProductServices implements Initializable {

    @FXML
    private VBox cardContainer;
    @FXML
    private VBox bestSellingContainer;
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

    /**
     * Initializes the controller class.
     * Sets up category filters and loads the initial product list if UI containers
     * are present.
     *
     * @param location  The location used to resolve relative paths for the root
     *                  object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (cardContainer != null) {
            // Setup Category Filter
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

    /**
     * Applies the selected category filter to the product display.
     * Updates the main product list to show only items matching the chosen
     * category.
     */
    private void applyFilter() {
        String selected = filterComboBox.getValue();
        if (selected == null || "All".equals(selected)) {
            displayProducts(DataManager.getProducts());
        } else {
            ArrayList<Products> filtered = getCategory(DataManager.getProducts(), selected);
            displayProducts(filtered);
        }
    }

    /**
     * Calculates and displays summary statistics for inventory.
     * Updates labels for Total Products, Total Inventory Value, and Total Stock
     * Count.
     */
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
            totalValueLabel.setText(String.format("₱%.2f", totalInventoryValue));
        if (totalStockLabel != null)
            totalStockLabel.setText(String.valueOf(totalStock));
    }

    /**
     * Opens a modal dialog to add a new product.
     * Handles the dialog result and updates the product list if a new product is
     * saved.
     */
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

    /**
     * Renders product cards in the UI.
     * Populates both the main product grid and the best-selling list.
     *
     * @param productsToDisplay The list of products to render.
     */
    private void displayProducts(ArrayList<Products> productsToDisplay) {
        System.out.println("Displaying products...");
        if (cardContainer != null)
            cardContainer.getChildren().clear();
        if (bestSellingContainer != null)
            bestSellingContainer.getChildren().clear();

        try {
            // Populate Main Available Products List
            if (cardContainer != null) {
                for (Products product : productsToDisplay) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/products/product_card.fxml"));
                    Node card = loader.load();

                    Label nameLabel = (Label) card.lookup("#nameLabel");
                    Label categoryLabel = (Label) card.lookup("#categoryLabel");
                    Label priceLabel = (Label) card.lookup("#priceLabel");
                    Label stockLabel = (Label) card.lookup("#stockLabel");
                    Label soldLabel = (Label) card.lookup("#soldLabel");
                    Label marginLabel = (Label) card.lookup("#marginLabel");
                    Label statusLabel = (Label) card.lookup("#statusLabel");

                    if (nameLabel != null)
                        nameLabel.setText(product.getName());
                    if (categoryLabel != null)
                        categoryLabel.setText(product.getCategory() + " | " + product.getSupplier());

                    if (priceLabel != null) {
                        priceLabel.setText(String.format("₱%.2f", product.getPrice()));
                        priceLabel.setStyle("-fx-text-fill: #fbbf24;"); // Gold
                    }

                    if (stockLabel != null) {
                        stockLabel.setText(String.valueOf(product.getCurrentStock()));
                    }

                    if (statusLabel != null) {
                        String status = product.getStockStatus();
                        statusLabel.setText(status);
                        // Color coding for stock & status
                        if (product.getCurrentStock() <= product.getReOrderStock()) {
                            if (stockLabel != null)
                                stockLabel.setStyle("-fx-text-fill: #ef4444;"); // Red
                            statusLabel.setStyle("-fx-text-fill: #ef4444;"); // Red
                        } else {
                            if (stockLabel != null)
                                stockLabel.setStyle("-fx-text-fill: #4ade80;"); // Green
                            statusLabel.setStyle("-fx-text-fill: #22c55e;"); // Green
                        }
                    }

                    if (soldLabel != null) {
                        soldLabel.setText(String.valueOf(product.getQtySold()));
                        soldLabel.setStyle("-fx-text-fill: #94a3b8;"); // Slate-400
                    }

                    if (marginLabel != null) {
                        Double[] profitData = product.getProfitMargin();
                        marginLabel.setText(String.format("%.0f%%", profitData[1]));
                        marginLabel.setStyle("-fx-text-fill: #f472b6;"); // Pink
                    }

                    cardContainer.getChildren().add(card);
                }
            }

            // Populate Best Selling List (Right Column)
            if (bestSellingContainer != null) {
                ArrayList<Products> bestSelling = new ArrayList<>(productsToDisplay);
                bestSelling.sort(Comparator.comparingInt(Products::getQtySold).reversed());

                for (Products product : bestSelling) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/products/best_seller_card.fxml"));
                    Node card = loader.load();

                    Label nameLabel = (Label) card.lookup("#nameLabel");
                    Label soldLabel = (Label) card.lookup("#soldLabel");

                    if (nameLabel != null)
                        nameLabel.setText(product.getName());
                    if (soldLabel != null) {
                        soldLabel.setText(product.getQtySold() + " sold");
                        soldLabel.setStyle("-fx-text-fill: #4ade80;"); // Green
                    }

                    bestSellingContainer.getChildren().add(card);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Filters a list of products by category.
     *
     * @param products The source list of products.
     * @param category The category name to filter by.
     * @return A new list containing products belonging to the specified category.
     */
    public static ArrayList<Products> getCategory(ArrayList<Products> products, String category) {
        return new ArrayList<>(products.stream().filter(p -> p.getCategory().equalsIgnoreCase(category)).toList());
    }

    /**
     * Returns a list of products sorted by quantity sold (descending).
     *
     * @param products The source list of products.
     * @return A new list of products sorted by sales volume.
     */
    public static ArrayList<Products> getBestSelling(ArrayList<Products> products) {
        return new ArrayList<>(
                products.stream().sorted(Comparator.comparingInt(Products::getQtySold).reversed()).toList());
    }

    /**
     * Adds a new product to the list.
     *
     * @param products The product list.
     * @param p        The product to add.
     * @return The updated list of products.
     */
    public static ArrayList<Products> add(ArrayList<Products> products, Products p) {
        products.add(p);
        return products;
    }
}
