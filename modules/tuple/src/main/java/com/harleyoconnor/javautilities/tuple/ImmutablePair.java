package com.harleyoconnor.javautilities.tuple;

/**
 * Implementation of {@link Pair} with both the {@link #key} and {@link #value} and being {@code final} - or in other
 * words immutable.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
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
     * Constructs a new {@link ImmutablePair} object with the given {@code key} and {@code value} objects assigned to
     * {@link #key} and {@link #value}.
     *
     * @param key   the {@link #key} to set
     * @param value the {@link #value} to set
     */
    public ImmutablePair(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     *
     * @return the key for this pair
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     *
     * @return the value for this pair
     */
    @Override
    public V getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     *
     * @return the duplicated pair
     */
    @Override
    public ImmutablePair<K, V> duplicate() {
        return new ImmutablePair<>(this.key, this.value);
    }

    /**
     * Returns this pair, as it is already immutable.
     *
     * @return this pair
     */
    @Override
    public ImmutablePair<K, V> toImmutable() {
        return this;
    }

}
