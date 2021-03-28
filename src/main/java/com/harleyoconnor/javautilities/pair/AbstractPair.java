package com.harleyoconnor.javautilities.pair;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * This class is an abstract implementation of a {@link Pair}, made for providing
 * {@link #equals(Object)}, {@link #hashCode()}, and {@link #toString()} methods
 * that are likely to be common for most implementations of {@link Pair}.
 *
 * <p>If an implementation of {@link Pair} wishes to provide custom implementations
 * of any of the aforementioned methods, they should not extend this class.</p>
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 *
 * @author Harley O'Connor
 * @see Pair
 * @since JavaUtilities 0.0.8
 */
public abstract class AbstractPair<K, V> implements Pair<K, V> {

    /**
     * Indicates whether or not the given {@link Object} {@code obj} is equal to
     * this {@link Pair}.
     *
     * <p>Note that two pairs will not be equal if they are of different classes (such
     * as an {@link ImmutablePair} and a {@link MutablePair}) even if they have the same
     * key and value. For an equals that is not 'class sensitive', use
     * {@link #isEqual(Pair)}</p>
     *
     * @param obj The reference object with which to compare.
     * @return {@code true} if this object is the same as the {@code obj}
     *         argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(@Nullable final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        final Pair<?, ?> otherPair = (Pair<?, ?>) obj;
        return Objects.equals(this.getKey(), otherPair.getKey()) && Objects.equals(this.getKey(), otherPair.getKey());
    }

    /**
     * Returns the hash code for this {@link Pair}. This is done by passing the {@code key}
     * and {@code value} to {@link Objects#hash(Object...)} - more details can be found
     * there.
     *
     * @return The hash code for this {@link Pair}.
     * @see Objects#hash(Object...)
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getKey(), this.getValue());
    }

    /**
     * Converts this {@link Pair} object to a {@link String}, with both the {@code key}
     * being added first, a colon, and then the {@code value}.
     *
     * @return The {@link Pair} as a {@link String}.
     */
    @Override
    public String toString() {
        return this.getKey() + ":" + this.getValue();
    }

}
