package com.harleyoconnor.javautilities.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Harley O'Connor
 */
public final class ArrayUtils {

    /**
     * Converts a primitive array of characters to a list.
     *
     * @param charArray The primitive array of characters.
     * @return The list of characters.
     */
    public static List<Character> toCharList (final char[] charArray) {
        final List<Character> characterList = new ArrayList<>();

        for (char character : charArray)
            characterList.add(character);

        return characterList;
    }

}
