package com.harleyoconnor.javautilities.util;

import java.util.Random;

/**
 * Provides various useful {@code static} {@link Integer} manipulation functions.
 *
 * @author Harley O'Connor
 * @since JavaUtilities 0.0.1
 */
public final class IntegerUtils {

    /**
     * The {@link Random} instance used by methods in this class.
     */
    public static final Random RANDOM = new Random();

    /**
     * Suppresses default constructor, ensuring non-instantiability.
     */
    private IntegerUtils() {
    }

    /**
     * Gets a random integer between the two parameters, inclusive.
     *
     * @param min the minimum returned value
     * @param max the maximum returned value
     * @return a random number between the two parameters, inclusive
     */
    public static int getRandomIntBetween(final int min, final int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    /**
     * Returns {@code true} if the specified {@code number} is prime.
     *
     * @param number the number to check
     * @return {@code true} if the specified {@code number} is prime
     * @author Archie Adams
     * @since JavaUtilities 0.0.3
     */
    public static boolean isPrime(final int number) {
        if (number == 1 || number == 0) {
            return false;
        }

        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the specified {@code number} rounded to the specified {@code decimalPlaces} number of decimal places.
     *
     * @param number        the number to round
     * @param decimalPlaces the number of decimal places to round to
     * @return the rounded number
     * @author Archie Adams
     * @since JavaUtilities 0.0.5
     * @deprecated seriously? A rounding function in a class named "IntegerUtils"? This will be moved in a future
     * version.
     */
    @Deprecated(since = "0.1.1")
    public static double round(final double number, final int decimalPlaces) {
        final int power = (int) Math.pow(10, decimalPlaces);
        return (double) Math.round(number * power) / power;
    }
}
