package com.harleyoconnor.javautilities;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Holds {@code primitive} classes and their wrapper equivalents.
 *
 * <p>The main application of this is through the use of {@link #from(Class)}, providing a means to convert a wrapper
 * class to its primitive equivalent.</p>
 *
 * @author Harley O'Connor
 * @since JavaUtilities 0.1.0
 */
public enum Primitive {
    /**
     * Holds the primitive {@code boolean} class and its wrapper class, {@link Boolean}.
     */
    BOOLEAN(boolean.class, Boolean.class),
    /**
     * Holds the primitive {@code byte} class and its wrapper class, {@link Byte}.
     */
    BYTE(byte.class, Byte.class),
    /**
     * Holds the primitive {@code short} class and its wrapper class, {@link Short}.
     */
    SHORT(short.class, Short.class),
    /**
     * Holds the primitive {@code int} class and its wrapper class, {@link Integer}.
     */
    INT(int.class, Integer.class),
    /**
     * Holds the primitive {@code long} class and its wrapper class, {@link Long}.
     */
    LONG(long.class, Long.class),
    /**
     * Holds the primitive {@code float} class and its wrapper class, {@link Float}.
     */
    FLOAT(float.class, Float.class),
    /**
     * Holds the primitive {@code double} class and its wrapper class, {@link Double}.
     */
    DOUBLE(double.class, Double.class),
    /**
     * Holds the primitive {@code char} class and its wrapper class, {@link Character}.
     */
    CHAR(char.class, Character.class);

    private final Class<?> primitiveClass;
    private final Class<?> wrapperClass;

    /**
     * Constructs a new {@link Primitive} {@code enum} for the specified {@code primitiveClass} and its equivalent
     * {@code wrapperClass}.
     *
     * @param primitiveClass the primitive class
     * @param wrapperClass   the wrapper class
     */
    Primitive(final Class<?> primitiveClass, final Class<?> wrapperClass) {
        this.primitiveClass = primitiveClass;
        this.wrapperClass = wrapperClass;
    }

    /**
     * Returns the primitive class for this primitive.
     *
     * @return the primitive class for this primitive
     */
    public Class<?> getPrimitiveClass() {
        return this.primitiveClass;
    }

    /**
     * Returns the wrapper class for this primitive.
     *
     * @return the wrapper class for this primitive
     */
    public Class<?> getWrapperClass() {
        return this.wrapperClass;
    }

    /**
     * Returns an optional containing the primitive equivalent for the specified {@code wrapperClass}, if one exists.
     *
     * @param wrapperClass the wrapper class to get the equivalent primitive class for
     * @return an optional containing the primitive equivalent for the specified {@code wrapperClass}, or {@linkplain
     * Optional#empty() an empty optional} if one does not exist
     */
    public static Optional<Class<?>> from(final Class<?> wrapperClass) {
        return Stream.of(values())
                .filter(primitive -> primitive.wrapperClass == wrapperClass)
                .map(Primitive::getPrimitiveClass)
                .findFirst()
                .map(primitiveClass -> (Class<?>) primitiveClass);
    }

    /**
     * Returns the primitive equivalent for the specified {@code wrapperClass}, or itself if one doesn't exist.
     *
     * @param wrapperClass the wrapper class to get the equivalent primitive class for
     * @return the primitive equivalent for the specified {@code wrapperClass}, or itself if one doesn't exist
     */
    public static Class<?> fromOrSelf(final Class<?> wrapperClass) {
        return from(wrapperClass).orElse(wrapperClass);
    }

}
