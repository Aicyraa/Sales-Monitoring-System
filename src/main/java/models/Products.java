package models;

public class Products {
    static int idCount = 1;

    private int id;
    private String name;
    private String category;
    private String supplier;
    private Double price;             // Price of each stock
    private Double cost;              // Cost per stock
    private int stock;                // total stock
    private int currentStock;         // Current Stock
    private int reOrderStock;         // Stock level where the owner should restock

    public Products (String[] data) {
        this.id = idCount++;
        this.name = data[0];
        this.category = data[1];
        this.supplier = data[2];
        this.price = Double.parseDouble(data[3]);
        this.cost = Double.parseDouble(data[4]);
        this.stock = Integer.parseInt(data[5]);
        this.currentStock = this.stock;
        this.reOrderStock = Integer.parseInt(data[6]);
    }

    // getters for gui
    public String getName() {return this.name;}
    public String getCategory() {return this.category;}
    public String getSupplier() {return this.supplier;}
    public Double getPrice() {return this.price;}
    public Double getCost() {return this.cost;}
    public Double getInventoryValue() {return this.price * this.stock;}
    public int getStock() {return this.stock;}
    public int getCurrentStock() {return this.currentStock;}
    public int getReOrderStock() {return this.reOrderStock;}

    // setters
    public void setStock(int stock) {this.stock = stock;}
    public void setCost(Double cost) {this.cost = cost;}
    public void setPrice(Double price) {this.price = price;}

    // business method
    public String getStockStatus() {
        if (this.currentStock > this.reOrderStock) { return "Healthy"; }
        else if (this.currentStock < reOrderStock && this.stock > 0) { return "Low"; }
        else { return "Out of stock"; }
    }

    public void deductStock(int stock) {
        if (!(stock > this.currentStock)) {this.currentStock -= stock;}
    }

    public void addStock(int stock) {
        this.currentStock += stock;
    }

    public int getQtySold() {
        return this.stock - this.currentStock;
    }

    public Double getTotalQtySold() {
        int qty = getQtySold();
        return qty * this.price;
    }

    public Double[] getProfitMargin() {
        Double profit = this.price - this.cost;
        double margin = (profit / this.price) * 100;
        return new Double[]{profit, margin};
    }

    @Override
    public String toString() {
        return "Product: " + this.name + " | Sold: " + (this.stock - this.currentStock) ;
    }

}