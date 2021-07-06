package com.harleyoconnor.javautilities.convention;

import java.lang.annotation.ElementType;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * A naming convention is defined as a set of rules for choosing the character sequence
 * to be used for identifiers which denote an entity in source code and documentation.
 *
 * <p>A {@link NamingConvention} object stores the rules of a naming convention and the
 * necessary tools required to convert to and from it.</p>
 *
 * @author Harley O'Connor
 * @see StandardNamingConvention
 * @since JavaUtilities 0.1.1
 */
public interface NamingConvention {

    /**
     * Flat Case is a convention in which word separations aren't denoted at all;
     * all characters are lower case. As such, it should be noted that converting to
     * another naming convention may not work as intended for many strings.
     */
    NamingConvention FLAT_CASE = new StandardNamingConvention(
            Character::toLowerCase,
            Character::isLowerCase,
            character -> character.toString().toLowerCase(),
            Character::toLowerCase,
            false
    );

    /**
     * Camel Case is a naming convention in which word separations are denoted by a single
     * capitalised letter, as follows: {@code thisIsACamelCaseString}.
     *
     * <p>This convention is used in Java for {@link ElementType#LOCAL_VARIABLE} and
     * {@link ElementType#FIELD} elements which are not {@code static} and
     * {@code final}.</p>
     */
    NamingConvention CAMEL_CASE = byCase(false);

    /**
     * Pascal Case is a naming convention similar to {@link #CAMEL_CASE} in which word
     * separations are denoted by a single capitalised letter, however in pascal case the
     * first letter of the string is also capitalised, as follows:
     * {@code ThisIsAPascalCaseString}.
     *
     * <p>This convention is used in Java for {@link ElementType#TYPE} and
     * {@link ElementType#ANNOTATION_TYPE} names.</p>
     */
    NamingConvention PASCAL_CASE = byCase(true);

    /**
     * Snake Case is a naming convention in which word separations are denoted by an
     * underscore placed between them. All other letters are lower case. For example:
     * {@code this_is_a_snake_case_string}.
     */
    NamingConvention SNAKE_CASE = byChar('_', false, false, false);

    /**
     * Screaming Snake Case is similar to {@link #SNAKE_CASE} in which word separations are
     * denoted by an underscore, however all other letters are also uppercase, as follows:
     * {@code THIS_IS_A_SCREAMING_SNAKE_CASE_STRING}.
     *
     * <p>This convention is used in Java for {@link ElementType#FIELD} elements which are
     * {@code static} and {@code final}.</p>
     */
    NamingConvention SCREAMING_SNAKE_CASE = byChar('_', true, true, true);

    /**
     * Camel Snake Case is similar to {@link #SNAKE_CASE} in which word separations are
     * denoted by an underscore, however the first letter of each word subsequent to the
     * first are capitalised, like in {@link #CAMEL_CASE}. For example:
     * {@code this_Is_A_Camel_Snake_Case_String}.
     */
    NamingConvention CAMEL_SNAKE_CASE = byChar('_', false, true, false);

    /**
     * Pascal Snake Case is similar to {@link #SNAKE_CASE} in which word separations are
     * denoted by an underscore, however the first letter of each word is capitalised, like
     * in {@link #PASCAL_CASE}. For example: {@code This_Is_A_Pascal_Snake_Case_String}.
     */
    NamingConvention PASCAL_SNAKE_CASE = byChar('_', true, true, false);

    /**
     * Kebab Case is a naming convention in which word separations are denoted by a dash.
     * All other letters are lower case. For example: {@code this-is-a-kebab-case-string}.
     */
    NamingConvention KEBAB_CASE = byChar('-', false, false, false);

    /**
     * Screaming Kebab Case is similar to {@link #KEBAB_CASE} in which word separations
     * are denoted by a dash, however all other letters are also uppercase, as follows:
     * {@code THIS-IS-A-KEBAB-SNAKE-CASE-STRING}.
     */
    NamingConvention SCREAMING_KEBAB_CASE = byChar('-', true, true, true);

    /**
     * Train Case is similar to {@link #KEBAB_CASE} in which word separations are denoted
     * by a dash, however the first letter of each word is capitalised, like in
     * {@link #PASCAL_CASE}, as follows: {@code This-Is-A-Train-Case-String}.
     */
    NamingConvention TRAIN_CASE = byChar('-', true, true, false);

    /**
     * The default {@link Pattern} of acceptable characters, used for
     * {@link #doesFollow(String)}.
     *
     * <p>This is {@code [A-z0-9]}, meaning that a {@link String} can follow this
     * {@link NamingConvention} as long as it uses only A-Z (ignoring case) and 0-9,
     * apart from for separator characters defined by the convention.</p>
     */
    Pattern ACCEPTABLE_CHARACTERS = Pattern.compile("[A-z0-9]");

