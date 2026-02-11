package controllers;

import models.Sales;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

/**
 * Utility service class for performing revenue calculations on sales data.
 * Provides static methods to aggregate revenue by various time periods and
 * categories.
 */
public class SaleServices {

    /**
     * Calculates the total revenue from a list of sales.
     * Sums the net amount (gross - discount) for all sales.
     *
     * @param salesList The list of sales transactions.
     * @return The total accumulated revenue.
     */
    public static Double getTotalRevenue(ArrayList<Sales> salesList) {
        double total = 0.0;
        for (Sales sale : salesList) {
            double gross = sale.getUnitPrice() * sale.getQuantitySold();
            double net = gross - sale.getDiscountAmount();
            total += net;
        }
        return total;
    }

    /**
     * Calculates the total revenue for a specific date.
     *
     * @param salesList The list of sales transactions.
     * @param date      The date to filter by.
     * @return The total revenue generated on that date.
     */
    public static Double getRevenuePerDay(ArrayList<Sales> salesList, LocalDate date) {
        double total = 0.0;
        for (Sales sale : salesList) {
            if (sale.getDate().isEqual(date)) {
                total += sale.getTotalAmount();
            }
        }
        return total;
    }

    /**
     * Calculates revenue for the last 7 days (including today).
     * Note: Uses the system's current date as the reference point.
     *
     * @param salesList The list of sales transactions.
     * @return The total revenue from the past week.
     */
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

    /**
     * Calculates the total revenue for a specific month and year.
     *
     * @param salesList The list of sales transactions.
     * @param month     The month to filter by.
     * @param year      The year to filter by.
     * @return The total revenue for that month.
     */
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

    /**
     * Calculates the total revenue for a specific season.
     * Filters sales by their season tag (case-insensitive).
     *
     * @param salesList The list of sales transactions.
     * @param seasonTag The season identifier (e.g., "Regular", "Back to School").
     * @return The total revenue for that season.
     */
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