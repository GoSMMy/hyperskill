package budget;

import java.io.*;
import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static double balance = 0.0;

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        while (true) {
            System.out.println("Choose your action: \n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchase\n" +
                    "4) Balance\n" +
                    "5) Save\n" +
                    "6) Load\n" +
                    "7) Analyze (Sort)\n" +
                    "0) Exit"
            );
            int choice = sc.nextInt();
            System.out.println();
            sc.nextLine();
            switch (choice) {
                case 1:
                    addIncome();
                    break;
                case 2:
                    addPurchase();
                    break;
                case 3:
                    showPurchaseList();
                    break;
                case 4:
                    showBalance();
                    break;
                case 5:
                    saveToFile();
                    break;
                case 6:
                    loadFromFile();
                    break;
                case 7:
                    sortMenu();
                    break;
                case 0:
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Unknown operation");

            }
            System.out.println();

        }
    }
    public static void saveToFile(){
        File file = new File("purchases.txt");
        try (PrintWriter writer = new PrintWriter(file)){
            writer.println(balance);
            for(Purchase p : Purchase.getPurchaseList(PurchaseType.ALL)){
                writer.println(p.getName());
                writer.println(p.getPrice());
                writer.println(p.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Purchases were saved!");
    }

    public static void loadFromFile(){
        File file = new File("purchases.txt");
        try (Scanner reader = new Scanner(file)){
            balance = Double.parseDouble(reader.nextLine());
            while (reader.hasNextLine()){
                String name = reader.nextLine();
                double price = Double.parseDouble(reader.nextLine());
                String type = reader.nextLine();
                new Purchase(name,price,PurchaseType.valueOf(type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Purchases were loaded!");
    }
    public static void addIncome() {
        System.out.println("Enter income:");
        balance += sc.nextDouble();
        System.out.println("Income was added!");
    }

    public static void addPurchase() {
        while (true) {
            System.out.println("Choose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) Back");

            int choice = sc.nextInt();
            System.out.println();
            sc.nextLine();
            if (choice == 5) {
                return;
            }
            System.out.println("Enter purchase name:");
            String name = sc.nextLine();
            System.out.println("Enter its price");
            double price = sc.nextDouble();

            switch (choice) {
                case 1:
                    new Purchase(name, price,PurchaseType.FOOD);
                    break;
                case 2:
                    new Purchase(name, price,PurchaseType.CLOTHES);
                    break;
                case 3:
                    new Purchase(name, price,PurchaseType.ENTERTAINMENT);
                    break;
                case 4:
                    new Purchase(name, price,PurchaseType.OTHER);
                    break;
                default:
            }
            balance -= price;
            if (balance < 0) {
                balance = 0.0;
            }
            System.out.println("Purchase was added!\n");
        }
    }

    public static void showPurchaseList() {
        while (true) {
            System.out.println("Choose the type of purchases\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back");

            int choice = sc.nextInt();
            System.out.println();
            sc.nextLine();
            switch (choice) {
                case 1:
                    Purchase.showPurchaseList(PurchaseType.FOOD);
                    break;
                case 2:
                    Purchase.showPurchaseList(PurchaseType.CLOTHES);
                    break;
                case 3:
                    Purchase.showPurchaseList(PurchaseType.ENTERTAINMENT);
                    break;
                case 4:
                    Purchase.showPurchaseList(PurchaseType.OTHER);
                    break;
                case 5:
                    Purchase.showPurchaseList(PurchaseType.ALL);
                    break;
                case 6:
                    return;
                default:
            }
            System.out.println();
        }
    }

    public static void sortMenu() {
        while (true) {
            System.out.println("How do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back");
            int choice = sc.nextInt();
            System.out.println();
            sc.nextLine();
            switch (choice) {
                case 1:
                    sortCertainType(PurchaseType.ALL);
                    break;
                case 2:
                    sortByType();
                    break;
                case 3:
                    sortCertainTypeMenu();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Unknown operation");
            }
        }
    }

    public static void sortByType() {
        System.out.printf("Types:\n" +
                "Food - $%.2f\n" +
                "Entertainment - $%.2f\n" +
                "Clothes - $%.2f\n" +
                "Other - $%.2f\n" +
                "Total sum: $%.2f\n\n",
                Purchase.getSum(PurchaseType.FOOD),
                Purchase.getSum(PurchaseType.ENTERTAINMENT),
                Purchase.getSum(PurchaseType.CLOTHES),
                Purchase.getSum(PurchaseType.OTHER),
                Purchase.getSum(PurchaseType.ALL));
    }

    public static void sortCertainTypeMenu() {
            System.out.println("Choose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other");
            int choice = sc.nextInt();
            System.out.println();
            sc.nextLine();
            switch (choice) {
                case 1:
                    sortCertainType(PurchaseType.FOOD);
                    break;
                case 2:
                    sortCertainType(PurchaseType.CLOTHES);
                    break;
                case 3:
                    sortCertainType(PurchaseType.ENTERTAINMENT);
                    break;
                case 4:
                    sortCertainType(PurchaseType.OTHER);
                    break;
                default:
                    System.out.println("Unknown operation");
            }
    }

    public static void sortCertainType(PurchaseType type) {
        List<Purchase> myList = Purchase.getPurchaseList(type);
        for (int i = 0; i < myList.size() - 1; i++) {
            for (int j = 0; j < myList.size() - i - 1; j++) {
                if (myList.get(j).getPrice() < myList.get(j + 1).getPrice()) {
                    Purchase tmp = myList.get(j);
                    myList.set(j, myList.get(j + 1));
                    myList.set(j + 1, tmp);
                }
            }
        }
        Purchase.showPurchaseList(type);
        System.out.println();
    }

    public static void showBalance() {
        System.out.printf("Balance: $%.2f\n", balance);
    }

}


enum PurchaseType {
    FOOD, CLOTHES, ENTERTAINMENT, OTHER, ALL
}

class Purchase {

    private static List<Purchase> purchases = new ArrayList<>();
    private static List<Purchase> foodPurchases = new ArrayList<>();
    private static List<Purchase> clothingPurchases = new ArrayList<>();
    private static List<Purchase> entertainmentPurchases = new ArrayList<>();
    private static List<Purchase> otherPurchases = new ArrayList<>();

    private static double totalSum = 0;
    private static double foodSum = 0;
    private static double clothingSum = 0;
    private static double entertainmentSum = 0;
    private static double otherSum = 0;

    private String name;
    private double price;
    private PurchaseType type;

    public PurchaseType getType() {
        return type;
    }

    public Purchase(String name, double price, PurchaseType type) {
        this.name = name;
        this.price = price;
        this.type = type;
        totalSum += price;
        purchases.add(this);
        switch (type) {
            case FOOD:
                foodPurchases.add(this);
                foodSum += price;
                break;
            case OTHER:
                otherPurchases.add(this);
                otherSum += price;
                break;
            case CLOTHES:
                clothingPurchases.add(this);
                clothingSum += price;
                break;
            case ENTERTAINMENT:
                entertainmentPurchases.add(this);
                entertainmentSum += price;
                break;
        }
    }

    public static List<Purchase> getPurchaseList(PurchaseType type) {
        switch (type) {
            case ENTERTAINMENT:
                return entertainmentPurchases;
            case CLOTHES:
                return clothingPurchases;
            case OTHER:
                return otherPurchases;
            case FOOD:
                return foodPurchases;
            default:
                return purchases;
        }
    }

    public static double getSum(PurchaseType type) {
        switch (type) {
            case FOOD:
                return foodSum;
            case OTHER:
                return otherSum;
            case CLOTHES:
                return clothingSum;
            case ENTERTAINMENT:
                return entertainmentSum;
            default:
                return totalSum;
        }
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public static void showPurchaseList(PurchaseType type) {
        List<Purchase> myList = getPurchaseList(type);
        switch (type) {
            case ENTERTAINMENT:
                System.out.println("Entertainment:");
                break;
            case CLOTHES:
                System.out.println("Clothes: ");
                break;
            case OTHER:
                System.out.println("Other: ");
                break;
            case FOOD:
                System.out.println("Food: ");
                break;
            default:
                System.out.println("All: ");
        }
        if (myList.isEmpty()) {
            System.out.println("Purchase list is empty");
            return;
        }
        for (Purchase p : myList) {
            System.out.printf("%s $%.2f\n", p.getName(), p.getPrice());
        }
        double sum = getSum(type);
        System.out.printf("Total: $%.2f \n", sum);
    }
}
