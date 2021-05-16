package com.harleyoconnor.javautilities.reflect;

import com.harleyoconnor.javautilities.util.Primitive;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A "checked" implementation of {@link Reflect}. This refers to the implementation
 * throwing any exceptions detailed in the base method signature (as opposed to
 * {@link ReflectUnchecked} which creates a {@link RuntimeException} for them).
 *
 * @param <T> The type of the {@link Class} being reflected on.
 *
 * @author Harley O'Connor
 * @see Reflect
 * @see ReflectUnchecked
 * @since JavaUtilities 0.1.0
 */
public final class ReflectChecked<T> implements Reflect<T> {

    private final Class<T> tClass;
    private final T object;

    /**
     * Constructs a new {@link ReflectChecked} with the specified {@link Class}.
     * This is used for reflecting on static members which do not need an instance
     * of the {@link Class} to function, and hence {@link #object} is set to 
     * {@code null}.
     * 
     * @param tClass The {@link Class} to be reflected on.
     */
    ReflectChecked(final Class<T> tClass) {
        this.tClass = tClass;
        this.object = null;
    }

    /**
     * Constructs a new {@link ReflectChecked} with the specified {@link Object}. 
     * The {@link Class} is obtained from {@link Object#getClass()}. 
     * 
     * @param object The {@link Object} to be reflected on. 
     */
    @SuppressWarnings("unchecked")
    ReflectChecked(final T object) {
        this.tClass = (Class<T>) object.getClass();
        this.object = object;
    }

