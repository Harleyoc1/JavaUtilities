package com.harleyoconnor.javautilities.io;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Scanner;

/**
 * Provides various useful {@code static} functions for reading from standard input ({@link System#in}) using {@link
 * #IN}, a simple {@link Scanner}.
 *
 * @author Harley O'Connor
 * @since JavaUtilities 0.0.1
 * @deprecated The uses for this are limited due to its rigidity. It will be replaced with a new console I/O system in
 * the future.
 */
@Deprecated(forRemoval = true, since = "0.1.1")
public final class InputUtils {

    /**
     * Creates an initialises a {@link Scanner} instance for reading from {@link System#in}.
     */
    private static final Scanner IN = new Scanner(System.in);

    /**
     * Returns a {@link String} input, looping until the inputted {@link String} is not empty.
     *
     * @param prompt The message sent to the user informing them of what to enter.
     * @return The entered {@link String}.
     */
    public static String getInput(final String prompt) {
        return getInput(prompt, true);
    }

    /**
     * Gets a {@link String} input. Loops until a valid string is input according to the parameters given.
     *
     * @param prompt          The message sent to the user informing them of what to enter.
     * @param requireNotEmpty A boolean value stating whether or not the string must be set a value.
     * @return The entered {@link String}.
     */
    public static String getInput(final String prompt, final boolean requireNotEmpty) {
        String strInput;

        while (true) {
            System.out.print(prompt + " ");
            strInput = IN.next();

            if (!strInput.equals("") || !requireNotEmpty) {
                break;
            }

            invalidInput("string");
        }

        return strInput;
    }

    /**
     * Returns an integer input. Loops until a positive, non-zero integer is inputted.
     *
     * @param prompt The message sent to the user informing them of what to enter.
     * @return The integer input.
     */
    public static int getIntInput(final String prompt) {
        return getIntInput(prompt, true, true);
    }

    /**
     * Gets an integer input. Loops until a valid integer is input according to the parameters given.
     *
     * @param prompt          The message sent to the user informing them of what to enter.
     * @param requirePositive A boolean value stating whether or not to allow negative values.
     * @param requireNotZero  A boolean value stating whether or not to allow zero.
     * @return The integer input.
     */
    public static int getIntInput(final String prompt, final boolean requirePositive, final boolean requireNotZero) {
        int intInput;

        while (true) {
            final Object input = getIntOrStringInput(prompt, requirePositive, requireNotZero);

            if (input instanceof Integer) {
                intInput = (Integer) input;
                break;
            }

            if (input != null) {
                System.out.println("\nYou must enter a valid integer.");
            }
        }

        return intInput;
    }

    /**
     * Gets an integer, or a string if one was input.
     *
     * @param prompt               The message sent to the user informing them of what to enter.
     * @param requirePositiveIfInt A boolean value stating whether or not to allow negative values if the inpout is an
     *                             integer.
     * @param requireNotZeroIfInt  A boolean value stating whether or not to allow zero if the input is an integer.
     * @return An integer, string, or null if it was an invalid integer (according to the parameters).
     */
    @Nullable
    public static Object getIntOrStringInput(final String prompt, final boolean requirePositiveIfInt,
                                             final boolean requireNotZeroIfInt) {
        final String inputStr = getInput(prompt, true);
        final int intInput;

        try {
            intInput = Integer.parseInt(inputStr);
        } catch (NumberFormatException e) {
            return inputStr;
        }

        if (intInput < 0 && requirePositiveIfInt) {
            invalidInput("positive integer", false);
            return null;
        }

        if (intInput == 0 && requireNotZeroIfInt) {
            invalidInput("non-zero integer", false);
            return null;
        }

        return intInput;
    }

    /**
     * Presents the user with all the options in the possible selections list, returns the one they select.
     *
     * @param prompt             The message sent to the user informing them of what they are selecting. Note that the
     *                           possible selections are added to this within the method.
     * @param possibleSelections The list of possible selection strings.
     * @return The possible selection string inputted.
     */
    public static String getSelection(String prompt, final List<String> possibleSelections) {
        StringBuilder promptBuilder = new StringBuilder(prompt);

        for (int i = 1; i <= possibleSelections.size(); i++) {
            promptBuilder.append("\n").append(i).append(". ").append(possibleSelections.get(i - 1).toLowerCase());
        }

        promptBuilder.append("\n> ");
        prompt = promptBuilder.toString();

        while (true) {
            final int selectionIndex = getIntInput(prompt, true, true) - 1;
            String selection = "";

            try {
                selection = possibleSelections.get(selectionIndex);
            } catch (IndexOutOfBoundsException ignored) {
            }

            if (selection.equals("")) {
                System.out.println("\nPlease enter a valid selection index.");
                continue;
            }

            return selection;
        }
    }

    private static void invalidInput(final String invalidValue) {
        invalidInput(invalidValue, true);
    }

    /**
     * Tells the user that what they entered was invalid and prepares the input scanner for new input.
     *
     * @param invalidValue The name of the invalid type.
     */
    private static void invalidInput(final String invalidValue, final boolean readyNextInput) {
        System.out.println("\nYou must enter a valid " + invalidValue + ".");
        if (readyNextInput) {
            IN.next();
        }
    }

}
