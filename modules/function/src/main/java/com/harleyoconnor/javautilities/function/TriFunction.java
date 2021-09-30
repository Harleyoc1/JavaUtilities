package com.harleyoconnor.javautilities.function;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Represents a function that accepts three arguments and produces a result. This is the three-arity specialization of
 * {@link Function}.
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is {@link #apply(Object, Object, Object)}.</p>
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <S> the type of the third argument to the function
 * @param <R> the type of the result of the function
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
     * @param t the first function argument
     * @param u the second function argument
     * @param s the third function argument
     * @return the function result
     */
    R apply(T t, U u, S s);

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
    default <V> TriFunction<T, U, S, V> andThen(@NotNull Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, U u, S s) -> after.apply(this.apply(t, u, s));
    }

}
