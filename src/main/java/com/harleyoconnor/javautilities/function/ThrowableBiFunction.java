package com.harleyoconnor.javautilities.function;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Mirrors the {@link java.util.function.BiFunction}, except that {@link #apply(Object, Object)} can throw a {@link
 * Throwable} of type {@link T}.
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is
 * {@link #apply(Object, Object)}.
 *
 * @param <U> The type of the first argument to the function.
 * @param <S> The type of the second argument to the function.
 * @param <R> The type of the result of the function.
 * @param <T> The type that the function {@code throws}.
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
     * @param u The first function argument.
     * @param s The second function argument.
     * @return The function result.
     * @throws T The function {@link Throwable}.
     */
    R apply(U u, S s) throws T;

    /**
     * Returns a composed function that first applies this function to its input, and then applies the {@code after}
     * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     *
     * @param <V>   The type of output of the {@code after} function, and of the composed function.
     * @param after The function to apply after this function is applied.
     * @return A composed function that first applies this function and then applies the {@code after} function.
     * @throws NullPointerException If {@code after} is {@code null}.
     */
    default <V> ThrowableBiFunction<U, S, V, T> andThen(@NotNull ThrowableFunction<? super R, ? extends V, T> after) {
        Objects.requireNonNull(after);
        return (U u, S s) -> after.apply(apply(u, s));
    }

    /**
     * Creates a {@link ThrowableBiFunction} that acts as a "proxy" to the given {@link BiFunction} (calling {@link
     * BiFunction#apply(Object, Object)} when {@link #apply(Object, Object)} is called on it). It can throw a {@link
     * Throwable} of inferred type {@link E}.
     *
     * @param function The {@link Function} to act as proxy to.
     * @param <T>      The type of the first argument to the function.
     * @param <U>      The type of the second argument to the function.
     * @param <R>      The type of the result of the function.
     * @param <E>      The type that the function {@code throws}.
     * @return A new {@link ThrowableFunction}.
     */
    static <T, U, R, E extends Throwable> ThrowableBiFunction<T, U, R, E> proxy(
            @NotNull final BiFunction<T, U, R> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

}