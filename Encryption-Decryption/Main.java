package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String option = "enc";
        String s = "";
        boolean prefer = false;
        String alg = "shift";
        String fileOut = null;
        String fileIn = null;
        int n = 0;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    if (args[i + 1].equals("dec")) {
                        option = "dec";
                    }
                    break;
                case "-key":
                    n = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    prefer = true;
                    s = args[i + 1];
                    break;
                case "-out":
                    fileOut = args[i + 1];
                    break;
                case "-in":
                    fileIn = args[i + 1];
                    break;
                case "-alg":
                    alg = args[i + 1];
                    break;
            }
        }
        n *= option.equals("enc") ? 1 : -1;
        if (!prefer && fileIn != null) {
            try {
                s = new String(Files.readAllBytes(Paths.get(fileIn)));
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        if (fileOut != null) {
            File file = new File(fileOut);
            try (FileWriter writer = new FileWriter(file)) {
                for (char ch : s.toCharArray()) {
                    if (alg.equals("unicode")) {
                        writer.write((char) (ch + n));
                    } else {
                        if (ch > 96 && ch < 123) {
                            if ((ch - 96 + n) % 26 < 0) {
                                writer.write((char) (((ch - 96 + n) + 26) + 96));
                            } else {
                                writer.write((char) (((ch - 96 + n) % 26) + 96));
                            }
                        } else if (ch > 64 && ch < 91) {
                            if ((ch - 64 + n) % 26 < 0) {
                                writer.write((char) (((ch - 64 + n) + 26) + 64));
                            } else {
                                writer.write((char) (((ch - 64 + n) % 26) + 64));
                            }
                        } else {
                            writer.write(ch);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } else {
            for (char ch : s.toCharArray()) {
                if (alg.equals("unicode")) {
                    System.out.println((char) (ch + n));
                } else {
                    if (ch > 96 && ch < 123) {
                        if ((ch - 96 + n) % 26 < 0) {
                            System.out.print((char) (((ch - 96 + n) + 26) + 96));
                        } else {
                            System.out.print((char) (((ch - 96 + n) % 26) + 96));
                        }
                    } else if (ch > 64 && ch < 91) {
                        if ((ch - 64 + n) % 26 < 0) {
                            System.out.print((char) (((ch - 64 + n) + 26) + 64));
                        } else {
                            System.out.print((char) (((ch - 64 + n) % 26) + 64));                        }
                    } else {
                        System.out.print(ch);
                    }
                }
            }
        }
    }
}
