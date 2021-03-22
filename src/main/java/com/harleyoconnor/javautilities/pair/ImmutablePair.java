package com.harleyoconnor.javautilities.pair;

/**
 * Implementation of {@link Pair} with both the {@link #key} and {@link #value}
 * and being {@code final} - or in other words immutable.
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 * @author Harley O'Connor
 * @see Pair
 * @see MutablePair
 * @see PartiallyMutablePair
 * @since JavaUtilities 0.0.7
 */
public final class ImmutablePair<K, V> extends AbstractPair<K, V> {

    private final K key;
    private final V value;

    /**
     * Constructs a new {@link ImmutablePair} object with the given {@code key}
     * and {@code value} objects assigned to {@link #key} and {@link #value}.
     *
     * @param key The {@link #key} to set.
     * @param value The {@link #value} to set.
     */
    public ImmutablePair(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     *
     * @return The {@link #key} for this {@link ImmutablePair}.
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     *
     * @return The {@link #value} for this {@link ImmutablePair}.
     */
    @Override
    public V getValue() {
        return value;
    }

}
