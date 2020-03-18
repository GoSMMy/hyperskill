package machine;

import java.util.Scanner;

public class CoffeeMachine {
    Scanner scanner = new Scanner(System.in);
    int water = 400;
    int milk = 540;
    int beans = 120;
    int cups = 9;
    int money = 550;

    public static void main(String[] args) {
        var coffeeMan = new CoffeeMachine();
        coffeeMan.askForActon();
    }

    public void askForActon() {
        String action;
        do {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            action = scanner.next();
            switch (action) {
                case "buy":
                    buyCoffee();
                    break;
                case "fill":
                    fillCoffee();
                    break;
                case "take":
                    takeMoney();
                    break;
                case "remaining":
                    remaining();
                    break;
            }
        } while (!action.equals("exit"));
    }

    public void remaining() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(beans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println(money + " of money");
    }

    public void buyCoffee() {
        String problem = "no";
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String type = scanner.next();
        if (cups < 1) {
            problem = "cups";
        } else {
            switch (type) {
                case "1":
                    if (water < 250) {
                        problem = "water";
                        break;
                    } else if (beans < 16) {
                        problem = "beans";
                        break;
                    }
                    water -= 250;
                    beans -= 16;
                    cups--;
                    money += 4;
                    break;
                case "2":
                    if (water < 350) {
                        problem = "water";
                        break;
                    } else if (milk < 75) {
                        problem = "milk";
                        break;
                    } else if (beans < 20) {
                        problem = "beans";
                        break;
                    }
                    water -= 350;
                    milk -= 75;
                    beans -= 20;
                    cups--;
                    money += 7;
                    break;
                case "3":
                    if (water < 200) {
                        problem = "water";
                        break;
                    } else if (milk < 100) {
                        problem = "milk";
                        break;
                    } else if (beans < 12) {
                        problem = "beans";
                        break;
                    }
                    water -= 200;
                    milk -= 100;
                    beans -= 12;
                    cups--;
                    money += 6;
                    break;
                case "back":
                    break;
            }
        }
        if (problem.equals("no")) {
            System.out.println("I have enough resources, making you a coffee!");
        } else {
            System.out.println("Sorry, not enough " + problem + "!");
        }
    }

    public void fillCoffee() {
        System.out.println("Write how many ml of water do you want to add:");
        water += scanner.nextInt();
        System.out.println("Write how many ml of milk do you want to add:");
        milk += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans do you want to add:");
        beans += scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        cups += scanner.nextInt();
    }

    public void takeMoney() {
        System.out.println("I gave you $" + money);
        money = 0;
    }
}
