package com.harleyoconnor.javautilities.convention;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Tests naming conventions function properly.
 *
 * @author Harley O'Connor
 * @see NamingConvention
 * @see StandardNamingConvention
 */
public final class NamingConventionTests {

    @Test
    public void testDoesFollow() {
        final var conventions = List.of(
                NamingConvention.CAMEL_CASE,
                NamingConvention.PASCAL_CASE,
                NamingConvention.SNAKE_CASE,
                NamingConvention.SCREAMING_SNAKE_CASE,
                NamingConvention.CAMEL_SNAKE_CASE,
                NamingConvention.PASCAL_SNAKE_CASE,
                NamingConvention.KEBAB_CASE,
                NamingConvention.SCREAMING_KEBAB_CASE,
                NamingConvention.TRAIN_CASE
        );

        final var conventionStrings = List.of(
                "camelCase", "InvalidCamelCase", "invalid_Camel_Case",
                "PascalCase", "invalidPascalCase", "Invalid_Pascal_Case",
                "snake_case", "Invalid_Snake_Case", "invalidSnakeCase",
                "SCREAMING_SNAKE_CASE", "invalid_screaming_snake_case", "Invalid_Screaming_Snake_Case",
                "camel_Snake_Case", "Invalid_Camel_Snake_Case", "invalid_CAMEL_SNAKE_CASE",
                "Pascal_Snake_Case", "INVALID_PASCAL_SNAKE_CASE", "invalid_Pascal_Snake_Case",
                "kebab-case", "Invalid-Kebab-Case", "invalid_kebab_case",
                "SCREAMING-KEBAB-CASE", "invalid-Screaming-Kebab-Case", "INVALID_SCREAMING_KEBAB_CASE",
                "Train-Case", "Invalid_Train_Case", "INVALID-TRAIN-CASE"
        );

        final var conventionIterator = conventions.iterator();
        NamingConvention currentConvention = null;

        for (int i = 0; i < conventionStrings.size(); i++) {
            if (i % 3 == 0) {
                currentConvention = conventionIterator.next();
            }

            final var currentString = conventionStrings.get(i);
            final var doesFollow = currentConvention.doesFollow(currentString);

            if (i % 3 == 0) {
                Assertions.assertTrue(doesFollow, "\"" + currentString + "\" was evaluated to not follow " +
                        currentConvention.name() + " convention.");
            } else {
                Assertions.assertFalse(doesFollow, "\"" + currentString + "\" was evaluated to follow " +
                        currentConvention.name() + " convention.");
            }
        }
    }

}
