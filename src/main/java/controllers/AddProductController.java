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
    private TextField costField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField reOrderField;

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

    public boolean isSaveClicked() {
        return saveClicked;
    }

    public Products getNewProduct() {
        return newProduct;
    }
}
