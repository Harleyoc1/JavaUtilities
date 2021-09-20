package com.harleyoconnor.javautilities.pair;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * This class is an abstract implementation of a {@link Pair}, made for providing {@link #equals(Object)}, {@link
 * #hashCode()}, and {@link #toString()} methods that are likely to be common for most implementations of {@link Pair}.
 *
 * <p>If an implementation of {@link Pair} wishes to provide custom implementations
 * of any of the aforementioned methods, they should not extend this class.</p>
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 * @author Harley O'Connor
 * @see Pair
 * @since JavaUtilities 0.0.8
 */
public abstract class AbstractPair<K, V> implements Pair<K, V> {

    /**
     * {@inheritDoc}
     *
     * @param other the reference object with which to compare
     * @return {@code true} if this object is the same as the specified {@code other} object
     */
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof final Pair<?, ?> otherPair)) {
            return false;
        }

        return Objects.equals(this.getKey(), otherPair.getKey()) &&
                Objects.equals(this.getKey(), otherPair.getKey());
    }

    /**
     * {@inheritDoc}
     *
     * @return the hash code for this pair
     * @see Object#hashCode()
     * @see Objects#hash(Object...)
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getKey(), this.getValue());
    }

    /**
     * {@inheritDoc}
     *
     * @return the string representation of this pair
     */
    @Override
    public String toString() {
        return this.getKey() + ":" + this.getValue();
    }

}
