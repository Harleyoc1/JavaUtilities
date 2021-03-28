package com.harleyoconnor.javautilities.pair;

/**
 * Implementation of {@link Pair} in which the {@link #key} is {@code final} -
 * or in other words immutable - but the {@link #value} is non-final and can be
 * mutated using {@link #setValue(Object)}.
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 *
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
     * Constructs a new {@link PartiallyMutablePair} object with the given
     * {@code key} and no value (meaning it will be {@code null}).
     *
     * @param key The {@link #key} to set.
     */
    public PartiallyMutablePair(final K key) {
        this.key = key;
    }

    /**
     * Constructs a new {@link PartiallyMutablePair} object with the given
     * {@code key} and {@code initialValue} objects assigned to {@link #key} and
     * {@link #value}.
     *
     * @param key The {@link #key} to set.
     * @param initialValue The initial {@link #value} to set.
     */
    public PartiallyMutablePair(final K key, final V initialValue) {
        this.key = key;
        this.value = initialValue;
    }

    /**
     * {@inheritDoc}
     *
     * @return The {@link #key} for this {@link PartiallyMutablePair}.
     */
    @Override
    public K getKey() {
        return this.key;
    }

    /**
     * {@inheritDoc}
     *
     * @return The {@link #value} for this {@link PartiallyMutablePair}.
     */
    @Override
    public V getValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     *
     * @param value The new {@link #value} for this {@link PartiallyMutablePair}.
     * @return This {@link PartiallyMutablePair} for chaining.
     */
    @Override
    public Pair<K, V> setValue(final V value) {
        this.value = value;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The duplicated {@link PartiallyMutablePair}.
     */
    @Override
    public PartiallyMutablePair<K, V> duplicate() {
        return new PartiallyMutablePair<>(this.key, this.value);
    }

}
