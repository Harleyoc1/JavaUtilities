package com.harleyoconnor.javautilities.pair;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Holds a {@code value} and its corresponding {@code key}.
 *
 * <p>Some implementations, such as the {@link ImmutablePair} will allow for
 * resetting the values via {@link #setKey(Object)}, {@link #setValue(Object)}, and {@link #setKeyValue(Object,
 * Object)}. However, the default implementation will throw {@link UnsupportedOperationException}, so be mindful of
 * using these methods.</p>
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 * @author Harley O'Connor
 * @see MutablePair
 * @see PartiallyMutablePair
 * @see ImmutablePair
 * @since JavaUtilities 0.0.7
 */
public interface Pair<K, V> {

    /**
     * Returns the key for this pair.
     *
     * @return the key for this pair
     */
    K getKey();

    /**
     * Sets the specified {@code key} to the key for this pair.
     *
     * @param key the new key
     * @return this pair, for chaining operations
     * @throws UnsupportedOperationException thrown if this pair implementation does not support mutating the key
     */
    default Pair<K, V> setKey(K key) {
        throw new UnsupportedOperationException(unsupportedMutation(this.getClass(), "key"));
    }

    /**
     * Returns the value for this pair.
     *
     * @return the value for this pair
     */
    V getValue();

    /**
     * Sets the specified {@code value} to the value for this pair.
     *
     * @param value the new value
     * @return this pair, for chaining operations
     * @throws UnsupportedOperationException thrown if this pair implementation does not support mutating the value
     */
    default Pair<K, V> setValue(V value) {
        throw new UnsupportedOperationException(unsupportedMutation(this.getClass(), "value"));
    }

    /**
     * Sets the specified {@code key} and {@code value} to the respective key and value for this pair.
     *
     * @param value the new value
     * @param key   the new key
     * @return this pair, for chaining operations
     * @throws UnsupportedOperationException thrown if this pair implementation does not support mutating the key or
     *                                       value
     */
    default Pair<K, V> setKeyValue(K key, V value) {
        throw new UnsupportedOperationException(unsupportedMutation(this.getClass(), "key and value"));
    }

    /**
     * Returns an {@link Exception} message to inform that the {@link Pair} implementation does not support mutation of
     * a certain field with the specified {@code fieldName}.
     *
     * @param pairClass the pair implementation
     * @param fieldName the name of the field that cannot be mutated
     * @param <P>       the type of the pair implementation
     * @return the {@code message} for the {@link Exception}
     */
    static <P extends Pair<?, ?>> String unsupportedMutation(final Class<P> pairClass, final String fieldName) {
        return "Pair implementation '" + pairClass.getName() + "' does not support resetting the " + fieldName + ".";
    }

    /**
     * Duplicates this pair, creating a new pair with this pair's {@code key} and {@code value}.
     *
     * @return the duplicated pair
     * @since JavaUtilities 0.0.9
     */
    Pair<K, V> duplicate();

    /**
     * Returns an immutable version of this pair.
     *
     * <p>Note that if this pair is already an {@link ImmutablePair} it will return itself. See {@link
     * #duplicate()} for creating a new pair from this one.</p>
     *
     * @return an immutable pari with this pair's {@code key} {@code value}.
     * @since JavaUtilities 0.0.9
     */
    default ImmutablePair<K, V> toImmutable() {
        return immutable(this.getKey(), this.getValue());
    }

    /**
     * Compares the specified {@code other} with this pair for equality. Returns {@code true} if the specified {@code
     * other} is also a pair, and the two have the same {@code key} and {@code value}.
     *
     * @param other the reference object with which to compare
     * @return {@code true} if this object is the same as the specified {@code other} object
     * @since JavaUtilities 0.1.0
     */
    boolean equals(@Nullable final Object other);

    /**
     * Returns the hash code value for this pair. Should be calculated by creating a hash from the key and value using
     * {@link Objects#hash(Object...)}.
     *
     * @return the hash code for this pair
     * @since JavaUtilities 0.1.0
     */
    int hashCode();

    /**
     * Returns the string representation of this pair. Generally, it will be represented in the form {@code key:value}.
     *
     * @return the string representation of this pair
     */
    String toString();

    /**
     * Checks if this pair is empty. View {@link EmptyPair} to see what constitutes an "empty" pair.
     *
     * <p>The default implementation of this uses {@link EmptyPair#equals(Object)}.</p>
     *
     * @return {@code true} if this pair is empty
     * @since JavaUtilities 0.1.0
     */
    default boolean isEmpty() {
        return EmptyPair.INSTANCE.equals(this);
    }

    /**
     * Creates a new {@link MutablePair} with no initial values (meaning it will be empty to start with).
     *
     * <p>This should <b>only</b> be used if the {@code key} and {@code value} will mutate, otherwise {@link #empty()}
     * should be used for better performance.</p>
     *
     * @param <K> the type of the key
     * @param <V> the type of the value
     * @return the new mutable pair
     */
    static <K, V> MutablePair<K, V> mutable() {
        return new MutablePair<>();
    }

    /**
     * Creates a new {@link MutablePair} with the given initial {@code key} and {@code value}.
     *
     * @param key   the initial key
     * @param value the initial value
     * @param <K>   the type of the key
     * @param <V>   the type of the value
     * @return the new mutable pair
     */
    static <K, V> MutablePair<K, V> mutable(final K key, final V value) {
        return new MutablePair<>(key, value);
    }

    /**
     * Creates a new {@link PartiallyMutablePair} with the specified {@code key} and a {@code null} value.
     *
     * @param key the key
     * @param <K> the type of the key
     * @param <V> the type of the value
     * @return the new partially mutable pair
     */
    static <K, V> PartiallyMutablePair<K, V> partiallyMutable(final K key) {
        return new PartiallyMutablePair<>(key);
    }

    /**
     * Creates a new {@link PartiallyMutablePair} with the specified {@code key} and initial {@code value}.
     *
     * @param key   the key
     * @param value the initial value
     * @param <K>   the type of the key
     * @param <V>   the type of the value
     * @return the new partially mutable pair
     */
    static <K, V> PartiallyMutablePair<K, V> partiallyMutable(final K key, final V value) {
        return new PartiallyMutablePair<>(key, value);
    }

    /**
     * Creates a new {@link ImmutablePair} with the specified {@code key} and {@code value}.
     *
     * @param key   the key
     * @param value the value
     * @param <K>   the type of the key
     * @param <V>   the type of the value
     * @return the new immutable pair
     */
    static <K, V> ImmutablePair<K, V> immutable(final K key, final V value) {
        return new ImmutablePair<>(key, value);
    }

    /**
     * Returns the {@linkplain EmptyPair#INSTANCE empty pair instance}. This is one in which no value is present for the
     * key and value (they evaluate to {@code null}. Note that the returned pair is the singular {@linkplain
     * EmptyPair#INSTANCE empty pair instance}, and as such cannot be mutated.
     *
     * @param <K> the type of the non-existent {@code key}
     * @param <V> the type of the non-existent {@code value}
     * @return the {@link EmptyPair#INSTANCE empty pair instance}
     * @since JavaUtilities 0.1.0
     */
    @SuppressWarnings("unchecked")
    static <K, V> EmptyPair<K, V> empty() {
        return ((EmptyPair<K, V>) EmptyPair.INSTANCE);
    }

}
