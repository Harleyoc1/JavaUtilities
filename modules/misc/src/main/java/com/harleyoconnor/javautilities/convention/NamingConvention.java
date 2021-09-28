package com.harleyoconnor.javautilities.convention;

import java.lang.annotation.ElementType;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * A naming convention is defined as a set of rules for choosing the character sequence to be used for identifiers which
 * denote an entity in source code and documentation.
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
     * Flat Case is a convention in which word separations aren't denoted at all; all characters are lower case. As
     * such, it should be noted that converting to another naming convention may not work as intended for many strings.
     */
    NamingConvention FLAT_CASE = new StandardNamingConvention(
            "FlatCase",
            Character::toLowerCase,
            Character::isLowerCase,
            character -> character.toString().toLowerCase(),
            Character::toLowerCase,
            false
    );

    /**
     * Camel Case is a naming convention in which word separations are denoted by a single capitalised letter, as
     * follows: {@code thisIsACamelCaseString}.
     *
     * <p>This convention is used in Java for {@link ElementType#LOCAL_VARIABLE} and
     * {@link ElementType#FIELD} elements which are not {@code static} and {@code final}.</p>
     */
    NamingConvention CAMEL_CASE = byCase("CamelCase", false);

    /**
     * Pascal Case is a naming convention similar to {@link #CAMEL_CASE} in which word separations are denoted by a
     * single capitalised letter, however in pascal case the first letter of the string is also capitalised, as follows:
     * {@code ThisIsAPascalCaseString}.
     *
     * <p>This convention is used in Java for {@link ElementType#TYPE} and
     * {@link ElementType#ANNOTATION_TYPE} names.</p>
     */
    NamingConvention PASCAL_CASE = byCase("PascalCase", true);

    /**
     * Snake Case is a naming convention in which word separations are denoted by an underscore placed between them. All
     * other letters are lower case. For example: {@code this_is_a_snake_case_string}.
     */
    NamingConvention SNAKE_CASE = byChar("SnakeCase", '_', false, false, false);

    /**
     * Screaming Snake Case is similar to {@link #SNAKE_CASE} in which word separations are denoted by an underscore,
     * however all other letters are also uppercase, as follows: {@code THIS_IS_A_SCREAMING_SNAKE_CASE_STRING}.
     *
     * <p>This convention is used in Java for {@link ElementType#FIELD} elements which are
     * {@code static} and {@code final}.</p>
     */
    NamingConvention SCREAMING_SNAKE_CASE = byChar("ScreamingSnakeCase", '_', true, true, true);

    /**
     * Camel Snake Case is similar to {@link #SNAKE_CASE} in which word separations are denoted by an underscore,
     * however the first letter of each word subsequent to the first are capitalised, like in {@link #CAMEL_CASE}. For
     * example: {@code this_Is_A_Camel_Snake_Case_String}.
     */
    NamingConvention CAMEL_SNAKE_CASE = byChar("CamelSnakeCase", '_', false, true, false);

    /**
     * Pascal Snake Case is similar to {@link #SNAKE_CASE} in which word separations are denoted by an underscore,
     * however the first letter of each word is capitalised, like in {@link #PASCAL_CASE}. For example: {@code
     * This_Is_A_Pascal_Snake_Case_String}.
     */
    NamingConvention PASCAL_SNAKE_CASE = byChar("PascalSnakeCase", '_', true, true, false);

    /**
     * Kebab Case is a naming convention in which word separations are denoted by a dash. All other letters are lower
     * case. For example: {@code this-is-a-kebab-case-string}.
     */
    NamingConvention KEBAB_CASE = byChar("KebabCase", '-', false, false, false);

    /**
     * Screaming Kebab Case is similar to {@link #KEBAB_CASE} in which word separations are denoted by a dash, however
     * all other letters are also uppercase, as follows: {@code THIS-IS-A-KEBAB-SNAKE-CASE-STRING}.
     */
    NamingConvention SCREAMING_KEBAB_CASE = byChar("ScreamingKebabCase", '-', true, true, true);

    /**
     * Train Case is similar to {@link #KEBAB_CASE} in which word separations are denoted by a dash, however the first
     * letter of each word is capitalised, like in {@link #PASCAL_CASE}, as follows: {@code
     * This-Is-A-Train-Case-String}.
     */
    NamingConvention TRAIN_CASE = byChar("TrainCase", '-', true, true, false);

    /**
     * The default {@link Pattern} of acceptable characters, used for {@link #doesFollow(String)}.
     *
     * <p>This is {@code [A-z0-9]}, meaning that a {@link String} can follow this
     * {@link NamingConvention} as long as it uses only A-Z (ignoring case) and 0-9, apart from for separator characters
     * defined by the convention.</p>
     */
    Pattern ACCEPTABLE_CHARACTERS = Pattern.compile("[a-zA-Z0-9]");

    /**
     * Checks the specified {@code string} follows this convention, using the {@linkplain #ACCEPTABLE_CHARACTERS default
     * acceptable characters}.
     *
     * @param string the string to check
     * @return {@code true} if the specified {@code string} follows this convention
     */
    default boolean doesFollow(String string) {
        return this.doesFollow(string, ACCEPTABLE_CHARACTERS);
    }

    /**
     * Checks the specified {@code string} follows this convention.
     *
     * @param string              the string to check
     * @param acceptableCharacter a pattern describing characters that are acceptable for use between separator
     *                            characters
     * @return {@code true} if the specified {@code string} follows this convention
     */
    boolean doesFollow(String string, Pattern acceptableCharacter);

    /**
     * Attempts to convert the specified {@code string} from this convention to the specified {@code toConvention}.
     *
     * <p>Invokers should note that naming conventions like {@link #FLAT_CASE} may be impossible to accurately convert
     * to another convention.</p>
     *
     * @param toConvention the convention to convert to
     * @param string       the string to convert
     * @return the converted string
     */
    String convertTo(NamingConvention toConvention, String string);

    /**
     * Returns the name of this convention. This should be formatted using the {@linkplain #PASCAL_CASE pascal case
     * convention}.
     *
     * @return the name of this convention, in pascal case
     * @since JavaUtilities 0.1.2
     */
    String name();

    /**
     * Returns a function that computes the first character to fit this convention from the given character.
     *
     * @return a function that computes the first character to fit this convention from the given character
     */
    Function<Character, Character> firstCharacterFunction();

    /**
     * Returns a predicate that determines if the given character is a separator.
     *
     * @return a predicate that determines if the given character is a separator
     */
    Predicate<Character> separatorPredicate();

    /**
     * Returns a function that takes the first character of a new word and transforms it to fit this convention,
     * returning a string separator to use in place of it.
     *
     * @return a function that computes a separator string to fit this convention from the given first character of a
     * new word
     */
    Function<Character, String> separatorFunction();

    /**
     * Returns a function that takes an intermediate character (one that is not the first character of a word) and
     * transforms it to fit this convention.
     *
     * @return a function that computer an intermediate character to fit this convention
     */
    Function<Character, Character> intermediateCharacterFunction();

    /**
     * Returns {@code true} if this convention uses an explicit extra character, such as the underscore in {@linkplain
     * #SNAKE_CASE snake case}, to denote word separations.
     *
     * @return {@code true} if this {@link NamingConvention} uses an explicit extra character to denote word separations
     */
    boolean characterSeparator();

    /**
     * Stores all registered {@link NamingConvention} objects, enclosed in an inner class to restrict access.
     *
     * <p>To retrieve or register conventions, use {@link #get(String)} and {@link #register(NamingConvention)}
     * respectively.</p>
     *
     * @since JavaUtilities 0.1.2
     */
    class Registry {
        /**
         * The {@link Set} of {@link NamingConvention} entries.
         */
        private static final Set<NamingConvention> ENTRIES = new HashSet<>();

        /**
         * Suppresses default constructor, ensuring non-instantiability.
         */
        private Registry() {
        }
    }

    /**
     * Appends the specified {@code convention} to the {@linkplain Registry registry}, then returning it.
     *
     * @param convention the convention of type {@link N} to register
     * @param <N>        the type of the specified {@code convention} to register
     * @return the specified {@code convention}
     * @throws IllegalArgumentException if a convention with the name of the specified {@code convention} already
     *                                  existed in the {@linkplain Registry registry}.
     * @since JavaUtilities 0.1.2
     */
    static <N extends NamingConvention> N register(final N convention) {
        if (get(convention.name()).isPresent()) {
            throw new IllegalArgumentException("Attempted to register convention with" +
                    "name \"" + convention + "\", which was already registered.");
        }

        Registry.ENTRIES.add(convention);
        return convention;
    }

    /**
     * Attempts to retrieve a convention of the specified {@code name} from the {@linkplain Registry registry}. Returns
     * an optional containing the corresponding convention, or {@linkplain Optional#empty() an empty optional} if one
     * did not exist.
     *
     * @param name the name of the convention to retrieve
     * @return an optional containing the convention corresponding to the specified {@code name}, or {@linkplain
     * Optional#empty() an empty optional} if one did not exist
     * @since JavaUtilities 0.1.2
     */
    static Optional<NamingConvention> get(final String name) {
        return Registry.ENTRIES.stream()
                .filter(namingConvention -> namingConvention.name().equals(name))
                .findFirst();
    }

    /**
     * Creates a new {@link StandardNamingConvention} instance which denotes separations by a case change. This method
     * also handles registering the instantiated object to the {@linkplain Registry registry} via {@link
     * #register(NamingConvention)}.
     *
     * @param name   the name of the convention; see {@link #name()} for details
     * @param pascal {@code true} if the convention should capitalise the first letter, like in the {@linkplain
     *               #PASCAL_CASE pascal case convention}
     * @return the instantiated {@link StandardNamingConvention}
     */
    private static StandardNamingConvention byCase(final String name, final boolean pascal) {
        return register(new StandardNamingConvention(
                name,
                pascal ? Character::toUpperCase : Character::toLowerCase,
                Character::isUpperCase,
                character -> character.toString().toUpperCase(),
                Character::toLowerCase,
                false
        ));
    }

    /**
     * Creates a new {@link StandardNamingConvention} which denotes separations by the specified {@code separator}
     * character. This method also handles registering the instantiated object to the {@linkplain Registry registry} via
     * {@link #register(NamingConvention)}.
     *
     * @param name      the name of the convention; see {@link #name()} for details
     * @param separator the character the convention should use as a word separator
     * @param pascal    {@code true} if the convention should capitalise the first letter (like in the {@linkplain
     *                  #PASCAL_CASE pascal case convention})
     * @param camel     {@code true} if the convention should capitalise the first letter after a word separation (like
     *                  in the {@linkplain #CAMEL_CASE camel case convention})
     * @param screaming {@code true} if all intermediate characters should be capitalised (like in the {@linkplain
     *                  #SCREAMING_SNAKE_CASE screaming snake case convention})
     * @return the instantiated {@link StandardNamingConvention}
     */
    static StandardNamingConvention byChar(final String name, final char separator, final boolean pascal,
                                           final boolean camel, final boolean screaming) {
        return register(new StandardNamingConvention(
                name,
                pascal ? Character::toUpperCase : Character::toLowerCase,
                character -> character == separator,
                camel ? character -> separator + character.toString().toUpperCase()
                        : character -> separator + character.toString().toLowerCase(),
                screaming ? Character::toUpperCase : Character::toLowerCase
        ));
    }

}
