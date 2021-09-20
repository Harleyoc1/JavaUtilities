package com.harleyoconnor.javautilities.pair;

/**
 * An empty {@link Pair}. A {@link Pair} is "empty" if both its key and value return {@code null}.
 *
 * <p>This can be instantiated, but it's recommended that {@link #INSTANCE}
 * is used for better performance. This can be obtained from {@link Pair#empty()}.</p>
 *
 * <p>{@link Pair#isEmpty()} should be used to check if a {@link Pair} is
 * empty, since it will check the key and value of any {@link Pair} implementation - since any {@link Pair} may be
 * considered "empty" if it does not have a key and value.</p>
 *
 * @author Harley O'Connor
 * @see Pair
 * @see Pair#empty()
 * @since JavaUtilities 0.1.0
 */
public final class EmptyPair<K, V> implements Pair<K, V> {

    /**
     * The main {@link EmptyPair} instance. Can be accessed from {@link Pair#empty()}.
     */
    static final Pair<?, ?> INSTANCE = new EmptyPair<>();

    /**
     * {@inheritDoc} This is an empty pair, so this will only ever be {@code null}.
     *
     * @return {@code null}
     */
    @Override
    public K getKey() {
        return null;
    }

    /**
     * {@inheritDoc} This is an empty pair, so this will only ever be {@code null}.
     *
     * @return {@code null}
     */
    @Override
    public V getValue() {
        return null;
    }

    /**
     * Returns this empty pair, since an empty pair is a immutable and so shouldn't need to be duplicated.
     *
     * @return this empty pair
     */
    @Override
    public EmptyPair<K, V> duplicate() {
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Since this is an {@link EmptyPair}, this implementation will only return {@code true} if the specified {@code
     * other} is a {@link Pair} and also has both a key and value which equate to {@code null}.
     *
     * @param other the reference object with which to compare
     * @return {@code true} if this object is the same as the specified {@code other} object
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof EmptyPair) {
            return true;
        }
        if (!(other instanceof final Pair<?, ?> pair)) {
            return false;
        }

        return pair.getKey() == null && pair.getValue() == null;
    }

}
