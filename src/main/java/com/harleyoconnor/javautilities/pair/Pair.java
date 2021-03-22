package com.harleyoconnor.javautilities.pair;

import javax.annotation.Nullable;
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
     * @throws UnsupportedOperationException thrown by default for classes that do not support this,
     *         but some implementations, may provide it (like {@link MutablePair#setKey(Object)}).
     */
    default Pair<K, V> setKey(K key) {
        throw settingFieldUnsupported(this.getClass(), "key");
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
     * @throws UnsupportedOperationException thrown by default for classes that do not support this,
     *         but some implementations, may provide it (like {@link MutablePair#setValue(Object)}).
     */
    default Pair<K, V> setValue(V value) {
        throw settingFieldUnsupported(this.getClass(), "value");
    }

    /**
     * Sets the {@code key} and {@code value} for this {@link Pair}.
     *
     * @param value The new value for this {@link Pair}.
     * @param key The new key for this {@link Pair}.
     * @return This {@link Pair} for chaining.
     * @throws UnsupportedOperationException thrown by default for classes that do not support this,
     *         but some implementations, may provide it (like {@link MutablePair#setKeyValue(Object, Object)}).
     */
    default Pair<K, V> setKeyValue(K key, V value) {
        throw settingFieldUnsupported(this.getClass(), "key and value");
    }

    static <P extends Pair<?, ?>> UnsupportedOperationException settingFieldUnsupported(final Class<P> pairClass, final String nameOfField) {
        return new UnsupportedOperationException("Pair implementation '" + pairClass.getSimpleName() + "' does not support resetting the " + nameOfField + ".");
    }

    /**
     * Indicates whether or not the given {@link Object} {@code obj} is equal to
     * this {@link Pair}.
     *
     * <p>Provides an alternative to {@link Object#equals(Object)} which is not class
     * sensitive - meaning this doesn't take into consideration what implementation
     * the other pair is using. For example, if this is a {@link MutablePair} and is
     * called with an {@link ImmutablePair}, {@code true} will be returned if both
     * have the same key and value.</p>
     *
     * @param otherPair The reference {@link Pair} with which to compare.
     * @return {@code true} if this object is the same as the {@code obj}
     *         argument or had an equal key and value; {@code false}
     *         otherwise.
     */
    default boolean isEqual(@Nullable final Pair<?, ?> otherPair) {
        if (this == otherPair)
            return true;
        if (otherPair == null)
            return false;

        return Objects.equals(this.getKey(), otherPair.getKey()) && Objects.equals(this.getKey(), otherPair.getKey());
    }

    /**
     * Creates a new {@link MutablePair} with {@code null} initial values.
     *
     * @param <K> The type of the key.
     * @param <V> The type of the value.
     * @return The new {@link MutablePair} object.
     */
    static <K, V> MutablePair<K, V> mutable() {
        return new MutablePair<>();
    }

    /**
     * Creates a new {@link MutablePair} with the given initial {@code key} and {@code value}.
     *
     * @param <K> The type of the key.
     * @param <V> The type of the value.
     * @param initialKey The initial key for the {@link ImmutablePair}.
     * @param initialValue The initial value for the {@link ImmutablePair}.
     * @return The new {@link MutablePair} object.
     */
    static <K, V> MutablePair<K, V> mutable(final K initialKey, final V initialValue) {
        return new MutablePair<>(initialKey, initialValue);
    }

    /**
     * Creates a new {@link PartiallyMutablePair} with {@code null} initial values.
     *
     * @param <K> The type of the key.
     * @param <V> The type of the value.
     * @param key The key for the {@link PartiallyMutablePair}.
     * @return The new {@link PartiallyMutablePair} object.
     */
    static <K, V> PartiallyMutablePair<K, V> partiallyMutable(final K key) {
        return new PartiallyMutablePair<>(key);
    }

    /**
     * Creates a new {@link PartiallyMutablePair} with the given initial {@code key} and {@code value}.
     *
     * @param <K> The type of the key.
     * @param <V> The type of the value.
     * @param key The key for the {@link PartiallyMutablePair}.
     * @param initialValue The initial value for the {@link PartiallyMutablePair}.
     * @return The new {@link PartiallyMutablePair} object.
     */
    static <K, V> PartiallyMutablePair<K, V> partiallyMutable(final K key, final V initialValue) {
        return new PartiallyMutablePair<>(key, initialValue);
    }

    /**
     * Creates a new {@link ImmutablePair} with the given {@code key} and {@code value}.
     *
     * @param <K> The type of the key.
     * @param <V> The type of the value.
     * @param key The key for the {@link ImmutablePair}.
     * @param value The value for the {@link ImmutablePair}.
     * @return The new {@link ImmutablePair} object.
     */
    static <K, V> ImmutablePair<K, V> immutable(final K key, final V value) {
        return new ImmutablePair<>(key, value);
    }

}