    /**
     * Returns a {@link ReflectUnchecked} object with this {@link ReflectChecked} object.
     * 
     * @return A {@link ReflectUnchecked} of this {@link ReflectChecked}. 
     */
    @Override
    public ReflectUnchecked<T> unchecked() {
        return new ReflectUnchecked<>(this);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Field}.
     * @return The {@link Field} {@code object} reflecting the member {@code field}.
     * @throws NoSuchFieldException If a {@code field} with the specified {@code name}
     *                              does not exist in the {@link Class} currently
     *                              being reflected on.
     */
    @Override
    public Field getField(final String name) throws NoSuchFieldException {
        try {
            return this.tClass.getField(name);
        } catch (final NoSuchFieldException e) {
            return this.tClass.getDeclaredField(name);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Field}.
     * @return The {@link Field} {@code object} reflecting the member {@code field}.
     * @throws NoSuchFieldException If a {@code field} with the specified {@code name}
     *                              does not exist in the {@link Class} currently
     *                              being reflected on.
     */
    @Override
    public Field getFieldAccessible(final String name) throws NoSuchFieldException {
        return Reflect.setAccessible(this.getField(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param field The {@link Field} {@code object} to get the value for.
     * @param <V> The inferred type of the {@code field}.
     * @return The value of the {@code field}.
     * @throws IllegalAccessException If the {@link Field} could not be accessed by this
     *                                method.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> V getFieldValue(final Field field) throws IllegalAccessException {
        return (V) field.get(this.object);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Field}.
     * @param <V> The inferred type of the {@code field}.
     * @return The value of the {@code field}.
     * @throws NoSuchFieldException If a {@code field} with the specified {@code name}
     *                              does not exist in the {@link Class} currently
     *                              being reflected on.
     * @throws IllegalAccessException If the {@link Field} could not be accessed by this
     *                                method.
     */
    @Override
    public <V> V getFieldValue(final String name) throws NoSuchFieldException, IllegalAccessException {
        return this.getFieldValue(this.getFieldAccessible(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param field The {@link Field} {@code object} to get the value for.
     * @param vClass The {@link Class} {@code object} of the value of the {@link Field}.
     * @param <V> The type of the {@code field}.
     * @return The value of the {@code field}.
     * @throws IllegalAccessException If the {@link Field} could not be accessed by this
     *                                method.
     */
    @Override
    public <V> V getFieldValue(final Field field, final Class<V> vClass) throws IllegalAccessException {
        return this.getFieldValue(field);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Field}.
     * @param vClass The {@link Class} {@code object} of the value of the {@link Field}.
     * @param <V> The type of the {@code field}.
     * @return The value of the {@code field}.
     * @throws NoSuchFieldException If a {@code field} with the specified {@code name}
     *                              does not exist in the {@link Class} currently
     *                              being reflected on.
     * @throws IllegalAccessException If the {@link Field} could not be accessed by this
     *                                method.
     */
    @Override
    public <V> V getFieldValue(final String name, final Class<V> vClass) throws NoSuchFieldException, IllegalAccessException {
        return this.getFieldValue(name);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws NoSuchMethodException If a {@code method} with the specified {@code name}
     *                               and {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    @Override
    public Method getMethod(final String name, final Class<?>... parameterTypes) throws NoSuchMethodException {
        try {
            // First, try getting the method whilst making sure all classes are primitive.
            return this.tryGetMethod(name, Stream.of(parameterTypes)
                    .map(Primitive::fromOrSelf).toArray(Class<?>[]::new));
        } catch (final NoSuchMethodException e) {
            // Then, try the given parameters.
            return this.tryGetMethod(name, parameterTypes);
        }
    }

    /**
     * Tries to get the method of the specified {@code name} with the specified 
     * {@code parameterTypes}. First it tries to get the method normally via 
     * {@link Class#getMethod(String, Class[])}, otherwise it uses 
     * {@link Class#getDeclaredMethod(String, Class[])}. 
     * 
     * @param name The {@link String} name of the {@link Method}. 
     * @param parameterTypes The {@link Class} types the member {@code method} takes. 
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws NoSuchMethodException If a {@code method} with specified {@code name} and
     *                               {@code parameterTypes} does not exist in the 
     *                               {@link Class} currently being reflected on. 
     */
    private Method tryGetMethod(final String name, final Class<?>... parameterTypes) throws NoSuchMethodException {
        try {
            return this.tClass.getMethod(name, parameterTypes);
        } catch (final NoSuchMethodException e) {
            return this.tClass.getDeclaredMethod(name, parameterTypes);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws NoSuchMethodException If a {@code method} with the specified {@code name}
     *                               and {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    @Override
    public Method getMethod(final String name, final List<Class<?>> parameterTypes) throws NoSuchMethodException {
        return this.getMethod(name, parameterTypes.toArray(new Class<?>[0]));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws NoSuchMethodException If a {@code method} with the specified {@code name}
     *                               and {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    @Override
    public Method getMethodAccessible(final String name, final Class<?>... parameterTypes) throws NoSuchMethodException {
        return Reflect.setAccessible(this.getMethod(name, parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws NoSuchMethodException If a {@code method} with the specified {@code name}
     *                               and {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    @Override
    public Method getMethodAccessible(final String name, final List<Class<?>> parameterTypes) throws NoSuchMethodException {
        return Reflect.setAccessible(this.getMethod(name, parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameters The parameters to pass on invocation of the specified
     *                   {@code method}.
     * @param <V> The return type of the {@code method}.
     * @return The value returned by the invocation of the {@code method}.
     * @throws NoSuchMethodException If a {@code method} with the specified {@code name}
     *                               and inferred {@code parameter} types does not exist
     *                               in the {@link Class} currently being reflected on.
     * @throws IllegalAccessException If the {@link Method} could not be accessed by this
     *                                method.
     * @throws InvocationTargetException If the underlying method invoked throws an
     *                                   exception.
     * @see Method#invoke(Object, Object...)
     */
    @Override
    public <V> V invoke(final String name, final Object... parameters) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return this.invoke(name, Arrays.asList(parameters));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameters The parameters to pass on invocation of the specified
     *                   {@code method}.
     * @param <V> The return type of the {@code method}.
     * @return The value returned by the invocation of the {@code method}.
     * @throws NoSuchMethodException If a {@code method} with the specified {@code name}
     *                               and {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     * @throws IllegalAccessException If the {@link Method} could not be accessed by this
     *                                method.
     * @throws InvocationTargetException If the underlying method invoked throws an
     *                                   exception.
     * @see Method#invoke(Object, Object...)
     */
    @Override
    public <V> V invoke(final String name, final List<Object> parameters) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return this.invoke(name, parameters.stream().map(Object::getClass).collect(Collectors.toList()), parameters);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameters The parameters to pass on invocation of the specified
     *                   {@code method}.
     * @param <V> The return type of the {@code method}.
     * @return The value returned by the invocation of the {@code method}.
     * @throws NoSuchMethodException If a {@code method} with the specified {@code name}
     *                               and {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     * @throws IllegalAccessException If the {@link Method} could not be accessed by this
     *                                method.
     * @throws InvocationTargetException If the underlying method invoked throws an
     *                                   exception.
     * @see Method#invoke(Object, Object...)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> V invoke(final String name, final List<Class<?>> parameterTypes, final List<Object> parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (V) this.getMethodAccessible(name, parameterTypes).invoke(this.object, parameters.toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
     * @return The {@link Constructor} {@code object} reflecting the member {@code constructor}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    @Override
    public Constructor<T> getConstructor(final Class<?>... parameterTypes) throws NoSuchMethodException {
        try {
            // First, try getting the constructor whilst making sure all classes are primitive.
            return this.tClass.getDeclaredConstructor(Stream.of(parameterTypes)
                    .map(Primitive::fromOrSelf).toArray(Class<?>[]::new));
        } catch (final NoSuchMethodException e) {
            // Then, try the given parameters.
            return this.tClass.getDeclaredConstructor(parameterTypes);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
     * @return The {@link Constructor} {@code object} reflecting the member {@code constructor}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    @Override
    public Constructor<T> getConstructor(final List<Class<?>> parameterTypes) throws NoSuchMethodException {
        return this.getConstructor(parameterTypes.toArray(new Class<?>[0]));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
     * @return The {@link Constructor} {@code object} reflecting the member {@code constructor}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    @Override
    public Constructor<T> getConstructorAccessible(final Class<?>... parameterTypes) throws NoSuchMethodException {
        return Reflect.setAccessible(this.getConstructor(parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
     * @return The {@link Constructor} {@code object} reflecting the member {@code constructor}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    @Override
    public Constructor<T> getConstructorAccessible(final List<Class<?>> parameterTypes) throws NoSuchMethodException {
        return Reflect.setAccessible(this.getConstructor(parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameters The parameters to pass on instantiation of the specified
     *                   {@code constructor}.
     * @return The constructed {@link Object} of type {@link T}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified, inferred
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     * @throws IllegalAccessException If the {@link Constructor} could not be accessed by this
     *                                method.
     * @throws InvocationTargetException If the underlying constructor invoked throws an
     *                                   exception.
     * @throws InstantiationException If the {@link Class} declaring the underlying constructor
     *                                is abstract.
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public T instantiate(final Object... parameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return this.instantiate(Arrays.asList(parameters));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameters The parameters to pass on instantiation of the specified
     *                   {@code constructor}.
     * @return The constructed {@link Object} of type {@link T}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified, inferred
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     * @throws IllegalAccessException If the {@link Constructor} could not be accessed by this
     *                                method.
     * @throws InvocationTargetException If the underlying constructor invoked throws an
     *                                   exception.
     * @throws InstantiationException If the {@link Class} declaring the underlying constructor
     *                                is abstract.
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public T instantiate(final List<Object> parameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return this.instantiate(parameters.stream().map(Object::getClass).collect(Collectors.toList()), parameters);
    }

    /**
     * {@inheritDoc}
     *
     * @param parameters The parameters to pass on instantiation of the specified
     *                   {@code constructor}.
     * @return The constructed {@link Object} of type {@link T}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     * @throws IllegalAccessException If the {@link Constructor} could not be accessed by this
     *                                method.
     * @throws InvocationTargetException If the underlying constructor invoked throws an
     *                                   exception.
     * @throws InstantiationException If the {@link Class} declaring the underlying constructor
     *                                is abstract.
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public T instantiate(final List<Class<?>> parameterTypes, final List<Object> parameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return this.getConstructorAccessible(parameterTypes).newInstance(parameters.toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param parameters The parameters to pass on instantiation of the specified
     *                   {@code constructor}.
     * @param <V> The inferred type of the {@link T}.
     * @return The constructed {@link Object} of type {@link V}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified, inferred
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     * @throws IllegalAccessException If the {@link Constructor} could not be accessed by this
     *                                method.
     * @throws InvocationTargetException If the underlying constructor invoked throws an
     *                                   exception.
     * @throws InstantiationException If the {@link Class} declaring the underlying constructor
     *                                is abstract.
     * @see Constructor#newInstance(Object...)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> V instantiateInferred(final Object... parameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (V) this.instantiate(parameters);
    }

    /**
     * {@inheritDoc}
     *
     * @param parameters The parameters to pass on instantiation of the specified
     *                   {@code constructor}.
     * @param <V> The inferred type of the {@link T}.
     * @return The constructed {@link Object} of type {@link V}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified, inferred
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     * @throws IllegalAccessException If the {@link Constructor} could not be accessed by this
     *                                method.
     * @throws InvocationTargetException If the underlying constructor invoked throws an
     *                                   exception.
     * @throws InstantiationException If the {@link Class} declaring the underlying constructor
     *                                is abstract.
     * @see Constructor#newInstance(Object...)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> V instantiateInferred(final List<Object> parameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (V) this.instantiate(parameters);
    }

    /**
     * {@inheritDoc}
     *
     * @param parameters The parameters to pass on instantiation of the specified
     *                   {@code constructor}.
     * @param <V> The inferred type of the {@link T}.
     * @return The constructed {@link Object} of type {@link V}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     * @throws IllegalAccessException If the {@link Constructor} could not be accessed by this
     *                                method.
     * @throws InvocationTargetException If the underlying constructor invoked throws an
     *                                   exception.
     * @throws InstantiationException If the {@link Class} declaring the underlying constructor
     *                                is abstract.
     * @see Constructor#newInstance(Object...)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> V instantiateInferred(final List<Class<?>> parameterTypes, final List<Object> parameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (V) this.instantiate(parameterTypes, parameters);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@code inner class}.
     * @return The {@link Class} {@code object} reflecting the member {@code inner class}.
     * @throws NoSuchInnerClassException If an {@code inner class} with the specified
     *                                   {@code name} does not exist in the {@link Class}
     *                                   currently being reflected on.
     */
    @Override
    public Class<?> getInnerClass(final String name) throws NoSuchInnerClassException {
        for (final Class<?> innerClass : this.tClass.getDeclaredClasses()) {
            if (innerClass.getSimpleName().equals(name))
                return innerClass;
        }
        throw new NoSuchInnerClassException(this.tClass.getName() + "$" + name);
    }
    
    /**
     * {@inheritDoc}
     *
     * @param name The {@link String} name of the {@code inner class}.
     * @param <I> The type of the {@code inner class} being reflected on.
     * @return The {@link Class} {@code object} reflecting the member {@code inner class}.
     * @throws NoSuchInnerClassException If an {@code inner class} with the specified
     *                                   {@code name} does not exist in the {@link Class}
     *                                   currently being reflected on.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <I> ReflectChecked<I> reflectInnerClass(final String name) throws NoSuchInnerClassException {
        return Reflect.on((Class<I>) this.getInnerClass(name));
    }

}
