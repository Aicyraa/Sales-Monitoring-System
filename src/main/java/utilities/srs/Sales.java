package utilities.srs;
import java.time.LocalDateTime;
import java.util.UUID;

public class Sales {

    // to identify the sale within the day, add a local time to identify
    // total of products sold within a day
    UUID saleID;
    int productId;
    int quantitySold;
    Double unitPrice;
    Double discountPrice;
    Double totalAmount;
    LocalDateTime dateTime;
    String seasonTag;

    public void Sales(int productId, int quantitySold, Double unitPrice, Double discountPrice, Double totalAmount, LocalDateTime dateTime, String seasonTag) {
        this.saleID = UUID.randomUUID();
        this.productId = productId;
        this.quantitySold = quantitySold;
        this.unitPrice = unitPrice;
        this.discountPrice = discountPrice;
        this.totalAmount = totalAmount;
        this.dateTime = dateTime;
        this.seasonTag = seasonTag;
    }




}
