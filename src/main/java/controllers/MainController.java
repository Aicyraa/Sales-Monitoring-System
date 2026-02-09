package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    // FXML Components
    @FXML
    private StackPane contentArea;

    // Sidebar Buttons
    @FXML
    private Button btnProducts;

    @FXML
    private Button btnOverview;
    @FXML
    private Button btnRevenue;
    @FXML
    private Button btnProfitMargin;
    @FXML
    private Button btnPeakDays;

    private Button activeButton;

    @FXML
    public void initialize() {
        System.out.println("MainController initialized");
        // Set btnPRoducts as default active view
        setActiveButton(btnProducts);
        switchToProfitMargin();
    }

    private void setActiveButton(Button button) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("active");
        }
        button.getStyleClass().add("active");
        activeButton = button;
    }

    private void loadView(String fxmlPath, Button button) {
        try {
            setActiveButton(button);
            String fullPath;
            if (fxmlPath.equals("overview.fxml") || fxmlPath.equals("revenue.fxml")
                    || fxmlPath.equals("profitMargin.fxml") || fxmlPath.equals("peakDays.fxml")) {
                fullPath = "/sales/" + fxmlPath;
            } else {
                fullPath = "/products/" + fxmlPath;
            }

            System.out.println("Loading: " + fullPath);

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fullPath));
            Node view = loader.load();

            // Set content
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

            System.out.println("Success!");

        } catch (IOException e) {
            System.err.println("Error loading: " + fxmlPath);
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToOverview() {
        loadView("overview.fxml", btnOverview);
    }

    @FXML
    private void switchToRevenue() {
        loadView("revenue.fxml", btnRevenue);
    }

    @FXML
    private void switchToProfitMargin() {
        loadView("profitMargin.fxml", btnProfitMargin);
    }

    @FXML
    private void switchToPeakDays() {
        loadView("peakDays.fxml", btnPeakDays);
    }

    @FXML
    private void switchToProducts() {
        loadView("products.fxml", btnProducts);
    }
}