package services.sms;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.*;
import models.sms.Products;

public class ProductServices {

    public static ArrayList<Products> getCategory(ArrayList<Products> products, String category) {
        return new ArrayList<>(products.stream().filter(p -> p.getCategory().equalsIgnoreCase(category)).toList());
    }

    public static ArrayList<Products> getBestSelling(ArrayList<Products> products) {
        return new ArrayList<>(products.stream().sorted(Comparator.comparingInt(Products::getQtySold).reversed()).toList());
    }

    public static ArrayList<Products> add(ArrayList<Products> products, Products p) {
        products.add(p);
        return products;
    }

}


