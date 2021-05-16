package com.harleyoconnor.javautilities.reflect;

import java.lang.reflect.*;
import java.util.List;

/**
 * Implementations will handle reflective operations. This aims to make reflection
 * much simpler, especially when using unchecked operations (see {@link ReflectUnchecked}).
 *
 * For using the default implementations, call {@link #on(Class)} for reflecting on
 * {@code static} members, or {@link #on(Object)} for reflecting on {@code non-static}
 * methods within the specified {@code object}. Both functions have an "unchecked"
 * version, which create a {@link ReflectUnchecked} {@code object}.
 *
 * @param <T> The type of the {@link Class} being reflected on.
 *
 * @author Harley O'Connor
 * @see ReflectChecked
 * @see ReflectUnchecked
 * @since JavaUtilities 0.1.0
 */
public interface Reflect<T> {

    /**
     * Returns a {@link ReflectUnchecked} object from this {@link Reflect} object.
     *
     * @return A {@link ReflectUnchecked} of this {@link Reflect}.
     */
    ReflectUnchecked<T> unchecked();

    /**
     * Gets the {@link Field} {@code object} which reflects the member {@code field} (of
     * the {@link Class} of type {@link T}) with the specified {@code name}.
     *
     * <p>If the field is inaccessible from the calling method, certain operations on the
     * returned {@link Field} {@code object} may throw {@link IllegalAccessException}. In
     * this case, {@link #getFieldAccessible(String)} can be used to bypass this (unless
     * a {@link SecurityManager} prevents it).</p>
     *
     * @param name The {@link String} name of the {@link Field}.
     * @return The {@link Field} {@code object} reflecting the member {@code field}.
     * @throws NoSuchFieldException If a {@code field} with the specified {@code name}
     *                              does not exist in the {@link Class} currently
     *                              being reflected on.
     */
    Field getField(String name) throws NoSuchFieldException;

    /**
     * Gets the {@link Field} {@code object} which reflects the member {@code field} (of
     * the {@link Class} of type {@link T}) with the specified {@code name}, making it
     * accessible.
     *
     * <p>This allows the calling methods to access {@code field}s which are usually
     * inaccessible, such as allowing access to a {@code private} member {@code field}
     * from outside the {@link Class}.</p>
     *
     * @param name The {@link String} name of the {@link Field}.
     * @return The {@link Field} {@code object} reflecting the member {@code field}.
     * @throws NoSuchFieldException If a {@code field} with the specified {@code name}
     *                              does not exist in the {@link Class} currently
     *                              being reflected on.
     */
    Field getFieldAccessible(String name) throws NoSuchFieldException;

    /**
     * Gets the value of the specified {@link Field} {@code object} in the {@link Class}
     * (and {@code object} if {@code non-static}) currently being reflected on.
     *
     * @param field The {@link Field} {@code object} to get the value for.
     * @param <V> The inferred type of the {@code field}.
     * @return The value of the {@code field}.
     * @throws IllegalAccessException If the {@link Field} could not be accessed by this
     *                                method.
     */
    <V> V getFieldValue(Field field) throws IllegalAccessException;

    /**
     * Gets the value of the {@link Field} {@code object} obtained from the specified
     * {@code name} in the {@link Class} (and {@code object} if {@code non-static})
     * currently being reflected on.
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
    <V> V getFieldValue(String name) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Gets the value of the specified {@link Field} {@code object} in the {@link Class}
     * (and {@code object} if {@code non-static}) currently being reflected on.
     *
     * @param field The {@link Field} {@code object} to get the value for.
     * @param vClass The {@link Class} {@code object} of the value of the {@link Field}.
     * @param <V> The type of the {@code field}.
     * @return The value of the {@code field}.
     * @throws IllegalAccessException If the {@link Field} could not be accessed by this
     *                                method.
     */
    <V> V getFieldValue(Field field, Class<V> vClass) throws IllegalAccessException;

