package services.sms;

import models.sms.Sales;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class SaleServices {

    public static Double getTotalRevenue(ArrayList<Sales> salesList) {
        double total = 0.0;
        for (Sales sale : salesList) {
            double grossAmount = sale.getUnitPrice() * sale.getQuantitySold();
            double netAmount = grossAmount - sale.getDiscountAmount();
            total += netAmount;
        }
        return total;
    }


}