package utilities;

import models.Products;
import models.Sales;

import java.util.ArrayList;

/**
 * Manages application data including products and sales.
 * Provides centralized access to data and ensures data is loaded before use.
 * Extends DummyData to populate initial data.
 */
public class DataManager extends DummyData {
    private static ArrayList<Products> productList = new ArrayList<>();
    private static ArrayList<Sales> saleList = new ArrayList<>();
    private static boolean dataLoaded = false;

    /**
     * Loads all application data if not already loaded.
     * Generates products and sales from dummy data, then synchronizes stock levels.
     */
    public static void loadData() {
        if (!dataLoaded) {
            generateProducts();
            generateSales();
            syncStockWithSales();
            dataLoaded = true;
        }
    }

    /**
     * Retrieves the list of all products.
     * Automatically loads data if not already loaded.
     * 
     * @return ArrayList of all products
     */
    public static ArrayList<Products> getProducts() {
        if (!dataLoaded)
            loadData();
        return productList;
    }

    /**
     * Retrieves the list of all sales.
     * Automatically loads data if not already loaded.
     * 
     * @return ArrayList of all sales
     */
    public static ArrayList<Sales> getSales() {
        if (!dataLoaded)
            loadData();
        return saleList;
    }

    /**
     * Adds a new product to the product list.
     * Ensures data is loaded before adding.
     * 
     * @param p Product to add
     */
    public static void addProduct(Products p) {
        if (!dataLoaded)
            loadData();
        productList.add(p);
    }

    /**
     * Adds a new sale to the sales list.
     * Ensures data is loaded before adding.
     * 
     * @param s Sale to add
     */
    public static void addSale(Sales s) {
        if (!dataLoaded)
            loadData();
        saleList.add(s);
    }

    /**
     * Generates products from dummy data and adds them to the product list.
     */
    private static void generateProducts() {
        String[][] data = DummyData.getDummyProduct();
        for (String[] datum : data) {
            productList.add(new Products(datum));
        }
    }

    /**
     * Generates sales from dummy data and adds them to the sales list.
     */
    private static void generateSales() {
        String[][] data = DummyData.getDummySales();
        for (String[] datum : data) {
            saleList.add(new Sales(datum));
        }
    }

    /**
     * Synchronizes product stock levels with sales data.
     * Deducts sold quantities from each product's current stock.
     */
    private static void syncStockWithSales() {
        for (Sales sale : saleList) {
            for (Products product : productList) {
                if (product.getId() == sale.getProductId()) {
                    product.deductStock(sale.getQuantitySold());
                    break;
                }
            }
        }
    }
}