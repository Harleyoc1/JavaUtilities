package com.harleyoconnor.javautilities.function;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Mirrors the {@link java.util.function.BiFunction}, except that {@link #apply(Object, Object)} can throw a {@link
 * Throwable} of type {@link T}.
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is {@link #apply(Object, Object)}.
 *
 * @param <U> the type of the first argument to the function
 * @param <S> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @param <T> the type that the function {@code throws}
 * @author Harley O'Connor
 * @see java.util.function.BiFunction
 * @see ThrowableFunction
 * @since JavaUtilities 0.0.9
 */
@FunctionalInterface
public interface ThrowableBiFunction<U, S, R, T extends Throwable> {

    /**
     * Applies this function to the given arguments.
     *
     * @param u the first function argument
     * @param s the second function argument
     * @return the function result
     * @throws T if the function application throws
     */
    R apply(U u, S s) throws T;

    /**
     * Returns a composed function that first applies this function to its input, and then applies the {@code after}
     * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     *
     * @param <V>   the type of output of the {@code after} function, and of the composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then applies the {@code after} function
     * @throws NullPointerException if {@code after} is {@code null}
     */
    default <V> ThrowableBiFunction<U, S, V, T> andThen(@NotNull ThrowableFunction<? super R, ? extends V, T> after) {
        Objects.requireNonNull(after);
        return (U u, S s) -> after.apply(apply(u, s));
    }

    /**
     * Creates a new throwable bi-function that acts as a "proxy" to the specified {@code function} (calling {@link
     * BiFunction#apply(Object, Object)} when {@link #apply(Object, Object)} is called on it). It can throw a {@link
     * Throwable} of inferred type {@link E}.
     *
     * @param function the function to act as proxy to
     * @param <T>      the type of the first argument to the function
     * @param <U>      the type of the second argument to the function
     * @param <R>      the type of the result of the function
     * @param <E>      the type that the function {@code throws}
     * @return a new throwable bi-function
     */
    static <T, U, R, E extends Throwable> ThrowableBiFunction<T, U, R, E> proxy(
            @NotNull final BiFunction<T, U, R> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

}