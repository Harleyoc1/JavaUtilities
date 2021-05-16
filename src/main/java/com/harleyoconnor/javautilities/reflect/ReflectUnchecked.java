package com.harleyoconnor.javautilities.reflect;

import com.harleyoconnor.javautilities.function.ThrowableSupplier;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * An unchecked implementation of {@link Reflect}, using {@link ReflectChecked} as a
 * backing and catching any exceptions, throwing a runtime exception instead.
 *
 * @author Harley O'Connor
 */
public final class ReflectUnchecked<T> implements Reflect<T> {

    private final Reflect<T> reflect;

    /**
     * Constructs a new {@link ReflectUnchecked} instance with the specified
     * {@link ReflectChecked}.
     *
     * @param reflect The {@link ReflectChecked}
     */
    ReflectUnchecked(Reflect<T> reflect) {
        this.reflect = reflect;
    }

    /**
     * Returns {@code this}, since it's already a {@link ReflectUnchecked}.
     *
     * @return This {@link ReflectUnchecked}.
     */
    @Override
    public ReflectUnchecked<T> unchecked() {
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Field}.
     * @return The {@link Field} {@code object} reflecting the member {@code field}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getField(String)}.
     */
    @Override
    public Field getField(final String name) {
        return getUnchecked(() -> this.reflect.getField(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Field}.
     * @return The {@link Field} {@code object} reflecting the member {@code field}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getFieldAccessible(String)}.
     */
    @Override
    public Field getFieldAccessible(final String name) {
        return getUnchecked(() -> this.reflect.getFieldAccessible(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param field The {@link Field} {@code object} to get the value for.
     * @param <V> The inferred type of the {@code field}.
     * @return The value of the {@code field}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getFieldValue(Field)}.
     */
    @Override
    public <V> V getFieldValue(final Field field) {
        return getUnchecked(() -> this.reflect.getFieldValue(field));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Field}.
     * @param <V> The inferred type of the {@code field}.
     * @return The value of the {@code field}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getField(String)}.
     */
    @Override
    public <V> V getFieldValue(final String name) {
        return getUnchecked(() -> this.reflect.getFieldValue(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param field The {@link Field} {@code object} to get the value for.
     * @param vClass The {@link Class} {@code object} of the value of the {@link Field}.
     * @param <V> The type of the {@code field}.
     * @return The value of the {@code field}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getFieldValue(Field, Class)}.
     */
    @Override
    public <V> V getFieldValue(final Field field, final Class<V> vClass) {
        return getUnchecked(() -> this.reflect.getFieldValue(field, vClass));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Field}.
     * @param vClass The {@link Class} {@code object} of the value of the {@link Field}.
     * @param <V> The type of the {@code field}.
     * @return The value of the {@code field}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getFieldValue(String, Class)}.
     */
    @Override
    public <V> V getFieldValue(final String name, final Class<V> vClass) {
        return getUnchecked(() -> this.reflect.getFieldValue(name, vClass));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getMethod(String, Class[])}.
     */
    @Override
    public Method getMethod(final String name, final Class<?>... parameterTypes) {
        return getUnchecked(() -> this.reflect.getMethod(name, parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getMethod(String, List)}.
     */
    @Override
    public Method getMethod(final String name, final List<Class<?>> parameterTypes) {
        return getUnchecked(() -> this.reflect.getMethod(name, parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getMethodAccessible(String, Class[])}.
     */
    @Override
    public Method getMethodAccessible(final String name, final Class<?>... parameterTypes) {
        return getUnchecked(() -> this.reflect.getMethod(name, parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getMethodAccessible(String, List)}.
     */
    @Override
    public Method getMethodAccessible(final String name, final List<Class<?>> parameterTypes) {
        return getUnchecked(() -> this.reflect.getMethod(name, parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameters The parameters to pass on invocation of the specified
     *                   {@code method}.
     * @param <V> The return type of the {@code method}.
     * @return The value returned by the invocation of the {@code method}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#invoke(String, List)}.
     * @see Method#invoke(Object, Object...)
     */
    @Override
    public <V> V invoke(final String name, final List<Object> parameters) {
        return getUnchecked(() -> this.reflect.invoke(name, parameters));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameters The parameters to pass on invocation of the specified
     *                   {@code method}.
     * @param <V> The return type of the {@code method}.
     * @return The value returned by the invocation of the {@code method}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#invoke(String, Object[])}.
     * @see Method#invoke(Object, Object...)
     */
    @Override
    public <V> V invoke(final String name, Object... parameters) {
        return getUnchecked(() -> this.reflect.invoke(name, parameters));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameters The parameters to pass on invocation of the specified
     *                   {@code method}.
     * @param <V> The return type of the {@code method}.
     * @return The value returned by the invocation of the {@code method}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#invoke(String, List, List)}.
     * @see Method#invoke(Object, Object...)
     */
    @Override
    public <V> V invoke(final String name, final List<Class<?>> parameterTypes, final List<Object> parameters) {
        return getUnchecked(() -> this.reflect.invoke(name, parameterTypes, parameters));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
     * @return The {@link Constructor} {@code object} reflecting the member {@code constructor}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getConstructor(Class[])}.
     */
    @Override
    public Constructor<T> getConstructor(final Class<?>... parameterTypes) {
        return getUnchecked(() -> this.reflect.getConstructor(parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
     * @return The {@link Constructor} {@code object} reflecting the member {@code constructor}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getConstructor(List)}.
     */
    @Override
    public Constructor<T> getConstructor(final List<Class<?>> parameterTypes) {
        return getUnchecked(() -> this.reflect.getConstructor(parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
     * @return The {@link Constructor} {@code object} reflecting the member {@code constructor}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getConstructorAccessible(Class[])}.
     */
    @Override
    public Constructor<T> getConstructorAccessible(final Class<?>... parameterTypes) {
        return getUnchecked(() -> this.reflect.getConstructorAccessible(parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
     * @return The {@link Constructor} {@code object} reflecting the member {@code constructor}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getConstructorAccessible(List)}.
     */
    @Override
    public Constructor<T> getConstructorAccessible(final List<Class<?>> parameterTypes) {
        return getUnchecked(() -> this.reflect.getConstructorAccessible(parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameters The parameters to pass on instantiation of the specified
     *                   {@code constructor}.
     * @return The constructed {@link Object} of type {@link T}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#instantiate(Object...)}.
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public T instantiate(final Object... parameters) {
        return getUnchecked(() -> this.reflect.instantiate(parameters));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameters The parameters to pass on instantiation of the specified
     *                   {@code constructor}.
     * @return The constructed {@link Object} of type {@link T}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#instantiate(List)}.
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public T instantiate(final List<Object> parameters) {
        return getUnchecked(() -> this.reflect.instantiate(parameters));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameters The parameters to pass on instantiation of the specified
     *                   {@code constructor}.
     * @return The constructed {@link Object} of type {@link T}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#instantiate(List, List)}.
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public T instantiate(final List<Class<?>> parameterTypes, final List<Object> parameters) {
        return getUnchecked(() -> this.reflect.instantiate(parameterTypes, parameters));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameters The parameters to pass on instantiation of the specified
     *                   {@code constructor}.
     * @param <V> The inferred type of the {@link T}.
     * @return The constructed {@link Object} of type {@link V}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#instantiateInferred(Object...)}.
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public <V> V instantiateInferred(final Object... parameters) {
        return getUnchecked(() -> this.reflect.instantiateInferred(parameters));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameters The parameters to pass on instantiation of the specified
     *                   {@code constructor}.
     * @param <V> The inferred type of the {@link T}.
     * @return The constructed {@link Object} of type {@link V}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#instantiateInferred(List)}.
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public <V> V instantiateInferred(final List<Object> parameters) {
        return getUnchecked(() -> this.reflect.instantiateInferred(parameters));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameters The parameters to pass on instantiation of the specified
     *                   {@code constructor}.
     * @param <V> The inferred type of the {@link T}.
     * @return The constructed {@link Object} of type {@link V}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#instantiateInferred(List, List)}.
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public <V> V instantiateInferred(final List<Class<?>> parameterTypes, final List<Object> parameters) {
        return getUnchecked(() -> this.reflect.instantiateInferred(parameterTypes, parameters));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@code inner class}.
     * @return The {@link Class} {@code object} reflecting the member {@code inner class}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#getInnerClass(String)}.
     */
    @Override
    public Class<?> getInnerClass(final String name) {
        return getUnchecked(() -> this.reflect.getInnerClass(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@code inner class}.
     * @param <I> The type of the {@code inner class} being reflected on.
     * @return The {@link Class} {@code object} reflecting the member {@code inner class}.
     * @throws RuntimeException If an exception was thrown by
     *                          {@link ReflectChecked#reflectInnerClass(String)}.
     */
    @Override
    public <I> Reflect<I> reflectInnerClass(final String name) {
        return getUnchecked(() -> this.reflect.reflectInnerClass(name));
    }

    /**
     * Calls {@link ThrowableSupplier#get()} on the specified {@link ThrowableSupplier},
     * catching any {@link Throwable}s thrown and creating a {@link RuntimeException}
     * from them. If nothing is thrown then it returns the return value.
     *
     * @param throwableSupplier The {@link ThrowableSupplier} to get.
     * @param <V> The type to return.
     * @return The {@code object} of type {@link V} returned by the specified
     *         {@link ThrowableSupplier}.
     */
    public static <V> V getUnchecked(final ThrowableSupplier<V, ?> throwableSupplier) {
        try {
            return throwableSupplier.get();
        } catch (final Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

}