    /**
     * Gets the value of the {@link Field} {@code object} obtained from the specified
     * {@code name} in the {@link Class} (and {@code object} if {@code non-static})
     * currently being reflected on.
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
    <V> V getFieldValue(String name, Class<V> vClass) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Gets the {@link Method} {@code object} which reflects the member {@code method} (of
     * the {@link Class} of type {@link T}) with the specified {@code name} with the
     * specified parameters {@link Class} types.
     *
     * <p>If the method is inaccessible from the calling method, certain operations on the
     * returned {@link Method} {@code object} may throw {@link IllegalAccessException}. In
     * this case, {@link #getMethodAccessible(String, Class[])} can be used to bypass this
     * (unless a {@link SecurityManager} prevents it).</p>
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws NoSuchMethodException If a {@code method} with the specified {@code name}
     *                               and {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    Method getMethod(String name, Class<?>... parameterTypes) throws NoSuchMethodException;

    /**
     * Gets the {@link Method} {@code object} which reflects the member {@code method} (of
     * the {@link Class} of type {@link T}) with the specified {@code name} with the
     * specified parameters {@link Class} types.
     *
     * <p>If the method is inaccessible from the calling method, certain operations on the
     * returned {@link Method} {@code object} may throw {@link IllegalAccessException}. In
     * this case, {@link #getMethodAccessible(String, Class[])} can be used to bypass this
     * (unless a {@link SecurityManager} prevents it).</p>
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws NoSuchMethodException If a {@code method} with the specified {@code name}
     *                               and {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    Method getMethod(String name, List<Class<?>> parameterTypes) throws NoSuchMethodException;

    /**
     * Gets the {@link Method} {@code object} which reflects the member {@code method} (of
     * the {@link Class} of type {@link T}) with the specified {@code name} with the
     * specified parameters {@link Class} types.
     *
     * <p>This allows the calling methods to access {@code method}s which are usually
     * inaccessible, such as allowing access to a {@code private} member {@code method}
     * from outside the {@link Class}.</p>
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws NoSuchMethodException If a {@code method} with the specified {@code name}
     *                               and {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    Method getMethodAccessible(String name, Class<?>... parameterTypes) throws NoSuchMethodException;

    /**
     * Gets the {@link Method} {@code object} which reflects the member {@code method} (of
     * the {@link Class} of type {@link T}) with the specified {@code name} with the
     * specified parameters {@link Class} types.
     *
     * <p>This allows the calling methods to access {@code method}s which are usually
     * inaccessible, such as allowing access to a {@code private} member {@code method}
     * from outside the {@link Class}.</p>
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @return The {@link Method} {@code object} reflecting the member {@code method}.
     * @throws NoSuchMethodException If a {@code method} with the specified {@code name}
     *                               and {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    Method getMethodAccessible(String name, List<Class<?>> parameterTypes) throws NoSuchMethodException;

    /**
     * Invokes the {@link Method} {@code object} obtained from the specified {@code name}
     * and {@code parameter} {@link Object}s in the {@link Class} (and {@code object} if
     * {@code non-static}) currently being reflected on.
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
    <V> V invoke(String name, Object... parameters) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    /**
     * Invokes the {@link Method} {@code object} obtained from the specified {@code name}
     * and {@code parameter} {@link Object}s in the {@link Class} (and {@code object} if
     * {@code non-static}) currently being reflected on.
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
    <V> V invoke(String name, List<Object> parameters) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    /**
     * Invokes the {@link Method} {@code object} obtained from the specified {@code name}
     * with the specified {@code parameter} {@link Object}s in the {@link Class}
     * (and {@code object} if {@code non-static}) currently being reflected on.
     *
     * <p>This method will use the specified {@code parameterTypes} rather than assuming them
     * from calling {@link Object#getClass()} on the specified {@code parameter}s.</p>
     *
     * @param name The {@link String} name of the {@link Method}.
     * @param parameterTypes The {@link Class} types the member {@code method} takes.
     * @param parameters The parameters to pass on invocation of the specified {@code method}.
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
    <V> V invoke(String name, List<Class<?>> parameterTypes, List<Object> parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    /**
     * Gets the {@link Constructor} {@code object} which reflects the member
     * {@code constructor} (of the {@link Class} of type {@link T}) with the specified the
     * specified parameters {@link Class} types.
     *
     * <p>If the constructor is inaccessible from the calling method, certain operations on
     * the returned {@link Constructor} {@code object} may throw {@link IllegalAccessException}.
     * In this case, {@link #getConstructorAccessible(Class...)} can be used to bypass this
     * (unless a {@link SecurityManager} prevents it).</p>
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
     * @return The {@link Constructor} {@code object} reflecting the member {@code constructor}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    Constructor<T> getConstructor(final Class<?>... parameterTypes) throws NoSuchMethodException;

    /**
     * Gets the {@link Constructor} {@code object} which reflects the member
     * {@code constructor} (of the {@link Class} of type {@link T}) with the specified the
     * specified parameters {@link Class} types.
     *
     * <p>If the constructor is inaccessible from the calling method, certain operations on
     * the returned {@link Constructor} {@code object} may throw {@link IllegalAccessException}.
     * In this case, {@link #getConstructorAccessible(Class...)} can be used to bypass this
     * (unless a {@link SecurityManager} prevents it).</p>
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
     * @return The {@link Constructor} {@code object} reflecting the member {@code constructor}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    Constructor<T> getConstructor(final List<Class<?>> parameterTypes) throws NoSuchMethodException;

    /**
     * Gets the {@link Constructor} {@code object} which reflects the member
     * {@code constructor} (of the {@link Class} of type {@link T}) with the specified the
     * specified parameters {@link Class} types.
     *
     * <p>This allows the calling methods to access {@code constructors}s which are usually
     * inaccessible, such as allowing access to a {@code private} member {@code constructor}
     * from outside the {@link Class}.</p>
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
     * @return The {@link Constructor} {@code object} reflecting the member {@code constructor}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    Constructor<T> getConstructorAccessible(final Class<?>... parameterTypes) throws NoSuchMethodException;

    /**
     * Gets the {@link Constructor} {@code object} which reflects the member
     * {@code constructor} (of the {@link Class} of type {@link T}) with the specified the
     * specified parameters {@link Class} types.
     *
     * <p>This allows the calling methods to access {@code constructors}s which are usually
     * inaccessible, such as allowing access to a {@code private} member {@code constructor}
     * from outside the {@link Class}.</p>
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
     * @return The {@link Constructor} {@code object} reflecting the member {@code constructor}.
     * @throws NoSuchMethodException If a {@code constructor} with the specified
     *                               {@code parameter} types does not exist in the
     *                               {@link Class} currently being reflected on.
     */
    Constructor<T> getConstructorAccessible(final List<Class<?>> parameterTypes) throws NoSuchMethodException;

