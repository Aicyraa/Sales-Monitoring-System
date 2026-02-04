package utilities.sms;

import models.sms.Products;
import models.sms.Sales;
import services.sms.ProductServices;

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
        for (int i = 0; i < Math.min(5, saleList.size()); i++) {
            Sales s = saleList.get(i);
            System.out.println(s);
        }

        // By category
        System.out.println("\nCategorize Products");
        ArrayList<Products> categorizedProducts = ProductServices.getProductCategory(productList, "utilities");
        for (int i = 0; i < categorizedProducts.size(); i++) {
            Products p = categorizedProducts.get(i);
            System.out.println(p);
        }

    }

}