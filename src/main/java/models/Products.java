package models;

/**
 * Represents a product in the inventory system.
 * Manages product information including pricing, stock levels, and inventory
 * tracking.
 */
public class Products {
    static int idCount = 1;

    private int id;
    private String name;
    private String category;
    private String supplier;
    private Double price; // Price of each stock
    private Double cost; // Cost per stock
    private int stock; // total stock
    private int currentStock; // Current Stock
    private int reOrderStock; // Stock level where the owner should restock

    /**
     * Constructs a new Product from an array of string data.
     * Automatically assigns a unique ID and initializes current stock to match
     * total stock.
     * 
     * @param data Array containing [name, category, supplier, price, cost, stock,
     *             reOrderStock]
     */
    public Products(String[] data) {
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

    /** Returns the unique product ID. */
    public int getId() {
        return this.id;
    }

    /** Returns the product name. */
    public String getName() {
        return this.name;
    }

    /** Returns the product category. */
    public String getCategory() {
        return this.category;
    }

    /** Returns the supplier name. */
    public String getSupplier() {
        return this.supplier;
    }

    /** Returns the selling price per unit. */
    public Double getPrice() {
        return this.price;
    }

    /** Returns the cost price per unit. */
    public Double getCost() {
        return this.cost;
    }

    /**
     * Calculates the total inventory value based on current price and stock.
     * 
     * @return Total value of all stock (price × stock)
     */
    public Double getInventoryValue() {
        return this.price * this.stock;
    }

    /** Returns the initial total stock quantity. */
    public int getStock() {
        return this.stock;
    }

    /** Returns the current available stock quantity. */
    public int getCurrentStock() {
        return this.currentStock;
    }

    /** Returns the reorder threshold stock level. */
    public int getReOrderStock() {
        return this.reOrderStock;
    }

    /**
     * Sets the total stock quantity.
     * 
     * @param stock New total stock value
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the cost price per unit.
     * 
     * @param cost New cost price
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     * Sets the selling price per unit.
     * 
     * @param price New selling price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Determines the stock status based on current stock levels.
     * 
     * @return "Out of stock" if stock is 0, "Low" if at or below reorder level,
     *         "Healthy" otherwise
     */
    public String getStockStatus() {
        if (this.currentStock == 0) {
            return "Out of stock";
        } else if (this.currentStock <= this.reOrderStock) {
            return "Low";
        } else {
            return "Healthy";
        }
    }

    /**
     * Deducts stock from current inventory.
     * Only deducts if requested amount does not exceed current stock.
     * 
     * @param stock Quantity to deduct
     */
    public void deductStock(int stock) {
        if (!(stock > this.currentStock)) {
            this.currentStock -= stock;
        }
    }

    /**
     * Adds stock to current inventory.
     * 
     * @param stock Quantity to add
     */
    public void addStock(int stock) {
        this.currentStock += stock;
    }

    /**
     * Calculates the total quantity sold.
     * 
     * @return Difference between initial stock and current stock
     */
    public int getQtySold() {
        return this.stock - this.currentStock;
    }

    /**
     * Calculates the total revenue from sold quantity.
     * 
     * @return Total value of sold items (quantity sold × price)
     */
    public Double getTotalQtySold() {
        int qty = getQtySold();
        return qty * this.price;
    }

    /**
     * Calculates profit and profit margin for this product.
     * 
     * @return Array containing [profit per unit, profit margin percentage]
     */
    public Double[] getProfitMargin() {
        Double profit = this.price - this.cost;
        double margin = (profit / this.price) * 100;
        return new Double[] { profit, margin };
    }

    /**
     * Returns a string representation of the product.
     * 
     * @return Product name and quantity sold
     */
    @Override
    public String toString() {
        return "Product: " + this.name + " | Sold: " + (this.stock - this.currentStock);
    }

}