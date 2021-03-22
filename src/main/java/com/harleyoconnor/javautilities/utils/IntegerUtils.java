package com.harleyoconnor.javautilities.utils;

import java.util.Random;

/**
 * @author Harley O'Connor
 */
public final class IntegerUtils {

    public static final Random RANDOM = new Random();

    /**
     * Gets a random number between the two parameters, inclusive.
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @return A random number between the parameters.
     */
    public static int getRandomIntBetween (final int min, final int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    /**
     * Checks if a number is prime.
     *
     * @param number The number to check.
     * @return Whether or not it was prime.
     */
    public static boolean isPrime (final int number) {
        if (number == 1 || number == 0) return false;

        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Rounds number to given decimal places
     *
     * @param number The number to round.
     * @param decimalPlaces The number to of places to round to.
     * @return Rounded number.
     */
    public static double round (final double number, final int decimalPlaces) {
        final int power = (int) Math.pow(10, decimalPlaces);
        return (double) Math.round(number * power) / power;
    }
}
