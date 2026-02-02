package utilities.srs;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Products {
    static int idCount = 1;

    int id;
    String name;
    String category;
    String supplier;
    Double price;             // Price of each stock
    Double cost;              // Cost per stock
    int stock;                // total stock
    int currentStock;         // Current Stock
    int reOrderStock;         // Stock level where the owner should restock

    public Products(String name, String category, String supplier, Double price, Double cost, int stock, int reOrderStock) {
        this.id = idCount++;
        this.name = name;
        this.category = category;
        this.supplier = supplier;
        this.price = price;
        this.cost = cost;
        this.stock = stock;
        this.currentStock = stock;
        this.reOrderStock = reOrderStock;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public String getSupplier() {
        return this.supplier;
    }

    public Double getPrice() {
        return this.price;
    }

    public Double getCost() {
        return this.cost;
    }

    public Double getInventoryValue() {
        return this.price * this.stock;
    }

    public int getStock() {
        return this.stock;
    }

    public int getCurrentStock() {
        return this.currentStock;
    }

    public int getReOrderStock() {
        return this.reOrderStock;
    }

    // setters
    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // business method
    public String getStockStatus() {
        if (this.currentStock > this.reOrderStock) {
            return "Healthy";
        } else if (this.currentStock < reOrderStock && this.stock > 0) {
            return "Low";
        } else {
            return "Out of stock";
        }
    }

    public void deductStock(int stock) {
        if (!(stock > this.currentStock)) {this.currentStock -= stock;};
    }

    public void addStock(int stock) {
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
        Double profit = this.price - this.cost;
        double margin = (profit / this.price) * 100;
        return new Double[]{profit, margin};
    }

    @Override
    public String toString() {
        return "Product: " + this.name + " | Category: " +  this.category + " | Supplier: " + this.supplier + " | Price: " + this.price + " | Current Stock: " +  this.currentStock + " | Stock:" +  this.stock + " | " +  this.reOrderStock + " | Stock Status: ";
    }

    ;

}