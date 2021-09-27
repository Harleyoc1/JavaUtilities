//package com.harleyoconnor.javautilities.convention;
//
//import com.harleyoconnor.javautilities.util.Primitive;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
///**
// * @author Harley O'Connor
// */
//public final class NamingConventionTests {
//
//    @Test
//    public void testDoesFollow() {
//        final var conventions = List.of(
//                NamingConvention.CAMEL_CASE,
//                NamingConvention.PASCAL_CASE,
//                NamingConvention.SNAKE_CASE,
//                NamingConvention.SCREAMING_SNAKE_CASE,
//                NamingConvention.CAMEL_SNAKE_CASE,
//                NamingConvention.PASCAL_SNAKE_CASE,
//                NamingConvention.KEBAB_CASE,
//                NamingConvention.SCREAMING_KEBAB_CASE,
//                NamingConvention.TRAIN_CASE
//        );
//        Primitive.from(null);
//
//        final var conventionStrings = List.of(
//                "camelCase", "InvalidCamelCase", "invalid_Camel_Case",
//                "PascalCase", "invalidPascalCase", "Invalid_Pascal_Case",
//                "snake_case", "Invalid_Snake_Case", "invalidSnakeCase",
//                "SCREAMING_SNAKE_CASE", "invalid_screaming_snake_case", "Invalid_Screaming_Snake_Case",
//                "Pascal_Snake_Case", "INVALID_PASCAL_SNAKE_CASE", "invalid_Pascal_Snake_Case",
//                "kebab-case", "Invalid-Kebab-Case", "invalid_kebab_case",
//                "SCREAMING-KEBAB-CASE", "invalid-Screaming-Kebab-Case", "INVALID_SCREAMING_KEBAB_CASE",
//                "Train-Case", "Invalid_Train_Case", "INVALID-TRAIN-CASE"
//        );
//
//        final var conventionIterator = conventions.iterator();
//        NamingConvention currentConvention = null;
//
//        for (int i = 0; i < conventionStrings.size(); i++) {
//            if (i % 3 == 0) {
//                currentConvention = conventionIterator.next();
//                System.out.println("\nTesting Convention " + currentConvention.name() + ":");
//            }
//
//            final var currentString = conventionStrings.get(i);
//            final var doesFollow = currentConvention.doesFollow(conventionStrings.get(i));
//
//            if (i % 3 == 0) {
//                if (!doesFollow) {
//                    throw new RuntimeException("Test Failed: \"" + currentString + "\" was evaluated to not follow " +
//                            currentConvention.name() + " convention.");
//                }
//            } else if (doesFollow) {
//                throw new RuntimeException(
//                        "Test Failed: \"" + currentString + "\" was evaluated to follow " + currentConvention.name() +
//                                " convention.");
//            }
//
//            System.out.println("String: " + currentString + " Does Follow: " + doesFollow);
//        }
//    }
//
//}
