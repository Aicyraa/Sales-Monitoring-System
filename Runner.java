package app.salesFruits;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner menuInput = new Scanner(System.in);
        SalesFruitsComputation sales = new SalesFruitsComputation();
        System.out.println("IT-252 Fruit Market");
        System.out.println("[1] Mango");
        System.out.println("[2] Apple");
        System.out.println("[3] Orange");
        System.out.println("[4] Pineapple");
        System.out.print("Select fruit: ");
        
        int choice = menuInput.nextInt();
        switch (choice) {
            case 1:
                sales.calculateFruit("Mango", 20.0, 35.5, 50.25);
                break;
            case 2:
                sales.calculateFruit("Apple", 15.0, 30.0, 45.0);
                break;
            case 3:
                sales.calculateFruit("Orange", 12.0, 22.0, 32.5);
                break;
            case 4:
                sales.calculateFruit("Pineapple", 40.0, 65.0, 90.0);
                break;
            default:
                System.out.println("Invalid Selection");
        }
        
        menuInput.close();
    }
}