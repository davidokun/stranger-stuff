package com.stranger.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Utility class to manage console print statements
 */
public class ConsoleManager {

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    private static Scanner readInput = new Scanner(System.in);

    private ConsoleManager() {
    }

    /**
     * Read user input in console.
     * @return the input from the user.
     */
    public static String read() {
        return readInput.next();
    }

    /**
     * Print text to console wit a given color.
     *
     * @param color the color to print the text.
     * @param message the message to print.
     */
    public static void print(String color, String message) {
        System.out.println(color + message);
    }

    /**
     * Helper method to print scrollable text in the console.
     *
     * @param color the color to print the text.
     * @param inputStream the <code>{@link InputStream}</code> to print.
     * @param delay a delay in milliseconds between each line of text.
     * @param text additional text to print within the InputStream.
     */
    public static void printScrollableText(String color, InputStream inputStream, int delay, String... text) {

        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int index = 0;

            while ((line = br.readLine()) != null) {

                try {
                    //Disclaimer: This is just for fun. Don't do this at home.
                    Thread.sleep(delay);

                } catch (InterruptedException e) {
                    ConsoleManager.print(RED, "There was an error printing the scrollable text");
                }

                if (line.contains("%s")) {
                    ConsoleManager.print(color, String.format(line, text[index++]));
                } else {
                    ConsoleManager.print(color, line);
                }
            }
        } catch (IOException e) {
            ConsoleManager.print(RED, "There was an error printing the scrollable text with the Buffer");
        }
    }
}
