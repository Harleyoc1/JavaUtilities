package com.harleyoconnor.javautilities.reflect;

import com.harleyoconnor.javautilities.collection.WeakHashSet;
import com.harleyoconnor.javautilities.function.ThrowableSupplier;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * An unchecked implementation of {@link Reflect}, using another {@link Reflect} as a backing and catching any
 * {@link Throwable}s, throwing a runtime exception instead.
 *
 * @param <T> the type of the class being reflected on
 * @author Harley O'Connor
 */
public final class ReflectUnchecked<T> implements Reflect<T> {

    /**
     * The object to delegate method calls to.
     */
    private final Reflect<T> delegate;

    /**
     * Constructs a new {@link ReflectUnchecked} instance with the specified {@link ReflectChecked}.
     *
     * @param delegate the {@link Reflect} to delegate to
     */
    ReflectUnchecked(@NotNull Reflect<T> delegate) {
        this.delegate = delegate;
    }

    /**
     * Returns {@code this}, since it's already a {@link ReflectUnchecked}.
     *
     * @return {@code this}
     */
    @Override
    public ReflectUnchecked<T> unchecked() {
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param name the name of the member field
     * @return the {@link Field} object reflecting the member field
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getField(String)} on
     *                          the delegate
     */
    @Override
    public Field getField(final String name) {
        return getUnchecked(() -> this.delegate.getField(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param name the name of the member field
     * @return the {@link Field} reflecting the member field
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getFieldAccessible(String)}
     *                          (String)} on the delegate
     */
    @Override
    public Field getFieldAccessible(final String name) {
        return getUnchecked(() -> this.delegate.getFieldAccessible(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param field the {@link Field}; reflects the member field to get the value of
     * @param <V>   the inferred type of the field
     * @return the value of the field
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getFieldValue(Field)}
     *                          on the delegate
     */
    @Override
    public <V> V getFieldValue(final Field field) {
        return getUnchecked(() -> this.delegate.getFieldValue(field));
    }

    /**
     * {@inheritDoc}
     *
     * @param name the name of the member field to get the value of
     * @param <V>  the inferred type of the field
     * @return the value of the field
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getFieldValue(String)}
     *                          on the delegate
     */
    @Override
    public <V> V getFieldValue(final String name) {
        return getUnchecked(() -> this.delegate.getFieldValue(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param field the {@link Field}; reflects the member field to get the value of
     * @param type  the class type of the field
     * @param <V>   the type of the field
     * @return the value of the field
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getFieldValue(Field,
     *                          Class)} on the delegate
     */
    @Override
    public <V> V getFieldValue(final Field field, final Class<V> type) {
        return getUnchecked(() -> this.delegate.getFieldValue(field, type));
    }

    /**
     * {@inheritDoc}
     *
     * @param name the name of the member field to get the value of
     * @param type the class type of the field
     * @param <V>  the type of the field
     * @return the value of the field
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getFieldValue(String,
     *                          Class)} on the delegate
     */
    @Override
    public <V> V getFieldValue(final String name, final Class<V> type) {
        return getUnchecked(() -> this.delegate.getFieldValue(name, type));
    }

    /**
     * {@inheritDoc}
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order they are declared by
     *                       the method.
     * @return the {@link Method} reflecting the member method
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getMethod(String,
     *                          Class[])} on the delegate
     */
    @Override
    public Method getMethod(final String name, final Class<?>... parameterTypes) {
        return getUnchecked(() -> this.delegate.getMethod(name, parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order they are declared by
     *                       the method.
     * @return the {@link Method} reflecting the member method
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getMethod(String,
     *                          List)} on the delegate
     */
    @Override
    public Method getMethod(final String name, final List<Class<?>> parameterTypes) {
        return getUnchecked(() -> this.delegate.getMethod(name, parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order they are declared by
     *                       the method.
     * @return the {@link Method} reflecting the member method
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getMethodAccessible(String,
     *                          Class[])} on the delegate
     */
    @Override
    public Method getMethodAccessible(final String name, final Class<?>... parameterTypes) {
        return getUnchecked(() -> this.delegate.getMethod(name, parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order they are declared by
     *                       the method.
     * @return the {@link Method} reflecting the member method
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getMethodAccessible(String,
     *                          List)} on the delegate
     */
    @Override
    public Method getMethodAccessible(final String name, final List<Class<?>> parameterTypes) {
        return getUnchecked(() -> this.delegate.getMethod(name, parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param name      the name of the member method to invoke
     * @param arguments the arguments to pass on invocation of the method. Must be in the same order in which the
     *                  corresponding parameter is declared by the method.
     * @param <V>       the inferred return type of the method
     * @return the value returned by the invocation of the method
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#invoke(String,
     *                          Object...)} on the delegate
     * @see Method#invoke(Object, Object...)
     */
    @Override
    public <V> V invoke(final String name, Object... arguments) {
        return getUnchecked(() -> this.delegate.invoke(name, arguments));
    }

    /**
     * {@inheritDoc}
     *
     * @param name      the name of the member method to invoke
     * @param arguments the arguments to pass on invocation of the method. Must be in the same order in which the
     *                  corresponding parameter is declared by the method.
     * @param <V>       the inferred return type of the method
     * @return the value returned by the invocation of the method
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#invoke(String, List)}
     *                          on the delegate
     * @see Method#invoke(Object, Object...)
     */
    @Override
    public <V> V invoke(final String name, final List<Object> arguments) {
        return getUnchecked(() -> this.delegate.invoke(name, arguments));
    }

    /**
     * {@inheritDoc}
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order in which they are
     *                       declared by the method.
     * @param arguments      the arguments to pass on invocation of the method. Must be in the same order in which the
     *                       corresponding parameter is declared by the method.
     * @param <V>            the inferred return type of the method
     * @return the value returned by the invocation of the method
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#invoke(String, List,
     *                          List)} on the delegate
     * @see Method#invoke(Object, Object...)
     */
    @Override
    public <V> V invoke(final String name, final List<Class<?>> parameterTypes, final List<Object> arguments) {
        return getUnchecked(() -> this.delegate.invoke(name, parameterTypes, arguments));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the constructor.
     * @return the {@link Constructor} reflecting the member constructor
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getConstructor(Class[])}
     *                          on the delegate
     */
    @Override
    public Constructor<T> getConstructor(final Class<?>... parameterTypes) {
        return getUnchecked(() -> this.delegate.getConstructor(parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the constructor.
     * @return the {@link Constructor} reflecting the member constructor
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getConstructor(List)}
     *                          on the delegate
     */
    @Override
    public Constructor<T> getConstructor(final List<Class<?>> parameterTypes) {
        return getUnchecked(() -> this.delegate.getConstructor(parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the constructor.
     * @return the {@link Constructor} reflecting the member constructor
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getConstructorAccessible(Class[])}
     *                          on the delegate
     */
    @Override
    public Constructor<T> getConstructorAccessible(final Class<?>... parameterTypes) {
        return getUnchecked(() -> this.delegate.getConstructorAccessible(parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the constructor.
     * @return the {@link Constructor} reflecting the member constructor
     * @throws RuntimeException      if an exception was caught from the invocation of {@link
     *                               Reflect#getConstructorAccessible(List)} on the delegate
     */
    @Override
    public Constructor<T> getConstructorAccessible(final List<Class<?>> parameterTypes) {
        return getUnchecked(() -> this.delegate.getConstructorAccessible(parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param arguments the arguments to pass on invocation of the constructor. Must be in the same order in which the
     *                  corresponding parameter is declared by the constructor.
     * @return the instantiated object of type {@link T}
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#instantiate(Object...)}
     *                          on the delegate
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public T instantiate(final Object... arguments) {
        return getUnchecked(() -> this.delegate.instantiate(arguments));
    }

    /**
     * {@inheritDoc}
     *
     * @param arguments the arguments to pass on invocation of the constructor. Must be in the same order in which the
     *                  corresponding parameter is declared by the constructor.
     * @return the instantiated object of type {@link T}
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#instantiate(List)} on
     *                          the delegate
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public T instantiate(final List<Object> arguments) {
        return getUnchecked(() -> this.delegate.instantiate(arguments));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the method.
     * @param arguments      the arguments to pass on invocation of the constructor. Must be in the same order in which
     *                       the corresponding parameter is declared by the constructor.
     * @return the instantiated object of type {@link T}
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#instantiate(List,
     *                          List)} on the delegate
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public T instantiate(final List<Class<?>> parameterTypes, final List<Object> arguments) {
        return getUnchecked(() -> this.delegate.instantiate(parameterTypes, arguments));
    }

    /**
     * {@inheritDoc}
     *
     * @param arguments the arguments to pass on invocation of the constructor. Must be in the same order in which the
     *                  corresponding parameter is declared by the constructor.
     * @param <V>       the inferred type of the instantiated object
     * @return the instantiated object of type {@link T}
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#instantiateInferred(Object...)}
     *                          on the delegate
     * @see Constructor#newInstance(Object...)
     * @deprecated see {@link WeakHashSet#spliterator()} for an example of instantiating an inner class without needing
     * this inferred method
     */
    @Deprecated(forRemoval = true, since = "0.1.3")
    @SuppressWarnings("removal")
    @Override
    public <V> V instantiateInferred(final Object... arguments) {
        return getUnchecked(() -> this.delegate.instantiateInferred(arguments));
    }

    /**
     * {@inheritDoc}
     *
     * @param arguments the arguments to pass on invocation of the constructor. Must be in the same order in which the
     *                  corresponding parameter is declared by the constructor.
     * @param <V>       the inferred type of the instantiated object
     * @return the instantiated object of type {@link T}
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#instantiateInferred(List)}
     *                          on the delegate
     * @see Constructor#newInstance(Object...)
     * @deprecated see {@link WeakHashSet#spliterator()} for an example of instantiating an inner class without needing
     * this inferred method
     */
    @Deprecated(forRemoval = true, since = "0.1.3")
    @SuppressWarnings("removal")
    @Override
    public <V> V instantiateInferred(final List<Object> arguments) {
        return getUnchecked(() -> this.delegate.instantiateInferred(arguments));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the method.
     * @param arguments      the arguments to pass on invocation of the constructor. Must be in the same order in which
     *                       the corresponding parameter is declared by the constructor.
     * @param <V>            the inferred type of the instantiated object
     * @return the instantiated object of type {@link T}
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#instantiateInferred(List,
     *                          List)} on the delegate
     * @see Constructor#newInstance(Object...)
     * @deprecated see {@link WeakHashSet#spliterator()} for an example of instantiating an inner class without needing
     * this inferred method
     */
    @Deprecated(forRemoval = true, since = "0.1.3")
    @SuppressWarnings({"unchecked", "removal"})
    @Override
    public <V> V instantiateInferred(final List<Class<?>> parameterTypes, final List<Object> arguments) {
        return getUnchecked(() -> this.delegate.instantiateInferred(parameterTypes, arguments));
    }

    /**
     * {@inheritDoc}
     *
     * @param name the simple name of the inner class
     * @return the {@link Class} reflecting the inner class with the specified {@code name}
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#getInnerClass(String)}
     *                          on the delegate
     */
    @Override
    public Class<?> getInnerClass(final String name) {
        return getUnchecked(() -> this.delegate.getInnerClass(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param name the simple name of the inner class
     * @param <I>  the inferred type of the inner class to reflect
     * @return a {@link Reflect} for the inner class with the specified {@code name}
     * @throws RuntimeException if an exception was caught from the invocation of {@link Reflect#reflectInnerClass(String)}
     *                          on the delegate
     */
    @Override
    public <I> Reflect<I> reflectInnerClass(final String name) {
        return getUnchecked(() -> this.delegate.reflectInnerClass(name));
    }

    /**
     * Calls {@link ThrowableSupplier#get()} on the specified {@code supplier}, catching any thrown {@link Throwable}s
     * and creating a {@link RuntimeException} from them. If nothing is thrown, returns the return value.
     *
     * @param supplier the throwable supplier to attempt to get from
     * @param <V>      the type the specified {@code supplier} returns
     * @return the value returned by the specified {@code supplier}
     * @throws RuntimeException if the specified {@code supplier} threw an exception on invocation of {@link
     *                          ThrowableSupplier#get()}
     */
    private static <V> V getUnchecked(final ThrowableSupplier<V, ?> supplier) {
        try {
            return supplier.get();
        } catch (final Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

}
