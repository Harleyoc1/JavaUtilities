package com.harleyoconnor.javautilities.reflect;

import java.io.Serial;

/**
 * Thrown when a particular inner class or interface cannot be found.
 *
 * @author Harley O'Connor
 * @since JavaUtilities 0.1.0
 */
public final class NoSuchInnerClassException extends ReflectiveOperationException {

    @Serial
    private static final long serialVersionUID = 3719968328942105078L;

    /**
     * Constructs a {@link NoSuchInnerClassException} without a detail message.
     */
    public NoSuchInnerClassException() {
    }

    /**
     * Constructs a {@link NoSuchInnerClassException} with a detail message.
     *
     * @param message the detail message
     */
    public NoSuchInnerClassException(String message) {
        super(message);
    }

}
