package utilities;

import models.Products;
import models.Sales;
import controllers.ProductServices;
import controllers.SaleServices;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class DataManager extends DummyData {
    static ArrayList<Products> productList = new ArrayList<>();
    static ArrayList<Sales> saleList = new ArrayList<>();


    public static ArrayList<Sales> getSaleList() {
        return saleList;
    }

    public static void generateProducts() {
        String[][] data = DummyData.getDummyProduct();
        for (String[] datum : data) {
            productList.add(new Products(datum));
        }
    }

    public static void generateSales() {
        String[][] data = DummyData.getDummySales();
        for (String[] datum : data) {
            saleList.add(new Sales(datum));
        }
    }

    public static void main(String[] args) {

        generateProducts();
        generateSales();

        if (!productList.isEmpty()) {
            productList.get(0).deductStock(10);
            productList.get(2).deductStock(15);
            productList.get(5).deductStock(7);
            productList.get(7).deductStock(20);
            productList.get(18).deductStock(25);
            productList.get(11).deductStock(23);
            productList.get(18).deductStock(16);
        }

        System.out.println("\n===== SAMPLE PRODUCTS =====");
        for (int i = 0; i < Math.min(5, productList.size()); i++) {
            Products p = productList.get(i);
            System.out.println(p);
            System.out.println("Stock Status: " + p.getStockStatus());
        }

        System.out.println("\n===== ALL SALES =====");
        for (int i = 0; i < Math.min(5, saleList.size()); i++) {
            Sales s = saleList.get(i);
            System.out.println(s);
        }

        System.out.println("\n===== CATEGORIZE PRODUCTS =====");
        ArrayList<Products> categorizedProducts = ProductServices.getCategory(productList, "utilities");
        for (int i = 0; i < categorizedProducts.size(); i++) {
            Products p = categorizedProducts.get(i);
            System.out.println(p);
        }

        System.out.println("\n==== BEST SELLING TO LEAST SELLING =====");
        ArrayList<Products> sortedProducts = ProductServices.getBestSelling(productList);
        for (int i = 0; i < Math.min(5, sortedProducts.size()); i++) {
            Products p = sortedProducts.get(i);
            System.out.println(p);
        }

        System.out.println("\n=================================");
        System.out.println("===== SALES REVENUE REPORTS =====");
        System.out.println("=================================");

        Double total = SaleServices.getTotalRevenue(saleList);
        System.out.printf("Total Revenue:          PHP %.2f%n", total);

        LocalDate today = LocalDate.of(2026, 2, 2);
        Double daily = SaleServices.getRevenuePerDay(saleList, today);
        System.out.printf("Daily Revenue (%s): PHP %.2f%n", today, daily);

        Double weekly = SaleServices.getRevenuePerWeek(saleList);
        System.out.printf("Weekly Revenue:         PHP %.2f%n", weekly);

        Double monthly = SaleServices.getRevenuePerMonth(saleList, Month.JANUARY, 2026);
        System.out.printf("Monthly Revenue (Jan):  PHP %.2f%n", monthly);

        System.out.println("\n===== SEASONAL BREAKDOWN =====");

        Double newYear = SaleServices.getRevenueBySeason(saleList, "New Year Sale");
        System.out.printf("New Year Sale:          PHP %.2f%n", newYear);

        Double backToSchool = SaleServices.getRevenueBySeason(saleList, "Back to School");
        System.out.printf("Back to School:         PHP %.2f%n", backToSchool);

        Double regular = SaleServices.getRevenueBySeason(saleList, "Regular");
        System.out.printf("Regular Days:           PHP %.2f%n", regular);
    }
}