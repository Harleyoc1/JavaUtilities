package com.harleyoconnor.javautilities.collection;

import com.harleyoconnor.javautilities.reflect.Reflect;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.WeakHashMap;

/**
 * This class implements the {@link Set} interface, backed by a hash table with <i>weak keys</i> (actually a {@link
 * WeakHashMap} instance).  This means entries that are no longer in ordinary use will be removed.  It makes no
 * guarantees as to the iteration order of the set; in particular, it does not guarantee that the order will remain
 * constant over time.  This class permits the {@code null} element.
 * <p>
 * This {@link Class} is effectively a copy of {@link HashSet} but with the backing map being a {@link WeakHashMap}.
 * For more information on the details of this, view the Javadoc for {@link HashSet}.
 *
 * @param <E> the type of elements maintained by this set
 * @author Harley O'Connor
 * @see Collection
 * @see Set
 * @see TreeSet
 * @see HashMap
 * @see WeakHashMap
 * @see HashSet
 * @since JavaUtilities 0.1.0
 */
public final class WeakHashSet<E> extends AbstractSet<E> implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = -641790799725457127L;

    private transient WeakHashMap<E, Object> map;

    /**
     * Dummy value to associate with an Object in the backing Map.
     */
    private static final Object PRESENT = new Object();

    /**
     * Constructs a new, empty set; the backing {@link WeakHashMap} instance has default initial capacity {@code 16} and
     * load factor {@code 0.75}.
     */
    public WeakHashSet() {
        this.map = new WeakHashMap<>();
    }

    /**
     * Constructs a new set containing the elements in the specified collection.  The {@link WeakHashMap} is created
     * with default load factor {@code 0.75} and an initial capacity sufficient to contain the elements in the specified
     * collection.
     *
     * @param c the collection whose elements are to be placed into this set
     * @throws NullPointerException if the specified collection is null
     */
    public WeakHashSet(Collection<? extends E> c) {
        this.map = new WeakHashMap<>(Math.max((int) (c.size() / .75f) + 1, 16));
        this.addAll(c);
    }

    /**
     * Constructs a new, empty set; the backing {@link WeakHashMap} instance has the specified initial capacity and the
     * specified load factor.
     *
     * @param initialCapacity the initial capacity of the hash map
     * @param loadFactor      the load factor of the hash map
     * @throws IllegalArgumentException if the initial capacity is less than zero, or if the load factor is nonpositive
     */
    public WeakHashSet(int initialCapacity, float loadFactor) {
        this.map = new WeakHashMap<>(initialCapacity, loadFactor);
    }

    /**
     * Constructs a new, empty set; the backing {@link WeakHashMap} instance has the specified initial capacity and
     * default load factor {@code 0.75}.
     *
     * @param initialCapacity the initial capacity of the hash table
     * @throws IllegalArgumentException if the initial capacity is less than zero
     */
    public WeakHashSet(int initialCapacity) {
        this.map = new WeakHashMap<>(initialCapacity);
    }

    /**
     * Returns an iterator over the elements in this set.  The elements are returned in no particular order.
     *
     * @return An {@link Iterator} over the elements in this set.
     * @see ConcurrentModificationException
     */
    @NotNull
    @Override
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }

    /**
     * Returns the number of elements in this set (its cardinality).
     *
     * @return The number of elements in this set (its cardinality).
     */
    @Override
    public int size() {
        return this.map.size();
    }

    /**
     * Returns {@code true} if this set contains no elements.
     *
     * @return {@code true} if this set contains no elements; {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    /**
     * Returns {@code true} if this set contains the specified element. More formally, returns {@code true} if and only
     * if this set contains an element {@code e} such that {@code (object==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;object.equals(e))}.
     *
     * @param object An element whose presence in this set is to be tested.
     * @return {@code true} if this set contains the specified element; {@code false} otherwise.
     */
    @Override
    public boolean contains(Object object) {
        return this.map.containsKey(object);
    }

    /**
     * Adds the specified element to this set if it is not already present. More formally, adds the specified element
     * {@code element} to this set if this set contains no element {@code e2} such that {@code
     * element==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;element.equals(e2)}. If this set already contains the element, the
     * call leaves the set unchanged and returns {@code false}.
     *
     * @param element element to be added to this set
     * @return {@code true} if this set did not already contain the specified element; {@code false} otherwise.
     */
    @Override
    public boolean add(E element) {
        return this.map.put(element, PRESENT) == null;
    }

    /**
     * Removes the specified element from this set if it is present. More formally, removes an element {@code e} such
     * that {@code object == null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;object.equals(e)}, if this set contains such an
     * element.  Returns {@code true} if this set contained the element (or equivalently, if this set changed as a
     * result of the call).  (This set will not contain the element once the call returns.)
     *
     * @param object The object to be removed from this set, if present.
     * @return {@code true} if the set contained the specified element; {@code false} otherwise.
     */
    @Override
    public boolean remove(Object object) {
        return this.map.remove(object) == PRESENT;
    }

    /**
     * Removes all of the elements from this set. The set will be empty after this call returns.
     */
    @Override
    public void clear() {
        this.map.clear();
    }

    /**
     * Returns a shallow copy of this {@link WeakHashSet} instance: the elements themselves are not cloned.
     *
     * @return A shallow copy of this set.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object clone() {
        try {
            final WeakHashSet<E> newSet = (WeakHashSet<E>) super.clone();
            newSet.map = Reflect.uncheckedOn(this.map).invoke("clone");
            return newSet;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Save the state of this {@link WeakHashSet} instance to a stream (that is, serialize it).
     *
     * @param s The stream to serialise to.
     * @throws IOException If an I/O error occurs.
     * @serialData The capacity of the backing {@link WeakHashMap} instance (int), and its load factor (float) are
     * emitted, followed by the size of the set (the number of elements it contains) (int), followed by all of its
     * elements (each an Object) in no particular order.
     */
    @Serial
    private void writeObject(ObjectOutputStream s)
            throws IOException {
        // Write out any hidden serialization magic.
        s.defaultWriteObject();

        // Write out HashMap capacity and load factor.
        s.writeInt(this.getMapCapacity());
        s.writeFloat(Reflect.uncheckedOn(this.map).getFieldValue("loadFactor"));

        // Write out size.
        s.writeInt(map.size());

        // Write out all elements in the proper order.
        for (final E e : map.keySet()) {
            s.writeObject(e);
        }
    }

    /**
     * Gets the capacity of the backing {@link WeakHashMap}.
     *
     * @return The capacity of the backing {@link WeakHashMap}.
     */
    private int getMapCapacity() {
        final Map.Entry<E, Object>[] table = Reflect.uncheckedOn(this.map).getFieldValue("table");
        final int threshold = Reflect.uncheckedOn(this.map).getFieldValue("threshold");

        return (table != null) ? table.length : (threshold > 0) ? threshold :
                Reflect.uncheckedOn(WeakHashMap.class).getFieldValue("DEFAULT_INITIAL_CAPACITY");
    }

    /**
     * Reconstitute the {@link WeakHashSet} instance from a stream (that is, deserialize it).
     *
     * @param s The stream to deserialise from.
     * @throws IOException            If an I/O error occurs.
     * @throws ClassNotFoundException If the class of the serialised object could not be found.
     */
    @Serial
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        // Read in any hidden serialization magic.
        s.defaultReadObject();

        // Read capacity and verify non-negative.
        int capacity = s.readInt();
        if (capacity < 0) {
            throw new InvalidObjectException("Illegal capacity: " +
                    capacity);
        }

        // Read load factor and verify positive and non NaN.
        float loadFactor = s.readFloat();
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new InvalidObjectException("Illegal load factor: " +
                    loadFactor);
        }

        // Read size and verify non-negative.
        int size = s.readInt();
        if (size < 0) {
            throw new InvalidObjectException("Illegal size: " +
                    size);
        }
        // Set the capacity according to the size and load factor ensuring that
        // the HashMap is at least 25% full but clamping to maximum capacity.
        capacity = (int) Math.min(size * Math.min(1 / loadFactor, 4.0f),
                Reflect.uncheckedOn(WeakHashMap.class).getFieldValue("MAXIMUM_CAPACITY", int.class));

        // Constructing the backing map will lazily create an array when the first element is
        // added, so check it before construction. Call HashMap.tableSizeFor to compute the
        // actual allocation size. Check Map.Entry[].class since it's the nearest public type to
        // what is actually created.

        Reflect.uncheckedOn(s).invoke("checkArray", Map.Entry[].class,
                Reflect.uncheckedOn(HashMap.class).invoke("tableSizeFor", capacity));

        // Create backing HashMap.
        this.map = new WeakHashMap<>(capacity, loadFactor);

        // Read in all elements in the proper order.
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked") final E e = (E) s.readObject();
            this.map.put(e, PRESENT);
        }
    }

    /**
     * Creates a <em>late-binding</em> and <em>fail-fast</em> {@link Spliterator} over the elements in this set.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#SIZED} and
     * {@link Spliterator#DISTINCT}.  Overriding implementations should document the reporting of additional
     * characteristic values.</p>
     *
     * @return A {@link Spliterator} over the elements in this set.
     */
    @Override
    public Spliterator<E> spliterator() {
        return Reflect.uncheckedOn(WeakHashMap.class).reflectInnerClass("KeySpliterator").unchecked()
                .instantiateInferred(this.map, 0, -1, 0, 0);
    }

}