    /**
     * Checks if the specified {@code string} follows this {@link NamingConvention},
     * using the default acceptable characters, {@link #ACCEPTABLE_CHARACTERS}.
     *
     * @param string The {@link String} to check.
     * @return {@code true} if the {@code string} follows this {@link NamingConvention},
     *         {@code false} otherwise.
     */
    default boolean doesFollow(String string) {
        return this.doesFollow(string, ACCEPTABLE_CHARACTERS);
    }

    /**
     * Checks if the specified {@code string} follows this {@link NamingConvention}.
     *
     * @param string The {@link String} to check.
     * @param acceptableCharacter A {@link Pattern} describing characters that are
     *                            acceptable for use between separator characters.
     * @return {@code true} if the {@code string} follows this {@link NamingConvention},
     *         {@code false} otherwise.
     */
    boolean doesFollow(String string, Pattern acceptableCharacter);

    /**
     * Attempts to convert the specified {@code string} from this {@link NamingConvention}
     * to the specified {@code toConvention}.
     *
     * <p>Invokers should note that naming conventions like {@link #FLAT_CASE} may be
     * impossible to accurately convert to another convention.</p>
     *
     * @param toConvention The {@link NamingConvention} to convert to.
     * @param string The {@link String} to convert.
     * @return The converted {@link String}.
     */
    String convertTo(NamingConvention toConvention, String string);

    /**
     * Creates a new {@link StandardNamingConvention} which denotes separations by a case.
     *
     * @param pascal {@code true} if the convention should capitalise the first letter,
     *               life in the {@link #PASCAL_CASE} convention.
     * @return The instantiated {@link StandardNamingConvention}.
     */
    private static StandardNamingConvention byCase(final boolean pascal) {
        return new StandardNamingConvention(
                pascal ? Character::toUpperCase : Character::toLowerCase,
                Character::isUpperCase,
                character -> character.toString().toUpperCase(),
                Character::toLowerCase,
                false
        );
    }

    /**
     * Creates a new {@link StandardNamingConvention} which denotes separations by the
     * specified {@code separator} character.
     *
     * @param separator The {@link Character} to the convention should use as a word
     *                  separator.
     * @param pascal {@code true} if the convention should capitalise the first letter
     *               (like in the {@link #PASCAL_CASE} convention) {@code false}
     *               otherwise.
     * @param camel {@code true} if the convention should capitalise the first letter
     *              after a word separation (like in the {@link #CAMEL_CASE} convention)
     *              {@code false} otherwise.
     * @param screaming {@code true} if all intermediate characters should be capitalised
     *                  (like in the {@link #SCREAMING_SNAKE_CASE} convention)
     *                  {@code false} otherwise.
     * @return The instantiated {@link StandardNamingConvention}.
     */
    static StandardNamingConvention byChar(final char separator, final boolean pascal, final boolean camel, final boolean screaming) {
        return new StandardNamingConvention(
                pascal ? Character::toUpperCase : Character::toLowerCase,
                character -> character == separator,
                camel ? character -> separator + character.toString().toUpperCase()
                        : character -> separator + character.toString().toLowerCase(),
                screaming ? Character::toUpperCase : Character::toLowerCase
        );
    }

    /**
     * Returns a {@link Function} that takes the first character of a {@link String}
     * and transforms it to fit this {@link NamingConvention}.
     *
     * @return The {@link Function} that computes the first character to fit this
     *         {@link NamingConvention}.
     */
    Function<Character, Character> firstCharacterFunction();

    /**
     * Returns a {@link Predicate} that determines if the specified {@link Character}
     * if a valid separator for this {@link NamingConvention}.
     *
     * @return The {@link Predicate} which validates that a separator fits this
     *         {@link NamingConvention}.
     */
    Predicate<Character> separatorPredicate();

    /**
     * Returns a {@link Function} that takes the first {@link Character} of a new word
     * and transforms it to fit this {@link NamingConvention}, returning a
     * {@link String} separator to use in place of this.
     *
     * @return The {@link Function} that computes a separator to fit this
     *         {@link NamingConvention}.
     */
    Function<Character, String> separatorFunction();

    /**
     * Returns a {@link Function} that takes an intermediate {@link Character} (one
     * that is not the first character of a word) and transforms it to fit this
     * {@link NamingConvention}.
     *
     * @return The {@link Function} that computer an intermediate character to fit
     *         {@link NamingConvention}.
     */
    Function<Character, Character> intermediateCharacterFunction();

    /**
     * Returns {@code true} if this {@link NamingConvention} uses an explicit extra
     * character, such as the underscore in {@link #SNAKE_CASE}, to denote word
     * separations.
     *
     * @return {@code true} if this {@link NamingConvention} uses an explicit extra
     *         character, such as the underscore in {@link #SNAKE_CASE}, to denote
     *         word separations, {@code false} otherwise.
     */
    boolean characterSeparator();

}
