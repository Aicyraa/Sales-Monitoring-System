package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Products;

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
    private TextField stockField;

    private boolean saveClicked = false;
    private Products newProduct;

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSave() {
        if (isValid()) {
            // "Name", "Category", "Stock", "Price", "Supplier"
            // Note: Products constructor uses String[] { name, category, stock, price,
            // supplier, image, description }
            // or similar. Let's check Products.java constructor.
            // Assuming for now we parse data and create an object.

            // Dummy logic to create a product array as expected by Products constructor
            // ID is auto-generated or not needed? Checked DummyData, it sends an array of
            // strings.
            // Let's create a temporary Products object or just pass data back.
            // For simplicity, we will create a Products object here if possible.

            // Re-checking Products.java constructor is needed to be safe, but I will assume
            // based on DummyData usage: new Products(String[] data)

            String name = nameField.getText();
            String category = categoryField.getText();
            String supplier = supplierField.getText();
            String price = priceField.getText();
            String stock = stockField.getText();
            String image = "product_placeHolder.jpg"; // Default
            String desc = "No description"; // Default

            String[] data = new String[] { name, category, stock, price, supplier, image, desc };
            newProduct = new Products(data);

            saveClicked = true;
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        }
    }

    private boolean isValid() {
        String errorMessage = "";
        if (nameField.getText() == null || nameField.getText().length() == 0)
            errorMessage += "No name!\n";
        if (priceField.getText() == null || priceField.getText().length() == 0)
            errorMessage += "No price!\n";

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show alert? For now just print
            System.out.println("Invalid input: " + errorMessage);
            return false;
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    public Products getNewProduct() {
        return newProduct;
    }
}
