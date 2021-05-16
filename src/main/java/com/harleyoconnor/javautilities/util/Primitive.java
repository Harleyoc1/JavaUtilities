package com.harleyoconnor.javautilities.util;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Holds {@code primitive} classes and their {@code wrapper} equivalents.
 *
 * <p>The main application of this is through the use of {@link #from(Class)},
 * providing a means to convert a wrapper {@link Class} to its {@code primitive}
 * equivalent.</p>
 *
 * @author Harley O'Connor
 * @since JavaUtilities 0.1.0
 */
public enum Primitive {
    BOOLEAN(boolean.class, Boolean.class),
    BYTE(byte.class, Byte.class),
    SHORT(short.class, Short.class),
    INT(int.class, Integer.class),
    LONG(long.class, Long.class),
    FLOAT(float.class, Float.class),
    DOUBLE(double.class, Double.class),
    CHAR(char.class, Character.class);

    private final Class<?> primitiveClass;
    private final Class<?> wrapperClass;

    /**
     * Constructs a new {@link Primitive} {@code enum} for the specified
     * {@code primitive} {@link Class} and its equivalent {@code wrapper}
     * {@link Class}.
     *
     * @param primitiveClass The {@code primitive} {@link Class}.
     * @param wrapperClass The {@code wrapper} {@link Class}.
     */
    Primitive(final Class<?> primitiveClass, final Class<?> wrapperClass) {
        this.primitiveClass = primitiveClass;
        this.wrapperClass = wrapperClass;
    }

    /**
     * Gets the {@link #primitiveClass} for this {@link Primitive}.
     *
     * @return The {@link #primitiveClass} for this {@link Primitive}.
     */
    public Class<?> getPrimitiveClass() {
        return this.primitiveClass;
    }

    /**
     * Gets the {@link #wrapperClass} for this {@link Primitive}.
     *
     * @return The {@link #wrapperClass} for this {@link Primitive}.
     */
    public Class<?> getWrapperClass() {
        return this.wrapperClass;
    }

    /**
     * Returns an {@link Optional} containing the {@code primitive}
     * {@link Class} equivalent for the specified {@code wrapper}
     * {@link Class}, if it exists.
     *
     * @param wrapperClass The {@code wrapper} {@link Class}.
     * @return An {@link Optional} containing the {@code primitive}
     *         {@link Class}, or an {@link Optional#empty()} if one
     *         didn't exist.
     */
    public static Optional<Class<?>> from(final Class<?> wrapperClass) {
        return Stream.of(values()).filter(primitive -> primitive.wrapperClass == wrapperClass)
                .map(Primitive::getPrimitiveClass).findFirst().map(primitiveClass -> (Class<?>) primitiveClass);
    }

    /**
     * Returns the {@code primitive} {@link Class} equivalent for the specified
     * {@link Class}, or itself if one didn't exist.
     *
     * @param wrapperClass The {@code wrapper} {@link Class}.
     * @return The {@code primitive} {@link Class} equivalent, or the specified
     *         {@link Class} if one didn't exist.
     */
    public static Class<?> fromOrSelf(final Class<?> wrapperClass) {
        return from(wrapperClass).orElse(wrapperClass);
    }

}
