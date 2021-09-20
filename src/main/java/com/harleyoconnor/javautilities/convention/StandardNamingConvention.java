package com.harleyoconnor.javautilities.convention;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * A standard implementation of {@link NamingConvention}, storing all required functions and predicates required and
 * providing general implementations of {@link #doesFollow(String, Pattern)} and {@link
 * #convertTo(NamingConvention, String)}.
 *
 * @author Harley O'Connor
 * @see NamingConvention
 * @since JavaUtilities 0.1.1
 */
public class StandardNamingConvention implements NamingConvention {

    /**
     * The name of the convention; returned by {@link #name()}.
     */
    protected final String name;
    /**
     * The first character function; returned by {@link #firstCharacterFunction()}.
     */
    protected final Function<Character, Character> firstCharacterFunction;
    /**
     * The separator predicate; returned by {@link #separatorPredicate()}.
     */
    protected final Predicate<Character> separatorPredicate;
    /**
     * The separator function; returned by {@link #separatorFunction()}.
     */
    protected final Function<Character, String> separatorFunction;
    /**
     * The intermediate character function; returned by {@link #intermediateCharacterFunction()}.
     */
    protected final Function<Character, Character> intermediateCharacterFunction;
    /**
     * The character separator boolean; returned by {@link #characterSeparator()}.
     */
    protected final boolean characterSeparator;

    /**
     * Constructs a {@link StandardNamingConvention} using the specified parameters, setting the {@link
     * #characterSeparator} to {@code true}.
     *
     * @param name                          the name of the convention; see {@link #name()} for details
     * @param firstCharacterFunction        the first character function; see {@link #firstCharacterFunction()} for
     *                                      details
     * @param separatorPredicate            the separator predicate; see {@link #separatorPredicate()} for details
     * @param separatorFunction             the separator function; see {@link #separatorFunction()} for details
     * @param intermediateCharacterFunction the intermediate character function; see {@link #intermediateCharacterFunction()}
     *                                      for details
     */
    public StandardNamingConvention(String name, Function<Character, Character> firstCharacterFunction,
                                    Predicate<Character> separatorPredicate,
                                    Function<Character, String> separatorFunction,
                                    Function<Character, Character> intermediateCharacterFunction) {
        this(name, firstCharacterFunction, separatorPredicate, separatorFunction, intermediateCharacterFunction, true);
    }

    /**
     * Constructs a {@link StandardNamingConvention} using the specified parameters.
     *
     * @param name                          the name of the convention; see {@link #name()} for details
     * @param firstCharacterFunction        the first character function; see {@link #firstCharacterFunction()} for
     *                                      details
     * @param separatorPredicate            the separator predicate; see {@link #separatorPredicate()} for details
     * @param separatorFunction             the separator function; see {@link #separatorFunction()} for details
     * @param intermediateCharacterFunction the intermediate character function; see {@link #intermediateCharacterFunction()}
     *                                      for details
     * @param characterSeparator            the character separator boolean; see {@link #characterSeparator()} for
     *                                      details
     */
    public StandardNamingConvention(String name, Function<Character, Character> firstCharacterFunction,
                                    Predicate<Character> separatorPredicate,
                                    Function<Character, String> separatorFunction,
                                    Function<Character, Character> intermediateCharacterFunction,
                                    boolean characterSeparator) {
        this.name = name;
        this.firstCharacterFunction = firstCharacterFunction;
        this.separatorPredicate = separatorPredicate;
        this.separatorFunction = separatorFunction;
        this.intermediateCharacterFunction = intermediateCharacterFunction;
        this.characterSeparator = characterSeparator;
    }

    /**
     * {@inheritDoc}
     *
     * @param string              the string to check
     * @param acceptableCharacter a pattern describing characters that are acceptable for use between separator
     *                            characters
     * @return {@code true} if the specified {@code string} follows this convention
     */
    @Override
    public boolean doesFollow(String string, Pattern acceptableCharacter) {
        // If the string is empty, we can't determine if it follows the convention.
        if (string.isEmpty()) {
            return false;
        }

        final var chars = string.toCharArray();

        {
            final var firstChar = chars[0];

            // If the first character doesn't follow the convention, the string must not.
            if (this.firstCharacterFunction.apply(firstChar) != firstChar ||
                    !acceptableCharacter.matcher(Character.toString(firstChar)).matches()) {
                return false;
            }
        }

        // Loop from the second character, since we already checked the first.
        for (int i = 1; i < chars.length; i++) {
            var currentChar = chars[i];

            if (this.separatorPredicate.test(currentChar)) {
                // If a character separator is the last character, it does not follow the convention.
                if (this.characterSeparator && i == chars.length - 1) {
                    return false;
                }

                // If this convention has a separator character, target the character after.
                if (this.characterSeparator) {
                    currentChar = chars[++i];
                }

                // If the character after the separator does not end with the current character,
                // it does not follow the convention.
                if (!this.separatorFunction.apply(currentChar).endsWith(Character.toString(currentChar))
                        || !acceptableCharacter.matcher(Character.toString(currentChar)).matches()) {
                    return false;
                }

                continue;
            }

            // If the current character isn't a conventional intermediate character, it does
            // not follow the convention.
            if (this.intermediateCharacterFunction.apply(currentChar) != currentChar ||
                    !acceptableCharacter.matcher(Character.toString(currentChar)).matches()) {
                return false;
            }
        }

        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param toConvention the convention to convert to
     * @param string       the string to convert
     * @return the converted string
     */
    @Override
    public String convertTo(NamingConvention toConvention, String string) {
        // Instantiate StringBuilder with result of applying the first character function.
        final var convertedString = new StringBuilder(toConvention.firstCharacterFunction()
                .apply(string.charAt(0)).toString());

        boolean nextIsSeparator = false;

        for (int i = 1; i < string.length(); i++) {
            final char character = string.charAt(i);

            // If the this character should be a separator (last was separator character)
            // apply the separator function and append the result.
            if (nextIsSeparator) {
                convertedString.append(toConvention.separatorFunction().apply(character));
                nextIsSeparator = false;
                continue;
            }

            // If the this character is not a separator, apply the intermediate character
            // function and append the result.
            if (!this.separatorPredicate().test(character)) {
                convertedString.append(toConvention.intermediateCharacterFunction().apply(character));
                continue;
            }

            // This character is a separator, if we are using a character separator we will
            // apply the next character to separator function.
            nextIsSeparator = this.characterSeparator();

            // Otherwise we need to apply this character, and append the result.
            if (!nextIsSeparator) {
                convertedString.append(toConvention.separatorFunction().apply(character));
            }
        }

        return convertedString.toString();
    }

    /**
     * {@inheritDoc}
     *
     * @return the name of this convention, in pascal case
     * @since JavaUtilities 0.1.2
     */
    @Override
    public String name() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     *
     * @return a function that computes the first character to fit this convention from the given character
     */
    @Override
    public Function<Character, Character> firstCharacterFunction() {
        return firstCharacterFunction;
    }

    /**
     * {@inheritDoc}
     *
     * @return a predicate that determines if the given character is a separator
     */
    @Override
    public Predicate<Character> separatorPredicate() {
        return separatorPredicate;
    }

    /**
     * {@inheritDoc}
     *
     * @return a function that computes a separator string to fit this convention from the given first character of a
     * new word
     */
    @Override
    public Function<Character, String> separatorFunction() {
        return separatorFunction;
    }

    /**
     * {@inheritDoc}
     *
     * @return a function that computer an intermediate character to fit this convention
     */
    @Override
    public Function<Character, Character> intermediateCharacterFunction() {
        return intermediateCharacterFunction;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code true} if this {@link NamingConvention} uses an explicit extra character to denote word separations
     */
    @Override
    public boolean characterSeparator() {
        return characterSeparator;
    }

}
