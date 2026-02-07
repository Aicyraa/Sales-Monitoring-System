package controllers;

import models.Sales;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class SaleServices {

    public static Double getTotalRevenue(ArrayList<Sales> salesList) {
        double total = 0.0;
        for (Sales sale : salesList) {
            double gross = sale.getUnitPrice() * sale.getQuantitySold();
            double net = gross - sale.getDiscountAmount();
            total += net;
        }
        return total;
    }

    public static Double getRevenuePerDay(ArrayList<Sales> salesList, LocalDate date) {
        double total = 0.0;
        for (Sales sale : salesList) {
            if (sale.getDate().isEqual(date)) {
                total += sale.getTotalAmount();
            }
        }
        return total;
    }

    public static Double getRevenuePerWeek(ArrayList<Sales> salesList) {
        double total = 0.0;
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);

        for (Sales sale : salesList) {
            LocalDate date = sale.getDate();
            if (!date.isBefore(sevenDaysAgo) && !date.isAfter(today)) {
                total += sale.getTotalAmount();
            }
        }
        return total;
    }

    public static Double getRevenuePerMonth(ArrayList<Sales> salesList, Month month, int year) {
        double total = 0.0;
        for (Sales sale : salesList) {
            LocalDate date = sale.getDate();
            if (date.getMonth() == month && date.getYear() == year) {
                total += sale.getTotalAmount();
            }
        }
        return total;
    }

    public static Double getRevenueBySeason(ArrayList<Sales> salesList, String seasonTag) {
        double total = 0.0;
        for (Sales sale : salesList) {
            if (sale.getSeasonTag().equalsIgnoreCase(seasonTag)) {
                total += sale.getTotalAmount();
            }
        }
        return total;
    }
}