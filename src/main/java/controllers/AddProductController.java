package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Products;

/**
 * Controller class for the "Add Product" dialog.
 * Handles user input for creating new product entries.
 */
public class AddProductController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField supplierField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField costField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField reOrderField;

    private boolean saveClicked = false;
    private Products newProduct;

    /**
     * Handles the cancel action.
     * Closes the dialog without saving any data.
     */
    @FXML
    private void handleCancel() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the save action.
     * Validates input fields, creates a new Product object, and closes the dialog.
     */
    @FXML
    private void handleSave() {
        if (isValid()) {
            String name = nameField.getText();
            String category = categoryField.getText();
            String supplier = supplierField.getText();
            String price = priceField.getText();
            String cost = costField.getText();
            String stock = stockField.getText();
            String reOrder = reOrderField.getText();

            // Products constructor expects: name, category, supplier, price, cost, stock,
            // reOrderStock
            String[] data = new String[] { name, category, supplier, price, cost, stock, reOrder };
            newProduct = new Products(data);

            saveClicked = true;
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Validates the user input fields.
     * Checks if required fields (Name, Price, Stock) are populated.
     *
     * @return true if input is valid, false otherwise.
     */
    private boolean isValid() {
        String errorMessage = "";
        if (nameField.getText() == null || nameField.getText().trim().isEmpty())
            errorMessage += "No name!\n";
        if (priceField.getText() == null || priceField.getText().trim().isEmpty())
            errorMessage += "No price!\n";
        if (stockField.getText() == null || stockField.getText().trim().isEmpty())
            errorMessage += "No stock!\n";

        if (errorMessage.length() == 0) {
            return true;
        } else {
            System.out.println("Invalid input: " + errorMessage);
            return false;
        }
    }

    /**
     * Checks if the user clicked the save button.
     *
     * @return true if the product was saved, false if cancelled.
     */
    public boolean isSaveClicked() {
        return saveClicked;
    }

    /**
     * Retrieves the newly created product.
     *
     * @return The new Product object, or null if not saved.
     */
    public Products getNewProduct() {
        return newProduct;
    }
}
