package app.salesFruits;
import java.util.Scanner;

public class SalesFruitsComputation {
    Scanner input = new Scanner(System.in);

    public void calculateFruit(String name, double small, double medium, double large) {
        double pricePerKilo = 0;
        System.out.print("Enter " + name + " size (Small, Medium, or Large): ");
        String fruitSize = input.nextLine();

        if (fruitSize.equalsIgnoreCase("small")) {
            pricePerKilo = small;
        } else if (fruitSize.equalsIgnoreCase("medium")) {
            pricePerKilo = medium;
        } else if (fruitSize.equalsIgnoreCase("large")) {
            pricePerKilo = large;
        } else {
            System.out.println("Invalid size");
            return;
        }

        System.out.print("How many kilos of " + name + " will you buy? ");
        double kilos = input.nextDouble();
        input.nextLine(); 

        double totalCost = pricePerKilo * kilos;

        System.out.println("\n--- Sales Receipt ---");
        System.out.println("Fruit name: " + name);
        System.out.println("Selected size: " + fruitSize);
        System.out.println("Price per kilo: " + pricePerKilo);
        System.out.println("Number of kilos: " + kilos);
        System.out.println("Total cost: " + totalCost);
        System.out.println("---------------------");
    }
}