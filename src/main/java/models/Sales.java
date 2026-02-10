package models;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a sales transaction in the system.
 * Tracks product sales with pricing, discounts, and temporal information.
 */
public class Sales {
    private UUID saleID;
    private int productId;
    private String productName;
    private int quantitySold;
    private Double unitPrice;
    private Double discountPercentage;
    private Double discountAmount;
    private Double totalAmount;
    private LocalDateTime dateTime;
    private String seasonTag;

    /**
     * Constructs a new Sale with current timestamp.
     * Automatically generates a unique sale ID and calculates discount and total
     * amounts.
     * 
     * @param productId          ID of the product being sold
     * @param productName        Name of the product
     * @param quantitySold       Quantity of items sold
     * @param unitPrice          Price per unit
     * @param discountPercentage Discount percentage (0-100), defaults to 0 if null
     * @param seasonTag          Season identifier (e.g., "Back to School",
     *                           "Regular"), defaults to "Regular" if null
     */
    public Sales(int productId, String productName, int quantitySold, Double unitPrice, Double discountPercentage,
            String seasonTag) {
        this.saleID = UUID.randomUUID();
        this.productId = productId;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.unitPrice = unitPrice;
        this.discountPercentage = discountPercentage != null ? discountPercentage : 0.0;
        this.dateTime = LocalDateTime.now();
        this.seasonTag = seasonTag != null ? seasonTag : "Regular";

        this.discountAmount = (unitPrice * quantitySold) * (this.discountPercentage / 100);
        this.totalAmount = (unitPrice * quantitySold) - this.discountAmount;
    }

    /**
     * Constructs a Sale from string array data (used for loading dummy data).
     * Allows specifying a custom datetime instead of using current time.
     * 
     * @param data Array containing [productId, productName, quantitySold,
     *             unitPrice, discountPercentage, seasonTag, dateTime]
     */
    public Sales(String[] data) {
        this.saleID = UUID.randomUUID();
        this.productId = Integer.parseInt(data[0]);
        this.productName = data[1];
        this.quantitySold = Integer.parseInt(data[2]);
        this.unitPrice = Double.parseDouble(data[3]);
        this.discountPercentage = Double.parseDouble(data[4]);
        this.seasonTag = data[5];
        this.dateTime = LocalDateTime.parse(data[6]);

        this.discountAmount = (this.unitPrice * this.quantitySold) * (this.discountPercentage / 100);
        this.totalAmount = (this.unitPrice * this.quantitySold) - this.discountAmount;
    }

    /** Returns the unique sale ID. */
    public UUID getSaleID() {
        return saleID;
    }

    /** Returns the product ID. */
    public int getProductId() {
        return productId;
    }

    /** Returns the product name. */
    public String getProductName() {
        return productName;
    }

    /** Returns the quantity sold. */
    public int getQuantitySold() {
        return quantitySold;
    }

    /** Returns the unit price. */
    public Double getUnitPrice() {
        return unitPrice;
    }

    /** Returns the discount percentage. */
    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    /** Returns the calculated discount amount. */
    public Double getDiscountAmount() {
        return discountAmount;
    }

    /** Returns the total amount after discount. */
    public Double getTotalAmount() {
        return totalAmount;
    }

    /** Returns the full date and time of the sale. */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /** Returns the date portion of the sale timestamp. */
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    /** Returns the season tag for this sale. */
    public String getSeasonTag() {
        return seasonTag;
    }

    /**
     * Calculates the revenue from this sale.
     * 
     * @return Total amount (same as getTotalAmount)
     */
    public Double getRevenue() {
        return this.totalAmount;
    }

    /**
     * Calculates the profit from this sale based on cost price.
     * 
     * @param costPrice Cost price per unit
     * @return Profit amount (total revenue - total cost)
     */
    public Double getProfit(Double costPrice) {
        Double totalCost = costPrice * quantitySold;
        return totalAmount - totalCost;
    }

    /**
     * Calculates the profit margin percentage for this sale.
     * 
     * @param costPrice Cost price per unit
     * @return Profit margin as a percentage
     */
    public Double getProfitMargin(Double costPrice) {
        Double profit = getProfit(costPrice);
        return (profit / totalAmount) * 100;
    }

    /**
     * Returns a formatted string representation of the sale.
     * 
     * @return Detailed sale information including ID, product, pricing, and date
     */
    @Override
    public String toString() {
        return String.format("Sale ID: %s | Product: %s | Qty: %d | Unit Price: %.2f | " +
                "Discount: %.2f%% | Total: %.2f | Date: %s | Season: %s",
                saleID.toString().substring(0, 8), productName, quantitySold,
                unitPrice, discountPercentage, totalAmount,
                dateTime.toLocalDate(), seasonTag);
    }
}