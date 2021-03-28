package com.harleyoconnor.javautilities.function;


import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Mirrors the {@link java.util.function.BiFunction}, except that
 * {@link #apply(Object, Object)} can throw a {@link Throwable} of type
 * {@link E}.
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is
 * {@link #apply(Object, Object)}.
 *
 * @param <T> The type of the first argument to the function.
 * @param <U> The type of the second argument to the function.
 * @param <R> The type of the result of the function.
 * @param <E> The type that the function {@code throws}.
 *
 * @author Harley O'Connor
 * @see java.util.function.BiFunction
 * @see ThrowableFunction
 * @since JavaUtilities 0.0.9
 */
@FunctionalInterface
public interface ThrowableBiFunction<T, U, R, E extends Throwable> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t The first function argument.
     * @param u The second function argument.
     * @return The function result.
     * @throws E The function {@link Throwable}.
     */
    R apply(T t, U u) throws E;

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
    default <V> ThrowableBiFunction<T, U, V, E> andThen(ThrowableFunction<? super R, ? extends V, E> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }

    /**
     * Creates a {@link ThrowableBiFunction} that acts as a "proxy" to the given
     * {@link java.util.function.BiFunction} (calling {@link BiFunction#apply(Object, Object)}
     * when {@link #apply(Object, Object)} is called on it). It can throw a
     * {@link Throwable} of inferred type {@link E}.
     *
     * @param function The {@link Function} to act as proxy to.
     * @param <T> The type of the first argument to the function.
     * @param <U> The type of the second argument to the function.
     * @param <R> The type of the result of the function.
     * @param <E> The type that the function {@code throws}.
     * @return A new {@link ThrowableFunction}.
     */
    static <T, U, R, E extends Throwable> ThrowableBiFunction<T, U, R, E> proxy(final BiFunction<T, U, R> function) {
        return function::apply;
    }

}