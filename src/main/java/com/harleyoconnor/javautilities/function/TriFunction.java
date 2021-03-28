package com.harleyoconnor.javautilities.function;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Represents a function that accepts three arguments and produces a result.
 * This is the three-arity specialization of {@link Function}.
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is
 * {@link #apply(Object, Object, Object)}.</p>
 *
 * @param <T> The type of the first argument to the function.
 * @param <U> The type of the second argument to the function.
 * @param <S> The type of the third argument to the function.
 * @param <R> The type of the result of the function.
 *
 * @author Harley O'Connor
 * @see Function
 * @see BiFunction
 * @since JavaUtilities 0.0.9
 */
@FunctionalInterface
public interface TriFunction<T, U, S, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t The first function argument.
     * @param u The second function argument.
     * @param s The third function argument.
     * @return The function result.
     */
    R apply(T t, U u, S s);

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
     * @throws NullPointerException If after is null.
     */
    default <V> TriFunction<T, U, S, V> andThen(@Nonnull Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, U u, S s) -> after.apply(this.apply(t, u, s));
    }

}
