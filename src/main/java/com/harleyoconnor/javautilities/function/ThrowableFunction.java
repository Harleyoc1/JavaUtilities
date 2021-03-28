package com.harleyoconnor.javautilities.function;


import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Function;

/**
 * Mirrors the {@link java.util.function.Function}, except that {@link #apply(Object)}
 * can throw a {@link Throwable} of type {@link E}.
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is
 * {@link #apply(Object)}.
 *
 * @param <T> The type of the input to the function.
 * @param <R> The type of the result of the function.
 * @param <E> The type that the function {@code throws}.
 *
 * @author Harley O'Connor
 * @see java.util.function.Function
 * @since JavaUtilities 0.0.9
 */
@FunctionalInterface
public interface ThrowableFunction<T, R, E extends Throwable> {

    /**
     * Applies this function to the given argument, or throws {@link E}.
     *
     * @param t The function argument.
     * @return The function result.
     * @throws E The function {@link Throwable}.
     */
    R apply(T t) throws E;

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> The type of input to the {@code before} function, and to the
     *           composed function.
     * @param before The function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     *         function and then applies this function.
     * @throws NullPointerException If {@code before} is {@code null}.
     *
     * @see #andThen(ThrowableFunction)
     */
    default <V> ThrowableFunction<V, R, E> compose(@Nonnull ThrowableFunction<? super V, ? extends T, E> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> The type of output of the {@code after} function, and of the
     *           composed function.
     * @param after The function to apply after this function is applied
     * @return a composed function that first applies this function and then
     *         applies the {@code after} function.
     * @throws NullPointerException If {@code after} is {@code null}.
     *
     * @see #compose(ThrowableFunction)
     */
    default <V> ThrowableFunction<T, V, E> andThen(@Nonnull ThrowableFunction<? super R, ? extends V, E> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> The type of the input and output objects to the function.
     * @param <E> The type that the function {@code throws}.
     * @return A function that always returns its input argument.
     */
    static <T, E extends Throwable> ThrowableFunction<T, T, E> identity() {
        return t -> t;
    }

    /**
     * Creates a {@link ThrowableFunction} that acts as a "proxy" to the given
     * {@link Function} (calling {@link Function#apply(Object)} when
     * {@link #apply(Object)} is called on it). It can throw a {@link Throwable}
     * of inferred type {@link E}.
     *
     * @param function The {@link Function} to act as proxy to.
     * @param <T> The type of the input to the function.
     * @param <R> The type of the result of the function.
     * @param <E> The type that the function {@code throws}.
     * @return A new {@link ThrowableFunction}.
     * @throws NullPointerException If {@code function} is {@code null}.
     */
    static <T, R, E extends Throwable> ThrowableFunction<T, R, E> proxy(@Nonnull final Function<T, R> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

}
