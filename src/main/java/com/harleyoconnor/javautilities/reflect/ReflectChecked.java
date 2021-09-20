package com.harleyoconnor.javautilities.reflect;

import com.harleyoconnor.javautilities.collection.WeakHashSet;
import com.harleyoconnor.javautilities.util.Primitive;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A checked implementation of {@link Reflect}. This refers to the implementation throwing any exceptions detailed in
 * the base method signature (as opposed to {@link ReflectUnchecked}, which creates a {@link RuntimeException} for
 * them).
 *
 * @param <T> the type of the class being reflected on
 * @author Harley O'Connor
 * @see Reflect
 * @see ReflectUnchecked
 * @since JavaUtilities 0.1.0
 */
public final class ReflectChecked<T> implements Reflect<T> {

    private final Class<T> clazz;
    @Nullable
    private final T object;

    /**
     * Constructs a new {@link ReflectChecked} with the specified {@code clazz}. This is used for reflecting on static
     * members which do not need an instance of the class to function, and hence {@link #object} is set to {@code
     * null}.
     *
     * @param clazz the {@link Class} reflecting the class to reflect on
     */
    ReflectChecked(final Class<T> clazz) {
        this.clazz = clazz;
        this.object = null;
    }

    /**
     * Constructs a new {@link ReflectChecked} with the specified {@code object}. This is used for reflecting on
     * non-static members of the parent class.
     *
     * @param object the object to reflect on
     */
    @SuppressWarnings("unchecked")
    ReflectChecked(@NotNull final T object) {
        this.clazz = (Class<T>) object.getClass();
        this.object = object;
    }

    /**
     * {@inheritDoc}
     *
     * @return a new {@link ReflectUnchecked} object that delegates to this
     */
    @Override
    public ReflectUnchecked<T> unchecked() {
        return new ReflectUnchecked<>(this);
    }

