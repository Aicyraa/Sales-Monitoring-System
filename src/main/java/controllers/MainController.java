package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * Main Controller for the application layout.
 * Manages navigation between different views (Overview, Products, Revenue,
 * etc.)
 * and handles the sidebar menu interactions.
 */
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

    /**
     * Initializes the controller class.
     * Sets the default view to Profit Margin on application startup.
     */
    @FXML
    public void initialize() {
        System.out.println("MainController initialized");
        // Set btnProducts as default active view
        setActiveButton(btnProducts);
        switchToProfitMargin();
    }

    /**
     * Updates the active state of sidebar buttons.
     * Highlights the currently selected button and un-highlights the previous one.
     *
     * @param button The button that was clicked.
     */
    private void setActiveButton(Button button) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("active");
        }
        button.getStyleClass().add("active");
        activeButton = button;
    }

    /**
     * Loads a specific FXML view into the main content area.
     * Handles path resolution for different modules (sales vs products).
     *
     * @param fxmlPath The filename of the FXML to load.
     * @param button   The sidebar button associated with this view.
     */
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

    /**
     * Navigates to the Sales Overview dashboard.
     */
    @FXML
    private void switchToOverview() {
        loadView("overview.fxml", btnOverview);
    }

    /**
     * Navigates to the Revenue Analysis dashboard.
     */
    @FXML
    private void switchToRevenue() {
        loadView("revenue.fxml", btnRevenue);
    }

    /**
     * Navigates to the Profit Margin analysis view.
     */
    @FXML
    private void switchToProfitMargin() {
        loadView("profitMargin.fxml", btnProfitMargin);
    }

    /**
     * Navigates to the Peak Days analytics view.
     */
    @FXML
    private void switchToPeakDays() {
        loadView("peakDays.fxml", btnPeakDays);
    }

    /**
     * Navigates to the Product Inventory management view.
     */
    @FXML
    private void switchToProducts() {
        loadView("products.fxml", btnProducts);
    }
}