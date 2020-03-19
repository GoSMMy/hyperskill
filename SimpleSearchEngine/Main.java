package search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);
    String[] data;
    String[][] book;

    public void start(String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            if ("--data".equals(args[i])) {
                data = readPeople(args[i + 1]);
                book = new String[data.length][];
                for (int j = 0; j < data.length; j++) {
                    book[j] = data[j].split(" ");
                }
            }
        }
        menu();
    }


    public static void main(String[] args) throws IOException {
        var program = new Main();
        program.start(args);
    }

    public String[] readPeople(String fileName) throws IOException {
        return readFileAsString(fileName).split("\n");
    }

    public void menu(){
        int n;
        do {
        System.out.println("\n=== Menu ===\n" +
                "1. Find a person\n" +
                "2. Print all people\n" +
                "0. Exit");
            n = scanner.nextInt();
            scanner.nextLine();
            switch (n) {
                case 1:
                    searchSeparator();
                    break;
                case 2:
                    printAll();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\nIncorrect option! Try again.");
            }
        } while (n != 0);
        System.out.println("\nBye!");
    }

    public void searchSeparator() {
        System.out.println("\nSelect a matching strategy: ALL, ANY, NONE");
        switch (scanner.nextLine().toUpperCase()) {
            case "ANY":
                searchAny();
                break;
            case "ALL":
                searchAll();
                break;
            case "NONE":
                searchNone();
                break;
        }
    }

    public void searchAny(){
        System.out.println("\nEnter a name or email to search all suitable people.");
        String[] query = scanner.nextLine().toLowerCase().split(" ");
        ArrayList<String[]> res = new ArrayList<>();

        for (int k = 0; k < query.length; k++) {
            for (int i = 0; i < book.length; i++) {
                for (int j = 0; j < book[i].length; j++) {
                    if (book[i][j].toLowerCase().equals(query[k])) {
                        res.add(book[i]);
                        break;
                    }
                }
            }
        }
        if (res.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            printList(res);
        }
    }

    public void searchAll(){
        System.out.println("\nEnter a name or email to search all suitable people.");
        String[] query = scanner.nextLine().toLowerCase().split(" ");
        ArrayList<String[]> res = new ArrayList<>();

        for (int i = 0; i < book.length; i++) {
            if (checkAll(query, i)) {
                res.add(book[i]);
            }
        }
        if (res.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            printList(res);
        }
    }

    public void searchNone(){
        System.out.println("\nEnter a name or email to search all suitable people.");
        String[] query = scanner.nextLine().toLowerCase().split(" ");
        ArrayList<String[]> res = new ArrayList<>();

        for (int i = 0; i < book.length; i++) {
            if (checkNone(query, i)) {
                res.add(book[i]);
            }
        }
        if (res.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            printList(res);
        }
    }

    public boolean checkAll(String[] query, int i) {
        int res = query.length;
        for (int k = 0; k < query.length; k++) {
            for (int j = 0; j < book[i].length; j++) {
                if (book[i][j].toLowerCase().equals(query[k])) {
                    res--;
                    break;
                }
            }
        }
        return res == 0;
    }

    public boolean checkNone(String[] query, int i) {
        for (int k = 0; k < query.length; k++) {
            for (int j = 0; j < book[i].length; j++) {
                if (book[i][j].toLowerCase().equals(query[k])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String readFileAsString(String fileName) throws IOException {
            return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public void printAll() {
        System.out.println("\n=== List of people ===");
            for (String s : data) {
                System.out.println(s);
        }
    }

    public void printList(ArrayList<String[]> res) {
        System.out.println(res.size() + " persons found:");
        for (String[] print : res) {
            for (int i = 0; i < print.length; i++) {
                if (i == print.length - 1) {
                    System.out.print(print[i]);
                } else {
                    System.out.print(print[i] + " ");
                }
            }
            System.out.print("\n");
        }
    }
}