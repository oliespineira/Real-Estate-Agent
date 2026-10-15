package edu.uni.realestate.model;

import java.util.Scanner;

/**
 * Console input utilities shared by the promptInit methods of the model classes.
 * The class has no access modifier (package-private), so only classes in
 * edu.uni.realestate.model can use it. Every method repeats the prompt until the
 * input is valid and returns the accepted value as a String, ready to put in the map.
 */
class InputHelper {

    private InputHelper() { } // utility class: no instances

    static String readInt(Scanner in, String prompt, int min) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min) {
                    return String.valueOf(value);
                }
            } catch (NumberFormatException e) {
                // fall through to the error message
            }
            System.out.println("Please enter a number of at least " + min + ".");
        }
    }

    static String readDouble(Scanner in, String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            try {
                double value = Double.parseDouble(line);
                if (value >= min) {
                    return String.valueOf(value);
                }
            } catch (NumberFormatException e) {
                // fall through to the error message
            }
            System.out.println("Please enter a number of at least " + min + ".");
        }
    }

    static String readYesNo(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            if (line.equalsIgnoreCase("yes") || line.equalsIgnoreCase("no")) {
                return line.toLowerCase();
            }
            System.out.println("Please answer yes or no.");
        }
    }

    static String readChoice(Scanner in, String prompt, String... options) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            for (String option : options) {
                if (option.equalsIgnoreCase(line)) {
                    return option;
                }
            }
            System.out.println("Please choose one of: " + String.join(", ", options));
        }
    }

    static String readText(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Please enter some text.");
        }
    }
}