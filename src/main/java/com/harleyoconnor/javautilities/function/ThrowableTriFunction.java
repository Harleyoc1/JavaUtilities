package com.harleyoconnor.javautilities.function;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Mirrors the {@link TriFunction}, except that {@link #apply(Object, Object, Object)} can throw a {@link Throwable} of
 * type {@link T}.
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is {@link #apply(Object, Object, Object)}.
 *
 * @param <U> the type of the first argument to the function
 * @param <S> the type of the second argument to the function
 * @param <Q> the type of the third argument to the function
 * @param <R> the type of the result of the function
 * @param <T> the type that the function {@code throws}
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
     * @param u the first function argument
     * @param s the second function argument
     * @param q the third function argument
     * @return the function result
     * @throws T if the function application throws
     */
    R apply(U u, S s, Q q) throws T;

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
    default <V> ThrowableTriFunction<U, S, Q, V, T> andThen(
            @NotNull ThrowableFunction<? super R, ? extends V, T> after) {
        Objects.requireNonNull(after);
        return (U u, S s, Q q) -> after.apply(this.apply(u, s, q));
    }

    /**
     * Creates a throwable tri-function that acts as a "proxy" to the specified {@code function} (calling {@link
     * TriFunction#apply(Object, Object, Object)} when {@link #apply(Object, Object, Object)} is called on it). It can
     * throw a {@link Throwable} of inferred type {@link T}.
     *
     * @param function the function to act as proxy to
     * @param <U>      the type of the first argument to the function
     * @param <S>      the type of the second argument to the function
     * @param <Q>      the type of the third argument to the function
     * @param <R>      the type of the result of the function
     * @param <T>      the type that the function {@code throws}
     * @return a new throwable tri-function
     */
    static <U, S, Q, R, T extends Throwable> ThrowableTriFunction<U, S, Q, R, T> proxy(
            @NotNull final TriFunction<U, S, Q, R> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

}