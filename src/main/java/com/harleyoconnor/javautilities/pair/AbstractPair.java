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
     * {@inheritDoc}
     *
     * @param object The reference object with which to compare.
     * @return {@code true} if this object is the same as the {@code obj}
     *         argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(@Nullable final Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Pair))
            return false;

        final Pair<?, ?> otherPair = (Pair<?, ?>) object;
        return Objects.equals(this.getKey(), otherPair.getKey()) &&
                Objects.equals(this.getKey(), otherPair.getKey());
    }

    /**
     * {@inheritDoc}
     *
     * @return The hash code for this {@link Pair} object.
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
     * @return The {@link String} representation of this {@link Pair}.
     */
    @Override
    public String toString() {
        return this.getKey() + ":" + this.getValue();
    }

}
