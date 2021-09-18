package com.harleyoconnor.javautilities.pair;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Holds a {@code value} and its corresponding {@code key}.
 *
 * <p>Some implementations, such as the {@link ImmutablePair} will allow for
 * resetting the values via {@link #setKey(Object)}, {@link #setValue(Object)},
 * and {@link #setKeyValue(Object, Object)}. However, the default implementation
 * will throw {@link UnsupportedOperationException}, so be mindful of using
 * these methods.</p>
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 *
 * @author Harley O'Connor
 * @see MutablePair
 * @see PartiallyMutablePair
 * @see ImmutablePair
 * @since JavaUtilities 0.0.7
 */
public interface Pair<K, V> {

    /**
     * Returns the {@code key} for this {@link Pair}.
     *
     * @return The key for this {@link Pair}.
     */
    K getKey();

    /**
     * Sets the {@code key} for this {@link Pair}.
     *
     * @param key The new key for this {@link Pair}.
     * @return This {@link Pair} for chaining.
     * @throws UnsupportedOperationException thrown by default for classes
     *         that do not support this, but some implementations may
     *         provide it (like {@link MutablePair#setKey(Object)}).
     */
    default Pair<K, V> setKey(K key) {
        throw new UnsupportedOperationException(unsupportedMutation(this.getClass(), "key"));
    }

    /**
     * Returns the {@code value} for this {@link Pair}.
     *
     * @return The value for this {@link Pair}.
     */
    V getValue();

    /**
     * Sets the {@code value} for this {@link Pair}.
     *
     * @param value The new value for this {@link Pair}.
     * @return This {@link Pair} for chaining.
     * @throws UnsupportedOperationException thrown by default for classes
     *         that do not support this, but some implementations may
     *         provide it (like {@link MutablePair#setValue(Object)}).
     */
    default Pair<K, V> setValue(V value) {
        throw new UnsupportedOperationException(unsupportedMutation(this.getClass(), "value"));
    }

    /**
     * Sets the {@code key} and {@code value} for this {@link Pair}.
     *
     * @param value The new value for this {@link Pair}.
     * @param key The new key for this {@link Pair}.
     * @return This {@link Pair} for chaining.
     * @throws UnsupportedOperationException thrown by default for classes
     *         that do not support this, but some implementations may
     *         provide it (like {@link MutablePair#setKeyValue(Object, Object)}).
     */
    default Pair<K, V> setKeyValue(K key, V value) {
        throw new UnsupportedOperationException(unsupportedMutation(this.getClass(), "key and value"));
    }

    /**
     * Gets an {@link Exception} message to inform that the {@link Pair}
     * implementation does not support mutation of a {@code field}.
     *
     * @param pairClass The relevant {@link Pair} implementation.
     * @param nameOfField The name of the {@code field} that cannot be
     *                    mutated.
     * @param <P> The type of the {@link Pair} implementation.
     * @return The {@code message} for the {@link Exception}.
     */
    static <P extends Pair<?, ?>> String unsupportedMutation(final Class<P> pairClass, final String nameOfField) {
        return "Pair implementation '" + pairClass.getName() + "' does not support resetting the " + nameOfField + ".";
    }

    /**
     * Duplicates this {@link Pair}, creating a new {@link Pair}
     * object with this {@link Pair}'s {@code key} and {@code value}.
     *
     * @return The duplicated {@link Pair}.
     * @since JavaUtilities 0.0.9
     */
    Pair<K, V> duplicate();

    /**
     * Converts this {@link Pair} to an {@link ImmutablePair}.
     *
     * <p>Note that if this {@link Pair} is already an {@link ImmutablePair}
     * it will return itself. See {@link #duplicate()} for creating a new
     * {@link Pair} object from this one.</p>
     *
     * @return An {@link ImmutablePair} with this {@link Pair}'s {@code key}
     *         {@code value}.
     * @since JavaUtilities 0.0.9
     */
    default ImmutablePair<K, V> toImmutable() {
        return immutable(this.getKey(), this.getValue());
    }

    /**
     * Compares the specified {@code object} with this {@link Pair} for
     * equality.  Returns {@code true} if the specified {@code object} is
     * also a {@link Pair}, and the two have the same {@code key} and
     * {@code value}.
     *
     * @param object The {@link Object} to be compared for equality with
     *               this {@link Pair}.
     * @return {@code true} if the specified {@code object} is equal to
     *         this {@link Pair}.
     * @see Object#equals(Object)
     * @since JavaUtilities 0.1.0
     */
    boolean equals(@Nullable final Object object);

    /**
     * Returns the hash code value for this {@link Pair} object. More about
     * how hash codes work can be found in {@link Object#hashCode()}.
     *
     * @return The hash code for this {@link Pair} object.
     * @see Object#hashCode()
     * @see Objects#hash(Object...)
     * @since JavaUtilities 0.1.0
     */
    int hashCode();

    /**
     * Returns a {@link String} representation of the {@link Pair}. Generally,
     * a {@link Pair} will be represented in the form {@code key:value}.
     *
     * @return The {@link String} representation of this {@link Pair}.
     */
    String toString();

    /**
     * Indicates whether or not the given {@link Object} {@code obj} is
     * equal to this {@link Pair}.
     *
     * <p>Provides an alternative to {@link Object#equals(Object)} which is
     * not class sensitive - meaning this doesn't take into consideration
     * what implementation the other pair is using. For example, if this is
     * a {@link MutablePair} and is called with an {@link ImmutablePair},
     * {@code true} will be returned if both have the same key and value.</p>
     *
     * @param otherPair The reference {@link Pair} with which to compare.
     * @return {@code true} if this object is the same as the {@code obj}
     *         argument or had an equal key and value; {@code false}
     *         otherwise.
     * @deprecated Use {@link #equals(Object)} as it now does the same thing. This
     *             will be removed in {@code 0.0.11}.
     */
    @Deprecated
    default boolean isEqual(@Nullable final Pair<?, ?> otherPair) {
        if (this == otherPair)
            return true;
        if (otherPair == null)
            return false;

        return Objects.equals(this.getKey(), otherPair.getKey()) &&
                Objects.equals(this.getKey(), otherPair.getKey());
    }

    /**
     * Checks if this {@link Pair} is empty. View {@link EmptyPair} to
     * see what constitutes an "empty" {@link Pair}.
     *
     * <p>The default implementation of this uses
     * {@link EmptyPair#equals(Object)}.</p>
     *
     * @return {@code true} if this {@link Pair} is empty; {@code false}
     *         otherwise.
     * @since JavaUtilities 0.1.0
     */
    default boolean isEmpty() {
        return EmptyPair.INSTANCE.equals(this);
    }

    /**
     * Creates a new {@link MutablePair} with no initial values (meaning it
     * will be empty to start with).
     *
     * <p>This should <b>only</b> be used if the {@code key} and {@code value}
     * will mutate, otherwise {@link #empty()} should be used for better
     * performance.</p>
     *
     * @param <K> The type of the key.
     * @param <V> The type of the value.
     * @return The new {@link MutablePair} object.
     */
    static <K, V> MutablePair<K, V> mutable() {
        return new MutablePair<>();
    }

    /**
     * Creates a new {@link MutablePair} with the given initial {@code key}
     * and {@code value}.
     *
     * @param initialKey The initial key for the {@link ImmutablePair}.
     * @param initialValue The initial value for the {@link ImmutablePair}.
     * @param <K> The type of the key.
     * @param <V> The type of the value.
     * @return The new {@link MutablePair} object.
     */
    static <K, V> MutablePair<K, V> mutable(final K initialKey, final V initialValue) {
        return new MutablePair<>(initialKey, initialValue);
    }

    /**
     * Creates a new {@link PartiallyMutablePair} with {@code null} initial
     * values.
     *
     * @param key The key for the {@link PartiallyMutablePair}.
     * @param <K> The type of the key.
     * @param <V> The type of the value.
     * @return The new {@link PartiallyMutablePair} object.
     */
    static <K, V> PartiallyMutablePair<K, V> partiallyMutable(final K key) {
        return new PartiallyMutablePair<>(key);
    }

    /**
     * Creates a new {@link PartiallyMutablePair} with the given initial
     * {@code key} and {@code value}.
     *
     * @param key The key for the {@link PartiallyMutablePair}.
     * @param initialValue The initial value for the {@link PartiallyMutablePair}.
     * @param <K> The type of the key.
     * @param <V> The type of the value.
     * @return The new {@link PartiallyMutablePair} object.
     */
    static <K, V> PartiallyMutablePair<K, V> partiallyMutable(final K key, final V initialValue) {
        return new PartiallyMutablePair<>(key, initialValue);
    }

    /**
     * Creates a new {@link ImmutablePair} with the given {@code key}
     * and {@code value}.
     *
     * @param key The key for the {@link ImmutablePair}.
     * @param value The value for the {@link ImmutablePair}.
     * @param <K> The type of the key.
     * @param <V> The type of the value.
     * @return The new {@link ImmutablePair} object.
     */
    static <K, V> ImmutablePair<K, V> immutable(final K key, final V value) {
        return new ImmutablePair<>(key, value);
    }

    /**
     * Returns an empty {@link Pair} object. This is one in which no
     * value is present for the key and value (they evaluate to {@code null}.
     *
     * @param <K> The type of the non-existent {@code key}.
     * @param <V> The type of the non-existent {@code value}.
     * @return The {@link EmptyPair#INSTANCE} instance.
     * @since JavaUtilities 0.1.0
     */
    @SuppressWarnings("unchecked")
    static <K, V> EmptyPair<K, V> empty() {
        return ((EmptyPair<K, V>) EmptyPair.INSTANCE);
    }

}
