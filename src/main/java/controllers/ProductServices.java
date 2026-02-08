package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.Products;
import utilities.DummyData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ProductServices implements Initializable {

    @FXML
    private VBox cardContainer;

    private ArrayList<Products> productList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Only load products if cardContainer exists (i.e., we are in products.fxml)
        if (cardContainer != null) {
            loadDummyData();
            displayProducts();
        }
    }

    private void loadDummyData() {
        String[][] rawData = DummyData.getDummyProduct();
        for (String[] data : rawData) {
            productList.add(new Products(data));
        }
    }

    private void displayProducts() {
        cardContainer.getChildren().clear();
        try {
            for (Products product : productList) {
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
                // ImageView productImage = (ImageView) card.lookup("#productImage"); //
                // Placeholder is set in FXML

                if (nameLabel != null)
                    nameLabel.setText(product.getName());
                if (categoryLabel != null)
                    categoryLabel.setText(product.getCategory() + " | " + product.getSupplier());
                if (priceLabel != null)
                    priceLabel.setText(String.format("$%.2f", product.getPrice()));
                if (stockLabel != null)
                    stockLabel.setText(String.valueOf(product.getStock()));

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