    /**
     * {@inheritDoc}
     *
     * @param name the name of the member field
     * @return the {@link Field} object reflecting the member field
     * @throws NoSuchFieldException if a field with the specified {@code name} does not exist in the class being
     *                              reflected on
     */
    @Override
    public Field getField(final String name) throws NoSuchFieldException {
        try {
            return this.clazz.getField(name);
        } catch (final NoSuchFieldException e) {
            return this.clazz.getDeclaredField(name);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param name the name of the member field
     * @return the {@link Field} reflecting the member field
     * @throws NoSuchFieldException if a field with the specified {@code name} does not exist in the class being
     *                              reflected on
     */
    @Override
    public Field getFieldAccessible(final String name) throws NoSuchFieldException {
        return Reflect.setAccessible(this.getField(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param field the {@link Field}; reflects the member field to get the value of
     * @param <V>   the inferred type of the field
     * @return the value of the field
     * @throws IllegalAccessException if the field cannot be accessed from this method
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> V getFieldValue(final Field field) throws IllegalAccessException {
        return (V) field.get(this.object);
    }

    /**
     * {@inheritDoc}
     *
     * @param name the name of the member field to get the value of
     * @param <V>  the inferred type of the field
     * @return the value of the field
     * @throws NoSuchFieldException   if a field with the specified {@code name} does not exist in the class being
     *                                reflected on
     * @throws IllegalAccessException if the field cannot be accessed from this method
     */
    @Override
    public <V> V getFieldValue(final String name) throws NoSuchFieldException, IllegalAccessException {
        return this.getFieldValue(this.getFieldAccessible(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param field the {@link Field}; reflects the member field to get the value of
     * @param type  the class type of the field
     * @param <V>   the type of the field
     * @return the value of the field
     * @throws IllegalAccessException if the field cannot be accessed from this method
     */
    @Override
    public <V> V getFieldValue(final Field field, final Class<V> type) throws IllegalAccessException {
        return this.getFieldValue(field);
    }

    /**
     * {@inheritDoc}
     *
     * @param name the name of the member field to get the value of
     * @param type the class type of the field
     * @param <V>  the type of the field
     * @return the value of the field
     * @throws NoSuchFieldException   if a field with the specified {@code name} does not exist in the class being
     *                                reflected on
     * @throws IllegalAccessException if the field cannot be accessed from this method
     */
    @Override
    public <V> V getFieldValue(final String name, final Class<V> type)
            throws NoSuchFieldException, IllegalAccessException {
        return this.getFieldValue(name);
    }

    /**
     * {@inheritDoc}
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order they are declared by
     *                       the method.
     * @return the {@link Method} reflecting the member method
     * @throws NoSuchMethodException if a method with the specified {@code name} and {@code parameterTypes} does not
     *                               exist in the class being reflected on
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
     * Tries to get the method of the specified {@code name} with the specified {@code parameterTypes}. First it tries
     * to get the method normally via {@link Class#getMethod(String, Class[])}, otherwise it uses {@link
     * Class#getDeclaredMethod(String, Class[])}.
     *
     * @param name           The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws NoSuchMethodException If a {@code method} with specified {@code name} and {@code parameterTypes} does not
     *                               exist in the {@link Class} currently being reflected on.
     */
    private Method tryGetMethod(final String name, final Class<?>... parameterTypes) throws NoSuchMethodException {
        try {
            return this.clazz.getMethod(name, parameterTypes);
        } catch (final NoSuchMethodException e) {
            return this.clazz.getDeclaredMethod(name, parameterTypes);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order they are declared by
     *                       the method.
     * @return the {@link Method} reflecting the member method
     * @throws NoSuchMethodException if a method with the specified {@code name} and {@code parameterTypes} does not
     *                               exist in the class being reflected on
     */
    @Override
    public Method getMethod(final String name, final List<Class<?>> parameterTypes) throws NoSuchMethodException {
        return this.getMethod(name, parameterTypes.toArray(new Class<?>[0]));
    }

    /**
     * {@inheritDoc}
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order they are declared by
     *                       the method.
     * @return the {@link Method} reflecting the member method
     * @throws NoSuchMethodException if a method with the specified {@code name} and {@code parameterTypes} does not
     *                               exist in the class being reflected on
     */
    @Override
    public Method getMethodAccessible(final String name, final Class<?>... parameterTypes)
            throws NoSuchMethodException {
        return Reflect.setAccessible(this.getMethod(name, parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order they are declared by
     *                       the method.
     * @return the {@link Method} reflecting the member method
     * @throws NoSuchMethodException if a method with the specified {@code name} and {@code parameterTypes} does not
     *                               exist in the class being reflected on
     */
    @Override
    public Method getMethodAccessible(final String name, final List<Class<?>> parameterTypes)
            throws NoSuchMethodException {
        return Reflect.setAccessible(this.getMethod(name, parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param name      the name of the member method to invoke
     * @param arguments the arguments to pass on invocation of the method. Must be in the same order in which the
     *                  corresponding parameter is declared by the method.
     * @param <V>       the inferred return type of the method
     * @return the value returned by the invocation of the method
     * @throws NoSuchMethodException     if a method with the specified {@code name} and parameter types (as determined
     *                                   by the specified {@code arguments}) does not exist in the class being reflected
     *                                   on
     * @throws IllegalAccessException    if the member method could not be accessed
     * @throws InvocationTargetException if the underlying method invoked throws an exception
     * @see Method#invoke(Object, Object...)
     */
    @Override
    public <V> V invoke(final String name, final Object... arguments)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return this.invoke(name, Arrays.asList(arguments));
    }

    /**
     * {@inheritDoc}
     *
     * @param name      the name of the member method to invoke
     * @param arguments the arguments to pass on invocation of the method. Must be in the same order in which the
     *                  corresponding parameter is declared by the method.
     * @param <V>       the inferred return type of the method
     * @return the value returned by the invocation of the method
     * @throws NoSuchMethodException     if a method with the specified {@code name} and parameter types (as determined
     *                                   by the specified {@code arguments}) does not exist in the class being reflected
     *                                   on
     * @throws IllegalAccessException    if the member method could not be accessed
     * @throws InvocationTargetException if the underlying method invoked throws an exception
     * @see Method#invoke(Object, Object...)
     */
    @Override
    public <V> V invoke(final String name, final List<Object> arguments)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return this.invoke(name, arguments.stream().map(Object::getClass).collect(Collectors.toList()), arguments);
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
     * @throws NoSuchMethodException     if a method with the specified {@code name} and parameter types (as determined
     *                                   by the specified {@code arguments}) does not exist in the class being reflected
     *                                   on
     * @throws IllegalAccessException    if the member method could not be accessed
     * @throws InvocationTargetException if the underlying method invoked throws an exception
     * @see Method#invoke(Object, Object...)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> V invoke(final String name, final List<Class<?>> parameterTypes, final List<Object> arguments)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (V) this.getMethodAccessible(name, parameterTypes).invoke(this.object, arguments.toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the constructor.
     * @return the {@link Constructor} reflecting the member constructor
     * @throws NoSuchMethodException if a constructor with the specified {@code parameterTypes} does not exist in the
     *                               class being reflected on
     */
    @Override
    public Constructor<T> getConstructor(final Class<?>... parameterTypes) throws NoSuchMethodException {
        try {
            // First, try getting the constructor whilst making sure all classes are primitive.
            return this.clazz.getDeclaredConstructor(Stream.of(parameterTypes)
                    .map(Primitive::fromOrSelf).toArray(Class<?>[]::new));
        } catch (final NoSuchMethodException e) {
            // Then, try the given parameters.
            return this.clazz.getDeclaredConstructor(parameterTypes);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the constructor.
     * @return the {@link Constructor} reflecting the member constructor
     * @throws NoSuchMethodException if a constructor with the specified {@code parameterTypes} does not exist in the
     *                               class being reflected on
     */
    @Override
    public Constructor<T> getConstructor(final List<Class<?>> parameterTypes) throws NoSuchMethodException {
        return this.getConstructor(parameterTypes.toArray(new Class<?>[0]));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the constructor.
     * @return the {@link Constructor} reflecting the member constructor
     * @throws NoSuchMethodException if a constructor with the specified {@code parameterTypes} does not exist in the
     *                               class being reflected on
     */
    @Override
    public Constructor<T> getConstructorAccessible(final Class<?>... parameterTypes) throws NoSuchMethodException {
        return Reflect.setAccessible(this.getConstructor(parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the constructor.
     * @return the {@link Constructor} reflecting the member constructor
     * @throws NoSuchMethodException if a constructor with the specified {@code parameterTypes} does not exist in the
     *                               class being reflected on
     */
    @Override
    public Constructor<T> getConstructorAccessible(final List<Class<?>> parameterTypes) throws NoSuchMethodException {
        return Reflect.setAccessible(this.getConstructor(parameterTypes));
    }

    /**
     * {@inheritDoc}
     *
     * @param arguments the arguments to pass on invocation of the constructor. Must be in the same order in which the
     *                  corresponding parameter is declared by the constructor.
     * @return the instantiated object of type {@link T}
     * @throws NoSuchMethodException     if a constructor with the parameter types as determined by the specified {@code
     *                                   arguments} does not exist in the class being reflected on
     * @throws IllegalAccessException    if the member constructor could not be accessed
     * @throws InvocationTargetException if the underlying constructor invoked throws an exception
     * @throws InstantiationException    if the class being reflected on is abstract
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public T instantiate(final Object... arguments)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return this.instantiate(Arrays.asList(arguments));
    }

    /**
     * {@inheritDoc}
     *
     * @param arguments the arguments to pass on invocation of the constructor. Must be in the same order in which the
     *                  corresponding parameter is declared by the constructor.
     * @return the instantiated object of type {@link T}
     * @throws NoSuchMethodException     if a constructor with the parameter types as determined by the specified {@code
     *                                   arguments} does not exist in the class being reflected on
     * @throws IllegalAccessException    if the member constructor could not be accessed
     * @throws InvocationTargetException if the underlying constructor invoked throws an exception
     * @throws InstantiationException    if the class being reflected on is abstract
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public T instantiate(final List<Object> arguments)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return this.instantiate(arguments.stream().map(Object::getClass).collect(Collectors.toList()), arguments);
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the method.
     * @param arguments      the arguments to pass on invocation of the constructor. Must be in the same order in which
     *                       the corresponding parameter is declared by the constructor.
     * @return the instantiated object of type {@link T}
     * @throws NoSuchMethodException     if a constructor with the specified {@code parameterTypes} does not exist in
     *                                   the class being reflected on
     * @throws IllegalAccessException    if the member constructor could not be accessed
     * @throws InvocationTargetException if the underlying constructor invoked throws an exception
     * @throws InstantiationException    if the class being reflected on is abstract
     * @see Constructor#newInstance(Object...)
     */
    @Override
    public T instantiate(final List<Class<?>> parameterTypes, final List<Object> arguments)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return this.getConstructorAccessible(parameterTypes).newInstance(arguments.toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param arguments the arguments to pass on invocation of the constructor. Must be in the same order in which the
     *                  corresponding parameter is declared by the constructor.
     * @param <V>       the inferred type of the instantiated object
     * @return the instantiated object of type {@link T}
     * @throws NoSuchMethodException     if a constructor with the parameter types as determined by the specified {@code
     *                                   arguments} does not exist in the class being reflected on
     * @throws IllegalAccessException    if the member constructor could not be accessed
     * @throws InvocationTargetException if the underlying constructor invoked throws an exception
     * @throws InstantiationException    if the class being reflected on is abstract
     * @see Constructor#newInstance(Object...)
     * @deprecated see {@link WeakHashSet#spliterator()} for an example of instantiating an inner class without needing
     * this inferred method
     */
    @Deprecated(forRemoval = true, since = "0.1.3")
    @SuppressWarnings({"unchecked", "removal"})
    @Override
    public <V> V instantiateInferred(final Object... arguments)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (V) this.instantiate(arguments);
    }

    /**
     * {@inheritDoc}
     *
     * @param arguments the arguments to pass on invocation of the constructor. Must be in the same order in which the
     *                  corresponding parameter is declared by the constructor.
     * @param <V>       the inferred type of the instantiated object
     * @return the instantiated object of type {@link T}
     * @throws NoSuchMethodException     if a constructor with the parameter types as determined by the specified {@code
     *                                   arguments} does not exist in the class being reflected on
     * @throws IllegalAccessException    if the member constructor could not be accessed
     * @throws InvocationTargetException if the underlying constructor invoked throws an exception
     * @throws InstantiationException    if the class being reflected on is abstract
     * @see Constructor#newInstance(Object...)
     * @deprecated see {@link WeakHashSet#spliterator()} for an example of instantiating an inner class without needing
     * this inferred method
     */
    @Deprecated(forRemoval = true, since = "0.1.3")
    @SuppressWarnings({"unchecked", "removal"})
    @Override
    public <V> V instantiateInferred(final List<Object> arguments)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (V) this.instantiate(arguments);
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
     * @throws NoSuchMethodException     if a constructor with the specified {@code parameterTypes} does not exist in
     *                                   the class being reflected on
     * @throws IllegalAccessException    if the member constructor could not be accessed
     * @throws InvocationTargetException if the underlying constructor invoked throws an exception
     * @throws InstantiationException    if the class being reflected on is abstract
     * @see Constructor#newInstance(Object...)
     */
    @Deprecated(forRemoval = true, since = "0.1.3")
    @SuppressWarnings({"unchecked", "removal"})
    @Override
    public <V> V instantiateInferred(final List<Class<?>> parameterTypes, final List<Object> arguments)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (V) this.instantiate(parameterTypes, arguments);
    }

    /**
     * {@inheritDoc}
     *
     * @param name the simple name of the inner class
     * @return the {@link Class} reflecting the inner class with the specified {@code name}
     * @throws NoSuchInnerClassException if an inner class with the specified {@code name} does not exist in the class
     *                                   being reflected on
     */
    @Override
    public Class<?> getInnerClass(final String name) throws NoSuchInnerClassException {
        return Stream.of(this.clazz.getDeclaredClasses())
                .filter(innerClass -> innerClass.getSimpleName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchInnerClassException(this.clazz.getName() + "$" + name));
    }

    /**
     * {@inheritDoc}
     *
     * @param name the simple name of the inner class
     * @param <I>  the type of the inner class to reflect
     * @return a {@link Reflect} for the inner class with the specified {@code name}
     * @throws NoSuchInnerClassException if an inner class with the specified {@code name} does not exist in the class
     *                                   being reflected on
     */
    @Override
    @SuppressWarnings("unchecked")
    public <I> ReflectChecked<I> reflectInnerClass(final String name) throws NoSuchInnerClassException {
        return Reflect.on((Class<I>) this.getInnerClass(name));
    }

}
