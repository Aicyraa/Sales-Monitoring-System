package utilities;

import models.Products;
import models.Sales;

import java.util.ArrayList;

public class DataManager extends DummyData {
    private static ArrayList<Products> productList = new ArrayList<>();
    private static ArrayList<Sales> saleList = new ArrayList<>();
    private static boolean dataLoaded = false;

    public static void loadData() {
        if (!dataLoaded) {
            generateProducts();
            generateSales();
            dataLoaded = true;
        }
    }

    public static ArrayList<Products> getProducts() {
        if (!dataLoaded)
            loadData();
        return productList;
    }

    public static ArrayList<Sales> getSales() {
        if (!dataLoaded)
            loadData();
        return saleList;
    }

    public static void addProduct(Products p) {
        if (!dataLoaded)
            loadData();
        productList.add(p);
    }

    public static void addSale(Sales s) {
        if (!dataLoaded)
            loadData();
        saleList.add(s);
    }

    private static void generateProducts() {
        String[][] data = DummyData.getDummyProduct();
        for (String[] datum : data) {
            productList.add(new Products(datum));
        }
    }

    private static void generateSales() {
        String[][] data = DummyData.getDummySales();
        for (String[] datum : data) {
            saleList.add(new Sales(datum));
        }
    }
}