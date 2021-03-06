package com.harleyoconnor.javautilities.util;

import java.util.Random;

/**
 * Provides various useful {@code static} {@link Integer} manipulation functions.
 *
 * @author Harley O'Connor
 * @since JavaUtilities 0.0.1
 */
public final class IntegerUtils {

    /** The {@link Random} instance used by methods in this class. */
    public static final Random RANDOM = new Random();

    /**
     * Suppresses default constructor, ensuring non-instantiability.
     */
    private IntegerUtils() {
    }

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
     * @since JavaUtilities 0.0.3
     * @author Archie Adams
     */
    public static boolean isPrime (final int number) {
        if (number == 1 || number == 0)
            return false;

        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Rounds number to given number of decimal places.
     *
     * @param number The number to round.
     * @param decimalPlaces The number to of places to round to.
     * @return The rounded number.
     * @since JavaUtilities 0.0.5
     * @author Archie Adams
     * @deprecated Seriously? A rounding function in a class named "IntegerUtils"?
     *             This will be moved in future versions.
     */
    @Deprecated
    public static double round (final double number, final int decimalPlaces) {
        final int power = (int) Math.pow(10, decimalPlaces);
        return (double) Math.round(number * power) / power;
    }
}
