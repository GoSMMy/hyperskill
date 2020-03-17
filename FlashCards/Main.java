package flashcards;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    Scanner scanner = new Scanner(System.in);
    private ArrayList<String> cardsTerms = new ArrayList<>();
    private ArrayList<String> cardsDefinitions = new ArrayList<>();
    private Map<String, Integer> wrongAnswers = new HashMap<>();
    StringBuilder logFile = new StringBuilder("");

    public static void main(String[] args) {
        var cardGame = new Main();
        cardGame.askForAction(args);
    }

    public void askForAction(String[] args) {
        int export = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-import")) {
                if (i + 1 < args.length) {
                    importFromArgs(args[i + 1]);
                }
            } else if (args[i].equals("-export")) {
                if (i + 1 < args.length) {
                    export = i + 1;
                }
            }
        }
        boolean exit = false;
        while (!exit){
            ioLog("Input the action (add, remove, import," +
                    " export, ask, exit, log, hardest card, reset stats):\n");
            String actionInput = ioLog();
            switch (actionInput) {
                case "add":
                    addCard();
                    break;
                case "remove":
                    remove();
                    break;
                case "import":
                    importFile();
                    break;
                case "export":
                    exportToFile();
                    break;
                case "ask":
                    askForCard();
                    break;
                case "hardest card":
                    hardestCard();
                    break;
                case "reset stats":
                    resetStats();
                    break;
                case "log":
                    log();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    if (export > 0) {
                        exportFromArgs(args[export]);
                    }
                    exit = true;
                    break;
            }
        }
    }

    public void log() {

        scanner.reset();
        ioLog("File name:\n");
        String fileName = ioLog();
        if (fileName.isEmpty()) {
            fileName = ioLog();
        }
        File file = new File(fileName);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(String.valueOf(logFile));
            ioLog("The log has been saved.\n");
        }  catch (IOException e) {
            ioLog(e.toString());
        }
    }

    public void ioLog(String io) {
        logFile.append(io);
        System.out.print(io);
    }

    public String ioLog() {
        String input = scanner.nextLine();
        logFile.append(input).append("\n");
        return input;
    }

    public void resetStats() {
        for (var entry : wrongAnswers.entrySet()) {
            entry.setValue(0);
        }
        ioLog("Card statistics has been reset.\n");
    }

    public void hardestCard() {
        int maxWrongCount = 0;
        int howManyCards = 0;
        StringBuilder toOutput = new StringBuilder("");
        for (var entry : wrongAnswers.entrySet()) {
            maxWrongCount = Math.max(maxWrongCount, entry.getValue());
        }
        for (var entry : wrongAnswers.entrySet()) {
            if (maxWrongCount == entry.getValue()) {
                howManyCards++;
                if (toOutput.length() > 0 ) {
                    toOutput.append(", ");
                }
                toOutput.append(String.format("\"%s\"", entry.getKey()));
            }
        }
        if (maxWrongCount == 0) {
            ioLog("There are no cards with errors.\n");
        } else {
            if (howManyCards == 1) {
                ioLog(String.format("The hardest card is %s. You have %d errors answering it.\n", toOutput, maxWrongCount));
            } else {
                ioLog(String.format("The hardest cards are %s. You have %d errors answering them.\n", toOutput, maxWrongCount));
            }
        }


    }

    public void exportToFile() {
        scanner.reset();
        ioLog("File name:\n");
        String fileName = ioLog();
        if (fileName.isEmpty()) {
            fileName = ioLog();
        }
        File file = new File(fileName);
        int addedCards = 0;

        try (FileWriter writer = new FileWriter(file)) {
            StringBuilder stringBuilder = new StringBuilder("");
            for (int i = 0; i < cardsTerms.size(); i++) {
                stringBuilder.append(cardsTerms.get(i)).append("\n");
                stringBuilder.append(cardsDefinitions.get(i)).append("\n");
                stringBuilder.append(wrongAnswers.getOrDefault(
                        cardsTerms.get(i),0)).append("\n");

                addedCards++;
            }
            writer.write(String.valueOf(stringBuilder));
            ioLog(addedCards + " cards have been saved.\n");
        } catch (IOException e) {
            ioLog(e.toString());
        }
    }

    public void exportFromArgs(String fileName) {
        scanner.reset();
        if (fileName.isEmpty()) {
            fileName = ioLog();
        }
        File file = new File(fileName);
        int addedCards = 0;

        try (FileWriter writer = new FileWriter(file)) {
            StringBuilder stringBuilder = new StringBuilder("");
            for (int i = 0; i < cardsTerms.size(); i++) {
                stringBuilder.append(cardsTerms.get(i)).append("\n");
                stringBuilder.append(cardsDefinitions.get(i)).append("\n");
                stringBuilder.append(wrongAnswers.getOrDefault(
                        cardsTerms.get(i),0)).append("\n");

                addedCards++;
            }
            writer.write(String.valueOf(stringBuilder));
            ioLog(addedCards + " cards have been saved.\n");
        } catch (IOException e) {
            ioLog(e.toString());
        }
    }

    public void importFile() {
        ioLog("File name:\n");
        String fileName = ioLog();

        int addedCards = 0;
        try {
            String stringsFromFile = readFileAsString(fileName);
            Scanner scannerFile = new Scanner(stringsFromFile);
            while (scannerFile.hasNextLine()) {
                addCardFromFile(scannerFile.nextLine(),
                        scannerFile.nextLine(), Integer.parseInt(scannerFile.nextLine()));
                addedCards++;
            }
            ioLog(addedCards + " cards have been loaded.\n");
        } catch (IOException e) {
            ioLog("File not found.\n");
        }
    }

    public void importFromArgs(String fileName) {
        int addedCards = 0;
        try {
            String stringsFromFile = readFileAsString(fileName);
            Scanner scannerFile = new Scanner(stringsFromFile);
            while (scannerFile.hasNextLine()) {
                addCardFromFile(scannerFile.nextLine(),
                        scannerFile.nextLine(), Integer.parseInt(scannerFile.nextLine()));
                addedCards++;
            }
            ioLog(addedCards + " cards have been loaded.\n");
        } catch (IOException e) {
            ioLog("File not found.\n");
        }
    }

    public void addCardFromFile(String termToAdd, String defToAdd, int wrong) {
        if (cardsTerms.indexOf(termToAdd) >= 0) {
            cardsDefinitions.set(cardsTerms.indexOf(termToAdd), defToAdd);
        } else {
            cardsTerms.add(termToAdd);
            cardsDefinitions.add(defToAdd);
        }
        if (wrong > 0) {
            wrongAnswers.put(termToAdd, wrong);
        }
    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public void remove() {
        boolean success = false;
        while (!success) {
            ioLog("The card:\n");
            scanner.reset();
            String cardToRemove = ioLog();
            while (cardToRemove.isEmpty()) {
                cardToRemove = ioLog();
            }
            int indexToRemove = cardsTerms.indexOf(cardToRemove);
            if (indexToRemove == -1 ) {
                ioLog(String.format("Can't remove \"%s\": there is no such card.\n",
                        cardToRemove));
            } else {
                cardsTerms.remove(indexToRemove);
                cardsDefinitions.remove(indexToRemove);
                wrongAnswers.remove(cardToRemove);
                ioLog("The card has been removed.\n");
            }
            success = true;
        }
    }

    public void addCard() {
        boolean success = false;
        ioLog("The card :\n");
        String termToAdd = ioLog();
        if (cardsTerms.indexOf(termToAdd) == -1) {
            cardsTerms.add(termToAdd);
            success = true;
        } else {
            ioLog(String.format("The card \"%s\" already exists. \n", termToAdd)); //Try again:
        }

        if (success ) {
            ioLog("The definition of the card :\n");
            String defToAdd = ioLog();
            if (cardsDefinitions.indexOf(defToAdd) == -1) {
                cardsDefinitions.add(defToAdd);
                success = true;
                ioLog(String.format("The pair (\"%s\":\"%s\") has been added.\n",
                        cardsTerms.get(cardsDefinitions.indexOf(defToAdd)),
                        cardsDefinitions.get(cardsDefinitions.indexOf(defToAdd))));
            } else {
                ioLog(String.format("The definition \"%s\" already exists. \n", defToAdd));//Try again:
                success = false;
            }
            if (!success) {
                cardsTerms.remove(cardsTerms.size() - 1);
            }

        }

    }

    public void askForCard() {
        Random random = new Random();
        ioLog("How many times to ask?\n");
        int howManyTimes =  Integer.parseInt(ioLog());
        for (int i = 0; i < howManyTimes; i++) {
            int numberToAsk = random.nextInt(cardsTerms.size());
            ioLog(String.format("Print the  definition of \"%s\":\n",
                    cardsTerms.get(numberToAsk)));
            String answer = ioLog();
            if (answer.equals(cardsDefinitions.get(numberToAsk))) {
                ioLog("Correct answer.\n");
            } else {
                ioLog(String.format("Wrong answer. The correct one is \"%s\"",
                        cardsDefinitions.get(numberToAsk)));
                if (wrongAnswers.containsKey(cardsTerms.get(numberToAsk))) {
                    wrongAnswers.put(cardsTerms.get(numberToAsk),
                            wrongAnswers.get(cardsTerms.get(numberToAsk)) + 1 );
                } else {
                    wrongAnswers.put(cardsTerms.get(numberToAsk), 1);
                }
                if (cardsDefinitions.indexOf(answer) >= 0 ) {
                    ioLog(String.format(", you've just written the definition of \"%s\".\n",
                            cardsTerms.get(cardsDefinitions.indexOf(answer))));
                } else   ioLog(".\n");
            }

        }
    }

}