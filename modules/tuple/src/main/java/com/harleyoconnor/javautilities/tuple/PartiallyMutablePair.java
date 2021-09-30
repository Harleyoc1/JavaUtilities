package com.harleyoconnor.javautilities.tuple;

/**
 * Implementation of {@link Pair} in which the key is final (or in other words immutable) but the
 * {@link #value} is non-final and can be mutated using {@link #setValue(Object)}.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 * @author Harley O'Connor
 * @see Pair
 * @see ImmutablePair
 * @see MutablePair
 * @since JavaUtilities 0.0.8
 */
public final class PartiallyMutablePair<K, V> extends AbstractPair<K, V> {

    private final K key;
    private V value;

    /**
     * Constructs a new {@link PartiallyMutablePair} object with the specified {@code key} and a {@code null} value.
     *
     * @param key the key
     */
    public PartiallyMutablePair(final K key) {
        this.key = key;
    }

    /**
     * Constructs a new {@link PartiallyMutablePair} object with the specified {@code key} and {@code value} objects
     * assigned to {@link #key} and {@link #value} respectively.
     *
     * @param key   the key
     * @param value the initial value
     */
    public PartiallyMutablePair(final K key, final V value) {
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
        return this.key;
    }

    /**
     * {@inheritDoc}
     *
     * @return the value for this pair
     */
    @Override
    public V getValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     *
     * @param value the new value
     * @return this pair, for chaining operations
     */
    @Override
    public Pair<K, V> setValue(final V value) {
        this.value = value;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return the duplicated pair
     */
    @Override
    public PartiallyMutablePair<K, V> duplicate() {
        return new PartiallyMutablePair<>(this.key, this.value);
    }

}