    /**
     * Instantiates a new instance of the {@link Class} (or {@link Object}) currently being
     * reflected on using the {@link Constructor} {@code object} obtained from the specified
     * {@code parameter} {@link Object}s, calling it with these parameters.
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
    T instantiate(Object... parameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Instantiates a new instance of the {@link Class} (or {@link Object}) currently being
     * reflected on using the {@link Constructor} {@code object} obtained from the specified
     * {@code parameter} {@link Object}s, calling it with these parameters.
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
    T instantiate(List<Object> parameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Instantiates a new instance of the {@link Class} (or {@link Object}) currently being
     * reflected on using the {@link Constructor} {@code object} obtained from the specified
     * {@code parameter} {@link Class} types, calling it with the specified parameters
     * {@link Object}s.
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
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
    T instantiate(List<Class<?>> parameterTypes, List<Object> parameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Instantiates a new instance of the {@link Class} (or {@link Object}) currently being
     * reflected on using the {@link Constructor} {@code object} obtained from the specified
     * {@code parameter} {@link Object}s, calling it with these parameters.
     *
     * <p>This modification of {@link #instantiate(Object...)} infers the {@link Class} type.
     * This is mainly for when creating inner classes in which the actual type is not known.</p>
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
    <V> V instantiateInferred(Object... parameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Instantiates a new instance of the {@link Class} (or {@link Object}) currently being
     * reflected on using the {@link Constructor} {@code object} obtained from the specified
     * {@code parameter} {@link Object}s, calling it with these parameters.
     *
     * <p>This modification of {@link #instantiate(List)} infers the {@link Class} type.
     * This is mainly for when creating inner classes in which the actual type is not known.</p>
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
    <V> V instantiateInferred(List<Object> parameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Instantiates a new instance of the {@link Class} (or {@link Object}) currently being
     * reflected on using the {@link Constructor} {@code object} obtained from the specified
     * {@code parameter} {@link Class} types, calling it with the specified parameters
     * {@link Object}s.
     *
     * <p>This modification of {@link #instantiate(List)} infers the {@link Class} type.
     * This is mainly for when creating inner classes in which the actual type is not known.</p>
     *
     * @param parameterTypes The {@link Class} types the member {@code constructor} takes.
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
    <V> V instantiateInferred(List<Class<?>> parameterTypes, List<Object> parameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Gets the {@link Class} {@code object} which reflects the member {@code inner class}
     * (of the {@link Class} of type {@link T}) with the specified {@code name}.
     *
     * @param name The {@link String} name of the {@code inner class}.
     * @return The {@link Class} {@code object} reflecting the member {@code inner class}.
     * @throws NoSuchInnerClassException If an {@code inner class} with the specified
     *                                   {@code name} does not exist in the {@link Class}
     *                                   currently being reflected on.
     */
    Class<?> getInnerClass(String name) throws NoSuchInnerClassException;

