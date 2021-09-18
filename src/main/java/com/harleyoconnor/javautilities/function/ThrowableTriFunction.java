package com.harleyoconnor.javautilities.function;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Mirrors the {@link TriFunction}, except that {@link #apply(Object, Object, Object)} can throw a {@link Throwable} of
 * type {@link T}.
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is
 * {@link #apply(Object, Object, Object)}.
 *
 * @param <U> The type of the first argument to the function.
 * @param <S> The type of the second argument to the function.
 * @param <Q> The type of the third argument to the function.
 * @param <R> The type of the result of the function.
 * @param <T> The type that the function {@code throws}.
 * @author Harley O'Connor
 * @see BiFunction
 * @see ThrowableFunction
 * @since JavaUtilities 0.0.9
 */
@FunctionalInterface
public interface ThrowableTriFunction<U, S, Q, R, T extends Throwable> {

    /**
     * Applies this function to the given arguments.
     *
     * @param u The first function argument.
     * @param s The second function argument.
     * @param q The third function argument.
     * @return The function result.
     * @throws T The function {@link Throwable}.
     */
    R apply(U u, S s, Q q) throws T;

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
    default <V> ThrowableTriFunction<U, S, Q, V, T> andThen(
            @NotNull ThrowableFunction<? super R, ? extends V, T> after) {
        Objects.requireNonNull(after);
        return (U u, S s, Q q) -> after.apply(this.apply(u, s, q));
    }

    /**
     * Creates a {@link ThrowableTriFunction} that acts as a "proxy" to the given {@link TriFunction} (calling {@link
     * TriFunction#apply(Object, Object, Object)} when {@link #apply(Object, Object, Object)} is called on it). It can
     * throw a {@link Throwable} of inferred type {@link T}.
     *
     * @param function The {@link Function} to act as proxy to.
     * @param <U>      The type of the first argument to the function.
     * @param <S>      The type of the second argument to the function.
     * @param <Q>      The type of the third argument to the function.
     * @param <R>      The type of the result of the function.
     * @param <T>      The type that the function {@code throws}.
     * @return A new {@link ThrowableFunction}.
     */
    static <U, S, Q, R, T extends Throwable> ThrowableTriFunction<U, S, Q, R, T> proxy(
            @NotNull final TriFunction<U, S, Q, R> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

}