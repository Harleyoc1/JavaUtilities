package com.harleyoconnor.javautilities.tuple;

/**
 * Implementation of {@link Pair} which allows for mutable keys and values - meaning that the keys and values are
 * non-final and can be mutated by the use of {@link #setKey(Object)}, {@link #setValue(Object)}, and {@link
 * #setKeyValue(Object, Object)}.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 * @author Harley O'Connor
 * @see Pair
 * @see PartiallyMutablePair
 * @see ImmutablePair
 * @since JavaUtilities 0.0.7
 */
public final class MutablePair<K, V> extends AbstractPair<K, V> {

    private K key;
    private V value;

    /**
     * Constructs an empty {@link MutablePair}, or in other words one in which both the key and value are {@code null}.
     */
    public MutablePair() {
    }

    /**
     * Constructs a new {@link MutablePair} object with the specified {@code key} and {@code value} objects
     * assigned to {@link #key} and {@link #value} respectively.
     *
     * @param key   the initial key
     * @param value the initial value
     */
    public MutablePair(final K key, final V value) {
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
     * @param key the new key
     * @return this pair, for chaining operations
     */
    @Override
    public MutablePair<K, V> setKey(final K key) {
        this.key = key;
        return this;
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
    public MutablePair<K, V> setValue(final V value) {
        this.value = value;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param value the new value
     * @param key   the new key
     * @return this pair, for chaining operations
     */
    @Override
    public Pair<K, V> setKeyValue(final K key, final V value) {
        return this.setKey(key).setValue(value);
    }

    /**
     * {@inheritDoc}
     *
     * @return the duplicated pair
     */
    @Override
    public MutablePair<K, V> duplicate() {
        return new MutablePair<>(this.key, this.value);
    }

}