    /**
     * Reflects on the {@link Class} {@code object} which reflects the member
     * {@code inner class} (of the {@link Class} of type {@link T}) with the specified
     * {@code name}.
     *
     * @param name The {@link String} name of the {@code inner class}.
     * @param <I> The type of the {@code inner class} being reflected on.
     * @return The {@link Class} {@code object} reflecting the member {@code inner class}.
     * @throws NoSuchInnerClassException If an {@code inner class} with the specified
     *                                   {@code name} does not exist in the {@link Class}
     *                                   currently being reflected on.
     */
    <I> Reflect<I> reflectInnerClass(String name) throws NoSuchInnerClassException;

    /**
     * Tries to give access to the specified {@link AccessibleObject} using
     * {@link AccessibleObject#setAccessible(boolean)}. Ensure a {@link SecurityManager}
     * is not in place before calling this or methods using this.
     *
     * @param accessibleObject The {@link AccessibleObject} to set as accessible.
     * @param <A> The type of the specified {@link AccessibleObject}.
     * @return The specified {@link AccessibleObject}, for in-line calls.
     */
    static <A extends AccessibleObject> A setAccessible(final A accessibleObject) {
        accessibleObject.setAccessible(true);
        return accessibleObject;
    }

    /**
     * Begins checked reflection on the specified {@link Class} object, returning a new
     * instance of {@link ReflectChecked}.
     *
     * @param tClass The {@link Class} object to reflect on.
     * @param <T> The type of the {@link Class} being reflected on.
     * @return The {@link ReflectChecked} instance created.
     */
    static <T> ReflectChecked<T> on(final Class<T> tClass) {
        return new ReflectChecked<>(tClass);
    }

    /**
     * Begins checked reflection on the specified {@link Object}, returning a new
     * instance of {@link ReflectChecked}.
     *
     * @param object The {@link Object} to reflect on.
     * @param <T> The type of the {@link Object} being reflected on.
     * @return The {@link ReflectChecked} instance created.
     */
    static <T> ReflectChecked<T> on(final T object) {
        return new ReflectChecked<>(object);
    }

    /**
     * Begins unchecked reflection on the specified {@link Class} object, returning a
     * new instance of {@link ReflectUnchecked}.
     *
     * @param tClass The {@link Class} object to reflect on.
     * @param <T> The type of the {@link Class} being reflected on.
     * @return The {@link ReflectUnchecked} instance created.
     */
    static <T> ReflectUnchecked<T> uncheckedOn(final Class<T> tClass) {
        return new ReflectChecked<>(tClass).unchecked();
    }

    /**
     * Begins unchecked reflection on the specified {@link Object}, returning a new
     * instance of {@link ReflectUnchecked}.
     *
     * @param object The {@link Object} to reflect on.
     * @param <T> The type of the {@link Object} being reflected on.
     * @return The {@link ReflectUnchecked} instance created.
     */
    static <T> ReflectUnchecked<T> uncheckedOn(final T object) {
        return new ReflectChecked<>(object).unchecked();
    }

}
