package com.stranger.util;

import java.util.Random;

/**
 * Just a random number generator to use with game events
 */
public class RandomEventGenerator {

    private RandomEventGenerator() {}

    /**
     * Generate a random number between min and max (inclusive)
     *
     * @param min minimum value to generate.
     * @param max maximum value to generate.
     * @return the generate random number
     */
    public static int generateOption(int min, int max) {

        return new Random().nextInt((max - min + 1)) + min;
    }
}
