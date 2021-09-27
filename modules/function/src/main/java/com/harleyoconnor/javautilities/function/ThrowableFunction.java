package com.harleyoconnor.javautilities.function;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * Mirrors the {@link java.util.function.Function}, except that {@link #apply(Object)} can throw a {@link Throwable} of
 * type {@link T}.
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is {@link #apply(Object)}.
 *
 * @param <U> the type of the input to the function
 * @param <R> the type of the result of the function
 * @param <T> the type that the function {@code throws}
 * @author Harley O'Connor
 * @see java.util.function.Function
 * @since JavaUtilities 0.0.9
 */
@FunctionalInterface
public interface ThrowableFunction<U, R, T extends Throwable> {

    /**
     * Applies this function to the given argument, or throws {@link T}.
     *
     * @param u the function argument
     * @return the function result
     * @throws T if the function application throws
     */
    R apply(U u) throws T;

    /**
     * Returns a composed function that first applies the {@code before} function to its input, and then applies this
     * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     *
     * @param <V>    the type of input to the {@code before} function, and to the composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before} function and then applies this function
     * @throws NullPointerException if {@code before} is {@code null}
     * @see #andThen(ThrowableFunction)
     */
    default <V> ThrowableFunction<V, R, T> compose(@NotNull ThrowableFunction<? super V, ? extends U, T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies this function to its input, and then applies the {@code after}
     * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     *
     * @param <V>   the type of output of the {@code after} function, and of the composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then applies the {@code after} function
     * @throws NullPointerException if {@code after} is {@code null}
     * @see #compose(ThrowableFunction)
     */
    default <V> ThrowableFunction<U, V, T> andThen(@NotNull ThrowableFunction<? super R, ? extends V, T> after) {
        Objects.requireNonNull(after);
        return (U u) -> after.apply(apply(u));
    }

    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> the type of the input and output objects to the function
     * @param <E> the type that the function {@code throws}
     * @return a function that always returns its input argument
     */
    static <T, E extends Throwable> ThrowableFunction<T, T, E> identity() {
        return t -> t;
    }

    /**
     * Creates a throwable function that acts as a "proxy" to the specified {@code function} (calling {@link
     * Function#apply(Object)} when {@link #apply(Object)} is called on it). It can throw a {@link Throwable} of
     * inferred type {@link E}.
     *
     * @param function the function to act as proxy to
     * @param <T>      the type of the input to the function
     * @param <R>      the type of the result of the function
     * @param <E>      the type that the function {@code throws}
     * @return a new throwable function
     * @throws NullPointerException if {@code function} is {@code null}
     */
    static <T, R, E extends Throwable> ThrowableFunction<T, R, E> proxy(@NotNull final Function<T, R> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

}
