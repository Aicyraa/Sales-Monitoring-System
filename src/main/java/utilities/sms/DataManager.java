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

        // Pag deduct ng stock manually
        productList.get(0).deductStock(10);
        productList.get(2).deductStock(15);
        productList.get(5).deductStock(7);
        productList.get(7).deductStock(20);
        productList.get(18).deductStock(25);
        productList.get(11).deductStock(23);
        productList.get(18).deductStock(16);

        // Products
        System.out.println("\n===== SAMPLE PRODUCTS =====");
        for (int i = 0; i < Math.min(5, productList.size()); i++) {
            Products p = productList.get(i);
            System.out.println(p);
            System.out.println("Stock Status: " + p.getStockStatus());
        }

        // Sales
        System.out.println("\n===== ALL SALES =====");
        for (int i = 0; i < Math.min(5, saleList.size()); i++) {
            Sales s = saleList.get(i);
            System.out.println(s);
        }

        // By category
        System.out.println("\n===== CATEGORIZE PRODUCTS =====");
        ArrayList<Products> categorizedProducts = ProductServices.getCategory(productList, "utilities");
        for (int i = 0; i < categorizedProducts.size(); i++) {
            Products p = categorizedProducts.get(i);
            System.out.println(p);
        }

        // least-selling to best-selling

        System.out.println("\n==== BEST SELLING TO LEAST SELLING =====");
        ArrayList<Products> sortedProducts = ProductServices.getBestSelling(productList);
        for (int i = 0; i < Math.min(5, sortedProducts.size()); i++) {
            Products p = sortedProducts.get(i);
            System.out.println(p);
        }

    }

}