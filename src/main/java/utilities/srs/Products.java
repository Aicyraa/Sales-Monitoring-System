package utilities.srs;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Products {
    int idCount = 1;

    int id;
    String name;
    String description;
    String category;
    String supplier;
    Double price;             // Price of each stock
    Double cost;              // Cost per stock
    int discount;             // Product Discount
    int stock;                // total stock
    int currentStock;         // Current Stock
    int reOrderStock;         // Stock level where the owner should restock

    public void Products (String name, String description, String category, String supplier, Double price, Double cost, int stock, int reOrderStock, int discount) {
        this.id = idCount++;
        this.name = name;
        this.description = description;
        this.category = category;
        this.supplier = supplier;
        this.price = price;
        this.cost = cost;
        this.stock = stock;
        this.currentStock = stock;
        this.reOrderStock = reOrderStock;
        this.discount = discount;
    }

    // getters
    public String getName() { return this.name; };
    public String getDescription() { return this.description;}
    public String getCategory() { return this.category;}
    public String getSupplier() { return this.supplier;}
    public Double getPrice() { return this.price;}
    public Double getCost() { return this.cost;}
    public Double getInventoryValue() { return this.price * this.stock;}
    public int getStock() { return this.stock;}
    public int getCurrentStock() { return this.stock;}
    public int getReOrderStock() {  return this.reOrderStock;}
    public int getDiscount() { return this.discount;}

    // setters
    public void setStock(int stock) { this.stock = stock;}
    public void setCost (Double cost) { this.cost = cost; }
    public void setPrice (Double price) { this.price = price; }
    public void setDiscount (int discount) { this.discount = discount; }

    // business method
    public String getStockStatus() {
        if (this.stock > this.reOrderStock) {return "Healthy"; }
        else if (this.stock < reOrderStock && this.stock > 0) {return "Low"; }
        else {return "Out of stock"; }
    }

    public void deductStock (int stock) {
        this.currentStock -= stock;
    }

    public void addStock (int stock) {
        this.currentStock += stock;
    }

    public int getQtySoldAmount() {
        return this.stock - this.currentStock;
    }

    public Double getQtyTotalSold() {
        int qty = getQtySoldAmount();
        return qty * this.price;
    }

    public Double[] getProfitMargin() {
        Double profit =  this.price - this.cost;
        double margin =  (profit / this.price) * 100;
        return new Double[]{profit, margin};
    }

}
