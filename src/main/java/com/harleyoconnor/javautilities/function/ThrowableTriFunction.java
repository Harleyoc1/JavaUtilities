package com.harleyoconnor.javautilities.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Mirrors the {@link TriFunction}, except that {@link #apply(Object, Object, Object)}
 * can throw a {@link Throwable} of type {@link E}.
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is
 * {@link #apply(Object, Object, Object)}.
 *
 * @param <T> The type of the first argument to the function.
 * @param <U> The type of the second argument to the function.
 * @param <S> The type of the third argument to the function.
 * @param <R> The type of the result of the function.
 * @param <E> The type that the function {@code throws}.
 *
 * @author Harley O'Connor
 * @see BiFunction
 * @see ThrowableFunction
 * @since JavaUtilities 0.0.9
 */
@FunctionalInterface
public interface ThrowableTriFunction<T, U, S, R, E extends Throwable> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t The first function argument.
     * @param u The second function argument.
     * @param s The third function argument.
     * @return The function result.
     * @throws E The function {@link Throwable}.
     */
    R apply(T t, U u, S s) throws E;

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> The type of output of the {@code after} function, and of the
     *           composed function.
     * @param after The function to apply after this function is applied.
     * @return A composed function that first applies this function and then
     *         applies the {@code after} function.
     * @throws NullPointerException If {@code after} is {@code null}.
     */
    default <V> ThrowableTriFunction<T, U, S, V, E> andThen(ThrowableFunction<? super R, ? extends V, E> after) {
        Objects.requireNonNull(after);
        return (T t, U u, S s) -> after.apply(this.apply(t, u, s));
    }

    /**
     * Creates a {@link ThrowableTriFunction} that acts as a "proxy" to the given
     * {@link TriFunction} (calling {@link TriFunction#apply(Object, Object, Object)}
     * when {@link #apply(Object, Object, Object)} is called on it). It can throw a
     * {@link Throwable} of inferred type {@link E}.
     *
     * @param function The {@link Function} to act as proxy to.
     * @param <T> The type of the first argument to the function.
     * @param <U> The type of the second argument to the function.
     * @param <S> The type of the third argument to the function.
     * @param <R> The type of the result of the function.
     * @param <E> The type that the function {@code throws}.
     * @return A new {@link ThrowableFunction}.
     */
    static <T, U, S, R, E extends Throwable> ThrowableTriFunction<T, U, S, R, E> proxy(final TriFunction<T, U, S, R> function) {
        return function::apply;
    }

}