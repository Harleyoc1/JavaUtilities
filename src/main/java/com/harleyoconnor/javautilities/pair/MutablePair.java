package com.harleyoconnor.javautilities.pair;

/**
 * Implementation of {@link Pair} which allows for mutable keys and values -
 * meaning that the keys and values are non-final and can be mutated by the
 * use of {@link #setKey(Object)}, {@link #setValue(Object)}, and
 * {@link #setKeyValue(Object, Object)}.
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
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
     * Constructs an empty {@link ImmutablePair}, or in other words one in which
     * both the {@link #key} and {@link #value} are null.
     */
    public MutablePair() {}

    /**
     * Constructs a new {@link MutablePair} object with the given {@code initialKey}
     * and {@code initialValue} objects assigned to {@link #key} and {@link #value}.
     *
     * @param initialKey The initial {@link #key} to set.
     * @param initialValue The initial {@link #value} to set.
     */
    public MutablePair(final K initialKey, final V initialValue) {
        this.key = initialKey;
        this.value = initialValue;
    }

    /**
     * {@inheritDoc}
     *
     * @return The {@link #key} for this {@link MutablePair}.
     */
    @Override
    public K getKey () {
        return this.key;
    }

    /**
     * {@inheritDoc}
     *
     * @param key The new {@link #key} for this {@link MutablePair}.
     * @return This {@link MutablePair} for chaining.
     */
    @Override
    public MutablePair<K, V> setKey (final K key) {
        this.key = key;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The {@link #value} for this {@link MutablePair}.
     */
    @Override
    public V getValue () {
        return this.value;
    }

    /**
     * {@inheritDoc}
     *
     * @param value The new {@link #value} for this {@link MutablePair}.
     * @return This {@link MutablePair} for chaining.
     */
    @Override
    public MutablePair<K, V> setValue (final V value) {
        this.value = value;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param key The new {@link #key} for this {@link Pair}.
     * @param value The new {@link #value} for this {@link Pair}.
     * @return This {@link MutablePair} for chaining.
     */
    @Override
    public Pair<K, V> setKeyValue(final K key, final V value) {
        return this.setKey(key).setValue(value);
    }

}
