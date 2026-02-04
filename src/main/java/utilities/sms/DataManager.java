package utilities.sms;

import models.sms.Products;
import models.sms.Sales;

import java.util.ArrayList;

public class DataManager extends DummyData {
    static ArrayList<Products> productList = new ArrayList<>();
    static ArrayList<Sales> saleList = new ArrayList<>();

    public static void generateProducts() {
        // Adds the dummy data to the list
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

        // Generate products and sales
        generateProducts();
        generateSales();

        System.out.println("===== PRODUCTS LOADED =====");
        System.out.println("Total Products: " + productList.size());
        System.out.println();

        // Display first 5 products
        System.out.println("===== SAMPLE PRODUCTS =====");
        for (int i = 0; i < Math.min(5, productList.size()); i++) {
            Products p = productList.get(i);
            System.out.print(p);
            System.out.println(p.getStockStatus());
        }

        System.out.println("\n===== SALES LOADED =====");
        System.out.println("Total Sales: " + saleList.size());
        System.out.println();

        // Display all sales
        System.out.println("===== ALL SALES =====");
        for (Sales s : saleList) {
            System.out.println(s);
        }

        // Computation for the sales and products
        // Revenue

        System.out.println("\n===== REVENUE SUMMARY =====");
        double totalRevenue = 0;
        for (Sales s : saleList) {
            totalRevenue += s.getTotalAmount();
        }

        System.out.println("Total Revenue from all sales: ₱" + String.format("%.2f", totalRevenue));


    }

}