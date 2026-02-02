package utilities.srs;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.UUID;

public class Sales {
    private UUID saleID;
    private int productId;
    private String productName;  // Add for easier reporting
    private int quantitySold;
    private Double unitPrice;
    private Double discountPercentage;  // Store as percentage (0-100)
    private Double discountAmount;
    private Double totalAmount;
    private LocalDateTime dateTime;
    private String seasonTag;

    public Sales(int productId, String productName, int quantitySold, Double unitPrice, Double discountPercentage, String seasonTag) {
        this.saleID = UUID.randomUUID();
        this.productId = productId;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.unitPrice = unitPrice;
        this.discountPercentage = discountPercentage != null ? discountPercentage : 0.0;
        this.dateTime = LocalDateTime.now();
        this.seasonTag = seasonTag != null ? seasonTag : "Regular";

        // Calculate amounts
        this.discountAmount = (unitPrice * quantitySold) * (this.discountPercentage / 100);
        this.totalAmount = (unitPrice * quantitySold) - this.discountAmount;
    }

    // Constructor for loading from dummy data with custom datetime
    public Sales(String[] data) {
        // Format: {productId, productName, quantitySold, unitPrice, discountPercentage, seasonTag, dateTime}
        this.saleID = UUID.randomUUID();
        this.productId = Integer.parseInt(data[0]);
        this.productName = data[1];
        this.quantitySold = Integer.parseInt(data[2]);
        this.unitPrice = Double.parseDouble(data[3]);
        this.discountPercentage = Double.parseDouble(data[4]);
        this.seasonTag = data[5];
        this.dateTime = LocalDateTime.parse(data[6]);

        // Calculate amounts
        this.discountAmount = (this.unitPrice * this.quantitySold) * (this.discountPercentage / 100);
        this.totalAmount = (this.unitPrice * this.quantitySold) - this.discountAmount;
    }

    // Getters
    public UUID getSaleID() { return saleID; }
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantitySold() { return quantitySold; }
    public Double getUnitPrice() { return unitPrice; }
    public Double getDiscountPercentage() { return discountPercentage; }
    public Double getDiscountAmount() { return discountAmount; }
    public Double getTotalAmount() { return totalAmount; }
    public LocalDateTime getDateTime() { return dateTime; }
    public LocalDate getDate() { return dateTime.toLocalDate(); }
    public String getSeasonTag() { return seasonTag; }

    // Business methods
    public Double getRevenue() {
        return this.totalAmount;
    }

    public Double getProfit(Double costPrice) {
        Double totalCost = costPrice * quantitySold;
        return totalAmount - totalCost;
    }

    public Double getProfitMargin(Double costPrice) {
        Double profit = getProfit(costPrice);
        return (profit / totalAmount) * 100;
    }

    @Override
    public String toString() {
        return String.format("Sale ID: %s | Product: %s | Qty: %d | Unit Price: %.2f | " +
                        "Discount: %.2f%% | Total: %.2f | Date: %s | Season: %s",
                saleID.toString().substring(0, 8), productName, quantitySold,
                unitPrice, discountPercentage, totalAmount,
                dateTime.toLocalDate(), seasonTag);
    }
}