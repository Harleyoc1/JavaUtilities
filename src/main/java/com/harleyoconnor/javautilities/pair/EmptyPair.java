package com.harleyoconnor.javautilities.pair;

/**
 * An empty {@link Pair}. A {@link Pair} is "empty" if both its key and
 * value return {@code null}.
 *
 * <p>This can be instantiated, but it's recommended that {@link #INSTANCE}
 * is used for better performance. This can be obtained from
 * {@link Pair#empty()}.</p>
 *
 * <p>{@link Pair#isEmpty()} should be used to check if a {@link Pair} is
 * empty, since it will check the key and value of any {@link Pair}
 * implementation - since any {@link Pair} may be considered "empty" if it
 * does not have a key and value.</p>
 *
 * @author Harley O'Connor
 * @see Pair
 * @see Pair#empty()
 * @since JavaUtilities 0.0.10
 */
public final class EmptyPair<K, V> implements Pair<K, V> {

    /** The main {@link EmptyPair} instance. Can be accessed from {@link Pair#empty()}. */
    static final Pair<?, ?> INSTANCE = new EmptyPair<>();

    /**
     * {@inheritDoc} This is an {@link EmptyPair}, so this will only
     * ever be {@code null}.
     *
     * @return {@code null}
     */
    @Override
    public K getKey() {
        return null;
    }

    /**
     * {@inheritDoc} This is an {@link EmptyPair}, so this will only
     * ever be {@code null}.
     *
     * @return {@code null}
     */
    @Override
    public V getValue() {
        return null;
    }

    /**
     * Returns this {@link EmptyPair} instance, since in most cases
     * an {@link EmptyPair} wouldn't need to be duplicated.
     *
     * @return This {@link EmptyPair} instance.
     */
    @Override
    public EmptyPair<K, V> duplicate() {
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Since this is an {@link EmptyPair}, this implementation will
     * only return {@code true} if the specified {@code object} is a
     * {@link Pair} and also has both a {@code key} and {@code value}
     * which equate to {@code null}.</p>
     *
     * @param object The {@link Object} to be compared for equality with
     *               this {@link EmptyPair}.
     * @return {@code true} if the specified {@code object} is a
     *         {@link Pair} and is empty; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof EmptyPair)
            return true;
        if (!(object instanceof Pair))
            return false;

        final Pair<?, ?> pair = ((Pair<?, ?>) object);
        return pair.getKey() == null && pair.getValue() == null;
    }

}
