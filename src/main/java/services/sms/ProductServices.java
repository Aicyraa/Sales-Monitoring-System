package services.sms;
import java.util.ArrayList;
import java.util.stream.*;
import models.sms.Products;

public class ProductServices {

    private static ArrayList<Products> getAsAL(Stream<Products> productsStream) {
        return new ArrayList<>(new ArrayList<>(productsStream.collect(Collectors.toList())));
    }

    public static ArrayList<Products> getProductCategory(ArrayList<Products> products, String category) {
        return new ArrayList<>(getAsAL(products.stream().filter(p -> p.getCategory().equalsIgnoreCase(category))));
    }

}


