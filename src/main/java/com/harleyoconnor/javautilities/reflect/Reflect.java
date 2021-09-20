package com.harleyoconnor.javautilities.reflect;

import com.harleyoconnor.javautilities.collection.WeakHashSet;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Implementations will handle reflective operations. Effectively a utility class for effortlessly reflecting an {@link
 * AccessibleObject}.
 * <p>
 * For using the default implementations, call {@link #on(Class)} for reflecting on static members, or {@link
 * #on(Object)} for reflecting on non-static members within the specified {@code object}.
 *
 * @param <T> the type of the class being reflected on
 * @author Harley O'Connor
 * @see ReflectChecked
 * @see ReflectUnchecked
 * @since JavaUtilities 0.1.0
 */
public interface Reflect<T> {

    /**
     * Returns a new {@link ReflectUnchecked} object that delegates to this.
     *
     * @return a new {@link ReflectUnchecked} object that delegates to this
     */
    ReflectUnchecked<T> unchecked();

    /**
     * Returns the {@link Field} reflecting the member field with the specified {@code name} in the class (or object if
     * non-static) being reflected on.
     * <p>
     * If the field is inaccessible from the invoker, certain operations on the returned {@link Field} throw {@link
     * IllegalAccessException}. In this case, {@link #getFieldAccessible(String)} can be used to bypass this (unless a
     * {@link SecurityManager} prevents it).
     *
     * @param name the name of the member field
     * @return the {@link Field} object reflecting the member field
     * @throws NoSuchFieldException if a field with the specified {@code name} does not exist in the class being
     *                              reflected on
     */
    Field getField(String name) throws NoSuchFieldException;

    /**
     * Returns the {@link Field} reflecting the member field with the specified {@code name} in the class (or object if
     * non-static) being reflected on. The returned {@link Field} is also made accessible via {@link
     * Field#setAccessible(boolean)}.
     * <p>
     * This allows access to a field which is usually inaccessible, such as allowing access to a private member field
     * from outside the parent class.
     *
     * @param name the name of the member field
     * @return the {@link Field} reflecting the member field
     * @throws NoSuchFieldException if a field with the specified {@code name} does not exist in the class being
     *                              reflected on
     */
    Field getFieldAccessible(String name) throws NoSuchFieldException;

    /**
     * Returns the value of the specified {@code field}'s reflected member field in the class (or object if non-static)
     * being reflected on.
     *
     * @param field the {@link Field}; reflects the member field to get the value of
     * @param <V>   the inferred type of the field
     * @return the value of the field
     * @throws IllegalAccessException if the field cannot be accessed from this method
     */
    <V> V getFieldValue(Field field) throws IllegalAccessException;

    /**
     * Returns the value of the field with the specified {@code name} in the class (or object if non-static) being
     * reflected on.
     *
     * @param name the name of the member field to get the value of
     * @param <V>  the inferred type of the field
     * @return the value of the field
     * @throws NoSuchFieldException   if a field with the specified {@code name} does not exist in the class being
     *                                reflected on
     * @throws IllegalAccessException if the field cannot be accessed from this method
     */
    <V> V getFieldValue(String name) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Returns the value of the specified {@code field}'s reflected member field in the class (or object if non-static)
     * being reflected on. The type of the field is cast to the specified {@code type}, instead of inferring the type as
     * is the case in {@link #getFieldValue(Field)}.
     *
     * @param field the {@link Field}; reflects the member field to get the value of
     * @param type  the class type of the field
     * @param <V>   the type of the field
     * @return the value of the field
     * @throws IllegalAccessException if the field cannot be accessed from this method
     */
    <V> V getFieldValue(Field field, Class<V> type) throws IllegalAccessException;

    /**
     * Returns the value of the field with the specified {@code name} in the class (or object if non-static) being
     * reflected on. The type of the field is cast to the specified {@code type}, instead of inferring the type as is
     * the case in {@link #getFieldValue(String)}.
     *
     * @param name the name of the member field to get the value of
     * @param type the class type of the field
     * @param <V>  the type of the field
     * @return the value of the field
     * @throws NoSuchFieldException   if a field with the specified {@code name} does not exist in the class being
     *                                reflected on
     * @throws IllegalAccessException if the field cannot be accessed from this method
     */
    <V> V getFieldValue(String name, Class<V> type) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Returns the {@link Method} reflecting the member method with the specified {@code name} and {@code
     * parameterTypes} in the class (or object if non-static) being reflected on.
     * <p>
     * If the method is inaccessible from the invoker, certain operations on the returned {@link Method} throw {@link
     * IllegalAccessException}. In this case, {@link #getMethodAccessible(String, Class[])} can be used to bypass this
     * (unless a {@link SecurityManager} prevents it).
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order in which they are
     *                       declared by the method.
     * @return the {@link Method} reflecting the member method
     * @throws NoSuchMethodException if a method with the specified {@code name} and {@code parameterTypes} does not
     *                               exist in the class being reflected on
     */
    Method getMethod(String name, Class<?>... parameterTypes) throws NoSuchMethodException;

    /**
     * Returns the {@link Method} reflecting the member method with the specified {@code name} and {@code
     * parameterTypes} in the class (or object if non-static) being reflected on.
     * <p>
     * If the method is inaccessible from the invoker, certain operations on the returned {@link Method} throw {@link
     * IllegalAccessException}. In this case, {@link #getMethodAccessible(String, Class[])} can be used to bypass this
     * (unless a {@link SecurityManager} prevents it).
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order in which they are
     *                       declared by the method.
     * @return the {@link Method} reflecting the member method
     * @throws NoSuchMethodException if a method with the specified {@code name} and {@code parameterTypes} does not
     *                               exist in the class being reflected on
     */
    Method getMethod(String name, List<Class<?>> parameterTypes) throws NoSuchMethodException;

    /**
     * Returns the {@link Method} reflecting the member method with the specified {@code name} in the class (or object
     * if non-static) being reflected on. The returned {@link Method} is also made accessible via {@link
     * Method#setAccessible(boolean)}.
     * <p>
     * This allows access to a method which is usually inaccessible, such as allowing access to a private member method
     * from outside the parent class.
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order in which they are
     *                       declared by the method.
     * @return the {@link Method} reflecting the member method
     * @throws NoSuchMethodException if a method with the specified {@code name} and {@code parameterTypes} does not
     *                               exist in the class being reflected on
     */
    Method getMethodAccessible(String name, Class<?>... parameterTypes) throws NoSuchMethodException;

    /**
     * Returns the {@link Method} reflecting the member method with the specified {@code name} in the class (or object
     * if non-static) being reflected on. The returned {@link Method} is also made accessible via {@link
     * Method#setAccessible(boolean)}.
     * <p>
     * This allows access to a method which is usually inaccessible, such as allowing access to a private member method
     * from outside the parent class.
     *
     * @param name           the name of the member method
     * @param parameterTypes the parameter types the member method takes. Must be in the same order in which they are
     *                       declared by the method.
     * @return the {@link Method} reflecting the member method
     * @throws NoSuchMethodException if a method with the specified {@code name} and {@code parameterTypes} does not
     *                               exist in the class being reflected on
     */
    Method getMethodAccessible(String name, List<Class<?>> parameterTypes) throws NoSuchMethodException;

    /**
     * Invokes the member method with the specified {@code name} and parameter types corresponding to the specified
     * {@code arguments} in the class (or object if non-static) being reflected on.
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
    <V> V invoke(String name, Object... arguments)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    /**
     * Invokes the member method with the specified {@code name} and parameter types corresponding to the specified
     * {@code arguments} in the class (or object if non-static) being reflected on.
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
    <V> V invoke(String name, List<Object> arguments)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    /**
     * Invokes the member method with the specified {@code name} and {@code parameterTypes} in the class (or object if
     * non-static) being reflected on.
     * <p>
     * This method will use the specified {@code parameterTypes} rather than obtaining them from calling {@link
     * Object#getClass()} on the specified {@code arguments}.
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
    <V> V invoke(String name, List<Class<?>> parameterTypes, List<Object> arguments)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    /**
     * Returns the {@link Constructor} reflecting the member constructor with the specified and {@code parameterTypes}
     * in the class (or object if non-static) being reflected on.
     * <p>
     * If the constructor is inaccessible from the invoker, certain operations on the returned {@link Constructor} throw
     * {@link IllegalAccessException}. In this case, {@link #getConstructorAccessible(Class[])} can be used to bypass
     * this (unless a {@link SecurityManager} prevents it).
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the constructor.
     * @return the {@link Constructor} reflecting the member constructor
     * @throws NoSuchMethodException if a constructor with the specified {@code parameterTypes} does not exist in the
     *                               class being reflected on
     */
    Constructor<T> getConstructor(final Class<?>... parameterTypes) throws NoSuchMethodException;

    /**
     * Returns the {@link Constructor} reflecting the member constructor with the specified and {@code parameterTypes}
     * in the class (or object if non-static) being reflected on.
     * <p>
     * If the constructor is inaccessible from the invoker, certain operations on the returned {@link Constructor} throw
     * {@link IllegalAccessException}. In this case, {@link #getConstructorAccessible(Class[])} can be used to bypass
     * this (unless a {@link SecurityManager} prevents it).
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the constructor.
     * @return the {@link Constructor} reflecting the member constructor
     * @throws NoSuchMethodException if a constructor with the specified {@code parameterTypes} does not exist in the
     *                               class being reflected on
     */
    Constructor<T> getConstructor(final List<Class<?>> parameterTypes) throws NoSuchMethodException;

    /**
     * Returns the {@link Constructor} reflecting the member constructor with the specified {@code name} in the class
     * (or object if non-static) being reflected on. The returned {@link Constructor} is also made accessible via {@link
     * Constructor#setAccessible(boolean)}.
     * <p>
     * This allows access to a constructor which is usually inaccessible, such as allowing access to a private member
     * constructor from outside the parent class.
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the constructor.
     * @return the {@link Constructor} reflecting the member constructor
     * @throws NoSuchMethodException if a constructor with the specified {@code parameterTypes} does not exist in the
     *                               class being reflected on
     */
    Constructor<T> getConstructorAccessible(final Class<?>... parameterTypes) throws NoSuchMethodException;

    /**
     * Returns the {@link Constructor} reflecting the member constructor with the specified {@code name} in the class
     * (or object if non-static) being reflected on. The returned {@link Constructor} is also made accessible via {@link
     * Constructor#setAccessible(boolean)}.
     * <p>
     * This allows access to a constructor which is usually inaccessible, such as allowing access to a private member
     * constructor from outside the parent class.
     *
     * @param parameterTypes the parameter types the member constructor takes. Must be in the same order in which they
     *                       are declared by the constructor.
     * @return the {@link Constructor} reflecting the member constructor
     * @throws NoSuchMethodException if a constructor with the specified {@code parameterTypes} does not exist in the
     *                               class being reflected on
     */
    Constructor<T> getConstructorAccessible(final List<Class<?>> parameterTypes) throws NoSuchMethodException;

    /**
     * Instantiates the class being reflected on using the constructor with the parameter types corresponding to the
     * specified {@code arguments}.
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
    T instantiate(Object... arguments)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Instantiates the class being reflected on using the constructor with the parameter types corresponding to the
     * specified {@code arguments}.
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
    T instantiate(List<Object> arguments)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Instantiates the class being reflected on using the constructor with the specified {@code parameterTypes}.
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
    T instantiate(List<Class<?>> parameterTypes, List<Object> arguments)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Instantiates the class being reflected on using the constructor with the parameter types corresponding to the
     * specified {@code arguments}.
     * <p>
     * This alternate {@link #instantiate(Object...)} method infers the returned type. This is mainly for when creating
     * inner classes in which the actual type is not known, but a super-type is.
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
    <V> V instantiateInferred(Object... arguments)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Instantiates the class being reflected on using the constructor with the parameter types corresponding to the
     * specified {@code arguments}.
     * <p>
     * This alternate {@link #instantiate(List)} method infers the returned type. This is mainly for when creating inner
     * classes in which the actual type is not known, but a super-type is.
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
    <V> V instantiateInferred(List<Object> arguments)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Instantiates the class being reflected on using the constructor with the specified {@code parameterTypes}.
     * <p>
     * This alternate {@link #instantiate(List, List)} method infers the returned type. This is mainly for when creating
     * inner classes in which the actual type is not known, but a super-type is.
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
    <V> V instantiateInferred(List<Class<?>> parameterTypes, List<Object> arguments)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * Returns the {@link Class} reflecting the inner class with the specified {@code name} in the class being reflected
     * on.
     *
     * @param name the simple name of the inner class
     * @return the {@link Class} reflecting the inner class with the specified {@code name}
     * @throws NoSuchInnerClassException if an inner class with the specified {@code name} does not exist in the class
     *                                   being reflected on
     */
    Class<?> getInnerClass(String name) throws NoSuchInnerClassException;

    /**
     * Returns a {@link Reflect} for the inner class with the specified {@code name} in the class being reflected on.
     *
     * @param name the simple name of the inner class
     * @param <I>  the inferred type of the inner class to reflect
     * @return a {@link Reflect} for the inner class with the specified {@code name}
     * @throws NoSuchInnerClassException if an inner class with the specified {@code name} does not exist in the class
     *                                   being reflected on
     */
    <I> Reflect<I> reflectInnerClass(String name) throws NoSuchInnerClassException;

    /**
     * Attempts to give access to the specified {@code accessibleObject} using {@link
     * AccessibleObject#setAccessible(boolean)}.
     *
     * @param accessibleObject the object to set as accessible
     * @param <A>              the type of the specified {@code accessibleObject}
     * @return the specified {@code accessibleObject}
     * @throws InaccessibleObjectException if access cannot be enabled
     * @throws SecurityException           if the request is denied by the security manager
     */
    static <A extends AccessibleObject> A setAccessible(final A accessibleObject) {
        accessibleObject.setAccessible(true);
        return accessibleObject;
    }

    /**
     * Begins checked reflection on the specified {@code clazz}, returning a new instance of {@link ReflectChecked}.
     *
     * @param clazz the {@link Class} reflecting the class to reflect on
     * @param <T>   the type of the class to reflect on
     * @return a new {@link ReflectChecked} for the specified {@code clazz}
     */
    static <T> ReflectChecked<T> on(final Class<T> clazz) {
        return new ReflectChecked<>(clazz);
    }

    /**
     * Begins checked reflection on the specified {@code object}, returning a new instance of {@link ReflectChecked}.
     *
     * @param object the object to reflect on
     * @param <T>    the type of the class to reflect on
     * @return a new {@link ReflectChecked} for the specified {@code object}
     */
    static <T> ReflectChecked<T> on(final T object) {
        return new ReflectChecked<>(object);
    }

    /**
     * Begins unchecked reflection on the specified {@code clazz}, returning a new instance of {@link
     * ReflectUnchecked}.
     *
     * @param clazz the {@link Class} reflecting the class to reflect on
     * @param <T>   the type of the class to reflect on
     * @return a new {@link ReflectChecked} for the specified {@code clazz}
     * @deprecated use {@link #on(Class)} and invoke {@link #unchecked()}
     */
    @Deprecated(forRemoval = true, since = "0.1.3")
    static <T> ReflectUnchecked<T> uncheckedOn(final Class<T> clazz) {
        return on(clazz).unchecked();
    }

    /**
     * Begins unchecked reflection on the specified {@code object}, returning a new instance of {@link
     * ReflectUnchecked}.
     *
     * @param object the object to reflect on
     * @param <T>    the type of the class to reflect on
     * @return a new {@link ReflectUnchecked} for the specified {@code object}
     * @deprecated use {@link #on(Object)} and invoke {@link #unchecked()}
     */
    @Deprecated(forRemoval = true, since = "0.1.3")
    static <T> ReflectUnchecked<T> uncheckedOn(final T object) {
        return on(object).unchecked();
    }

}
